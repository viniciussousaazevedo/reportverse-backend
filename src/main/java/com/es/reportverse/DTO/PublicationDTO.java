package com.es.reportverse.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PublicationDTO {

    private String description;

    private String longitude;

    private String latitude;

    private Boolean isAuthorAnonymous;

}
