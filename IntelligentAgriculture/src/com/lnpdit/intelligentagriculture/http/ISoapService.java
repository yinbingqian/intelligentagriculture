package com.lnpdit.intelligentagriculture.http;

/**
 * webService请求接口
 * 
 * @author huanyu 类名称：ISoapService 创建时间:2014-11-4 下午7:08:50
 */
public interface ISoapService extends IASoapService {

	/**
	 * 用户登录--用户名|密码
	 * 
	 * @param property_va
	 */
	void getLastWatchData(Object[] property_va);
//	/**
//	 * 用户登录--用户名|密码
//	 * 
//	 * @param property_va
//	 */
//	void userLogin(Object[] property_va);
	
}
