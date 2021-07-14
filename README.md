API Testing for three GitHub API GET services are done using Rest Assured, Java , Junit and Maven.

GetUserGitDetailsTest.java is the Junit test which has two methods
***  To Retrieve the Git Hub User details which covers the Task1- Scenario 1
***  To Retrieve the Git Hub user Repo and then retrieving the Releases and Stars which covers the Task1- Scenario 2


#### 1. Run the Test
* You are ready to test the services using mvn
* You can pass your Gitusername as in Command line by just passing the Username as below.
Example: 
mvn test  -DGit_user=torvalds


#### 2. Test Result
* Reports available under `target` in the project report- under surefire-reports
####
3. Assertion are kept to check for StatusCode 200.

