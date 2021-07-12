package com.income.qa.git;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
//import org.junit.Test;
import org.junit.jupiter.api.Order;

import Utilities.SaveJsonResponse;
import Utilities.SetupResource;

public class GetUserGitDetailsTest {

    private static String response;
    private static String gitUserName = SetupResource.getUserName();
    private static String basepathUser = SetupResource.path_user + gitUserName;
    private static String basePathRepo = SetupResource.path_user + gitUserName + SetupResource.path_repo;
    private static String basePathReleases = null;
    private static String baseURI = SetupResource.baseURI;
    private static JsonPath jsn = null;


    @Test
    public void displayAccountDetails() {

        response = given().baseUri(baseURI).when().get(basepathUser).then().assertThat().statusCode(200).extract()
                .response().asString();
        JsonPath jsn = SaveJsonResponse.fromRawToJson(response);
        String gitHubUserName = jsn.getString("login");
        String userFullName = jsn.getString("name");
        String accountCreatedDate = jsn.getString("created_at");

        System.out.println(" GitHubUserName =" + " " + gitHubUserName);
        System.out.println(" UserFullName =" + " " + userFullName);
        System.out.println(" Account Created Date =" + " " + accountCreatedDate);

    }

    @Test
    public void displayRepoDetails() {

        response = given().baseUri(baseURI).when().get(basePathRepo).then().assertThat().statusCode(200).extract()
                .response().asString();
        JsonPath jsn = SaveJsonResponse.fromRawToJson(response);
        int responseSize;
        int noofRepos = jsn.getInt("name.size()");

        for (int i = 0; i < noofRepos; i++) {
            String nameofRepo = jsn.get("name[" + i + "]");
            basePathReleases = SetupResource.path_repo + "/" + gitUserName + "/" + nameofRepo
                    + SetupResource.path_release;
            String getReleases = given().baseUri(baseURI).when().get(basePathReleases).then().extract().response()
                    .asString();
            JsonPath jsn1 = SaveJsonResponse.fromRawToJson(getReleases);
            String noofStars = jsn.get("stargazers_count[" + i + "]").toString();
            System.out.println(" Repository " + (i + 1) + " = " + nameofRepo);
            System.out.println('\u2022' + " " + "Stars = " + noofStars);
            if ((jsn1.get("message").toString().equalsIgnoreCase("Not Found")
                    || jsn1.get("message").toString().equalsIgnoreCase("Git Repository is empty."))) {
                System.out.println('\u2022' + " " + "Releases = " + 0);
            } else {
                responseSize = jsn1.getInt("ref.size()");
                System.out.println('\u2022' + " " + "Releases = " + responseSize);
            }

        }

    }


}
