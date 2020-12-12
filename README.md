# GitHubAnalysis

## About Application

Login with your GitHub details, you will be forwarded to Analysis application.
This application takes owner name and repository name as input and show the analysis history of it in chronological
order.

### GitHubAPI

Using Public GitHubAPI, here I am calculating the required details for the given ownername and repositoryname, such as
number of commits, open pull requests and ReadMe file.

### RESTApi Details
```
1) LoggedInUser : http://localhost:8080/user/me .Gives Logged in user details

Sample Response

{
loginId: "username"
name: "Name"
email: null
image: "https://avatars1.githubusercontent.com/u/48855118?v=4"
location: null
}

2) GithubRepoAnalysis : GET http://localhost:8080/github-analysis/owner/{owner}/repository/{repositoryName}.

This gives github analysis results for request details

Sample Response

{
   "ownerName":"springframeworkguru",
   "repositoryName":"springbootwebapp",
   "response":{
      "repositoryUrl":"https://github.com/springframeworkguru/springbootwebapp",
      "commitsCount":12,
      "openPullRequestCount":6,
      "readmeUrl":"https://github.com/springframeworkguru/springbootwebapp/blob/master/README.md",
      "readmeContent":"IyBTcHJpbmcgQm9vdCBXZWIgQXBwbGljYXRpb24KVGhpcyByZXBvc2l0b3J5\nIGhhcyB0aGUgcHJvamVjdCBmaWxlcyBmb3IgYSB0dXRvcmlhbCBzZXJpZXMg\nb24gU3ByaW5nIEJvb3QgYXZhaWxhYmxlIGZyb20gYnkgd2Vic2l0ZSBhdCBb\nU3ByaW5nIEZyYW1ld29yayBHdXJ1XShodHRwczovL3NwcmluZ2ZyYW1ld29y\nay5ndXJ1KQoKIyMgQ2hlY2tvdXQgdGhlIGZ1bGwgdHV0b3JpYWwgaGVyZSEK\nW1NwcmluZyBCb290IC0gbWFraW5nIFNwcmluZyBGdW4gYWdhaW4hXShodHRw\nczovL3NwcmluZ2ZyYW1ld29yay5ndXJ1L3NwcmluZy1ib290LXdlYi1hcHBs\naWNhdGlvbi1wYXJ0LTEtc3ByaW5nLWluaXRpYWxpenIvKQ==\n"
   },
   "searchedOn":"2020-03-16T20:30:48.723+01:00"
}

3) AnalysisHistory; GET http://localhost:8080/github-analysis/owner/{owner}/repository/{repositoryName}/history?page
=&pageSize=

This will return past search history of the repository

Sample Response

{
   "page":0,
   "pageSize":60,
   "totalPageCount":1,
   "totalEntityCount":1,
   "entities":[
      {
         "ownerName":"springframeworkguru",
         "repositoryName":"springbootwebapp",
         "response":{
            "repositoryUrl":"https://github.com/springframeworkguru/springbootwebapp",
            "commitsCount":12,
            "openPullRequestCount":6,
            "readmeUrl":"https://github.com/springframeworkguru/springbootwebapp/blob/master/README.md",
            "readmeContent":"content"
         },
         "searchedOn":"2020-03-16T20:15:56.413+01:00"
      }
   ]
}

```


### Spec -
* Java 8
* Build with Maven
* InMemory database

### Few Design Details and Improvements-
1. I have used token based authentication as microservices are stateless.
2. Solution can be improved by converting this service into three services, ie., auth-service,
    analysis-service, analysis-frontend. This way we can separate the concerns.
3. For storing past history - I am not creating history if the current search result is same as the last
   analysed result of the same repository.
4. On Front end, I have used Angular JS1 for simplicity. While displaying past history i am displaying only recently
searched 50 results. It can be improved by implementing Pagination, but backend service supports pagination)

### Running -

This service is using maven wrapper, it is not necessary to have maven in the execution environment.

 1) unzip folder
 2) ./mvnw clean verify - to run tests
 3) ./mvnw clean package - creates executable jar
 replace ./mvnw with mvnw.cmd for windows machines.
 Note: if you don't want to use maven wrapper then use regular mvn command.

 4) java -jar ./target/github_analysis-1.0-SNAPSHOT.jar - Runs the executable jar on default port(8080)
 5) Go to -> http://localhost:8080
 6) login with git account details
