package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.MainBoardDTO;
import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustorm {

    Page<Board> getBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable);

    Page<MainBoardDTO> getMainBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable);

}
