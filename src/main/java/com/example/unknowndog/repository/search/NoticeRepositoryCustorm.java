package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.NoticeSearchDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustorm {

    Page<Notice> getNoticePage(NoticeSearchDTO noticeSearchDTO, Pageable pageable);

//    Page<MainNoticeDTO> getMainNoticePage(NoticeSearchDTO noticeSearchDTO, Pageable pageable);

}
