package com.scout24.githubanalysis.controller;

import com.scout24.githubanalysis.annotation.LoggedInUser;
import com.scout24.githubanalysis.dtos.AnalysisHistoryDTO;
import com.scout24.githubanalysis.dtos.PageDTO;
import com.scout24.githubanalysis.security.service.UserDetailsImpl;
import com.scout24.githubanalysis.service.AnalysisHistoryService;
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
