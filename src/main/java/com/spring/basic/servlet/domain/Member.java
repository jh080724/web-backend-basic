package com.spring.basic.servlet.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor  // 매개값은 받지 않는 생성자
@AllArgsConstructor  // 모든 매개값을 받는 생성자
public class Member {

    //사용자의 입력값을 객체로 포장해서 관리.
    private String id;
    private String pw;
    private String userName;
    private int age;


}
