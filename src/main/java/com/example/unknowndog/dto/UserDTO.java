package com.example.unknowndog.dto;


import com.example.unknowndog.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    //로그인 작업
    //아이디, 비번 , 주소, 이름
    //비밀번호는 8~16이내이여야하며
    @NotBlank(message = "이름은 필수 입력 값이개.")
    private String name;    //이름

    @NotEmpty(message = "이메일은 필수 입력 값이개.")
    @Email(message = "이메일 형식으로 입력해주시개")
    private String email;   //아이디

    @NotEmpty(message = "비밀번호는 필수 입력 값이개.")
    @Length(min = 8, max = 16,
            message = "비밀번호는 8자 이상, " +
                    "16자 이하로 입력해주개")
    private String password;    //비번

    @NotEmpty(message = "비밀번호를 재입력 해주개")
    @Length(min = 8, max = 16,
            message = "비밀번호는 8자 이상, " +
                    "16자 이하로 입력해주개")
    private String password2;

    @NotEmpty(message = "별명은 필수 입력 값이개")
    @Length(min = 2, max = 10, message = "별명은 2자 이상, 10자 이하로 입력해주세요.")
    private String nickname;    //별명

    
    private LocalDate birth;    //별명


    @NotEmpty(message = "주소는 필수 입력 값이개")
    private String address;     //주소

    private Long id;






    private static ModelMapper modelMapper = new ModelMapper();


    public static UserDTO of(User user){

        return modelMapper.map(user, UserDTO.class);
    }




}
