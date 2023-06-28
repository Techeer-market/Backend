package com.teamjo.techeermarket.domain.comment.dto.request;

import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull
    private UUID productUuid;

    @NotNull
    private String content;

    @NotNull
    private UUID userUuid;
}
