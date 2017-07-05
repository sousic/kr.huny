package kr.huny.common;

/**
 * Created by sousic on 2017-03-24.
 */
public class CommonRole {
    public final static String ROLE_NAME_ROOT = "ROLE_ROOT";
    public final static String ROLE_NAME_ADMIN = "ROLE_ADMIN";
    public final static String ROLE_NAME_USER = "ROLE_USER";
    public final static String ROLE_NAME_GUEST = "ROLE_GUEST";

    public final static int ROLE_NUMBER_ROOT  = 255;
    public final static int ROLE_NUMBER_ADMIN = 100;
    public final static int ROLE_NUMBER_USER = 10;
    public final static int ROLE_NUMBER_GUEST = 1;

    public static String getRoleName(Integer roleNumber)
    {
        String roleName = "";
        switch (roleNumber) {
            case ROLE_NUMBER_ROOT:
                roleName = ROLE_NAME_ROOT;
                break;
            case ROLE_NUMBER_ADMIN:
                roleName = ROLE_NAME_ADMIN;
                break;
            case ROLE_NUMBER_USER:
                roleName = ROLE_NAME_USER;
                break;
            case ROLE_NUMBER_GUEST:
                roleName = ROLE_NAME_GUEST;
                break;
            default:
                break;
        }
        return roleName;
    }

    public static Integer getRoleNumber(String roleName)
    {
        Integer roleNumber = 0;
        switch (roleName) {
            case ROLE_NAME_ROOT:
                roleNumber = ROLE_NUMBER_ROOT;
                break;
            case ROLE_NAME_ADMIN:
                roleNumber = ROLE_NUMBER_ADMIN;
                break;
            case ROLE_NAME_USER:
                roleNumber = ROLE_NUMBER_USER;
                break;
            case ROLE_NAME_GUEST:
                roleNumber = ROLE_NUMBER_GUEST;
                break;
            default:
                break;
        }
        return roleNumber;
    }
}
