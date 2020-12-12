package com.githubanalysis.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisHistoryDTO {

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    private ResponseDTO response;

    private OffsetDateTime searchedOn;
}
