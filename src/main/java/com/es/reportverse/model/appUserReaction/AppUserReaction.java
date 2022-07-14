package com.es.reportverse.model.appUserReaction;

import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AppUserReaction extends GenericModel {

    @ManyToOne
    private AppUser appUser;

}
