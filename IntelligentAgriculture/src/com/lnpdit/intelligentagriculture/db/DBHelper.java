package com.lnpdit.intelligentagriculture.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.lnpdit.intelligentagriculture.entity.ChatMessage;
import com.lnpdit.intelligentagriculture.entity.LoginUser;
import com.lnpdit.intelligentagriculture.entity.UserInfo;
import com.lnpdit.intelligentagriculture.utils.SOAP_UTILS;
import com.lnpdit.intelligentagriculture.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "";
	private static final int VERSION = 1;
	private static final CursorFactory FACTORY = null;
	private static String DB_NAME = "stockup.db";
	private Context context;
	private static String DB_PATH = "/data/data/lnpdit.lntv.tradingtime/databases/";
	private static final int ASSETS_SUFFIX_BEGIN = 101;
	private static final int ASSETS_SUFFIX_END = 103;
	private SQLiteDatabase myDataBase = null;

	private String CHAT_DB = "";
	private String FRIE_DB = "";

	/**
	 * _id integer primary key autoincrement not null "byte_content blob "
	 * user_guid text "insert_date text "direction integer "type integer "
	 * user_clid bigint "target_type integer "child_clid text
	 * 
	 */
	private static final String CREATE_TABLE_T_SU_NEWS = "CREATE TABLE T_SU_NEWS (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT, NEWSCOMCOUNT TEXT)";

	private static final String CREATE_TABLE_T_SU_ADT = "CREATE TABLE T_SU_ADT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ID TEXT, ORDERS TEXT,  PICTURE TEXT, TITLE TEXT)";

	private static final String CREATE_TABLE_T_SU_STOCKNEWS = "CREATE TABLE T_SU_STOCKNEWS (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,NEWSCOMCOUNT TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT)";
	
	private static final String CREATE_TABLE_T_SU_STANDPOINT = "CREATE TABLE T_SU_STANDPOINT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,NEWSCOMCOUNT TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT)";
	
	private static final String CREATE_TABLE_T_SU_USER = "CREATE TABLE T_SU_USER (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,CHECKNUM TEXT, CHECKSTA TEXT,  HEADPIC TEXT,ID TEXT, LEVEL TEXT,MARK TEXT,NAME TEXT,PRESTIGE TEXT,RANK TEXT,REALNAME TEXT, BIRTH TEXT, SEX TEXT)";
	

	private static final String CREATE_TABLE_T_SU_GUEST = "CREATE TABLE T_SU_GUEST (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANALYSIS TEXT,  HEADPIC TEXT,WEBID TEXT, LEVEL TEXT,PAIDMARK TEXT,REALNAME TEXT,STOCKSTYLE TEXT,RESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_EXPERT = "CREATE TABLE T_SU_EXPERT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,REWARDMARK TEXT,  HEADPIC TEXT,WEBID TEXT, LEVEL TEXT,MARK TEXT,REALNAME TEXT,STOCKSTYLE TEXT,RESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_NOWLIVING = "CREATE TABLE T_SU_NOWLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,DESCRIBECC TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";
	
	private static final String CREATE_TABLE_T_SU_ALLLIVING = "CREATE TABLE T_SU_ALLLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,DESCRIBECC TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_HOTLIVING = "CREATE TABLE T_SU_hotLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,DESCRIBECC TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";
	
	private static final String CREATE_TABLE_T_SU_MSG = "CREATE TABLE T_SU_MSG (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,STATUS INTEGER, CONTENT1 TEXT,  CONTENT2 BLOB, CONTENT3_TITLE TEXT,CONTENT3_CONTENT TEXT,CONTENT3_URL TEXT,CONTENT3_IMG TEXT,MSG_DIRECTION INTEGER,MSG_TYPE INTEGER,MSG_DATE TEXT,LOGIN_ID TEXT,SENDER_ID TEXT)";

	private static final String CREATE_TABLE_T_SU_A_MSG = "CREATE TABLE T_SU_A_MSG (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,STATUS INTEGER, CONTENT1 TEXT,  CONTENT2 BLOB, CONTENT3_TITLE TEXT,CONTENT3_CONTENT TEXT,CONTENT3_URL TEXT,CONTENT3_IMG TEXT,MSG_DIRECTION INTEGER,MSG_TYPE INTEGER,MSG_DATE TEXT,LOGIN_ID TEXT,SENDER_ID TEXT,HEADERPIC TEXT,SENDERNAME TEXT)";

	private static final String CREATE_TABLE_T_SU_LUI = "CREATE TABLE T_SU_LUI (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,IMEI TEXT,TYPE TEXT,SIM TEXT,REMARK TEXT,PROVINCE TEXT,CITY TEXT,STOCK_AGE TEXT,STOCK_STYLE TEXT,AUTOLOGIN INTEGER,LOGIN_TYPE INTEGER)";

	private static final String CREATE_TABLE_T_SU_ARI = "CREATE TABLE T_SU_ARI (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADV_USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,OFFICIAL INTEGER, PAY_ATTENTION INTEGER,HEARTCOUNT TEXT)";

	private static final String CREATE_TABLE_T_SU_ARI_GZ = "CREATE TABLE T_SU_ARI_GZ (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADV_USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,HEARTCOUNT TEXT)";

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, null, version);
		this.context = context;
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DBHelper(Context context) {
		this(context, DB_PATH + DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("stockup.db ========== onCreate");
		db.execSQL(CREATE_TABLE_T_SU_NEWS);
		db.execSQL(CREATE_TABLE_T_SU_STOCKNEWS);
		db.execSQL(CREATE_TABLE_T_SU_STANDPOINT);
		db.execSQL(CREATE_TABLE_T_SU_USER);
		db.execSQL(CREATE_TABLE_T_SU_GUEST);
		db.execSQL(CREATE_TABLE_T_SU_EXPERT);
		db.execSQL(CREATE_TABLE_T_SU_NOWLIVING);
		db.execSQL(CREATE_TABLE_T_SU_ALLLIVING);
		db.execSQL(CREATE_TABLE_T_SU_HOTLIVING);
		db.execSQL(CREATE_TABLE_T_SU_ADT);
		db.execSQL(CREATE_TABLE_T_SU_MSG);
		db.execSQL(CREATE_TABLE_T_SU_LUI);
		db.execSQL(CREATE_TABLE_T_SU_ARI);
		db.execSQL(CREATE_TABLE_T_SU_A_MSG);
		db.execSQL(CREATE_TABLE_T_SU_ARI_GZ);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insUserInfo(UserInfo data) {
		System.out.println("#SU DB# insUserInfo");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CHECKNUM", data.getchecknum());
		values.put("CHECKSTA", data.getchecksta());
		values.put("HEADPIC", data.getheadpic());
		values.put("ID", data.getid());
		values.put("LEVEL", data.getlevel());
		values.put("MARK", data.getmark());
		values.put("NAME", data.getname());
		values.put("PRESTIGE", data.getprestige());
		values.put("RANK", data.getrank());
		values.put("REALNAME", data.getrealname());
		values.put("BIRTH", data.getbirth());
		values.put("SEX", data.getsex());
		db.insert("T_SU_USER", "", values);
		close();
	}
	
	public void updateUser(String loginUserId, String name, String data) {
		ContentValues cv = new ContentValues();
		cv.put(name, data);
		SQLiteDatabase db = getWritableDatabase();
		db.update("T_SU_USER", cv, "ID=?", new String[] { loginUserId });
		close();
	}

	public List<UserInfo> queryUserInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserInfo user = new UserInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_USER", null);
		while (c.moveToNext()) {
			user = new UserInfo();
			user.setchecknum(c.getString(c.getColumnIndex("CHECKNUM")));
			user.setchecksta(c.getString(c.getColumnIndex("CHECKSTA")));
			user.setheadpic(c.getString(c.getColumnIndex("HEADPIC")));
			user.setid(c.getString(c.getColumnIndex("ID")));
			user.setlevel(c.getString(c.getColumnIndex("LEVEL")));
			user.setmark(c.getString(c.getColumnIndex("MARK")));
			user.setname(c.getString(c.getColumnIndex("NAME")));
			user.setprestige(c.getString(c.getColumnIndex("PRESTIGE")));
			user.setrank(c.getString(c.getColumnIndex("RANK")));
			user.setrealname(c.getString(c.getColumnIndex("REALNAME")));
			user.setsex(c.getString(c.getColumnIndex("SEX")));
			user.setbirth(c.getString(c.getColumnIndex("BIRTH")));
			list.add(user);
		}
		close();
		return list;
	}

	public void clearNewsData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_NEWS", null, null);
	}

	public void clearAdtData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_ADT", null, null);
	}
	
	public void clearStockNewsData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_STOCKNEWS", null, null);
	}
	
	public void clearStandPointData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_STANDPOINT", null, null);
	}
	
	public void clearUserInfoData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_USER", null, null);
	}


	/**
	 * 登陆者
	 * 
	 * @param loginUser
	 */

	public void insLoginUserInfo(LoginUser loginUser) {
		System.out.println("#SU DB# insLoginUserInfo");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("AUTOLOGIN", loginUser.getAutologin());
		values.put("CRTIME", loginUser.getCrtime());
		values.put("CITY", loginUser.getCity());
		values.put("EMAIL", loginUser.getEmail());
		values.put("GROUP_ID", loginUser.getGroupid());
		values.put("HEADPIC", loginUser.getHeadpic());
		values.put("IMEI", loginUser.getImei());
		values.put("ISLOCK", loginUser.getIslock());
		values.put("LEVEL", loginUser.getLevel());
		values.put("LOGIN_TYPE", loginUser.getLoginType());
		values.put("MARK", loginUser.getMark());
		values.put("MOBILEPHONE", loginUser.getMobilephone());
		values.put("NAME", loginUser.getName());
		values.put("ORGID", loginUser.getOrgid());
		values.put("ORGNAME", loginUser.getOrgname());
		values.put("PAIDMARK", loginUser.getPaidmark());
		values.put("PTITLE", loginUser.getPtitle());
		values.put("PASSWORD", loginUser.getPassword());
		values.put("PROVINCE", loginUser.getProvince());
		values.put("REALNAME", loginUser.getRealname());
		values.put("REMARK", loginUser.getRemark());
		values.put("RESUME", loginUser.getResume());
		values.put("REWARDMARK", loginUser.getRewardmark());
		values.put("SEX", loginUser.getSex());
		values.put("SIM", loginUser.getSim());
		values.put("SPECIALTY", loginUser.getSpecialty());
		values.put("STATUS", loginUser.getStatus());
		values.put("STOCK_AGE", loginUser.getStock_age());
		values.put("STOCK_STYLE", loginUser.getStock_style());
		values.put("TYPE", loginUser.getType());
		values.put("USER_ID", loginUser.getUserid());
		db.insert("T_SU_LUI", "", values);
		close();
	}

	public void cheanLoginUserData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_LUI", null, null);
	}

	public LoginUser queryLoginUserInfo() {
		System.out.println("#SU DB# queryLoginUserInfo");
		LoginUser loginUser = new LoginUser();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT MAX(_ID) , L.* FROM T_SU_LUI L", null);
		while (c.moveToNext()) {
			loginUser = new LoginUser();
			loginUser.setUserid(c.getString(c.getColumnIndex("USER_ID")));
			loginUser.setAutologin(c.getString(c.getColumnIndex("AUTOLOGIN")));
			loginUser.setCity(c.getString(c.getColumnIndex("CITY")));
			loginUser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			loginUser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			loginUser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			loginUser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			loginUser.setImei(c.getString(c.getColumnIndex("IMEI")));
			loginUser.setIslock(c.getString(c.getColumnIndex("ISLOCK")));
			loginUser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			loginUser.setLoginType(c.getInt(c.getColumnIndex("LOGIN_TYPE")));
			loginUser.setMark(c.getString(c.getColumnIndex("MARK")));
			loginUser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			loginUser.setName(c.getString(c.getColumnIndex("NAME")));
			loginUser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			loginUser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			loginUser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			loginUser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			loginUser.setProvince(c.getString(c.getColumnIndex("PROVINCE")));
			loginUser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			loginUser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			loginUser.setRemark(c.getString(c.getColumnIndex("REMARK")));
			loginUser.setResume(c.getString(c.getColumnIndex("RESUME")));
			loginUser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			loginUser.setSex(c.getString(c.getColumnIndex("SEX")));
			loginUser.setSim(c.getString(c.getColumnIndex("SIM")));
			loginUser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			loginUser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			loginUser.setStock_age(c.getString(c.getColumnIndex("STOCK_AGE")));
			loginUser.setStock_style(c.getString(c.getColumnIndex("STOCK_STYLE")));
			loginUser.setType(c.getString(c.getColumnIndex("TYPE")));
		}
		System.out.println("###   ###  " + loginUser.getUserid() + ":" + loginUser.getName() + ":"
				+ loginUser.getPassword() + ":" + loginUser.getRealname() + ":" + loginUser.getLoginType());
		close();
		return loginUser;
	}


	public int queryGzAdminMsg() {

		return 0;

	}

	public int queryGzAdminMsg(String loginid) {
		System.out.println("#SU DB# queryMsgData");
		List<ChatMessage> list = new ArrayList<ChatMessage>();
		String[] param = { loginid };
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_MSG WHERE LOGIN_ID  = ? AND SENDER_ID = 1", param);
		System.out.println(">>>>>" + c.getCount());
		return c.getCount();
	}

	public void initSysMsgData(String loginid, String[] wStr) {
		System.out.println(">>>　初始化消息数据...");
		System.out.println("#SU DB# initAMsgData" + "id : " + loginid);
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values1 = new ContentValues();
		values1.put("STATUS", 0);
		values1.put("CONTENT1", wStr[0]);
		values1.put("CONTENT2", "");
		values1.put("CONTENT3_TITLE", "");
		values1.put("CONTENT3_CONTENT", "");
		values1.put("CONTENT3_URL", "");
		values1.put("CONTENT3_IMG", "");
		values1.put("MSG_DIRECTION", 0);
		values1.put("MSG_TYPE", 0);
		values1.put("MSG_DATE", Utils.getSystemDate());
		values1.put("LOGIN_ID", loginid);
		values1.put("SENDER_ID", 1);
		db.insert("T_SU_MSG", "", values1);
		ContentValues values2 = new ContentValues();
		values2.put("STATUS", 0);
		values2.put("CONTENT1", wStr[1]);
		values2.put("CONTENT2", "");
		values2.put("CONTENT3_TITLE", "");
		values2.put("CONTENT3_CONTENT", "");
		values2.put("CONTENT3_URL", "");
		values2.put("CONTENT3_IMG", "");
		values2.put("MSG_DIRECTION", 0);
		values2.put("MSG_TYPE", 0);
		values2.put("MSG_DATE", Utils.getSystemDate());
		values2.put("LOGIN_ID", loginid);
		values2.put("SENDER_ID", -1);
		db.insert("T_SU_MSG", "", values2);
		close();
	}

}
