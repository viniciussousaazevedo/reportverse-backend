package com.es.reportverse.model;

import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Publication extends GenericModel {

    private String description;

    private String longitude;

    private String latitude;

    private Long authorId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AppUserLike> likes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AppUserReport> reports;

    private Boolean isAuthorAnonymous;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<AppUserComment> comments;

    private Boolean isAvailable;

    private Boolean isResolved;

    private Date isResolvedDate;

    private Boolean needsReview;

    private Date creationDate;

}