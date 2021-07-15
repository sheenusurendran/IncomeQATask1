package com.income.utilities;

public class SetupResource {

    public static String path_user = "users/";
    public static String path_repo = "/repos";
    public static String path_release = "/git/refs/tags";
    public static String baseURI = "https://api.github.com/";
    public static String userName = " ";

/*Below methods accepts the gitusername via
command line and need to pass the value via mvn command
 */
    public static String getUserName() {
        userName = System.getProperty("Git_user");
        return userName;
    }
}

