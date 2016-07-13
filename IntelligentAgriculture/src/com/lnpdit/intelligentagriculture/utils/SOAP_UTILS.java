package com.lnpdit.intelligentagriculture.utils;

public class SOAP_UTILS {
	public class METHOD {
		public static final String LOGIN = "login";
	}

	public class ERROR {
		public static final String ERR0000 = "ERR 000";
		public static final String ERR0001 = "ERR 001";
		public static final String ERR0002 = "ERR 002";
		public static final String ERR0003 = "ERR 003";
		public static final String ERR0004 = "ERR 004";
		public static final String ERR0005 = "ERR 005 XML解析错误";
		public static final String ERR0006 = "ERR 006 本地错误";

	}

	public static final String NAMESPACE = "MobileAlarm";
    public static final String IP_SIMPLE = "221.180.149.201";
    public static final String IP = "http://200.20.30.231:8090";
    public static final String URL = IP + "/phoneinvoke.asmx?wsdl";
    public static final String URL_WITHOUT_WSDL = IP + "/phoneinvoke.asmx";
    public static final String PIC_FILE = IP + "/manage/pic/";
    public static final String PIC_JOURNAL = IP + "/manage/magpic/";
    public static final String PIC_PUSH = IP + "/upload/";
    public static final String URL_SERVER = IP + "/apksource/version.xml";
    public static final String VIDEO_PATH = IP + "/manage/videofile/";
    public static final String ATTEND_PATH = IP + "/manage/pic/attend/";
    public static final String FOOD_PATH = IP + "/manage/pic/food/";
    public static final String AUDIO_PATH = IP + "/audio/";
    public static final String COL_PATH = IP + "/columns.xml";
    
	// login type
	public static final int POLICE = 0;// 警察
	public static final int CITIZEN = 1;// 市民

}
