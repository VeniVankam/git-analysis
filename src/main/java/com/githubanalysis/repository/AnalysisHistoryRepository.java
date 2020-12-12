package com.githubanalysis.repository;

import com.githubanalysis.domain.AnalysisHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnalysisHistoryRepository extends PagingAndSortingRepository<AnalysisHistory, Long> {

    AnalysisHistory findFirstByLoginIdAndOwnerNameAndRepositoryNameOrderBySearchedOnDesc(String loginId, String ownerName, String repositoryName);

    Page<AnalysisHistory> findByLoginIdAndOwnerNameAndRepositoryName(String loginId, String ownerName, String repositoryName, Pageable pageable);

}
