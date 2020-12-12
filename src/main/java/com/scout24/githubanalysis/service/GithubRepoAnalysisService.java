package com.scout24.githubanalysis.service;

import com.scout24.githubanalysis.client.GithubApi;
import com.scout24.githubanalysis.dtos.AnalysisHistoryDTO;
import com.scout24.githubanalysis.dtos.GitHubRepoAnalysisDTO;
import com.scout24.githubanalysis.dtos.GithubRepoInfo;
import com.scout24.githubanalysis.dtos.ReadMeInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.scout24.githubanalysis.util.HttpHeaderUtils.parseLinkHttpHeaderAndFetchLastPageCount;

@Service
@RequiredArgsConstructor
public class GithubRepoAnalysisService {

    private final GithubApi githubApi;
    private final AnalysisHistoryService analysisHistoryService;

    public AnalysisHistoryDTO getAnalysis(final String owner, final String repository, final String loginId) {
        GithubRepoInfo repoInfo = githubApi.getRepoInfo(owner, repository);
        ReadMeInfoDTO readMeInfoDTO = githubApi.getReadMe(owner, repository);
        int totalOpenPullRequests = githubApi.getOpenPullRequests(owner, repository).size();
        Integer totalCommits = getCommitCount(owner, repository);
        GitHubRepoAnalysisDTO analysisDTO = GitHubRepoAnalysisDTO.builder()
                .repository(repoInfo)
                .readMe(readMeInfoDTO)
                .ownerName(owner)
                .totalOpenPullRequests(totalOpenPullRequests)
                .totalCommits(totalCommits)
                .build();
        return analysisHistoryService.saveHistory(loginId, analysisDTO);
    }

    private Integer getCommitCount(final String owner, final String repository) {
        ResponseEntity<List<Object>> commits = githubApi.getCommits(owner, repository, 1);
        HttpHeaders responseHeaders = commits.getHeaders();
        return parseLinkHttpHeaderAndFetchLastPageCount(responseHeaders.get("Link").get(0));
    }

}
