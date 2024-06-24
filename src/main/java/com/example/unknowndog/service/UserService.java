package com.example.unknowndog.service;

import com.example.unknowndog.constant.Role;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.repository.UserRepository;
import com.example.unknowndog.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    //회원정보찾기 이메일로
    public User findByEmail(String email) {

       return userRepository.findByEmail(email);
    }


    // 회원가입
    public User saveUser(User user) {
        log.info("사용자가 있는지 확인하기 전");

        // 사용자가 이미 있는지 찾기
        validateDuplicate(user);

        log.info("가입된 사용자가 없어서 저장하기 전");
        
        return userRepository.save(user);

    }

    public User validateDuplicateEmailonly(String email) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        User findMemberE = userRepository.findByEmail(email);

        return findMemberE;

    }

    public User validateDuplicateNnameonly(String nickname) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        User findMemberN = userRepository.findByNickname(nickname);

        return findMemberN;

    }

    private void validateDuplicate(User user) {
        log.info("이미 가입된 회원인지 확인");
        // 데이터베이스에 저장된 회원가입이 되어있는지 찾아본다.
        User findMemberE = userRepository.findByEmail(user.getEmail());
        User findMemberN = userRepository.findByNickname(user.getNickname());

        //이미 가입된 email이라면
        if(findMemberE != null || findMemberN != null) {
            log.info("이미 가입된 회원");
            throw new IllegalStateException("이미 가입된 회원이개.");
        }

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + email);
        }
//        User user1 = user.get();  optional 이라서 했던 코드
//        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한들
        log.info(user);
        log.info("현재 로그인하신분의 권한 : " + user.getRole().name());
        String role  = "";
        if("ADMIN".equals(user.getRole().name())){   //auth 컬럼을 추가로 지정해서 사용
            log.info("관리자");
            role = Role.ADMIN.name();
//            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
        }else {
            log.info("일반유저");
            role = Role.USER.name();
           // authorities.add(new SimpleGrantedAuthority(Role.USER.name()));
        }


        return org.springframework.security.core.userdetails.User.builder()
                .username( user.getEmail() )
                .password( user.getPassword())
                .roles(role)
                .build();
                //User(memberuser.getEmail(), memberuser.getPassword(), authorities);
        // 권한 까지 부여된후 User객체를 생성해 반환함, 스프링시큐리티에서 사용하며, 아이디, 패스워드, 권한 을 리턴
        // 리턴된 User객체의 비밀번호와 , 사용자가 폼에 입력한 비밀번호가 일치하는지 검사하는 기능을 내부에 가지고 있음
        //여러 롤을 배열로 던질것인가 생각
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDTO getUserDtl(Long userId){ //pk 상품번호
        //itemImgRepository.findById(itemId); 이거면된다.
        //하지만 필요에 의해서 정렬된 값으로 가져오고 싶어요 그러면 repository에서
        //쿼리메소드로 추가로 만들면 된다.


        //아이디로 유저정보 가져오기
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        //select * from user  where user_id = :user_id
        //4. 상품정보를 dto로 변환
        UserDTO userDTO = UserDTO.of(user);

        return userDTO;

    }


    //정보 업데이트
    public Long modifyUser (UserDTO userDTO) throws Exception {

        //정보가져오기 이멜로
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        //상품정보 업데이트  repository.save(item) 이것 수정이다 >
        // 영속성 상태일때는 변경감지를 이용 update > 트랜잭션이 종료될때
        user.updateUser(userDTO);

        return user.getId();
    }



}
