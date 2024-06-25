package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {



    Page<Board> jpqlQuerygetBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable);



}
