package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestRepositoryCustorm {

    Page<Quest> getQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable);

    Page<MainQuestDTO> getMainQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable);

    Page<MainQuestDTO> getUserQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable);

}
