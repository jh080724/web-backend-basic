package com.spring.basic.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MyViewResolver {
    private String prefix;  // 경로에 있는 공통 접두사
    private String suffix;  // 경로에 있는 공통 접미사
    private Boolean redirect;   // 리다이렉트 여부
    private String viewPath;  // 포워딩할 view 경로


    // 전달되는 viewName 은 공통점이 있음.
    // "/WEB-INF/views/"는 모두 같다. 확장자가 .jsp라는 것도 모두 동일함.
    public MyViewResolver(String viewPath) {
        this.prefix = "/WEB-INF/views/";
        this.suffix = ".jsp";

        if (!isRedirect(viewPath)) {
            // 리다이렉트 아님
            this.viewPath = prefix + viewPath + suffix;
        } else {
            // 리다이렉트 요청임/
            // redirect: front/conteoller/v3/members
            this.viewPath = viewPath.substring(viewPath.indexOf(":") + 1);
        }
    }

    // chatgpt에 물어본 render 의미:
    //  ㄴ HTML, CSS, JavaScript 등을 통해 웹 페이지를 사용자에게 보여주는 과정입니다.
    //  ㄴ브라우저가 코드를 해석하여 시각적으로 표시하는 단계입니다.

    // 기존의 render는 forward만 함.
    // 이제는 상황에 따라 sendRedirect도 해야 함. -> redirect 필드를 확인.
    public void render(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        if (!this.redirect) {
            RequestDispatcher dp = request.getRequestDispatcher(viewPath);
            dp.forward(request, response);
        } else{
            response.sendRedirect(viewPath);
        }

    }

    // 전달된 view요청이 리다이렉트인지 확인
    private boolean isRedirect(String viewName) {
        // 리다이렉트 요청은 아마 redirect: 으로 시작한다.
        this.redirect = viewName.startsWith("redirect:");
        return this.redirect;
    }
}
