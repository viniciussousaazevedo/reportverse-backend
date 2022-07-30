package com.es.reportverse.DTO;

import lombok.*;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

    private List<MultipartFile> mediasBytesList;

}
