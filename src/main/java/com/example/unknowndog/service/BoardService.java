package com.example.unknowndog.service;

import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.*;
import com.example.unknowndog.repository.BoardImgRepository;
import com.example.unknowndog.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserService userService;

    private final BoardImgService boardImgService;

    private final BoardImgRepository boardImgRepository;

    //글 등록
    public Long newboard(BoardDTO boardDTO,
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



    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();

        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = pageRequestDTO.getPageable("id");

        Page<BoardListReplyCountDTO> boardPage = boardRepository.searchWithReplyCount(types, keyword, pageable);

        boardPage.getContent().forEach(boardListReplyCountDTO -> log.info(boardListReplyCountDTO));

//    PageResponseDTO<BoardDTO> aa =
//            new PageResponseDTO<BoardDTO>(pageRequestDTO, boardDTOList, (int) boardPage.getTotalElements());

//    return aa;

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardPage.getContent())
                .total((int) boardPage.getTotalElements())
                .build();

    }


    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCountMain(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();

        String keyword = pageRequestDTO.getKeyword();

        // 페이지 번호와 페이지 크기 설정
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

        Page<BoardListReplyCountDTO> boardPage = boardRepository.searchWithReplyCount(types, keyword, pageable);


        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardPage.getContent())
                .total((int) boardPage.getTotalElements())
                .build();

    }



    @Transactional(readOnly = true)
    public BoardDTO getboardDetail(Long boardId){  //읽기

        //1. 게시글 아이디로 이미지가져오기
        List<BoardImg> boardImgList =
                boardImgRepository.findByBoardIdOrderByIdAsc(boardId);
        //select * from ItemImg where itemid = :itemid order by id asc;
        //2. 이미지 entity list를 dto list로 변환
        List<BoardImgDTO> boardImgDTOList = new ArrayList<>();
//        private static ModelMapper modelMapper = new ModelMapper();
        for(BoardImg boardImg : boardImgList){


            BoardImgDTO boardImgDTO = BoardImgDTO.of(boardImg);
            boardImgDTOList.add(boardImgDTO);
        }

        //아이디로 글 가져오고
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        //select * from item  where item_id = :item_id
        //글 정보를 dto로 변환
        BoardDTO boardDTO = BoardDTO.of(board);

        //게시판 dto 에 이미지들을 set!!
        boardDTO.setBoardImgDTOList(boardImgDTOList);

        return boardDTO;
    }



    public Page<Board> getboardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        return boardRepository.getBoardPage(boardSearchDTO, pageable);
    }



}
