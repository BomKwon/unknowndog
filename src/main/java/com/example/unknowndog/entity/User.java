package com.example.unknowndog.entity;

import com.example.unknowndog.constant.Role;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            //아이디

    @Column
    private String name;       //이름

    @Column(unique = true)
    private String email;       //이메일

    @Column(nullable = false, length = 100)
    private String password;    //비밀번호

//    @Column(name = "birth", nullable = false, length = 45)
    @Column
    private LocalDate birth;    //생일

    @Column(unique = true)
    private String nickname;    //별명

    @Column
    private String address;     // 주소

    @Enumerated(EnumType.STRING)
    private Role role;          //권한


    //회원가입용
    public static User createUser(UserDTO userDTO,
                                        PasswordEncoder passwordEncoder) {
        //modelmapper
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setBirth(userDTO.getBirth());
        user.setNickname(userDTO.getNickname());
        //패스워드의 암호화
        String password =  passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(password);
        user.setAddress(userDTO.getAddress());
        user.setRole(Role.USER);      // 사용자가 가입했을때
        //user.setRole(Role.ADMIN);      // 관리자가 가입했을때 (웬만해선 꺼두기)

        return user;
    }

    public void updateUser(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.nickname = userDTO.getNickname();
        this.password = userDTO.getPassword();
        this.address = userDTO.getAddress();
    }


}
