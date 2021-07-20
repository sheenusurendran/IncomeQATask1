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
        JsonPath repositories;
        int noofRepos;
        try {
            repositories = fetchData(basePathRepo);
            noofRepos = repositories.getInt("name.size()");
            for (int repoIndex = 0; repoIndex < noofRepos; repoIndex++) {
                String nameOfRepo = repositories.get("name[" + repoIndex + "]");
                String noOfStars = repositories.get("stargazers_count[" + repoIndex + "]").toString();
                JsonPath releases = fetchReleases(nameOfRepo);
                printRepoDetails(noOfStars, repoIndex, nameOfRepo, releases);
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private JsonPath fetchReleases(String nameofRepo) throws Exception {
        basePathReleases = SetupResource.path_repo + "/" + gitUserName + "/" + nameofRepo
                + SetupResource.path_release;
        String releases = given().baseUri(baseURI).when().get(basePathReleases).then().extract().response()
                .asString();
        if (releases == null || releases.length() == 0) {
            throw new Exception("No Releases Found");
        }
        return ParseJson.fromRawToJson(releases);
    }

    private JsonPath fetchData(String getPath) throws Exception {
        apiResponse = given().baseUri(baseURI).
                when().get(getPath);
        response = apiResponse.then().assertThat().statusCode(200).extract().response().asString();
        if (response == null || response.length() == 0) {
            throw new Exception("No Data Found");
        }
        return ParseJson.fromRawToJson(response);
    }

    private void printRepoDetails(String noOfStars, int repoIndex, String nameOfRepo, JsonPath releases) {
        int responseSize;
        System.out.println(" Repository " + (repoIndex + 1) + " = " + nameOfRepo);
        System.out.println(" " + "Stars = " + noOfStars);
        if ((releases.get("message").toString().equalsIgnoreCase("Not Found")
                || releases.get("message").toString().equalsIgnoreCase("Git Repository is empty."))) {
            System.out.println(" " + "Releases = " + 0);
        } else {
            responseSize = releases.getInt("ref.size()");
            System.out.println(" " + "Releases = " + responseSize);
        }
    }
}
