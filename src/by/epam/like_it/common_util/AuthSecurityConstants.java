package by.epam.like_it.common_util;


import by.epam.like_it.service.validator.util.ValidationNavigator;

public class AuthSecurityConstants {

    public static final String ROLE_ATTR = "role";
    public static final String ADMIN_VALUE = "responsible";
    public static final String CLIENT_VALUE = "client";
    public static final String ANONYM_VALUE = "anonymous";

    public static final String USER_ID_ATTR = "user_id";

    public static final Long ID_ANONYMUS_VALUE= -1L;

    public static final Long ID_NOT_EXIST_VALUE = -1L;
    public static final Long ID_BANNED_VALUE = -2L;

    public static final String PHOTO_PATH = "photoPath";
    public static final String DEFAULT_PHOTO_PATH = ValidationNavigator.getInstance().getDefault("User", "fotoPath");
    public static final String LOGIN = "login";

    public static final String OWNER_ID_PARAM = "owner_id";


    /*options that mark operation as available only to special group of users */
    public static final String OPT_ATR = "opt";

    /*operation  available only for users with admin role */
    public static final String OPT_ADMIN_VALUE = "admin";

    /*operation  available only for authorized users, who create entity operation with which is requested  */
    public static final String OPT_OWNER_VALUE = "owner";

    /*operation  available only for users with client role */
    public static final String OPT_CLIENT_VALUE = "client";

  /*operation  available only for users with client or admin role */
      public static final String OPT_AUTH_VALUE = "auth";

    /*operation  available only for authorized users, who create entity operation with which is requested, and for
    users with admin role  */
    public static final String OPT_OWNER_AND_ADMIN_VALUE = "responsible";

    public static final String IS_ADMIN_ATTR = "is_admin";

}

