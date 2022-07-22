package com.es.reportverse.model.media;

import com.es.reportverse.model.GenericModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Media extends GenericModel {

    @Column(columnDefinition = "text")
    private String code;

}
