package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-07T22:32:33+0900",
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
}
