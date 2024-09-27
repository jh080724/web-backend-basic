<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 주석 위의 JSP 코드는 아래 자바 코드와 같다. --%>
<%-- response.setContentType("text/html"); --%>
<%-- response.setCharacterEncoding("UTF-8"); --%>

<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원가입 form</title>
  </head>
  <body>
    <form action="/servlet-mvc/member/join" method="post">
      아이디: <input type="text" name="id" /> <br />
      비밀번호: <input type="password" name="pw" /> <br />
      이름: <input type="text" name="username" /> <br />
      나이: <input type="text" name="age" /> <br />
      <button type="submit">회원가입</button>
    </form>
  </body>
</html>