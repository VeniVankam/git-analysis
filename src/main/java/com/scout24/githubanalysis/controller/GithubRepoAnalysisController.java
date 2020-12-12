package com.scout24.githubanalysis.controller;

import com.scout24.githubanalysis.annotation.LoggedInUser;
import com.scout24.githubanalysis.dtos.AnalysisHistoryDTO;
import com.scout24.githubanalysis.security.service.UserDetailsImpl;
import com.scout24.githubanalysis.service.GithubRepoAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GithubRepoAnalysisController {

    private final GithubRepoAnalysisService githubRepoAnalysisService;

    @GetMapping("/github-analysis/owner/{owner}/repository/{repositoryName}")
    public AnalysisHistoryDTO get(@PathVariable("owner") String owner,
                                  @PathVariable("repositoryName") String repositoryName,
                                  @LoggedInUser UserDetailsImpl userDetails) {
        return githubRepoAnalysisService.getAnalysis(owner, repositoryName, userDetails.getLoginId());
    }
}
