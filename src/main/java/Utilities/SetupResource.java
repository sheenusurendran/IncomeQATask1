package Utilities;

import java.util.Scanner;

public class SetupResource {

    public static String path_user = "users/";
    public static String path_repo = "/repos";
    public static String path_release = "/git/refs/tags";
    public static String baseURI = "https://api.github.com/";

    public static String getUserName() {
        System.out.println("Enter your git username:");
        Scanner input = new Scanner(System.in);
        return  input.nextLine();
    }
}

