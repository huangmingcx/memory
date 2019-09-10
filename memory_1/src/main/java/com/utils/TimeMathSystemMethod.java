package com.utils;

import java.util.Date;

public class TimeMathSystemMethod {
	/**
	 * use the JDK method not need a lot judge but test run time more than have
	 * a lot judge method
	 * @param endTime
	 * @return
	 */
	public static String mathRemianTime(Date endTime) {
		Date beginTime=new Date();
		long beginTimeM=beginTime.getTime();
		long endTimeM=endTime.getTime();
		long remainTimeM=endTimeM-beginTimeM;
		long remainTimeSecond=remainTimeM/1000;
		long remainDay=remainTimeSecond/86400;
		remainTimeSecond=remainTimeSecond%86400;
		long remianHour=remainTimeSecond/3600;
		remainTimeSecond=remainTimeSecond%3600;
		long remainMinute=remainTimeSecond/60;
		remainTimeSecond=remainTimeSecond%60;
		return remainDay+"_"+remianHour+"_"+remainMinute+"_"+remainTimeSecond;
	}
}

