package com.nada.server;

import com.nada.server.domain.Authority;
import com.nada.server.domain.Card;
import com.nada.server.domain.Group;
import com.nada.server.domain.User;
import com.nada.server.repository.UserRepository;
import com.nada.server.service.CardGroupService;
import com.nada.server.service.CardService;
import com.nada.server.service.GroupService;
import com.nada.server.service.UserService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserRepository userRepository;
        private final CardService cardService;
        private final GroupService groupService;
        private final CardGroupService cardGroupService;

        public void dbInit(){

            // 유저 생성
            User user1 = new User();
            user1.setId("nada");
            user1.setAuthority(Authority.ROLE_USER);
            userRepository.save(user1);

            User user2 = new User();
            user2.setId("nada2");
            user2.setAuthority(Authority.ROLE_USER);
            userRepository.save(user2);

            // 카드 생성
            Card card = new Card();
            card.setId("cardA");
            card.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/4.png");
            card.setTitle("명함이름이여");
            card.setName("내 이름이여");
            card.setBirthDate("1999.05.12");
            card.setMbti("ENFP");
            card.setIsMincho(true);
            card.setIsBoomuk(false);
            card.setIsSoju(true);
            card.setIsSauced(true);
            cardService.create(card, "nada");

            Card card1 = new Card();
            card1.setId("cardB");
            card1.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/3.png");
            card1.setTitle("나다 수석졸업생");
            card1.setName("예원이여");
            card1.setBirthDate("1967.06.21");
            card1.setMbti("ISTJ");
            card1.setIsMincho(false);
            card1.setIsBoomuk(false);
            card1.setIsSoju(true);
            card1.setIsSauced(true);
            cardService.create(card1, "nada");

            // 그룹 생성 - 상황 : nada2가 nada의 카드를 넣으려는 상황
            Group group = Group.createGroup("Group");
            groupService.create(group, "nada2");

            // 그룹 속 카드 추가
            cardGroupService.add("cardA", Long.valueOf(1), "nada2");

            Card card2 = new Card();
            card2.setId("cardC");
            card2.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/1.png");
            card2.setTitle("호로롤");
            card2.setName("나다2카드");
            card2.setBirthDate("1950.06.21");
            card2.setMbti("ENFP");
            card2.setIsMincho(false);
            card2.setIsBoomuk(false);
            card2.setIsSoju(true);
            card2.setIsSauced(true);
            cardService.create(card2, "nada2");

            Card card3 = new Card();
            card3.setId("cardD");
            card3.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/7.png");
            card3.setTitle("호로롤");
            card3.setName("나다2카드22222");
            card3.setBirthDate("1950.06.21");
            card3.setMbti("ESTJ");
            card3.setIsMincho(false);
            card3.setIsBoomuk(false);
            card3.setIsSoju(true);
            card3.setIsSauced(true);
            cardService.create(card3, "nada2");

            Card card4 = new Card();
            card4.setId("cardE");
            card4.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/5.png");
            card4.setTitle("뚜룹뚜뚜 더미카드유");
            card4.setName("울랄라");
            card4.setBirthDate("1950.06.21");
            card4.setMbti("ENFP");
            card4.setIsMincho(false);
            card4.setIsBoomuk(true);
            card4.setIsSoju(true);
            card4.setIsSauced(true);
            cardService.create(card4, "nada2");

            Card card5 = new Card();
            card5.setId("cardF");
            card5.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/5.png");
            card5.setTitle("뚜룹뚜뚜 더미카드유");
            card5.setName("울랄라");
            card5.setBirthDate("1950.06.21");
            card5.setMbti("ENFP");
            card5.setIsMincho(false);
            card5.setIsBoomuk(true);
            card5.setIsSoju(true);
            card5.setIsSauced(false);
            cardService.create(card5, "nada2");

            Card card6 = new Card();
            card6.setId("cardG");
            card6.setBackground("https://nada-server.s3.ap-northeast-2.amazonaws.com/default/2.png");
            card6.setTitle("뚜룹뚜뚜 더미카드유");
            card6.setName("울랄라");
            card6.setBirthDate("1999.06.21");
            card6.setMbti("ENFP");
            card6.setIsMincho(false);
            card6.setIsBoomuk(true);
            card6.setIsSoju(true);
            card6.setIsSauced(true);
            cardService.create(card6, "nada2");
        }


    }


}
