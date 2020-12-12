package com.githubanalysis.client;

import com.githubanalysis.dtos.GithubRepoInfo;
import com.githubanalysis.dtos.ReadMeInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("github")
public interface GithubApi {

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{ownerUserName}/{repoName}")
    GithubRepoInfo getRepoInfo(@PathVariable("ownerUserName") String owner, @PathVariable("repoName") String repoName);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{ownerUserName}/{repoName}/pulls")
    List<Object> getOpenPullRequests(@PathVariable("ownerUserName") String owner, @PathVariable("repoName") String repoName);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{ownerUserName}/{repoName}/readme")
    ReadMeInfoDTO getReadMe(@PathVariable("ownerUserName") String owner, @PathVariable("repoName") String repoName);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{ownerUserName}/{repoName}/commits")
    ResponseEntity<List<Object>> getCommits(@PathVariable("ownerUserName") String owner,
                                            @PathVariable("repoName") String repoName,
                                            @RequestParam("per_page") int perPage);
}
