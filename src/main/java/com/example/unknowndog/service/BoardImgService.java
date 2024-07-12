package com.example.unknowndog.service;

import com.example.unknowndog.dto.BoardDTO;
import com.example.unknowndog.dto.BoardImgDTO;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.BoardImg;
import com.example.unknowndog.repository.BoardImgRepository;
import com.example.unknowndog.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardImgService {

    @Value("${boardImgLocation}")
    private String boardImgLocation;
    //DB 저장을 위해서
    private final BoardImgRepository boardImgRepository;
    // 물리적인 저장을 위해서
    private final FileService fileService;
    private final BoardRepository boardRepository;


    public String makeDir() {
        Date date = new Date();
        SimpleDateFormat yymmdd = new SimpleDateFormat("yyMMdd");
        String now = yymmdd.format(date);
        //240513

        String path = boardImgLocation + "\\" + now; //풀 경로
        //c:/upload/240513
        //application에서 설정한 path + \\ + 오늘 날짜 = 경로

        File file = new File(path);

        if (file.exists() == false) {       //exists 파일이 존재한다면 true, x false
            file.mkdir(); //폴더 생성
        }

        return path;

    }


    //이미 itemService에서 dto -> entity로 변환이 된 상태
    //이미 itemService에서 MultipartFile 리스트로 입력받아서 for문으로 돌리는 중
    public void saveBoardImg(BoardImg boardImg, MultipartFile multipartFile) throws Exception {


        // 화면에서 넘겨받은 이미지파일 multipartFile 에서 파일명을 반환하는 메소드
        String oriImgName = multipartFile.getOriginalFilename();
        // 선언만 아래에서 변환
        String imgName = "";
        String imgUrl = "";

        //파일업로드
        // 입력받은 multipartFile 파일명이 없다면
        // 화면단에서 파일업로드 폼만있고 이미지를 넣지 않았다면 name="itemImgFile" 의
        // getOriginalFilename() 실행시 결과값이 ""  << null 이 아니다.
//        if(oriImgName != null && oriImgName != "" && oriImgName.length() != 0){
//            // 아래와 같은조건  //oriImgName 이 비어있지 않을때
//        }

        String filepath = makeDir(); //파일을 저장할 경로
        // c:/upload/240513/개발자.png


        if (!StringUtils.isEmpty(oriImgName) && !multipartFile.isEmpty() && multipartFile.getSize() > 0) {

            imgName = fileService.uploadFile(boardImgLocation, oriImgName,
                    multipartFile.getBytes());
            //확장자를 제외하고  uuid를 붙인 경로
            imgUrl = "/images/board/" + imgName;

            // 상품 이미지 정보 저장 DB
            //파라미터로 입력받은 itemImg entity 에 가공된 데이터를 set!!!!!

        }


        boardImg.updateBoardImg(oriImgName, imgName, imgUrl);
        boardImgRepository.save(boardImg);


    }


    public List<BoardImgDTO> imglist(Long boardId) {

        List<BoardImg> boardImgList
                = boardImgRepository.findByBoard_IdOrderById(boardId);

        List<BoardImgDTO> boardImgDTOList
                = new ArrayList<>();

        for (BoardImg a : boardImgList) {
            BoardImgDTO boardImgDTO = new BoardImgDTO();
            boardImgDTO = boardImgDTO.of(a);
            boardImgDTOList.add(boardImgDTO);
            log.info("날짜 : " + boardImgDTO.getRegTime());
        }


        return boardImgDTOList;
    }


    //이름은 의미를 포함
    public void deleteBoardImgg(BoardDTO boardDTO, List<MultipartFile> multipartFiles) throws Exception {

        Board board = boardRepository.findById(boardDTO.getId()).orElseThrow(EntityNotFoundException::new);

        //이미지 삭제 먹저하고 바로 그에 맞는 db삭제
        if(!ListUtils.isEmpty( boardDTO.getBoardImgIds())){
            //리스트에 들어있는 boardImgId를 하나씩 boardImgId를 빼와서
            for (Long boardImgId :boardDTO.getBoardImgIds()) {
                //boardImg를 찾는다.
                log.info(boardImgId);
                BoardImg savedBoardImg = boardImgRepository
                        .findById(boardImgId).orElseThrow(EntityNotFoundException::new);

                log.info("저장된아이디"+savedBoardImg);
                //물리적인 파일 1개 삭제
                fileService.deleteFile(boardImgLocation + savedBoardImg.getImgName());
                //디비 삭제
                log.info("삭제할 이미지 아이디"+boardImgId);
                board.getBoardImgList().removeIf(boardImg -> boardImg.getId() == boardImgId);
                boardImgRepository.deleteById(boardImgId);

            }
        }


        //새로운 파일 등록

//        for (MultipartFile multipartFile : multipartFiles){
//            // 화면에서 넘겨받은 이미지파일 multipartFile 에서 파일명을 반환하는 메소드
//            String oriImgName = multipartFile.getOriginalFilename();
//            // 선언만 아래에서 변환
//            String imgName = "";
//            String imgUrl = "";
//
//            String filepath = makeDir(); //파일을 저장할 경로
//            // c:/upload/240513/개발자.png
//            if (!StringUtils.isEmpty(oriImgName) && !multipartFile.isEmpty() && multipartFile.getSize() > 0) {
//                imgName = fileService.uploadFile(boardImgLocation, oriImgName,
//                        multipartFile.getBytes());
//                //확장자를 제외하고  uuid를 붙인 경로
//                imgUrl = "/images/board/" + imgName;
//
//                // 상품 이미지 정보 저장 DB
//                //파라미터로 입력받은 itemImg entity 에 가공된 데이터를 set!!!!!
//            }
//
//            log.info(boardDTO);
//
//            BoardImg boardImg = new BoardImg();
//
//            Board board =boardRepository.findById(boardDTO.getId()).orElseThrow(EntityNotFoundException::new);
//
//            log.info("보드이미지서비스:"+board);
//            if (board != null) {
//                boardImg.setBoard(board);
//                boardImg.updateBoardImg(oriImgName, imgName, imgUrl);
//                boardImgRepository.save(boardImg);
//            }
//        }



    }


    // 이미지 업데이트

    public void updateBoardImg(Long boardImgId, MultipartFile multipartFile) throws Exception {
        //이미지 파일 비어있는지 확인 // 이유는 우리는 화면에서 input file을 다 열어놔서
        //빈 값이 올수 있음
        if (!multipartFile.isEmpty()) {

            String filepath = makeDir(); //파일을 저장할 경로

            //이미지 가져오기 아이디를 가지고
            BoardImg savedBoardImg = boardImgRepository
                    .findById(boardImgId).orElseThrow(EntityNotFoundException::new);
            //기존 물리적 이미지파일을 삭제
            if (!StringUtils.isEmpty(savedBoardImg.getImgName())) {
                fileService.deleteFile(boardImgLocation + savedBoardImg.getImgName());
            }
            // 입력받은 이미지파일의 명가져와서 fileService에 있는 물리적인 파일 저장
            String orImgName = multipartFile.getOriginalFilename();
            String imgName = fileService.
                    uploadFile(boardImgLocation, orImgName, multipartFile.getBytes());

            // /images/item
            String imgUrl = "/images/board/" + imgName;
            // 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것만으로도
            // 변경감지기능 동작하여 트랜잭션이 끝날때 update 쿼리 실행
            savedBoardImg.updateBoardImg(orImgName, imgName, imgUrl);

        }


    }


    public void deleteBoardImg(Long[] boardImgId) {

//    log.info(Arrays.toString(ino));

        //이미지(ino)가 1개만 오지 않고 여러개 올 수 있으니까 배열로 ino를 받기
        //반복

        for (int i = 0; i < boardImgId.length; i++) {

            if (boardImgId[i] != null && boardImgId[i] > 0 ) { //ino가 null이 아니고 빈칸이 아닐 때


                BoardImg boardImg = boardImgRepository.findById(boardImgId[i]).get();

                log.info("아 이거 왜 못받아와" + boardImg);

                BoardImgDTO boardImgDTO = BoardImgDTO.of(boardImg);

                log.info("보드이미지삭제: " + boardImgDTO);

                String path = boardImgLocation + boardImgDTO.getImgName();

                log.info(path);

                File dele = new File(path);

                boardImgRepository.deleteById(boardImgId[i]);


            }
        }

        //리스트에 담긴 이미지를 전부 불러오기
        //modify에서 삭제 버튼을 눌러서 입력받은 번호만 삭제 > board/modify의 저장 버튼을 눌러야 실행

//        File dele = new File(경로);
//         dele.delete();


    }




}
