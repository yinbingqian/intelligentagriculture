package com.lnpdit.agriculturalmechanization.mservice;

import com.lnpdit.agriculturalmechanization.base.framework.BaseService;
import com.lnpdit.agriculturalmechanization.entity.UserEntity;
import com.lnpdit.agriculturalmechanization.entity.http.request.RegisterEntity;

public interface UserService extends BaseService {
    
    void userRegister(RegisterEntity registerEntity);

    void userLogin(String username, String password);

    void getUserInfo(String userid);

    void getContacts(String userid);
    
    void updateUserInfo(UserEntity userEntity);
    
    void test(String cityCode);

}
