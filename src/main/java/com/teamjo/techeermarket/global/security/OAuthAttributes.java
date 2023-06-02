//package com.teamjo.techeermarket.global.security;
//
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//@Builder
//public class OAuthAttributes {
//    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//    private String picture;
//    private String social;
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
//        if("kakao".equals(registrationId)) {
//            return ofKakao("id", attributes);
//        }
//        if("google".equals(registrationId)) {
//            return ofGoogle(userNameAttributeName, attributes);
//        }
//        if("naver".equals(registrationId)){
//            return ofNaver("id", attributes);
//        }
//        return null;
//    }
//
//    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        // kakao는 kakao_account에 유저정보가 있다. (email)
//        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
//        System.out.println(kakaoAccount.toString());
//        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
//        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
//
//        return OAuthAttributes.builder()
//                .name((String) kakaoProfile.get("nickname"))
//                .email((String) kakaoAccount.get("email"))
//                .picture((String) kakaoProfile.get("profile_image_url"))
//                .social("kakao")
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
//                .social("google")
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
//
//        return OAuthAttributes.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .picture((String) response.get("profile_image"))
//                .social("naver")
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//}
