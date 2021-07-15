
##### GitHub API Automation testing using  **Rest Assured**, **Java** , **Junit** and **Maven**.

## Summary

*   Retrieve the Git Hub User details which covers the Task1- Scenario 1
*   Retrieve the Git Hub user Repo and then retrieving the Releases and Stars which covers the Task1- Scenario 2

### Steps to run
* mvn test -DGit_user=`username` from Terminal.
* You can pass your Gitusername  in Command line by just passing the Username as below.
Example: mvn test  -DGit_user=torvalds.
  
### Test Result
* Reports available under `target` in the project report- under surefire-reports
