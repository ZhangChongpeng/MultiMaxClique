package cn.zzp.graph;

/*
 * 根据权值(即顶点标号)找当前邻接表中权值对应的下标
 * */
public class GetIndex {

	public static int getVerName(int n, Vertex V[]) {

		for (int i = 0; i < V.length; i++) {
			if (V[i].cost == n)
				return V[i].verName;

		}
		return 0;

	}
}
