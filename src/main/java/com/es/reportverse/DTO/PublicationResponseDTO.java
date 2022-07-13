package com.es.reportverse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicationResponseDTO {

    private Long id;

    private String description;

    private String longitude;

    private String latitude;

    private Long authorId;

    private List<LikeDTO> likes;

    private List<MidiaDTO> medias;

    private List<ReportDTO> reports;

}
