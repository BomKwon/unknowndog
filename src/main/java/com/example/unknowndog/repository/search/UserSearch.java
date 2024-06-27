package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.dto.UserSearchDTO;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSearch {

    Page<User> jpqlQuerygetUserPage(UserSearchDTO userSearchDTO, Pageable pageable);
}
