package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-05T00:27:49+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Users toEntity(SignUpRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.name( dto.getName() );
        users.email( dto.getEmail() );
        users.password( dto.getPassword() );
        users.birthday( dto.getBirthday() );
        users.social( dto.getSocial() );

        return users.build();
    }

    @Override
    public SignUpRequestDto toDto(Users entity) {
        if ( entity == null ) {
            return null;
        }

        SignUpRequestDto.SignUpRequestDtoBuilder signUpRequestDto = SignUpRequestDto.builder();

        signUpRequestDto.email( entity.getEmail() );
        signUpRequestDto.name( entity.getName() );
        signUpRequestDto.password( entity.getPassword() );
        signUpRequestDto.birthday( entity.getBirthday() );
        signUpRequestDto.social( entity.getSocial() );

        return signUpRequestDto.build();
    }

    @Override
    public UserDetailResponseDto fromEntity(Users users) {
        if ( users == null ) {
            return null;
        }

        UserDetailResponseDto.UserDetailResponseDtoBuilder userDetailResponseDto = UserDetailResponseDto.builder();

        userDetailResponseDto.email( users.getEmail() );
        userDetailResponseDto.name( users.getName() );
        userDetailResponseDto.birthday( users.getBirthday() );
        userDetailResponseDto.social( users.getSocial() );

        return userDetailResponseDto.build();
    }
}
