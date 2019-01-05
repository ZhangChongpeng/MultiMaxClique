package cn.zzp.spark;

import java.util.ArrayList;

import cn.zzp.graph.Edge;
import cn.zzp.graph.GetIndex;
import cn.zzp.graph.Vertex;

public class Handle {

	/*
	 * 对图按度数降序排序---着色----着色后升序排序
	 * */
	public static ArrayList<Integer> rest(Vertex[] Ver) {

		// System.out.println("----对顶点度数进行降序排序-----");
		ArrayList<Integer> list = DescendArrayOrder(Ver);
		// System.out.println("-------对图的各顶点进行着色--------");
		
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for(int i=0;i<Ver.length;i++) {
			temp.add(i+1);
		}
		
		SimpleColoring sc = new SimpleColoring(Ver);
		Vertex[] coloringV = sc.GraphListColor(temp);
		// System.out.println("--------着色后进行升序排序----");
		ArrayList<Integer> ascColorList = AscColorOrder(coloringV,list);
		return ascColorList;
	}
	
	/*
	 * 根据度数降序排序
	 * */
	public static ArrayList<Integer> DescendArrayOrder(Vertex[] V) {
		ArrayList<Integer> list = new ArrayList<Integer>(V.length);
		for (int i = 0; i < V.length; i++)
			list.add(V[i].cost);
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (V[GetIndex.getVerName(list.get(i), V)].currentDegree < V[GetIndex.getVerName(list.get(j), V)].currentDegree) {
					int temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}

		return list;

	}
	/*
	 * 着色后对颜色进行升序排序
	 * */
	public static ArrayList<Integer> AscColorOrder(Vertex[] V,ArrayList<Integer> list) {

		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (V[GetIndex.getVerName(list.get(i), V)].color > V[GetIndex.getVerName(list.get(j), V)].color) {
					int temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}

		return list;

	}
	
	/*
	 * 计算list集合中与head节点一样颜色的顶点放到newList中
	 * */
	public static ArrayList<Integer> RemainNode(int head, ArrayList<Integer> list,Vertex[] V){
		ArrayList<Integer> newList=new ArrayList<Integer>();
		int temp=V[GetIndex.getVerName(head, V)].color;  //得到head顶点的颜色
		for(int i=0;i<list.size();i++){
			if(V[GetIndex.getVerName(list.get(i), V)].color==temp)//如果当前list顶点的颜色和head相同，就填加到newList中
				newList.add(list.get(i));
		}
		
		return newList;
	}
	
	/*
	 * 计算图密度
	 * */
	public static double Density(Vertex[] V) {
		int n=0;
		for(int i=0;i<V.length;i++) {
			Edge e=V[i].link;
			while(e!=null) {
				n++;
				e=e.link;
			}
		}
		double density=(2*((double)n/2))/((double)V.length*((double)V.length-1));
		return density;
	}

}






















