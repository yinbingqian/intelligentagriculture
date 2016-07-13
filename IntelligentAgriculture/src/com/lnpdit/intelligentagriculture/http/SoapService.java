package com.lnpdit.intelligentagriculture.http;

import org.json.JSONException;
import org.json.JSONObject;

import com.lnpdit.intelligentagriculture.entity.UserInfo;
import com.lnpdit.intelligentagriculture.http.AsyncTaskBase.HttpObjectResult;
import com.lnpdit.intelligentagriculture.utils.EventCache;
import com.lnpdit.intelligentagriculture.utils.SOAP_UTILS;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();

	@Override
	public void userLogin(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {"username","password"};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.LOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONObject user_obj = new JSONObject(obj.toString());
					UserInfo user = new UserInfo();
					user.setchecknum(user_obj.get("CheckNum").toString());
					user.setchecksta(user_obj.get("CheckSta").toString());
					user.setid(user_obj.get("Id").toString());
					user.setlevel(user_obj.get("Level").toString());
					user.setmark(user_obj.get("Redheart").toString());
					user.setname(user_obj.get("Name").toString());
					user.setprestige(user_obj.get("Mark").toString());
					user.setrank(user_obj.get("Rank").toString());
					user.setrealname(user_obj.get("RealName").toString());
					user.setbirth(user_obj.get("StockAge").toString());
					user.setsex(user_obj.get("Sex").toString());
					user.setNewprestige(user_obj.get("Prestige").toString());
					user.setRewardmark(user_obj.get("Rewardmark").toString());
					user.setattuser(user_obj.get("AttUser").toString());
					
					soapRes.setObj(user);
					soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

}
