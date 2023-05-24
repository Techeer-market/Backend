package com.teamjo.techeermarket.domain.comment.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CommentResponseDto {

    @NotNull
    private UUID commentUuid;

    private UUID productUuid;

    @NotNull
    private String content;

    private String createdDate;

    private String modifiedDate;

}
