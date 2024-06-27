package com.example.unknowndog.repository;


import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  @Query("select r " +
          "from Reply r where r.board.id = :id")
  List<Reply> listReplyfromid(Long id);

  List<Reply> findByBoard(Board board);

  List<Reply> findByBoard_id(Long id);

  @Query("select r from Reply r where r.board.id = :id")
  Page<Reply> listOfBoard(Long id, Pageable pageable);

  //댓글수
  Long countByBoard_id(Long id);

  @Query(value = "select * from Reply", nativeQuery = true)
  List<Reply> findaaa ();






}
