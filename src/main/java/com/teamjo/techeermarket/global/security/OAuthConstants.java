//package com.teamjo.techeermarket.global.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class OAuthConstants {
//    // TODO 추후 public static 방식 이외에 다른 방식 사용 가능한지 확인할 것
//
//    // registration Kakao
//    public static String RegistrationKakaoClientId;
//
//    @Value("${security.oauth2.client.registration.kakao.client-id}")
//    private void setRegistrationKakaoClientId(String temp) {
//        this.RegistrationKakaoClientId = temp;
//    }
//
//    public static String RegistrationKakaoClientSecret;
//
//    @Value("${security.oauth2.client.registration.kakao.client-secret}")
//    private void setRegistrationKakaoClientSecret(String temp) {
//        this.RegistrationKakaoClientSecret = temp;
//    }
//
//    public static String RegistrationKakaoClientName;
//
//    @Value("${security.oauth2.client.registration.kakao.client-name}")
//    private void setRegistrationKakaoClientName(String temp) {
//        this.RegistrationKakaoClientName = temp;
//    }
//
//
//    // registration Google
//    public static String RegistrationGoogleClientId;
//
//    @Value("${security.oauth2.client.registration.google.client-id}")
//    private void setRegistrationGoogleClientId(String temp) {
//        this.RegistrationGoogleClientId = temp;
//    }
//
//    @Value("${security.oauth2.client.registration.google.client-secret}")
//    public static String RegistrationGoogleClientSecret;
//
//    @Value("${security.oauth2.client.registration.google.client-secret}")
//    private void setRegistrationGoogleClientSecret(String temp) {
//        this.RegistrationGoogleClientSecret = temp;
//    }
//
//    @Value("${security.oauth2.client.registration.google.client-name}")
//    public static String RegistrationGoogleClientName;
//
//    @Value("${security.oauth2.client.registration.google.client-name}")
//    private void setRegistrationGoogleClientName(String temp) {
//        this.RegistrationGoogleClientName = temp;
//    }
//
//
//    // registration Naver
//    public static String RegistrationNaverClientId;
//
//    @Value("${security.oauth2.client.registration.naver.client-id}")
//    private void setRegistrationNaverClientId(String temp) {
//        this.RegistrationNaverClientId = temp;
//    }
//
//    public static String RegistrationNaverClientSecret;
//
//    @Value("${security.oauth2.client.registration.naver.client-secret}")
//    private void setRegistrationNaverClientSecret(String temp) {
//        this.RegistrationNaverClientSecret = temp;
//    }
//
////    public static String RegistrationNaverAuthorizationGrantType;
////
////    @Value("${security.oauth2.client.registration.naver.authorization-grant-type}")
////    private void setRegistrationNaverAuthorizationGrantType(String temp) {
////        this.RegistrationNaverAuthorizationGrantType = temp;
////    }
//
//    public static String RegistrationNaverClientName;
//
//    @Value("${security.oauth2.client.registration.naver.client-name}")
//    private void setRegistrationNaverClientName(String temp) {
//        this.RegistrationNaverClientName = temp;
//    }
//
//
//    // provider Kakao
//    public static String ProviderKakaoAuthorizationUri;
//
//    @Value("${security.oauth2.client.provider.kakao.authorization-uri}")
//    private void setProviderKakaoAuthorizationUri(String temp) {
//        this.ProviderKakaoAuthorizationUri = temp;
//    }
//
//    public static String ProviderKakaoTokenUri;
//
//    @Value("${security.oauth2.client.provider.kakao.token-uri}")
//    private void setProviderKakaoTokenUri(String temp) {
//        this.ProviderKakaoTokenUri = temp;
//    }
//
//    public static String ProviderKakaoUserInfoUri;
//
//    @Value("${security.oauth2.client.provider.kakao.user-info-uri}")
//    private void setProviderKakaoUserInfoUri(String temp) {
//        this.ProviderKakaoUserInfoUri = temp;
//    }
//
//    public static String ProviderKakaoUserNameAttribute;
//
//    @Value("${security.oauth2.client.provider.kakao.user-name-attribute}")
//    private void setProviderKakaoUserNameAttribute(String temp) {
//        this.ProviderKakaoUserNameAttribute = temp;
//    }
//
//    public static String ProviderKakaoJwkSetUri;
//
//    @Value("${security.oauth2.client.provider.kakao.jwk-set-uri}")
//    private void setProviderKakaoJwkSetUri(String temp) {
//        this.ProviderKakaoJwkSetUri = temp;
//    }
//
//
//    // provider Google
//    public static String ProviderGoogleAuthorizationUri;
//
//    @Value("${security.oauth2.client.provider.google.authorization-uri}")
//    private void setProviderGoogleAuthorizationUri(String temp) {
//        this.ProviderGoogleAuthorizationUri = temp;
//    }
//
//    public static String ProviderGoogleTokenUri;
//
//    @Value("${security.oauth2.client.provider.google.token-uri}")
//    private void setProviderGoogleTokenUri(String temp) {
//        this.ProviderGoogleTokenUri = temp;
//    }
//
//    public static String ProviderGoogleUserInfoUri;
//
//    @Value("${security.oauth2.client.provider.google.user-info-uri}")
//    private void setProviderGoogleUserInfoUri(String temp) {
//        this.ProviderGoogleUserInfoUri = temp;
//    }
//
//    public static String ProviderGoogleJwkSetUri;
//
//    @Value("${security.oauth2.client.provider.google.jwk-set-uri}")
//    private void setProviderGoogleJwkSetUri(String temp) {
//        this.ProviderGoogleJwkSetUri = temp;
//    }
//
//
//    // provider Naver
//    public static String ProviderNaverAuthorizationUri;
//
//    @Value("${security.oauth2.client.provider.naver.authorization_uri}")
//    private void setProviderNaverAuthorizationUri(String temp) {
//        this.ProviderNaverAuthorizationUri = temp;
//    }
//
//    public static String ProviderNaverTokenUri;
//
//    @Value("${security.oauth2.client.provider.naver.token_uri}")
//    private void setProviderNaverTokenUri(String temp) {
//        this.ProviderNaverTokenUri = temp;
//    }
//
//    public static String ProviderNaverUserInfoUri;
//
//    @Value("${security.oauth2.client.provider.naver.user-info-uri}")
//    private void setProviderNaverUserInfoUri(String temp) {
//        this.ProviderNaverUserInfoUri = temp;
//    }
//
//    public static String ProviderNaverUserNameAttribute;
//
//    @Value("${security.oauth2.client.provider.naver.user_name_attribute}")
//    private void setProviderNaverUserNameAttribute(String temp) {
//        this.ProviderNaverUserNameAttribute = temp;
//    }
//
//    public static String ProviderNaverJwkSetUri;
//
//    @Value("${security.oauth2.client.provider.naver.jwk-set-uri}")
//    private void setProviderNaverJwkSetUri(String temp) {
//        this.ProviderNaverJwkSetUri = temp;
//    }
//
//}
