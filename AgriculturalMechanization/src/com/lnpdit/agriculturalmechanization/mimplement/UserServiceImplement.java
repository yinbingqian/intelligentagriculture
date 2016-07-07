package com.lnpdit.agriculturalmechanization.mimplement;

import com.lnpdit.agriculturalmechanization.base.framework.BaseServiceImplement;
import com.lnpdit.agriculturalmechanization.common.Instance;
import com.lnpdit.agriculturalmechanization.config.MLog;
import com.lnpdit.agriculturalmechanization.entity.UserEntity;
import com.lnpdit.agriculturalmechanization.entity.http.request.RegisterEntity;
import com.lnpdit.agriculturalmechanization.http.BaseRdaHttp;
import com.lnpdit.agriculturalmechanization.http.MAsycnHttpHandler;
import com.lnpdit.agriculturalmechanization.http.MHttpClient;
import com.lnpdit.agriculturalmechanization.method.UserMethod;
import com.lnpdit.agriculturalmechanization.mservice.UserService;
import com.lnpdit.agriculturalmechanization.utils.JsonUtil;
import com.loopj.android.http.RequestParams;

public class UserServiceImplement extends BaseServiceImplement implements
        UserService {

    @Override
    public void userRegister(RegisterEntity registerEntity) {
        String json = Instance.gson.toJson(registerEntity);
        MHttpClient.post(UserMethod.METHOD_USER_REGISTER.getMethodUrl(), json, new MAsycnHttpHandler(this, Method("SetUserIdentfyInfo")) {
            @Override
            public void onSuccess(String json) {
                MLog.I(json);
                if (JsonUtil.isSuccess(json)) {
                    postSuccessData("");
                } else {
                    postServerError(JsonUtil.getErrorCode(json));
                }
            }
        });

    }

    @Override
    public void userLogin(String username, String password) {
        RequestParams req = new RequestParams();
        // TODO Auto-generated method stub

    }

    @Override
    public void getUserInfo(String userid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getContacts(String userid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateUserInfo(UserEntity userEntity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void test(String cityCode) {
//        String json = Instance.gson.toJson(registerEntity);
        RequestParams req = new RequestParams();
        req.put("cityCode", cityCode);
        
        
        MHttpClient.post(UserMethod.METHOD_USER_TEST.getMethodUrl(), req, new MAsycnHttpHandler(this, Method("test")) {
            @Override
            public void onSuccess(String json) {
                MLog.I(json);
                if (JsonUtil.isSuccess(json)) {
                    postSuccessData(json);
                } else {
                    postServerError(JsonUtil.getErrorCode(json));
                }
            }
        });
        
    }

    @Override
    public BaseRdaHttp This() {
        // TODO Auto-generated method stub
        return this;
    }

}
