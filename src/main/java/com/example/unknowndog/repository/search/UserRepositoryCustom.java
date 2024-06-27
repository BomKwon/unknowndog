package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.UserSearchDTO;
import com.example.unknowndog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> getUserPage(UserSearchDTO userSearchDTO, Pageable pageable);

}
