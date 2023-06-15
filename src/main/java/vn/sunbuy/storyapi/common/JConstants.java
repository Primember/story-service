package vn.sunbuy.storyapi.common;
public class JConstants {

	public static final String SCHEMA = "_schema";

	public static final String USER_API = "/user-api";
	public static final String PARTNER_API = "/partner-api";
	public static final String PLAN_FREE_ID = "FREE";
	public static final String MD5 = "MD5withRSA";
	public static final String SHA1 = "MD5withRSA";

	public static final String WARN_EXPIRED_PASS = "warn_expired_pass";
	public static final String EXPIRED_PASS = "expired_pass";

	public static final String PAGE = "1";
	public static final String SIZE = "10";
	public static final String DATA_LIST = "list";
	public static final String DATA_TOTAL = "total";
	public static final String BEARER = "Bearer ";

	public static final String MMddHHmm = "MMddHHmm";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	public static final String UI_FORMAT = "dd/MM HH:mm";
	public static final String DDMM = "dd-MM";
	public static final String HHmm = "HH:mm";
	public static final String yyMMdd = "yyMMdd";
	public static final String TID = "yyMMddHHmm";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYMMDDHHmm = "yyMMddHHmm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String MM_YYYY = "MM-yyyy";
	public static final String HHmmYYYYMMDD = "HH:mm yyyy-MM-dd";
	public static final String yyMMddHHmm = "yyMMddHHmm";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String HHmmssSSSddMMyyyy = "HHmmssSSSddMMyyyy";
	public static final String MMddHHmmss = "MMddHHmmss";
	public static int HOUR_LOCAL = 7;// GMT-06:00 to GMT+07:00

	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String AUTH_HEADER = "Authorization";
	public static final String DEVICE_ID = "deviceId";

	

	public enum ERole {
		
		ROLE_USER,
		ROLE_ADMIN

	}
	public enum Status {
		 COMING_SOON,
		 PUBLISHING,
		 FULL
	}
	public enum type {
		NEW_STORY,
		HOT_STORY,
		TIENHIEP_HAY,
		NGONTINH_HAY,
		NGONTINH_SAC,
		NGONTINH_SUNG,
		NGONTINH_NGUOC,
		NGONTINHH_HAI,
		DAMMY_HAI,
		DAMMY_HAY,
		DAMMY_HVAN
	}

	public enum Env {
		local, dev, stg,

	}
	
	



}
