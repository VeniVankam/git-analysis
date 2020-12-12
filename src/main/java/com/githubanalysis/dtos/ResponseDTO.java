package com.githubanalysis.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private String repositoryUrl;

    private Integer commitsCount;

    private Integer openPullRequestCount;

    private String readmeUrl;

    private String readmeContent;
}
