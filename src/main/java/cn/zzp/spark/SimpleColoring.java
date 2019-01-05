package cn.zzp.spark;

import java.util.ArrayList;

import cn.zzp.graph.Edge;
import cn.zzp.graph.GetIndex;
import cn.zzp.graph.Vertex;

/*
 * 着色
 * */

public class SimpleColoring {

	Vertex[] V;

	public SimpleColoring(Vertex[] V) {
		this.V = V;
	}

	public Vertex[] GraphListColor(ArrayList<Integer> list) {

		ArrayList<Integer> C = new ArrayList<Integer>();
		int i = 1;//循环次数
		int max = 1;//一次循环中所用颜色的最大值
		while (i <= list.size()) {
			int j = 1;
			int v = list.get(i - 1);
			while (!C.isEmpty()) {
				int newTemp;//标记颜色
				ArrayList<Integer> newList = new ArrayList<Integer>();
				//把C集合中v的邻接点加入到newList中
				for (int x = 0; x < C.size(); x++) {
					Edge p = V[GetIndex.getVerName(C.get(x), V)].link;
					while (p != null) {
						if (p.verAdj == v) {
							newList.add(C.get(x));
							break;
						}
						p = p.link;
					}
				}
				//找颜色的最大值max
				for (int y = 0; y < newList.size(); y++) {
					int temp = newList.get(y);
					if (max < V[GetIndex.getVerName(temp, V)].color)
						max = V[GetIndex.getVerName(temp, V)].color;
				}
				newTemp = max;
				//如果v是单个节点,没有邻接点,将newTemp设为0
				if(newList.size()==0){
					newTemp=0;
					break;
					
				}
				//如果颜色没有跳过的,标记颜色是最大的颜色,否则标记颜色就是跳过去的颜色减1
				outer: for (int z = 1; z <= max; z++) {
					int index;
					for (index = 0; index < newList.size(); index++) {
						if (V[GetIndex.getVerName(newList.get(index), V)].color == z) {
							break;
						}
						if (index == newList.size() - 1) {
							newTemp = z - 1;
							break outer;
						}
					}
				}
				j = newTemp + 1;//颜色等于标记颜色+1
				break;
			}
			V[GetIndex.getVerName(list.get(i - 1), V)].color = j;
			C.add(v);
			i = i + 1;
		}

//		for (int k = 0; k < list.size(); k++) {
//			System.out.println(C.get(k) + " color is:"
//					+ V[GetIndex.getVerName(list.get(k), V)].color);
//		}
		return V;
	}
}
