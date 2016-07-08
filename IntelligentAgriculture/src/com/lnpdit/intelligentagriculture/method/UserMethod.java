package com.lnpdit.intelligentagriculture.method;

import com.lnpdit.intelligentagriculture.entity.MethodEntity;

public class UserMethod {
    
    public static MethodEntity METHOD_USER_REGISTER = new MethodEntity(0,"action/register.do");
    public static MethodEntity METHOD_USER_LOGIN = new MethodEntity(1,"action/login.do");
    public static MethodEntity METHOD_USER_TEST = new MethodEntity(-1,"regionController/selectByMap.do");

}
