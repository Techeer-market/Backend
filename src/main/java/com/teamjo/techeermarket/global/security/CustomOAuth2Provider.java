//package com.teamjo.techeermarket.global.security;
//
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
//
//public enum CustomOAuth2Provider {
//
//    KAKAO {
//        @Override
//        public ClientRegistration.Builder getBuilder (String registrationId){
//            ClientRegistration.Builder builder = getBuilder(registrationId,
//                    ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//            builder.scope("profile_nickname", "account_email")
//                    .clientId(OAuthConstants.RegistrationKakaoClientId)
//                    .clientSecret(OAuthConstants.RegistrationKakaoClientSecret)
//                    .authorizationUri(OAuthConstants.ProviderKakaoAuthorizationUri)
//                    .tokenUri(OAuthConstants.ProviderKakaoTokenUri)
//                    .userInfoUri(OAuthConstants.ProviderKakaoUserInfoUri)
//                    .jwkSetUri(OAuthConstants.ProviderKakaoJwkSetUri)
//                    .userNameAttributeName(OAuthConstants.ProviderKakaoUserNameAttribute)
//                    .clientName(OAuthConstants.RegistrationKakaoClientName)
//                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
//            return builder;
//        }
//    },
//    GOOGLE {
//        @Override
//        public ClientRegistration.Builder getBuilder (String registrationId){
//            ClientRegistration.Builder builder = getBuilder(registrationId,
//                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_LOGIN_REDIRECT_URL);
//            builder.scope("profile", "email")
//                    .clientId(OAuthConstants.RegistrationGoogleClientId)
//                    .clientSecret(OAuthConstants.RegistrationGoogleClientSecret)
//                    .authorizationUri(OAuthConstants.ProviderGoogleAuthorizationUri)
//                    .tokenUri(OAuthConstants.ProviderGoogleTokenUri)
//                    .userInfoUri(OAuthConstants.ProviderGoogleUserInfoUri)
//                    .jwkSetUri(OAuthConstants.ProviderGoogleJwkSetUri)
//                    .userNameAttributeName(IdTokenClaimNames.SUB)
//                    .clientName(OAuthConstants.RegistrationGoogleClientName);
//            return builder;
//        }
//    },
//    NAVER {
//        @Override
//        public ClientRegistration.Builder getBuilder (String registrationId){
//            ClientRegistration.Builder builder = getBuilder(registrationId,
//                    ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL);
//            builder.scope("name", "email", "profile_image")
//                    .clientId(OAuthConstants.RegistrationNaverClientId)
//                    .clientSecret(OAuthConstants.RegistrationNaverClientSecret)
//                    .authorizationUri(OAuthConstants.ProviderNaverAuthorizationUri)
//                    .tokenUri(OAuthConstants.ProviderNaverTokenUri)
//                    .userInfoUri(OAuthConstants.ProviderNaverUserInfoUri)
//                    .jwkSetUri(OAuthConstants.ProviderNaverJwkSetUri)
//                    .userNameAttributeName(OAuthConstants.ProviderNaverUserNameAttribute)
//                    .clientName(OAuthConstants.RegistrationNaverClientName);
//            return builder;
//        }
//    } ;
//
//    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}";
//
//    protected final ClientRegistration.Builder getBuilder (String registrationId, ClientAuthenticationMethod method, String redirectUri){
//        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
//        builder.clientAuthenticationMethod(method);
//        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
//        builder.redirectUriTemplate(redirectUri);
//        return builder;
//    }
//
//    public abstract ClientRegistration.Builder getBuilder (String registrationId);
//}
