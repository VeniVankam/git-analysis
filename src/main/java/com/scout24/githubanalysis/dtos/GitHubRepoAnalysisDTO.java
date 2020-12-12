package com.scout24.githubanalysis.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GitHubRepoAnalysisDTO {
    private GithubRepoInfo repository;
    private String ownerName;
    private Integer totalCommits;
    private Integer totalOpenPullRequests;
    private ReadMeInfoDTO readMe;
}
