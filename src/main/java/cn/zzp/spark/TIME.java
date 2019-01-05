package cn.zzp.spark;

import cn.zzp.graph.Edge;
import cn.zzp.graph.Vertex;

public class TIME {

	/*
	 * 计算子图运行时间
	 * */
	
	public static double RunningTime(Vertex[] V) {
		int n=0;
		for(int i=0;i<V.length;i++) {
			Edge e=V[i].link;
			while(e!=null) {
				n++;
				e=e.link;
			}
		}
		
		double x=V.length;
		double y=(2*((double)n/2))/((double)V.length*((double)V.length-1));
		
//		//大于1秒的数据，随机公式拟合
//		double z=(124862.29+721.53*x-3.63*x*x-418142.20*y+276246.82*y*y)/(1-0.0004*x-2.11*y+1.11*y*y+0.062*y*y*y);
//		return z;
//		//大于1秒的数据，论文公式拟合
//		double z=Math.pow(((1+x+x*x+x*x*x+x*x*x*x)*(7.933+173.6123*y-12.3054*y*y-143.9512*y*y*y+74.9332*y*y*y*y)),(x*y*y-x*y*0.8927+0.2195)); 
//		return z;
		
//		//全体数据，随机公式拟合
//		double z= (17808273.61-8618933.69*Math.log(x)+1083405.49*Math.log(x)*Math.log(x)-9542.04*Math.log(x)*Math.log(x)*Math.log(x)+513194.86*Math.log(y)+233198.12*Math.log(y)*Math.log(y)/(1-0.28*Math.log(x)-3.12*Math.log(y)));
//		return Math.abs(z);
		
////		//全体数据，论文公式拟合
//		double z=Math.pow(((1+x+x*x+x*x*x+x*x*x*x)*(0.2440+20119695317352.5*y+10836679076047.1*y*y+19916764058135.6*y*y*y+22115131414764.7*y*y*y*y)),(x*y*y-x*y*1.0268+12119072654233.9));
//		return Math.abs(z);
		
//		double a1=1.068542;
//		double a2=0.061751;
//		double a3=-0.000166;
//		double a4=0.000318;
//		double b=-0.929870;
//		double c=0.288462;
//		
//		double t=Math.pow((a1+a2*y)+x*(a3+a4*y), x*(y*y+b*y+c));
//		return t;
		
		double a00=-1.403425;
		double a01=14.449873;
		double a02=-32.046989;
		double a03=32.139918;
		double a04=-12.402114;
		double a10=0.006446;
		double a11=-0.041770;
		double a12=0.102931;
		double a13=-0.114257;
		double a14=0.048228;
		double b=-0.389024;
		double c=0.023410;
		double t=Math.pow((a00+a01*y+a02*y*y+a03*y*y*y+a04*y*y*y*y)+x*(a10+a11*y+a12*y*y+a13*y*y*y+a14*y*y*y*y), x*(y*y+b*y+c));
		return Math.abs(t);
		
	}
	
}















