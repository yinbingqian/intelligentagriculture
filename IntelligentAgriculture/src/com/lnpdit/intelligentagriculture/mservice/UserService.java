package com.lnpdit.intelligentagriculture.mservice;

import com.lnpdit.intelligentagriculture.base.framework.BaseService;
import com.lnpdit.intelligentagriculture.entity.UserEntity;
import com.lnpdit.intelligentagriculture.entity.http.request.RegisterEntity;

public interface UserService extends BaseService {
    
    void userRegister(RegisterEntity registerEntity);

    void userLogin(String username, String password);

    void getUserInfo(String userid);

    void getContacts(String userid);
    
    void updateUserInfo(UserEntity userEntity);
    
    void test(String cityCode);

}
