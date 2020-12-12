package com.githubanalysis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "analysis_history")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class AnalysisHistory implements Serializable {

    private static final long serialVersionUID = -6235488077325203287L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "repository_name", nullable = false)
    private String repositoryName;

    @Column(name = "searched_on", nullable = false)
    @NotNull
    private OffsetDateTime searchedOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private Response response;

}
