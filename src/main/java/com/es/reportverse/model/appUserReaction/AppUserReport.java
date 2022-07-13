package com.es.reportverse.model.appUserReaction;

import com.es.reportverse.model.AppUser;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity
public class AppUserReport extends AppUserReaction {

    public AppUserReport(AppUser user) {
        super(user);
    }

}
