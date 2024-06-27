package com.example.unknowndog.service;

import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.dto.NoticeSearchDTO;
import com.example.unknowndog.dto.QuestFormDTO;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.QuestImg;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.NoticeRepository;
import com.example.unknowndog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserService userService;

    //글 등록
    public Long newNotice(NoticeDTO noticeDTO, Principal principal) throws Exception{


        String email = principal.getName();
        User user = userService.findByEmail(email);

        //noticeDTO.setWriter(user.getName()); <- 이렇게 하면안됨~ dto에 저장을 한다해도 modelmapper로는 사용이 안됨 서비스 들어가서 해야됨

        noticeDTO.setWriter(user.getNickname()); //일단 노티스에 넣어둠

        // modelmapper
        Notice notice = noticeDTO.newNotice();
        noticeRepository.save(notice);  //반환이 있으니 더티

        return notice.getId();
    }

    //글을 쓰려는 회원의 닉네임을 가져오는것인데..
    public void getUserName(NoticeDTO noticeDTO, Principal principal){

        String email = principal.getName();
        User user = userService.findByEmail(email);

        String userName = user.getNickname();

        noticeDTO.setWriter(userName);
    }


    @Transactional
    public int updateViews(Long noticeId) { //조회수

        // TODO: 2024-06-24 자기자신이 보면 조회수가 늘어나지 않도록 추후 추가하기

        return noticeRepository.updateViews(noticeId);
    }

    @Transactional(readOnly = true)
    public NoticeDTO getNoticeDetail(Long noticeId){  //읽기

        //아이디로 상품정보 가져오고
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
        //select * from item  where item_id = :item_id
        //글 정보를 dto로 변환
        NoticeDTO noticeDTO = NoticeDTO.of(notice);

        return noticeDTO;
    }


    //공지사항 수정
    public Long updateNotice (NoticeDTO noticeDTO) {

        //상품정보가져오기
        Notice notice = noticeRepository.findById(noticeDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        //상품정보 업데이트  repository.save(item) 이것 수정이다 >
        // 영속성 상태일때는 변경감지를 이용 update > 트랜잭션이 종료될때
        notice.updateNotice(noticeDTO);

        return notice.getId();
    }





    public Page<Notice> getNoticePage(NoticeSearchDTO noticeSearchDTO, Pageable pageable) {
        return noticeRepository.getNoticePage(noticeSearchDTO, pageable);
    }



}
