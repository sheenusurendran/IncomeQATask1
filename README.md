##### GitHub API Automation testing using  **Rest Assured**, **Java** , **Junit** and **Maven**.

## Summary

* Accept a git username and display following information.
    1) Username = github_username
    2) Name = Name of the user
    3) Created on = date when user account created
* And Display below information for each of the repositories owned by this user,
  1) Repository 1 = name of repository
  2) Stars = number of stars
  3) Releases = number of releases

### Steps to run

* mvn test -DGit_user=`username` from Terminal.
* You can pass your Gitusername in Command line by just passing the Username as below.

Example:
mvn test -DGit_user=torvalds

### Test Result

* Reports available under `target` in the project report- under surefire-reports
