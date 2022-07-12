package com.es.reportverse.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(columnDefinition = "text")
    private String code;

    private Long publicationId;


    public Media(String code, Long publicationId) {
        this.code = code;
        this.publicationId = publicationId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
