package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.utils.TimeMath;
import com.utils.TimeMathSystemMethod;


public class TestTimeMethod {
	public static void main(String[] args) throws Exception {
//		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//		Date endTime=timeFormat.parse("2100_2_10_1_24_00");
//		System.out.println(TimeMathSystemMethod.mathRemianTime(endTime));//41486471ns
		
//		System.out.println(TimeMath.mathRemainTime("2100_2_10_1_24_00"));//m
//		System.out.println(TimeMath.mathGoOnTime("2016_6_11_12_30_00"));
//		String rs=TimeMath.mathGoOnTime("2016_6_11_12_30_00");
//		String[]a=rs.split("_");
//		int endDay=Integer.parseInt(a[0]);
//		int endHour=Integer.parseInt(a[1]);
//		int endMinute=Integer.parseInt(a[2]);
//		int endSecond=Integer.parseInt(a[3]);
//		int year=endDay/365;
//		endDay=endDay%365;
//		System.out.println(year+"年"+endDay+"_"+endHour+"_"+endMinute+"_"+endSecond);
		
		long startTime=System.currentTimeMillis();
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date endTime=timeFormat.parse("2100_2_10_1_24_00");
		System.out.println(TimeMathSystemMethod.mathRemianTime(endTime));
		System.out.println(TimeMath.mathRemainTime("2100_2_10_1_24_00"));
		long endDime=System.currentTimeMillis();
		System.out.println("程序运行耗时："+(endDime-startTime)+"毫秒");//693991
		/*int []a=new int[2];
		a=TimeMath.mathRemianDayOY(2019, 900, 2);
		for(int i=0;i<2;i++) {
			System.out.println(a[i]);
		}*/
//		int []spday= {100,200,365,520,1314};
//		String[] aStrings=TimeMath.mathSpecialDayNumTime(spday, "2016_6_11_12_30_00");
//		for(int i=0;i<aStrings.length;i++) {
//			System.out.println(aStrings[i]);
//		}
//		int[] aString=TimeMath.mathMemorialDay0(300, 2020);
//		for(int x:aString) {
//			System.out.println(x);
//		}
	}
}
