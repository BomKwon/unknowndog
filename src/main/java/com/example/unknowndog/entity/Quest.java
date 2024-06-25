package com.example.unknowndog.entity;


import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.dto.QuestFormDTO;
import com.example.unknowndog.entity.base.BaseEntity;
import com.example.unknowndog.entity.base.BaseTimeEntity;
import com.example.unknowndog.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "quest")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
                        // 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
public class Quest extends BaseEntity {

    @Id
    @Column(name = "quest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //의뢰 글 번호

    private String writer; //작성자(회원별명)

    @Column(nullable = false, length = 50)
    private String title; //글 제목

    @Column(nullable = false)
    private String salaryOption; //시급, 일급, 무료 등등

    @Column(name = "salary", nullable = false)  //컬럼명은 salary , notnull
    private int salary;  //급여

    @Column(nullable = false)
    private String area; //지역(이건 회원 주소랑 연동 이지만 수정 가능)


    @Column(columnDefinition = "integer default 1")
    private int stockNumber; //재고수량, 1고정

    @Lob    //@Lob은 일반적인 데이터베이스에서 저장하는 길이인 255개 이상의 문자를 저장하고 싶을 때 지정한다.
    @Column(nullable = false)
    private String questDetail;  //구인 글 상세 내용

//    @Transient
//    private int a;
    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus; // 상품 판매 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;   //조회수


    public void updateQuest(QuestFormDTO questFormDto) {
        this.title = questFormDto.getTitle();
        this.writer = questFormDto.getWriter();
        this.salary = questFormDto.getSalary();
        this.salaryOption = questFormDto.getSalaryOption();
        this.area = questFormDto.getArea();
        this.questDetail = questFormDto.getQuestDetail();
        this.questStatus = questFormDto.getQuestStatus();
    }

    //수량을 입력받아서 db의 저장된 개수확인과 , 수량변경
    public void removeStock(int stockNumber){   //구매수량
        //이미 이 entity는 select를 통해서 값을 가져와서
        //entitymanager가 데이터를 가지고 있다.
        //그래서 수정이 가능하다
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. " +
                    "(현재 재고수량 : " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;

    }

    //취소를 눌렀을때
    public void stockUpdate(int stockNumber){  //의뢰 취소할때

        this.stockNumber += stockNumber;
    }


    //승낙을 눌렀을 때
    public void consent(QuestStatus questStatus){  //의뢰 승낙할때

        this.questStatus = QuestStatus.valueOf("SUCCESS");
    }

    //취소를 눌렀을때
    public void cancel(QuestStatus questStatus){  //의뢰 취소할때

        this.questStatus = QuestStatus.valueOf("UNSUCCESS");
    }






}
