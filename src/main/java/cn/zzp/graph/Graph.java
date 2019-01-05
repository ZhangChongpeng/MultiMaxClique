package cn.zzp.graph;

import java.util.ArrayList;

/*
 * 创建邻接表
 * */
public class Graph {

	public Vertex[] Ver;// 新顶点表
	public Vertex[] V; // 旧顶点表
	public static Vertex[] remainVer; // 剩余顶点表
	ArrayList<Integer> subList; // 子图顶点

	public Graph(ArrayList<Integer> subList, Vertex[] V) {
		this.V = V;
		this.subList = subList;
	}

	public Vertex[] CreateGraph() {

		int from;
		int to;
		Ver = new Vertex[subList.size()];
		for (int i = 0; i < subList.size(); i++) {
			Ver[i] = new Vertex();
			Ver[i].verName = i;
			Ver[i].cost = subList.get(i);
			Ver[i].link = null;
		}
		for (int i = 0; i < subList.size(); i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			Edge e = V[GetIndex.getVerName(Ver[i].cost, V)].link;
			while (e != null) {
				for (int k = 0; k < subList.size(); k++) {
					if (e.verAdj == subList.get(k)) {
						list.add(e.verAdj);
						break;
					}
				}
				e = e.link;
			}
			Ver[i].currentDegree = list.size();
				for (int j = 0; j < list.size(); j++) {
//					from = Ver[j].cost;
					to = list.get(j);
					Edge p = new Edge();
					p.verAdj = to;
					p.link = null;
					Edge q = Ver[i].link;
					if (q == null)
						Ver[i].link = p;
					else {
						while (q.link != null) {
							q = q.link;
						}
						q.link = p;
					}
				}
				

		}
		/* 求出各顶点度数 */
		for (int i = 0; i < Ver.length; i++) {
			Edge p = Ver[i].link;
			int n = 0;
			while (p != null) {
				n++;
				p = p.link;

			}
			Ver[i].currentDegree = n;
		}
		
		return Ver;
	}

	public static Vertex[] RemainGraph(Vertex[] Ver) throws ArrayIndexOutOfBoundsException,NullPointerException{

		remainVer = new Vertex[Ver.length];

		for (int i = 0; i < Ver.length; i++) {
			remainVer[i] = new Vertex();
			remainVer[i].verName = i;
			remainVer[i].cost = Ver[i].cost;
			remainVer[i].link = null;
		}
		
		for (int i = 0; i < Ver.length; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j < Ver.length; j++)
				list.add(Ver[j].cost);
			Edge e = Ver[i].link;
			ArrayList<Integer> arr = new ArrayList<Integer>();
			while (e != null) {
				arr.add(e.verAdj);
				e = e.link;
			}
			list.removeAll(arr);
			list.remove((Integer) Ver[i].cost);

//			remainVer[i] = new Vertex();
//			remainVer[i].verName = i;
//			remainVer[i].cost = Ver[i].cost;
//			remainVer[i].link = null;
			int from, to;
			for (int j = 0; j < list.size(); j++) {
//				from = remainVer[i].cost;
				to = list.get(j);
				Edge p = new Edge();
				p.verAdj = to;
				p.link = null;
				Edge q = remainVer[i].link;
				if (q == null)
					remainVer[i].link = p;
				else {
					while (q.link != null) {
						q = q.link;
					}
					q.link = p;
				}
			}

		}
		/* 求出各顶点度数 */
		for (int i = 0; i < remainVer.length; i++) {
			Edge p = remainVer[i].link;
			int n = 0;
			while (p != null) {
				n++;
				p = p.link;

			}
			remainVer[i].currentDegree = n;
		}
		
		return remainVer;

	}
}
