package com.es.reportverse.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Publication extends GenericModel {

    private String description;

    private String longitude;

    private String latitude;

    private Long authorId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AppUserLike> likes;

    private Boolean isAuthorAnonymous = false;

    private Boolean isAvailable = true;
}
