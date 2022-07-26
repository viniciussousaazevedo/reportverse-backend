package com.es.reportverse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AppUserComment extends GenericModel{

    @ManyToOne
    private AppUser appUser;

    private String text;

    private Boolean isAuthorAnonymous = false;
}
