package com.es.reportverse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationLocationDTO {

    private Long id;

    private String longitude;

    private String latitude;
}
