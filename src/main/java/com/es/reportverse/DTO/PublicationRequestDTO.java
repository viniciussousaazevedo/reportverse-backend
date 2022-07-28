package com.es.reportverse.DTO;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PublicationRequestDTO {

    private String description;

    private String longitude;

    private String latitude;

    private Boolean isAuthorAnonymous;

    private List<byte[]> mediasBytesList;

}
