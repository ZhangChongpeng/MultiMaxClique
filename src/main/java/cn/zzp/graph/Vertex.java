package cn.zzp.graph;

import java.io.Serializable;

public class Vertex implements Serializable{
	public int verName; // 顶点序号
	public int cost; // 顶点权值
	public int color; // 顶点颜色
	public int currentDegree; //顶点的度
	public Edge link;// 边链表头指针

}
