package com.es.reportverse.model.media;

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
public class PublicationMedia extends Media {

    private Long publicationId;

    public PublicationMedia(String codeMedia, Long publicationId) {
        super(codeMedia);
        this.publicationId = publicationId;
    }
}
