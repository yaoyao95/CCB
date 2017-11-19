package com.CantoneseClubBBS.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Display {
	public static String DateForDisply(Date date) {
		return new SimpleDateFormat("yy-MM-dd HH时").format(date);
	}
}
