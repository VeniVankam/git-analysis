package com.scout24.githubanalysis.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "responses")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Response implements Serializable {

    private static final long serialVersionUID = -7731972573363266467L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repository_url", nullable = false)
    private String repositoryUrl;

    @Column(name = "commits_count")
    private Integer commitsCount;

    @Column(name = "open_pull_request_count")
    private Integer openPullRequestCount;

    @Column(name = "readme_url")
    private String readmeUrl;

    @Column(name = "readme_content")
    private String readmeContent;
}
