package com.lnpdit.agriculturalmechanization.webservice;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.lnpdit.agriculturalmechanization.utils.EventCache;
import com.lnpdit.agriculturalmechanization.utils.SOAP_UTILS;
import com.lnpdit.agriculturalmechanization.webservice.AsyncTaskBase.SoapObjectResult;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();

	@Override
	public void userLogin(Object[] property_va) {
		String[] property_nm = { "userName", "passWord" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.LOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

			@Override
			public void soapResult(SoapObject obj) {
//			    List<UserInfo> list = new ArrayList<UserInfo>();
				SoapObject soapchild = (SoapObject) obj.getProperty(0);
				int count = soapchild.getPropertyCount();
                for (int i = 0; i < count; i++) {
                    SoapObject soapchilds = (SoapObject) soapchild.getProperty(i);
//                    UserInfo userinfo = new UserInfo();
//                    userinfo.setHeadPic(soapchilds.getProperty("HeadPic").toString());
//                    userinfo.setSport(soapchilds.getProperty("Sport").toString());
//                    userinfo.setDeid(soapchilds.getProperty("Deid").toString());
//                    userinfo.setStoragelistYN(soapchilds.getProperty("StoragelistYN").toString());
//                    userinfo.setUsign(soapchilds.getProperty("Usign").toString());
//                    userinfo.setId(soapchilds.getProperty("Id").toString());
//                    userinfo.setUserId(soapchilds.getProperty("UserId").toString());
//                    userinfo.setEmpId(soapchilds.getProperty("EmpId").toString());
//                    userinfo.setComId(soapchilds.getProperty("ComId").toString());
//                    userinfo.setRealName(soapchilds.getProperty("RealName").toString());
//                    userinfo.setBbyy(soapchilds.getProperty("Bbyy").toString());
//                    userinfo.setBbgj(soapchilds.getProperty("Bbgj").toString());
//                    userinfo.setKqgl(soapchilds.getProperty("Kqgl").toString());
//                    userinfo.setBbhy(soapchilds.getProperty("Bbhy").toString());
//                    userinfo.setVideo(soapchilds.getProperty("Video").toString());
//                    userinfo.setUserptz(soapchilds.getProperty("Userptz").toString());
//                    userinfo.setRecord(soapchilds.getProperty("Record").toString());
//                    userinfo.setSnap(soapchilds.getProperty("Snap").toString());
//                    userinfo.setMap(soapchilds.getProperty("Map").toString());
//                    userinfo.setFavor(soapchilds.getProperty("Favor").toString());
//                    userinfo.setDistance(soapchilds.getProperty("Distance").toString());
//                    userinfo.setKinder(soapchilds.getProperty("Kinder").toString());
//                    userinfo.setUpload(soapchilds.getProperty("Upload").toString());
//                    userinfo.setNews(soapchilds.getProperty("News").toString());
//                    userinfo.setYys(soapchilds.getProperty("Yys").toString());
//                    userinfo.setPay(soapchilds.getProperty("Pay").toString());
//                    userinfo.setEnddate(soapchilds.getProperty("Enddate").toString());
//                    userinfo.setPayStatus(soapchilds.getProperty("PayStatus").toString());
//                    userinfo.setStatus(soapchilds.getProperty("Status").toString());
//                    userinfo.setKinderType(soapchilds.getProperty("KinderType").toString());
//                    userinfo.setKinderName(soapchilds.getProperty("KinderName").toString());
//                    userinfo.setDid(soapchilds.getProperty("Did").toString());
//                    userinfo.setDevName(soapchilds.getProperty("DevName").toString());
//                    userinfo.setChName(soapchilds.getProperty("ChName").toString());
//                    userinfo.setDevId(soapchilds.getProperty("DevId").toString());
//                    userinfo.setIp(soapchilds.getProperty("Ip").toString());
//                    userinfo.setPort(soapchilds.getProperty("Port").toString());
//                    userinfo.setDevPort(soapchilds.getProperty("DevPort").toString());
//                    userinfo.setType(soapchilds.getProperty("Type").toString());
//                    userinfo.setUsername(soapchilds.getProperty("Username").toString());
//                    userinfo.setPassword(soapchilds.getProperty("Password").toString());
//                    userinfo.setChNo(soapchilds.getProperty("ChNo").toString());
//                    userinfo.setListCount(soapchilds.getProperty("ListCount").toString());
//                    userinfo.setListNo(soapchilds.getProperty("ListNo").toString());
//                    userinfo.setWidth(soapchilds.getProperty("Width").toString());
//                    userinfo.setHeight(soapchilds.getProperty("Height").toString());
//                    userinfo.setLongitude(soapchilds.getProperty("Longitude").toString());
//                    userinfo.setLatitude(soapchilds.getProperty("Latitude").toString());
//                    userinfo.setAdapterId(soapchilds.getProperty("AdapterId").toString());
//                    userinfo.setPtz(soapchilds.getProperty("Ptz").toString());
//                    userinfo.setZoom(soapchilds.getProperty("Zoom").toString());
//                    userinfo.setTalk(soapchilds.getProperty("Talk").toString());
//                    userinfo.setRtsp(soapchilds.getProperty("Rtsp").toString());
//                    userinfo.setStayline(soapchilds.getProperty("Stayline").toString());
//                    
//                    list.add(userinfo);
                    
                }
//				soapRes.setObj(list);
				soapRes.setCode(SOAP_UTILS.METHOD.LOGIN);
				EventCache.commandActivity.post(soapRes);
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
