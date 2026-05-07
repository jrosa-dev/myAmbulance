package com.jrosa.myAmb.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    //    Endpoints
    public static final String MAPPING_LOGIN = "/login";
    public static final String MAPPING_SIGNUP = "/signup";
    public static final String MAPPING_HOME = "/inicio";



    //    Login and Sign Up
    public static final String ERROR_USERNAME_TAKEN = "error.username.taken";
    public static final String ERROR_USERNAME_INVALID = "error.username.invalid";
    public static final String SUCCESS_ACCOUNT_CREATED = "success.account.created";



    public static final RoleName DEFAULT_ROLE = RoleName.ROLE_UNASSIGNED;
    public enum RoleName {
        ROLE_UNASSIGNED,
        ROLE_INTERN,
        ROLE_ACTIVE,
        ROLE_CHIEF,
        ROLE_COMMAND
    }
}
