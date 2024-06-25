package com.example.unknowndog.service;

import com.example.unknowndog.dto.BoardDTO;
import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.BoardImg;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.BoardRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserService userService;

    private final BoardImgService boardImgService;

    //글 등록
    public Long newboard(BoardDTO boardDTO, Principal principal,
                         List<MultipartFile> multipartFiles) throws Exception{



        // modelmapper
        Board board = boardDTO.newBoard();
        boardRepository.save(board);  //반환이 있으니 더티


        //이미지 등록
        for (int i=0; i < multipartFiles.size(); i++){
            //입력받은 아이템이미지 숫자만큼
            //하지만 우리가 만들어놓은건 5개라 5개 들어옴
            BoardImg boardImg = new BoardImg();
            boardImg.setBoard(board);      //이 아이템은 id를 가지고 있는가 저장되는가? 더티

            if(i == 0 ){
                boardImg.setRepimgYn("Y");   //대표이미지

            }else {
                boardImg.setRepimgYn("N");
            }

            boardImgService.saveBoardImg(boardImg, multipartFiles.get(i));

        }


        return board.getId();
    }

    //글을 쓰려는 회원의 닉네임을 가져오는것인데..
    public void getUserName(BoardDTO boardDTO, Principal principal){

        String email = principal.getName();
        User user = userService.findByEmail(email);

        String userName = user.getNickname();

        boardDTO.setWriter(userName);
    }


    @Transactional
    public int updateViews(Long boardId) { //조회수

        // TODO: 2024-06-24 자기자신이 보면 조회수가 늘어나지 않도록 추후 추가하기

        return boardRepository.updateViews(boardId);
    }

    @Transactional(readOnly = true)
    public BoardDTO getboardDetail(Long boardId){  //읽기

        //아이디로 상품정보 가져오고
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        //select * from item  where item_id = :item_id
        //글 정보를 dto로 변환
        BoardDTO boardDTO = BoardDTO.of(board);

        return boardDTO;
    }



    public Page<Board> getboardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        return boardRepository.getBoardPage(boardSearchDTO, pageable);
    }



}
