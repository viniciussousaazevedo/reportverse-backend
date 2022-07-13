package com.es.reportverse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Midia extends GenericModel {

    @Column(columnDefinition = "text")
    private String code;

    private Long publicationId;
}
