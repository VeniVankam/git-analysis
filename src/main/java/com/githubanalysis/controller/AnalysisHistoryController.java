package com.githubanalysis.controller;

import com.githubanalysis.annotation.LoggedInUser;
import com.githubanalysis.dtos.AnalysisHistoryDTO;
import com.githubanalysis.dtos.PageDTO;
import com.githubanalysis.service.AnalysisHistoryService;
import com.githubanalysis.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalysisHistoryController {

    private final AnalysisHistoryService analysisHistoryService;

    @GetMapping("/github-analysis/owner/{owner}/repository/{repositoryName}/history")
    public PageDTO<AnalysisHistoryDTO> getHistory(@PathVariable("owner") String owner,
                                                  @PathVariable("repositoryName") String repositoryName,
                                                  @LoggedInUser UserDetailsImpl userDetails,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return analysisHistoryService.getHistory(owner, repositoryName, userDetails.getLoginId(), page, pageSize);
    }
}
