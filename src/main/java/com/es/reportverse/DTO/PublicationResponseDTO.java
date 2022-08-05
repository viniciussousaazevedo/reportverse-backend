package com.es.reportverse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublicationResponseDTO {

    private Long id;

    private String description;

    private String locationDescription;

    private String longitude;

    private String latitude;

    private String authorName;

    private List<LikeDTO> likes;

    private List<ReportDTO> reports;

    private List<MediaDTO> medias;

    private Date creationDate;

    private List<CommentDTO> comments;

    private Boolean isAvailable;

    private Boolean isResolved;

    private Date isResolvedDate;

    private Boolean needsReview;

    private Boolean isAuthorAnonymous;

}
