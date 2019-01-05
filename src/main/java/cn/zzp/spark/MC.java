package cn.zzp.spark;

import java.io.Serializable;
import java.util.*;

public class MC implements Serializable {

	int[] degree; // degree of vertices
	int[][] A; // 0/1 adjacency matrix
	public int n; // n vertices
	long nodes; // number of decisions
	int maxSize; // size of max clique
	public int[] solution; // as it says
	ArrayList<Integer>[] colourClass;

	public MC(int[][] A,int[] degree) {
		this.n=A.length;
		this.A = A;
		this.degree = degree;
		nodes = maxSize = 0;
		solution = new int[n];
	}
	
	public int[] search() {
		
		nodes = 0;
		colourClass = new ArrayList[n];
		ArrayList<Integer> C = new ArrayList<Integer>(n);
		ArrayList<Integer> P = new ArrayList<Integer>(n);
		ArrayList<Integer> ColOrd = new ArrayList<Integer>(n);
		for (int i = 0; i < n; i++)
			colourClass[i] = new ArrayList<Integer>(n);
		orderVertices(ColOrd);
		expand(C, P, ColOrd);
		
		return solution;
	}

	void expand(ArrayList<Integer> C, ArrayList<Integer> P, ArrayList<Integer> ColOrd) {
		nodes++;
		int m = ColOrd.size();
		int[] colour = new int[m];
		numberSort(C, ColOrd, P, colour);
		for (int i = m - 1; i >= 0; i--) {
			int v = P.get(i);
			if (C.size() + colour[i] <= maxSize)
				return;
			C.add(v);
			ArrayList<Integer> newP = new ArrayList<Integer>(i);
			ArrayList<Integer> newColOrd = new ArrayList<Integer>(i);
			for (int j = 0; j <= i; j++) {
				int u = P.get(j);
				if (A[u][v] == 1)
					newP.add(u);
				int w = ColOrd.get(j);
				if (A[v][w] == 1)
					newColOrd.add(w);
			}
			if (newP.isEmpty() && C.size() > maxSize)
				saveSolution(C);
			if (!newP.isEmpty())
				expand(C, newP, newColOrd);
			C.remove(C.size() - 1);
			P.remove(i);
			ColOrd.remove((Integer) v);
		}
	}

	void numberSort(ArrayList<Integer> C, ArrayList<Integer> ColOrd, ArrayList<Integer> P, int[] colour) {
		int delta = maxSize - C.size();
		int colours = 0;
		int m = ColOrd.size();
		for (int i = 0; i < m; i++)
			colourClass[i].clear();
		for (int i = 0; i < m; i++) {
			int v = ColOrd.get(i);
			int k = 0;
			while (conflicts(v, colourClass[k]))
				k++;
			colourClass[k].add(v);
			colours = Math.max(colours, k + 1);
			if (k + 1 > delta && colourClass[k].size() == 1 && repair(v, k))
				colours--;
		}
		P.clear();
		int i = 0;
		for (int k = 0; k < colours; k++)
			for (int j = 0; j < colourClass[k].size(); j++) {
				int v = (Integer) (colourClass[k].get(j));
				P.add(v);
				colour[i++] = k + 1;
			}
	}

	boolean repair(int v, int k) {
		for (int i = 0; i < k - 1; i++) {
			int w = getSingleConflictVariable(v, colourClass[i]);
			if (w >= 0)
				for (int j = i + 1; j < k; j++)
					if (!conflicts(w, colourClass[j])) {
						colourClass[k].remove((Integer) v);
						colourClass[i].remove((Integer) w);
						colourClass[i].add(v);
						colourClass[j].add(w);
						return true;
					}
		}
		return false;
	}

	int getSingleConflictVariable(int v, ArrayList<Integer> colourClass) {
		int conflictVar = -1;
		int count = 0;
		for (int i = 0; i < colourClass.size() && count < 2; i++) {
			int w = colourClass.get(i);
			if (A[v][w] == 1) {
				conflictVar = w;
				count++;
			}
		}
		if (count > 1)
			return -count;
		return conflictVar;
	}

	boolean conflicts(int v, ArrayList<Integer> colourClass) {
		for (int i = 0; i < colourClass.size(); i++) {
			int w = colourClass.get(i);
			if (A[v][w] == 1)
				return true;
		}
		return false;
	}

	void orderVertices(ArrayList<Integer> ColOrd) {
		Vertex[] V = new Vertex[n];
		for (int i = 0; i < n; i++)
			V[i] = new Vertex(i, degree[i]);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (A[i][j] == 1)
					V[i].nebDeg = V[i].nebDeg + degree[j];

		minWidthOrder(V);
		for (Vertex v : V)
			ColOrd.add(v.index);
	}

	void minWidthOrder(Vertex[] V) {
		ArrayList<Vertex> L = new ArrayList<Vertex>(n);
		Stack<Vertex> S = new Stack<Vertex>();
		for (Vertex v : V)
			L.add(v);
		while (!L.isEmpty()) {
			Vertex v = L.get(0);
			for (Vertex u : L)
				if (u.degree < v.degree)
					v = u;
			S.push(v);
			L.remove(v);
			for (Vertex u : L)
				if (A[u.index][v.index] == 1)
					u.degree--;
		}
		int k = 0;
		while (!S.isEmpty())
			V[k++] = S.pop();
	}

	void saveSolution(ArrayList<Integer> C) {
		Arrays.fill(solution, 0);
		for (int i : C)
			solution[i] = 1;
		maxSize = C.size();
	}
}
