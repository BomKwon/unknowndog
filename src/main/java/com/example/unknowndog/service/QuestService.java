package com.example.unknowndog.service;

import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.QuestImg;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.QuestImgRepository;
import com.example.unknowndog.repository.QuestRepository;
import com.example.unknowndog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class QuestService {


    // item 정보저장
    private final QuestRepository questRepository;
    // itemImg 정보저장
    private final QuestImgService questImgService;

    private final QuestImgRepository questImgRepository;

    private final UserService userService;

    private final UserRepository userRepository;



    public Long saveQuest(QuestFormDTO questFormDTO,
                          List<MultipartFile> multipartFiles) throws Exception{


        // TODO: 2024-06-21 알려주신 대로 했지만내가 멍청해서 적용을 못하기에 추후에 다시 보도록(작성자=닉네임 연결부분)



        // modelmapper
        Quest quest = questFormDTO.newQuest();
//        quest.setWriter(user.getNickname());  //작성자 부분에 닉네임을 자동입력하기 위해 추가
        questRepository.save(quest);  //반환이 있으니 더티



        //이미지 등록
        for (int i=0; i < multipartFiles.size(); i++){
            //입력받은 아이템이미지 숫자만큼
            //하지만 우리가 만들어놓은건 5개라 5개 들어옴
            QuestImg questImg = new QuestImg();
            questImg.setQuest(quest);      //이 아이템은 id를 가지고 있는가 저장되는가? 더티

            if(i == 0 ){
                questImg.setRepimgYn("Y");   //대표이미지

            }else {
                questImg.setRepimgYn("N");
            }

            questImgService.saveQuestImg(questImg, multipartFiles.get(i));

        }
        return quest.getId();
    }


    //글을 쓰려는 회원의 닉네임을 가져오는것인데..
    public void getUserName(QuestFormDTO questFormDTO, Principal principal){

        String email = principal.getName();
        User user = userService.findByEmail(email);

        questFormDTO.setWriter(user.getNickname());
    }


    @Transactional
    public int updateViews(Long noticeId) { //조회수

        // TODO: 2024-06-24 자기자신이 보면 조회수가 늘어나지 않도록 추후 추가하기

        return questRepository.updateViews(noticeId);
    }



    //수정
    @Transactional(readOnly = true)
    public QuestFormDTO getQuestDetail(Long questId){ //pk 상품번호
        //questImgRepository.findById(questId); 이거면된다.
        //하지만 필요에 의해서 정렬된 값으로 가져오고 싶어요 그러면 repository에서
        //쿼리메소드로 추가로 만들면 된다.


        //1. 의뢰 아이디로 이미지가져오기
        List<QuestImg> questImgList =
                questImgRepository.findByQuestIdOrderByIdAsc(questId);
                                //select * from ItemImg where itemid = :itemid order by id asc;
        //2. 이미지 entity list를 dto list로 변환
        List<QuestImgDTO> questImgDTOList = new ArrayList<>();
//        private static ModelMapper modelMapper = new ModelMapper();
        for(QuestImg questImg : questImgList){

//            ItemImgDto itemImgDto =  modelMapper.map(itemImg, ItemImgDto.class);
            //modelmapper
            QuestImgDTO questImgDTO = QuestImgDTO.of(questImg);
            questImgDTOList.add(questImgDTO);
        }
        //3. 상품 아이디로 상품정보 가져오고
        Quest quest = questRepository.findById(questId).orElseThrow(EntityNotFoundException::new);
                                //select * from item  where item_id = :item_id
        //4. 상품정보를 dto로 변환
        QuestFormDTO questFormDTO = QuestFormDTO.of(quest);
        //5. 상품정보dto 에 이미지들을 set!!
        questFormDTO.setQuestImgDTOList(questImgDTOList);

        return questFormDTO;


    }


    //상품정보 업데이트
    public Long updateQuest (QuestFormDTO questFormDTO,
                            List<MultipartFile> multipartFiles) throws Exception {

        //상품정보가져오기
        Quest quest = questRepository.findById(questFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        //상품정보 업데이트  repository.save(item) 이것 수정이다 >
        // 영속성 상태일때는 변경감지를 이용 update > 트랜잭션이 종료될때
        quest.updateQuest(questFormDTO);

        List<Long> questImgIds = questFormDTO.getQuestImgIds();


        //이미지 등록
        for (int i = 0; i < multipartFiles.size(); i++) {

            questImgService
                    .updateQuestImg(questImgIds.get(i),multipartFiles.get(i) );

        }
        return quest.getId();
    }

    @Transactional(readOnly = true)
    public Page<Quest> getQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable){

        return questRepository.getQuestPage(questSearchDTO, pageable);
  //      return itemRepository.jpqlQuerygetAdimItePage(itemSerchDTO, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainQuestDTO> getMainQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable){

        return questRepository.getMainQuestPage(questSearchDTO, pageable);

    }




    @Transactional(readOnly = true)
    public Page<MainQuestDTO> getUserQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable, Principal principal){

        questRepository.findQuestByCreateBy(principal.getName(), pageable);

        return questRepository.getUserQuestPage(questSearchDTO, pageable);
    }






    // TODO: 2024-06-21 이게 문제인지.. ㄴㄴ 완료
    public String remove(Long questId) {


        Quest quest
                = questRepository
                .findById(questId)
                .orElseThrow(EntityNotFoundException::new);
        String questNm = quest.getTitle();
        questRepository.deleteById(questId);


        return questNm;

    }








}
