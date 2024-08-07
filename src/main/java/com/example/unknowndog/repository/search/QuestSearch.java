package com.example.unknowndog.repository.search;


import com.example.unknowndog.dto.BoardListReplyCountDTO;
import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestSearch {


    Page<Quest> jpqlQuerygetQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable);

    Page<MainQuestDTO> searchQuest(String[] types, String keyword, Pageable pageable);


}
