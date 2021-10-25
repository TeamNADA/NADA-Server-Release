package com.nada.server;

import com.nada.server.domain.Card;
import com.nada.server.domain.User;
import com.nada.server.service.CardService;
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

        private final UserService userService;
        private final CardService cardService;

        public void dbInit(){

            // 유저 생성
            User user1 = new User();
            user1.setId("nada");
            userService.register(user1);

            User user2 = new User();
            user2.setId("nada2");
            userService.register(user2);

            // 카드 생성
            Card card = new Card();
            card.setId("cardA");
            card.setBackground("사진파일 어쩌구");
            card.setTitle("명함이름이여");
            card.setName("내 이름이여");
            card.setBirthDate("1999/05/12");
            card.setAge("23세");
            card.setMbti("ENFP");
            card.setIsMincho(true);
            card.setIsBoomuk(false);
            card.setIsSoju(true);
            card.setIsSauced(true);
            cardService.create(card, "nada");
        }


    }

}
