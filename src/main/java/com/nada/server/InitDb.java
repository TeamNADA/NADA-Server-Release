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
            card.setBackground("사진파일 어쩌구");
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
            card1.setBackground("파일더미가");
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
        }


    }


}
