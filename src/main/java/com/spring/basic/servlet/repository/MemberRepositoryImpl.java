package com.spring.basic.servlet.repository;

import com.spring.basic.servlet.domain.Member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

// 역할: 실제 데이터베이스에 Member들을 CRUD
// MVC에서 <M> Model 역할
public class MemberRepositoryImpl implements MemberRepository {

    // 싱글톤 구현
    private MemberRepositoryImpl() {
    }

    private static MemberRepositoryImpl repo = new MemberRepositoryImpl();

    public static MemberRepositoryImpl getInstance() {
        return repo;
    }

    private String username = "root"; // db 계정명
    private String password = "root"; // db 패스워드
    // db url(데이터베이스 주소)
    private String url = "jdbc:mysql://localhost:3306/spring?serverTimeZone=Asia/Seoul";
    private String driverClassName="com.mysql.cj.jdbc.Driver"; // db 벤더별 전용 연결 클래스

    @Override
    public void save(Member member) {

        // try with resource 문법을 사용하여 지저분한 try/catch를 없앤다.
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Connector 드라이버 강제 구동 -> 자바 프로그램과 DB 연동
            Class.forName(driverClassName);

            // 실행할 SQL을 작성 ??? 미완성 (문자열)
            String sql = "INSERT INTO tbl_members VALUES(?, ?, ?, ?)";

            // SQL을 실행할 객체를 생성
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 미완성 SQL의 물음표(?) 채우기
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPw());
            pstmt.setString(3, member.getUserName());
            pstmt.setInt(4, member.getAge());

            // 실행 명령
            // INSERT, UPDATE, DELETE는 실행명령 Method가 동일하다.
            int result = pstmt.executeUpdate(); // 성공 시 실행 쿼리문의 개수, 실패 시 0

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Member> getList() {
        return List.of();
    }
}
