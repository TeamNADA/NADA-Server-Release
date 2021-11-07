package com.nada.server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private String key; // memberID
    private String value; // rt string값

    // 만약 rdb 구현한다면 생성/수정시간 컬럼 추가해서 배치작업으로 만료 토큰 삭제 해야함.

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
