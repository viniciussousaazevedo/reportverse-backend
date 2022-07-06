package com.es.reportverse.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    private Long id;

    private String description;

    private String longitude;

    private String latitude;

    private Long authorId;

    private int qttComplaints;

    private int qttLikes;

    private Boolean isAuthorAnonymous = false;

    private Boolean isAvailable = true;

    public Publication(String description, String longitude, String latitude, Long authorId, int qttComplaints, int qttLikes,
    Boolean isAuthorAnonymous, Boolean isSolved, Boolean isAvailable) {
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.authorId = authorId;
        this.qttComplaints = qttComplaints;
        this.qttLikes = qttLikes;
        this.isAuthorAnonymous = isAuthorAnonymous;
        this.isAvailable = isAvailable;
    }
}
