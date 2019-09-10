package com.utils;

import java.util.Calendar;
/**
 * @time   2019/7/31
 * @author ChengXiao
 */
public final class TimeMath {
	/**
	 * function:math count down 
	 * @param endTime	set the endTime,format:year_month_day_hour_minute_second
	 * @return resultTime(String) or null:tell you beginTime greater than endTime
	 * return format:day_hour_minute_second
	 */
	public static String mathRemainTime(String endTime) {
		String resultTime=null;
		String[]time=endTime.split("_");
		int endYear=Integer.parseInt(time[0]);
		int endMonth=Integer.parseInt(time[1]);
		int endDay=Integer.parseInt(time[2]);
		int endHour=Integer.parseInt(time[3]);
		int endMinute=Integer.parseInt(time[4]);
		int endSecond=Integer.parseInt(time[5]);
		Calendar c = Calendar.getInstance();//get beginTime
		int beginYear = c.get(Calendar.YEAR);
		int beginMonth = c.get(Calendar.MONTH)+1;
		int beginDay = c.get(Calendar.DATE);
		int beginHour = c.get(Calendar.HOUR_OF_DAY);
		int beginMinute = c.get(Calendar.MINUTE);
		int beginSecond = c.get(Calendar.SECOND);
		boolean endTimeLegal=judgeEndTimeLegal(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endYear, endMonth, endDay, endHour, endMinute, endSecond);
		if(endTimeLegal) {
			return null;
		}
		if(beginYear!=endYear) {
			resultTime=mathDifferentYear(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endYear, endMonth, endDay, endHour, endMinute, endSecond);
		}else if(beginMonth==endMonth&&beginDay==endDay){
			resultTime=mathSameYearAndDay(beginHour,beginMinute,beginSecond,endHour,endMinute,endSecond);
		}else {
			resultTime=mathSameYearDifferentDay(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endMonth, endDay, endHour, endMinute, endSecond);
		}
		return resultTime; 
	}
	/**
	 * function:Calculates the current time that the start date of the anniversary has passed 
	 * @param memorialTime	set the memorialTime,format:year_month_day_hour_minute_second
	 * @return	resultTime(String) or null:tell you memorialTime greater than nowTime
	 * return format:day_hour_minute_second
	 */
	public static String mathGoOnTime(String memorialTime) {
		String resultTime=null;
		String[]time=memorialTime.split("_");
		int beginYear=Integer.parseInt(time[0]);
		int beginMonth=Integer.parseInt(time[1]);
		int beginDay=Integer.parseInt(time[2]);
		int beginHour=Integer.parseInt(time[3]);
		int beginMinute=Integer.parseInt(time[4]);
		int beginSecond=Integer.parseInt(time[5]);
		Calendar c = Calendar.getInstance();//get beginTime
		int endYear = c.get(Calendar.YEAR);
		int endMonth = c.get(Calendar.MONTH)+1;
		int endDay = c.get(Calendar.DATE);
		int endHour = c.get(Calendar.HOUR_OF_DAY);
		int endMinute = c.get(Calendar.MINUTE);
		int endSecond = c.get(Calendar.SECOND);
		boolean endTimeLegal=judgeEndTimeLegal(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endYear, endMonth, endDay, endHour, endMinute, endSecond);
		if(endTimeLegal) {
			return null;
		}
		if(beginYear!=endYear) {
			resultTime=mathDifferentYear(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endYear, endMonth, endDay, endHour, endMinute, endSecond);
		}else if(beginMonth==endMonth&&beginDay==endDay){
			resultTime=mathSameYearAndDay(beginHour,beginMinute,beginSecond,endHour,endMinute,endSecond);
		}else {
			resultTime=mathSameYearDifferentDay(beginYear, beginMonth, beginDay, beginHour, beginMinute, 
				beginSecond, endMonth, endDay, endHour, endMinute, endSecond);
		}
		return resultTime;
	}
	/**
	 * function:math special number memorial date
	 * @param specialNum	set the special number to math special memorial date
	 * @param memorialTime	set the memorial date that begin date
	 * @return	specialTime[](String),everyone array element represent special number date
	 * 			format:specialNum_year_month_day_hour_minute_second
	 */
	public static String[] mathSpecialDayNumTime(int[] specialNum,String memorialTime) {
		String[]specialTime=new String[specialNum.length];
		String[]time=memorialTime.split("_");
		int beginHour=Integer.parseInt(time[3]);
		int beginMinute=Integer.parseInt(time[4]);
		int beginSecond=Integer.parseInt(time[5]);
		int []getNumYear=new int[2];
		int []getMonthDay=new int[2];
		for(int i=0;i<specialNum.length;i++) {
			int beginYear=Integer.parseInt(time[0]);
			int beginMonth=Integer.parseInt(time[1]);
			int beginDay=Integer.parseInt(time[2]);
			int overYearDay=mathBeginYearDay(beginYear, beginMonth, beginDay);
			int num=specialNum[i];
			if(num>overYearDay) {
				int overYearNum=(num-overYearDay)/366;
				num-=overYearDay;
				getNumYear=mathRemianSDayOY(beginYear, num, overYearNum);
				num=getNumYear[0];
				beginYear=getNumYear[1];
				beginMonth=12;
				beginDay=31;
				if(num!=0) {
					beginYear+=1;
					getMonthDay=mathMemorialDay0(num, beginYear);
					beginMonth=getMonthDay[0];
					beginDay=getMonthDay[1];
				}
			}else {
				getMonthDay=mathMermorialDay1(num, beginYear, beginMonth, beginDay);
				beginMonth=getMonthDay[0];
				beginDay=getMonthDay[1];
			}
			specialTime[i]=specialNum[i]+"_"+beginYear+"_"+beginMonth+"_"+beginDay+"_"+beginHour+"_"
					+beginMinute+"_"+beginSecond;
		}
		return specialTime;
	}
	/**
	 * function:when month=12&&day=31 can math the end month and day
	 * @param specialNum	set the end year day number
	 * @param beginYear		set the end year number
	 * @return	the special number date:array element[0] represent month
	 *			,array element[1] represent day
	 */
	private static int[] mathMemorialDay0(int specialNum,int beginYear) {
		int[]a=new int[2];
		int[]b= {0,31,59,90,120,151,181,212,243,273,304,334};
		int[]c= {0,31,60,91,121,152,182,213,244,274,305,335};
		boolean year=judgeLeapYear(beginYear);
		if(year) {
			for(int i=11;i>=0;i--) {
				if(specialNum>c[i]) {
					a[0]=i+1;
					a[1]=specialNum-c[i];
					return a;
				}
			}
		}else {
			for(int i=11;i>=0;i--) {
				if(specialNum>b[i]) {
					a[0]=i+1;
					a[1]=specialNum-b[i];
					return a;
				}
			}
		}
		return null;
	}
	/**
	 * function:moth the end month and day when begin month!=12&&day!=31
	 * @param specialNum	set the end year day number
	 * @param beginYear		set the end year number
	 * @param beginMonth	set the end month number
	 * @param beginDay		set the end day number
	 * @return	the special number date:array element[0] represent month
	 *			,array element[1] represent day
	 */
	private static int[] mathMermorialDay1(int specialNum,int beginYear,int beginMonth,int beginDay) {
		int[]a=new int[2];
		int num=mathEndYearDay(beginYear, beginMonth, beginDay)+1;
		specialNum+=num;
		a=mathMemorialDay0(specialNum, beginYear);
		return a;
	}
	/**
	 * function:math remain special number pass over year
	 * @param beginYear
	 * @param specialNum
	 * @param overYearNum
	 * @return	array element[0] represent specialNum,array element[1] represent year
	 */
	private static int[] mathRemianSDayOY(int beginYear,int specialNum,int overYearNum) {
		int[]a=new int[2];
		if(overYearNum>0) {
			int sum=0;
			for(int i=0;i<overYearNum;i++) {
				if(judgeLeapYear(beginYear+1+i)) {
					sum+=366;
				}else {
					sum+=365;
				}
			}
			beginYear+=overYearNum;
			specialNum-=sum;
			boolean specialCase=true;
			while(specialCase) {
				if(specialNum>=366&&judgeLeapYear(beginYear+1)) {
					specialNum-=366;
					beginYear+=1;
					specialCase=false;
				}else if(specialNum>=365&&!judgeLeapYear(beginYear+1)) {
					specialNum-=365;
					beginYear+=1;
					specialCase=false;
				}else {
					specialCase=false;
				}
				if(specialNum>=365) {
					specialCase=true;
				}
			}
		}else {
			boolean specialCase=true;
			while(specialCase) {
				if(specialNum>=366&&judgeLeapYear(beginYear+1)) {
					specialNum-=366;
					beginYear+=1;
					specialCase=false;
				}else if(specialNum>=365&&!judgeLeapYear(beginYear+1)) {
					specialNum-=365;
					beginYear+=1;
					specialCase=false;
				}else {
					specialCase=false;
				}
				if(specialNum>=365) {
					specialCase=true;
				}
			}
		}
		a[0]=specialNum;
		a[1]=beginYear;
		return a;
	}
	/**
	 * function:judge beginTime whether greater than endTime
	 * @param beginYear
	 * @param beginMonth
	 * @param beginDay
	 * @param beginHour
	 * @param beginMinute
	 * @param beginSecond
	 * @param endYear
	 * @param endMonth
	 * @param endDay
	 * @param endHour
	 * @param endMinute
	 * @param endSecond
	 * @return	ture:yes,false:no
	 */
	private static boolean judgeEndTimeLegal(int beginYear, int beginMonth, int beginDay, int beginHour,
			int beginMinute, int beginSecond, int endYear, int endMonth, int endDay, int endHour, int endMinute,
			int endSecond) {
		if(beginYear>endYear) {
			return true;
		}else if(beginYear==endYear) {
			if(beginMonth>endMonth) {
				return true;
			}else if(beginMonth==endMonth){
				if(beginDay>endDay) {
					return true;
				}else if(beginDay==endDay) {
					if(beginHour>endHour) {
						return true;
					}else if(beginHour==endHour) {
						if(beginMinute>endMinute) {
							return true;
						}else if(beginMinute==endMinute) {
							if(beginSecond>endSecond) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * function:math day number when year and day same
	 * @param beginHour
	 * @param beginMinute
	 * @param beginSecond
	 * @param endHour
	 * @param endMinute
	 * @param endSecond
	 * @return	day number
	 */
	private static String mathSameYearAndDay(int beginHour, int beginMinute, int beginSecond, int endHour,
			int endMinute, int endSecond) {
		int day=0;
		int hour=0;
		int minute=0;
		int second=0;
		if(beginHour==endHour) {
			if(beginMinute==endMinute) {
				if(beginSecond!=endSecond) {
					second=endSecond-beginSecond;
				}
			}else {
				minute=endMinute-beginMinute-1;
				second=60-beginSecond+endSecond;
			}
		}else {
			hour=endHour-beginHour-1;
			minute=59-beginMinute+endMinute;
			second=60-beginSecond+endSecond;
		}
		return adjuestmentTimeFormat(day, hour, minute, second);
	}
	/**
	 * function:math resultTime when year is same and day is different
	 * @param beginYear
	 * @param beginMonth
	 * @param beginDay
	 * @param beginHour
	 * @param beginMinute
	 * @param beginSecond
	 * @param endMonth
	 * @param endDay
	 * @param endHour
	 * @param endMinute
	 * @param endSecond
	 * @return	result time
	 */
	private static String mathSameYearDifferentDay(int beginYear, int beginMonth, int beginDay, int beginHour, int beginMinute,
			int beginSecond, int endMonth, int endDay, int endHour, int endMinute, int endSecond) {
		int day=0;
		int hour=0;
		int minute=0;
		int second=0;
		hour=23-beginHour+endHour;
		minute=59-beginMinute+endMinute;
		second=60-beginSecond+endSecond;
		day=mathDaySameYear(beginYear,beginMonth,beginDay,endMonth,endDay);
		return adjuestmentTimeFormat(day, hour, minute, second);
	}
	/**
	 * function:when year is same and day is different use to math day number
	 * @param beginYear
	 * @param beginMonth
	 * @param beginDay
	 * @param endMonth
	 * @param endDay
	 * @return	day number
	 */
	private static int mathDaySameYear(int beginYear, int beginMonth, int beginDay, int endMonth, int endDay) {
		int day=0;
		int []b= {31,28,31,30,31,30,31,31,30,31,30,31};
		boolean a=judgeLeapYear(beginYear);
		if(beginMonth==endMonth) {
			if(beginDay==endDay) {
				return 0;
			}else {
				return (endDay-beginDay-1);
			}
		}
		for(int i=0;i<(endMonth-beginMonth-1);i++) {
			day+=b[beginMonth+i];
		}
		if(a&&beginMonth<=2&&endMonth>2) {
			day++;
		}
		if(beginMonth==1||beginMonth==3||beginMonth==5||beginMonth==7
				||beginMonth==8||beginMonth==10||beginMonth==12) {
			day+=(31-beginDay+endDay-1);
		}else if(beginMonth==2) {
			if(a) {
				day+=(29-beginDay+endDay-1);
			}else {
				day+=(28-beginDay+endDay-1);
			}
		}else {
			day+=(30-beginDay+endDay-1);
		}
		return day;
	}

	private static boolean judgeLeapYear(int year) {
		return (year%4==0&&year%100!=0)||year%400==0;
	}
	/**
	 * function:when beginYear!=endYear can use to math count down result time
	 * @param beginYear
	 * @param beginMonth
	 * @param beginDay
	 * @param beginHour
	 * @param beginMinute
	 * @param beginSecond
	 * @param endYear
	 * @param endMonth
	 * @param endDay
	 * @param endHour
	 * @param endMinute
	 * @param endSecond
	 * @return	result time
	 */
	private static String mathDifferentYear(int beginYear,int beginMonth,int beginDay,int beginHour,
			int beginMinute,int beginSecond,int endYear,int endMonth,int endDay,int endHour,
			int endMinute,int endSecond) {
		int day=0;
		int hour=0;
		int minute=0;
		int second=0;
		day=mathTotalYearDay(beginYear, endYear);
		day+=mathBeginYearDay(beginYear, beginMonth, beginDay);
		day+=mathEndYearDay(endYear, endMonth, endDay);
		hour=23-beginHour+endHour;
		minute=59-beginMinute+endMinute;
		second=60-beginSecond+endSecond;
		return adjuestmentTimeFormat(day, hour, minute, second);
	}
	/**
	 * function:adjustment time format
	 * @param day		math sum day
	 * @param hour		math sum hour
	 * @param minute	math sum minute
	 * @param second	math sum second
	 * @return 	math time format:day_hour_minute_second
	 */
	private static String adjuestmentTimeFormat(int day,int hour,int minute,int second) {
		if(second>=60) {
			minute+=(second/60);
			second=second%60;
		}
		if(minute>=60) {
			hour+=(minute/60);
			minute=minute%60;
		}
		if(hour>=24) {
			day+=(hour/24);
			hour=hour%24;
		}
		return day+"_"+hour+"_"+minute+"_"+second;
	}
	/**
	 * function:if beginYear!=endYear can get the total year day number at first
	 * @param beginYear		set begin year
	 * @param endYear		set end year
	 * @return	total year day number
	 */
	private static int mathTotalYearDay(int beginYear,int endYear) {
		int day=0;
		if(endYear-beginYear<=1) {
			return 0;
		}
		int totalYear=endYear-beginYear-1;
		if(totalYear==1) {
			if(judgeLeapYear(endYear-1)) {
				return 366;
			}else {
				return 365;
			}
		}
		int []year=new int[totalYear];
		for(int i=0;i<totalYear;i++) {
			year[i]=beginYear+i+1;
			if(judgeLeapYear(year[i])) {
				day+=366;
			}else {
				day+=365;
			}
		}
		return day;
	}
	/**
	 * function:math the begin year beginMonth_beginDay to the last day number
	 * @param beginYear		set begin year
	 * @param beginMonth	set begin month
	 * @param beginDay		set begin day
	 * @return	day number(not add the begin day)
	 * if(beginYear!=endYear) can use mathBeginYearDay and mathEndYearDay method
	 */
	private static int mathBeginYearDay(int beginYear,int beginMonth,int beginDay) {
		int day=0;
		boolean a=judgeLeapYear(beginYear);
		int []b= {31,30,31,30,31,31,30,31,30,31};//[3-12]
		if(beginMonth<2) {
			if(a) {
				day=335;//306+29
			}else {
				day=334;
			}
		}
		for(int i=0;i<12-beginMonth;i++) {
			day+=b[beginMonth-2+i];
		}
		if(beginMonth==1||beginMonth==3||beginMonth==5||beginMonth==7
				||beginMonth==8||beginMonth==10||beginMonth==12) {
			day+=(31-beginDay);
		}else if(beginMonth==2) {
			if(a) {
				day+=(29-beginDay);
			}else {
				day+=(28-beginDay);
			}
		}else {
			day+=(30-beginDay);
		}
		return day;
	}
	/**
	 * function:math the endYear first day to endMonth_endDay day number
	 * @param endYear		set the end year number
	 * @param endMonth		set the end month number
	 * @param endDay		set the end day number
	 * @return	day number(not add the end day)
	 */
	private static int mathEndYearDay(int endYear,int endMonth,int endDay) {
		int day=0;
		int []b= {31,28,31,30,31,30,31,31,30,31,30,31};
		if(endMonth>1) {
			for(int i=0;i<endMonth-1;i++) {
				day+=b[i];
			}
		}
		if(endMonth>2&&judgeLeapYear(endYear)) {
			day++;
		}
		day+=(endDay-1);
		return day;
	}
}
