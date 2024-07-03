package com.example.unknowndog.service;


import com.example.unknowndog.dto.BoardDTO;
import com.example.unknowndog.dto.PageRequestDTO;
import com.example.unknowndog.dto.PageResponseDTO;
import com.example.unknowndog.dto.ReplyDTO;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Reply;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService {

  private final ReplyRepository replyRepository;
  private final ModelMapper modelMapper;
  private final UserService userService;


  //글을 쓰려는 회원의 닉네임을 가져오는것인데..
  public void getUserName(ReplyDTO replyDTO, Principal principal){

    String email = principal.getName();
    User user = userService.findByEmail(email);

    String userName = user.getNickname();

    replyDTO.setReplyer(userName);

  }


  @Override
  public Long register(ReplyDTO replyDTO) {

    Board board = Board.builder().id(replyDTO.getBoardId()).build();

    Reply reply = modelMapper.map(replyDTO, Reply.class);

    reply.setBoard(board);

    return replyRepository
            .save(reply).getId();

  }

  @Override
//  @Transactional
  public PageResponseDTO<ReplyDTO> getListOfBoard(Long boardId, PageRequestDTO pageRequestDTO) {

    Pageable pageable = PageRequest
            .of(pageRequestDTO.getPage() <= 0 ? 0: pageRequestDTO.getPage()-1,
                    pageRequestDTO.getSize(),
                    Sort.by("id" +
                            "").descending());

    Page<Reply> result = replyRepository.listOfBoard(boardId, pageable);

    log.info("변환값 " +result);
     List<ReplyDTO> replyDTOList = result.getContent()
             .stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
             .collect(Collectors.toList());
     result.getTotalElements();
     log.info("결과값" + replyDTOList);

    return PageResponseDTO.<ReplyDTO>withAll()
            .pageRequestDTO(pageRequestDTO)
            .dtoList(replyDTOList)
            .total((int) result.getTotalElements())
            .build();
  }

  @Override
  public ReplyDTO read(Long replayId
  ) {

    return modelMapper.map(replyRepository.findById(replayId
    ).get(), ReplyDTO.class);

  }

  @Override
  @Transactional
  public void modify(ReplyDTO replyDTO) {

    Reply reply = replyRepository.findById(replyDTO.getId()).get();
    log.info(reply);
    reply.changeText(replyDTO.getReplyText()); //댓글 수정

    replyRepository.save(reply);


  }

  @Override
  public void remove(Long replayId
  ) {

    replyRepository.deleteById(replayId);

  }


}
