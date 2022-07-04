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

    private int authorId;

    private int qttComplaints;

    private int qttLikes;

    private Boolean isAuthorAnonymous = false;

    private Boolean isAvailable = true;

    public Publication(String description, String longitude, String latitude, int authorId, int qttComplaints, int qttLikes,
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

    public String getDescription() {
        return description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getQttComplaints() {
        return qttComplaints;
    }

    public void setQttComplaints(int qttComplaints) {
        this.qttComplaints = qttComplaints;
    }

    public int getQttLikes() {
        return qttLikes;
    }

    public void setQttLikes(int qttLikes) {
        this.qttLikes = qttLikes;
    }

    public boolean getIsAuthorAnonymous() {
        return isAuthorAnonymous;
    }   

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
