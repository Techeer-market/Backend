package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.jwt.JwtTokenProvider;
import com.teamjo.techeermarket.global.jwt.TokenInfo;
import com.teamjo.techeermarket.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersApiService {

    public final UserRepository userRepository;

    public final UsersMapper usersMapper;

    public final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final S3Service s3Service;

    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public Users signup(UsersSignupRequestDto usersSignupRequestDto) {
        try {
            // 이메일 중복 확인
            if(userRepository.existsByEmailAndSocial(usersSignupRequestDto.getEmail(), "local")){
                return null;
            } else {
                String thumbnailImageUrl;

                // 파일 존재 여부 확인 후 thumbnailImageUrl 설정
                if(usersSignupRequestDto.getThumbnailImage() != null && !usersSignupRequestDto.getThumbnailImage().isEmpty()) {
                    thumbnailImageUrl = s3Service.uploadImage(usersSignupRequestDto.getThumbnailImage());
                } else {
                    thumbnailImageUrl = "https://techeermarket-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/bfe7def1-2f96-4a0e-a4a8-0255ee6bd874/default_profile.png";
                }

                usersSignupRequestDto.setThumbnailImageUrl(thumbnailImageUrl);
                String password = passwordEncoder.encode(usersSignupRequestDto.getPassword());
                usersSignupRequestDto.setPassword(password);

                return userRepository.save(usersMapper.toEntity(usersSignupRequestDto));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< refs/remotes/origin/develop
<<<<<<< refs/remotes/origin/develop
        return userRepository.save(usersMapper.toEntity(usersRequestDto));
    }
    //유저 정보 세션 처리 예정
    @Transactional
    public Users update(Users updateUser){
        Users existingUser = userRepository.findById(updateUser.getId())
                .orElseThrow(()-> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        existingUser.update(updateUser);
        return userRepository.save(existingUser);
=======

        return userRepository.save(usersMapper.toEntity(usersRequestDto));
>>>>>>> (TM-10) 카카오,구글,네이버 로그인
    }
    //유저 정보 세션 처리 예정
    @Transactional
    public Users delete(Users deleteUser){
        Users existingUser = userRepository.findById(deleteUser.getId())
                .orElseThrow(()-> new RuntimeException("유저 정보를 찾알 수 없습니다."));
        existingUser.setDeleted(true);
//        existingUser.delete(deleteUser);
        return userRepository.save(existingUser);
=======
        return null;
>>>>>>> (TM-09) 로컬 회원가입에 email 중복여부 체크 로직 추가 (B)
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

}
