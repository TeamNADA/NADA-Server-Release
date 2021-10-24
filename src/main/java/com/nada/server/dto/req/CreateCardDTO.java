package com.nada.server.dto.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCardDTO {
    private MultipartFile image;

    @NotEmpty(message = "(작성자)유저 아이디는 필수입니다")
    private String userId;

    @NotNull(message = "default Image flag를 넣어주세요")
    private Integer defaultImage;

    @NotEmpty(message = "명함 이름은 필수입니다")
    private String title;
    @NotEmpty(message = "성명은 필수입니다")
    private String name;
    @NotEmpty(message = "생년월일은 필수입니다")
    private String birthDate;
    @NotEmpty(message = "MBTI는 필수입니다")
    private String mbti;

    private String instagram;
    private String linkName;
    private String link;
    private String description;

    @NotNull(message = "민초 여부는 필수입니다")
    private boolean isMincho;
    @NotNull(message = "소맥 여부는 필수입니다")
    private boolean isSoju;
    @NotNull(message = "부찍먹 여부는 필수입니다")
    private boolean isBoomuk;
    @NotNull(message = "양냠후라이도 여부는 필수입니다")
    private boolean isSauced;

    private String oneQuestion;
    private String oneAnswer;
    private String twoQuestion;
    private String twoAnswer;
}
