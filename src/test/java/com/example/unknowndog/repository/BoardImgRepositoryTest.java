package com.example.unknowndog.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardImgRepositoryTest {

    @Autowired
    BoardImgRepository boardImgRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void test(){


        boardRepository.findById(35L);
        boardImgRepository.deleteById(177L);
    }

}