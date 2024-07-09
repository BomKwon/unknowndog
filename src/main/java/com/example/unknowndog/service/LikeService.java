package com.example.unknowndog.service;


import com.example.unknowndog.dto.LikeDetailDTO;
import com.example.unknowndog.dto.LikeOrderDTO;
import com.example.unknowndog.dto.LikeQuestDTO;
import com.example.unknowndog.dto.OrderDTO;
import com.example.unknowndog.entity.Likes;
import com.example.unknowndog.entity.LikeQuest;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.LikeQuestRepository;
import com.example.unknowndog.repository.LikeRepository;
import com.example.unknowndog.repository.QuestRepository;
import com.example.unknowndog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final QuestRepository questRepository; //장바구니에 넣을 아이템을 찾기위해
    private final UserRepository userRepository;
                        //장바구니를 만들 회원
    private final LikeRepository likeRepository; //장바구니 저장, 수정등
    private final LikeQuestRepository likeQuestRepository; //장바구니에 담을 카트아이템
    private final OrderService orderService;

    public Long addLike(LikeQuestDTO likeQuestDTO, String email) {
        //컨트롤러에서 입력받은 likeQuestDTO의 id를 통해 상품을 찾습니다.
        Quest quest = questRepository.findById(likeQuestDTO.getQuestId())
                .orElseThrow(EntityNotFoundException::new);


        // email Like를 찾습니다.
        User user = userRepository.findByEmail(email);

        Likes Likes = likeRepository.findByUserId(user.getId());
        //회원으로 찾은 Like가 없다면
        if (Likes == null) {
            Likes = Likes.createLike(user);
            likeRepository.save(Likes);
        }

        //찾은 상품을 likeQuest으로 변환합니다.

        //카트에 이미 같은 상품이 있다면을 알고 싶어서 카트아이템을 찾아봅니다.
        LikeQuest savedlikeQuest
                = likeQuestRepository.findByLikes_IdAndQuestId(Likes.getId(), quest.getId());


        if(savedlikeQuest !=null){
            //장바구니에 이미 같은 종류의 상품이 있다면 수량을 더해주고
            // 더티체킹을 통해서 트랜잭션이 끝날때 update실행
            savedlikeQuest.addCount(likeQuestDTO.getCount());
            return savedlikeQuest.getId();
        }else {
            LikeQuest likeQuest
                    =LikeQuest.createLikeQuest(Likes, quest,likeQuestDTO.getCount());
            likeQuestRepository.save(likeQuest);
            return likeQuest.getId();
        }

        //변환된 X , 직접 카트 아이디와 아이템id로 찾거나
        // 직접 생성해서 likeQuest을 Like에 담습니다.



    }

    @Transactional(readOnly = true)
    public List<LikeDetailDTO>  getLikeList(String email) {

        List<LikeDetailDTO> LikeDetailDTOList = new ArrayList<>();      //빈 리스트 or 장바구니목록

        User user = userRepository.findByEmail(email);

        Likes Likes = likeRepository.findByUserId(user.getId());

        if (Likes == null) {
            return LikeDetailDTOList;
        }

        LikeDetailDTOList = likeQuestRepository.finaaaaa(Likes.getId());

        return LikeDetailDTOList;


    }

    
    //현재 장바구니가 내꺼인지 확인하는 메소드
    @Transactional(readOnly = true)
    public boolean validatelikeQuest(Long likeQuestId, String email){

        LikeQuest likeQuest = likeQuestRepository
                .findById(likeQuestId).orElseThrow(EntityNotFoundException::new);

        if(likeQuest.getCreateBy().equals(email)){
            // ==이면 객체를 비교하고 , .equals는 값을 비교한다.
            return true;
        }
        return false;
    }

    public String deletelikeQuest(Long likeQuestId){
        LikeQuest likeQuest
                = likeQuestRepository
                .findById(likeQuestId)
                .orElseThrow(EntityNotFoundException::new);
        String likeQuestNm = likeQuest.getQuest().getTitle();
        likeQuestRepository.delete(likeQuest);

        return likeQuestNm;
    }

    public void updatelikeQuestCount(Long likeQuestId, int count) {
        //넘겨받은 likeQuestId를 통해서 likeQuest을 찾아온다
        LikeQuest likeQuest
                = likeQuestRepository
                .findById(likeQuestId)
                .orElseThrow(FindException::new);
        //찾아온 likeQuest의 count를 변경하면 찾아올때 manager가 관리하므로
        //더티체킹 수행
        likeQuest.updateCount(count);
    }


    //장바구니에서 준 id를 통해서 주문을 하고, 카트를 비운다.
    public Long orderlikeQuest(List<LikeOrderDTO> LikeOrderDTOList, String email){

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (LikeOrderDTO LikeOrderDTO : LikeOrderDTOList) {
            //likeQuest entity
            LikeQuest likeQuest
                    = likeQuestRepository
                    .findById(LikeOrderDTO.getLikeQuestId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDTO orderDTO = new OrderDTO(); //뷰단에서 받은것처럼 객체만들기
                                                //원래 주문은 dto로 컨트롤러에서부터 받는다.
            orderDTO.setQuestId(likeQuest.getQuest().getId()); //장바구니아이템id > 주문아이템id
            orderDTO.setCount(likeQuest.getCount());         //장바구니아이템수량 > 주문아이템수량

            orderDTOList.add(orderDTO); //orderDTOList에 추가해준다.


        }


        Long orderId =  orderService.orders(orderDTOList, email); //주문테이블에 저장

        //카트삭제
        for(LikeOrderDTO LikeOrderDTO : LikeOrderDTOList){

            LikeQuest item = likeQuestRepository
                    .findById(LikeOrderDTO.getLikeQuestId())
                    .orElseThrow(EntityNotFoundException::new);

            likeQuestRepository.delete(item);
        }

        return orderId;
    }


}
