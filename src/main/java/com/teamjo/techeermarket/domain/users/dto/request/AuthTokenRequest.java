package com.teamjo.techeermarket.domain.users.dto.request;


import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenRequest {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("redirect_uri")
    private String redirectUri;

    private String code;

    @SerializedName("client_secret")
    private String clientSecret;
}
