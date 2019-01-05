package cn.zzp.spark;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import cn.zzp.graph.Edge;
import cn.zzp.graph.Vertex;


/*
 * 读文件
 * */
public class ReadFile {

	static Vertex[] V; // 指向顶点表的引用
	static int vertexNum; // 顶点个数

	public static Vertex[] ReadDIMACS(String fname) throws IOException {

		int from, to;
		String s = "";
		Scanner sc = new Scanner(new File(fname));
		while (sc.hasNext() && !s.equals("p"))
			s = sc.next();
		sc.next();
		vertexNum = sc.nextInt();
		V = new Vertex[vertexNum];
		sc.nextInt(); // 跳过边数
		for (int i = 0; i < vertexNum; i++) {
			V[i] = new Vertex();
			V[i].verName = i;
			V[i].cost = i + 1;
			V[i].color = 0;
			V[i].link = null;
		}

		while (sc.hasNext()) {
			sc.next();
			from = sc.nextInt();
			to = sc.nextInt();
			Edge p = new Edge();
			p.verAdj = to;
			p.link = null;
			Edge q = V[from - 1].link;
			if (q == null) {
				V[from - 1].link = p;
			} else {
				while (q.link != null) {
					q = q.link;
				}
				q.link = p;
			}
			
			Edge np = new Edge();
			np.verAdj = from;
			np.link = null;
			Edge nq = V[to - 1].link;
			if (nq == null) {
				V[to - 1].link = np;
			} else {
				while (nq.link != null) {
					nq = nq.link;
				}
				nq.link = np;
			}
			
		}
		sc.close();

//		System.out.println("--------初始邻接表----------");
//		for (int i = 0; i < vertexNum; i++) {
//			System.out.print(V[i].cost + ":");
//			Edge p = V[i].link;
//			while (p != null) {
//				System.out.print(p.verAdj + " ");
//				p = p.link;
//			}
//			System.out.println();
//		}

		/* 求出各顶点度数 */
		for (int i = 0; i < vertexNum; i++) {
			Edge p = V[i].link;
			int n = 0;
			while (p != null) {
				n++;
				p = p.link;

			}
			V[i].currentDegree = n;
		}
		
		return V;
	}

}
