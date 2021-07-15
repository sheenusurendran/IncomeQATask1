package com.income.qa.git;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
import com.income.utilities.*;

public class GetUserGitDetailsTest {

    public static Response apiResponse;
    public static String response;
    public static String gitUserName = SetupResource.getUserName();
    public static String basepathUser = SetupResource.path_user + gitUserName;
    public static String basePathRepo = SetupResource.path_user + gitUserName + SetupResource.path_repo;
    public static String basePathReleases = null;
    public static String baseURI = SetupResource.baseURI;

    @Test
    public void displayAccountDetails() {
        JsonPath accountDetails = null;
        try {
            accountDetails = fetchData(basepathUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String apiDateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        System.out.println("- GitHubUserName =" + " " + accountDetails.getString("login"));
        System.out.println(" - UserFullName =" + " " + accountDetails.getString("name"));
        System.out.println(" - Account Created =" + " " + DateFormat.basicFormat(accountDetails.getString("created_at"),
                apiDateFormat));
    }

    @Test
    public void displayRepoDetails() {

        JsonPath repositories = null;
        try {
            repositories = fetchData(basePathRepo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getReleaseDetails(repositories);
    }


    private JsonPath fetchReleases(String nameofRepo) throws Exception {
        basePathReleases = SetupResource.path_repo + "/" + gitUserName + "/" + nameofRepo
                + SetupResource.path_release;
        String releases = given().baseUri(baseURI).when().get(basePathReleases).then().extract().response()
                .asString();
        if (releases == null || releases.length() == 0) {
            throw new Exception("No Releases Found");
        }
        return SaveJsonResponse.fromRawToJson(releases);
    }

    private JsonPath fetchData(String getPath) throws Exception {
        apiResponse = given().baseUri(baseURI).
                when().get(getPath);
        response = apiResponse.then().assertThat().statusCode(200).extract().response().asString();
        if (response == null || response.length() == 0) {
            throw new Exception("No Data Found");
        }
        return SaveJsonResponse.fromRawToJson(response);
    }

    private void getReleaseDetails(JsonPath repositories) {
        int responseSize;
        int noofRepos = repositories.getInt("name.size()");

        for (int i = 0; i < noofRepos; i++) {
            String nameOfRepo = repositories.get("name[" + i + "]");
            JsonPath releases = null;
            try {
                releases = fetchReleases(nameOfRepo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printRepoDetails(repositories, i, nameOfRepo, releases);
        }
    }

    private void printRepoDetails(JsonPath repositories, int i, String nameOfRepo, JsonPath releases) {
        int responseSize;
        String noofStars = repositories.get("stargazers_count[" + i + "]").toString();
        System.out.println(" Repository " + (i + 1) + " = " + nameOfRepo);
        System.out.println(" " + "Stars = " + noofStars);
        if ((releases.get("message").toString().equalsIgnoreCase("Not Found")
                || releases.get("message").toString().equalsIgnoreCase("Git Repository is empty."))) {
            System.out.println(" " + "Releases = " + 0);
        } else {
            responseSize = releases.getInt("ref.size()");
            System.out.println(" " + "Releases = " + responseSize);
        }
    }
}
