package com.githubanalysis.service;

import com.githubanalysis.domain.Response;
import com.githubanalysis.util.PagingUtils;
import com.githubanalysis.domain.AnalysisHistory;
import com.githubanalysis.dtos.AnalysisHistoryDTO;
import com.githubanalysis.dtos.GitHubRepoAnalysisDTO;
import com.githubanalysis.dtos.PageDTO;
import com.githubanalysis.dtos.ResponseDTO;
import com.githubanalysis.repository.AnalysisHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalysisHistoryService {

    private final AnalysisHistoryRepository analysisHistoryRepository;

    public AnalysisHistoryDTO saveHistory(String loginId, GitHubRepoAnalysisDTO analysisDTO) {
        AnalysisHistory analysisHistory = analysisHistoryRepository.findFirstByLoginIdAndOwnerNameAndRepositoryNameOrderBySearchedOnDesc(loginId, analysisDTO.getOwnerName(), analysisDTO.getRepository().getName());
        if (analysisHistory != null && analysisHistory.getResponse().equals(toResponse(analysisDTO))) {
            analysisHistory.setSearchedOn(OffsetDateTime.now());
            analysisHistoryRepository.save(analysisHistory);
        } else {
            analysisHistory = new AnalysisHistory();
            analysisHistory.setLoginId(loginId);
            analysisHistory.setOwnerName(analysisDTO.getOwnerName());
            analysisHistory.setRepositoryName(analysisDTO.getRepository().getName());
            analysisHistory.setSearchedOn(OffsetDateTime.now());
            analysisHistory.setResponse(toResponse(analysisDTO));
            analysisHistoryRepository.save(analysisHistory);
        }
        return toAnalysisHistoryDTO(analysisHistory);
    }

    public PageDTO<AnalysisHistoryDTO> getHistory(String owner, String repositoryName, String loginId, int page, int pageSize) {
        Pageable pageable = createPageRequest(page, pageSize);
        Page<AnalysisHistory> history = analysisHistoryRepository.findByLoginIdAndOwnerNameAndRepositoryName(loginId, owner, repositoryName, pageable);
        return PagingUtils.createPageDTO(history, this::toAnalysisHistoryDTO);
    }

    private AnalysisHistoryDTO toAnalysisHistoryDTO(AnalysisHistory analysisHistory) {
        return AnalysisHistoryDTO.builder()
                .ownerName(analysisHistory.getOwnerName())
                .repositoryName(analysisHistory.getRepositoryName())
                .response(toResponseDTO(analysisHistory.getResponse()))
                .searchedOn(analysisHistory.getSearchedOn())
                .build();
    }

    private Response toResponse(GitHubRepoAnalysisDTO analysisDTO) {
        Response response = new Response();
        response.setRepositoryUrl(analysisDTO.getRepository().getUrl());
        response.setCommitsCount(analysisDTO.getTotalCommits());
        response.setOpenPullRequestCount(analysisDTO.getTotalOpenPullRequests());
        response.setReadmeUrl(analysisDTO.getReadMe().getUrl());
        response.setReadmeContent(analysisDTO.getReadMe().getContent());
        return response;
    }

    private ResponseDTO toResponseDTO(Response response) {
        ResponseDTO responseDTO = new ResponseDTO();
        BeanUtils.copyProperties(response, responseDTO);
        return responseDTO;
    }

    private PageRequest createPageRequest(final int page, final int pageSize) {
        Sort.Order searchedOnSorting = new Sort.Order(Sort.Direction.fromString(Sort.Direction.DESC.name()), "searchedOn");
        return PageRequest.of(page, pageSize, Sort.by(searchedOnSorting));
    }
}
