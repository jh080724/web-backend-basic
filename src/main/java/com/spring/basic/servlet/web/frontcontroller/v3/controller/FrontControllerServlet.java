package com.spring.basic.servlet.web.frontcontroller.v3.controller;

//import com.spring.basic.servlet.web.frontcontroller.v1.ControllerV1;
import com.spring.basic.servlet.web.frontcontroller.ModelView;
import com.spring.basic.servlet.web.frontcontroller.MyViewResolver;
import com.spring.basic.servlet.web.frontcontroller.v2.ControllerV2;
import com.spring.basic.servlet.web.frontcontroller.v3.ControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/front-controller/v3/*")
public class FrontControllerServlet extends HttpServlet {

    // 각 컨트롤러들을 모아놓을 Map생성
    // key를 url로 주어서 각 요청마다 명령을 내릴 콘트롤러를 구분
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 객체 초기화시 url에 따라 사용할 컨트롤러 초기화
    public FrontControllerServlet() {
        controllerMap.put("/front-controller/v3/member/form", new MemberFormController());
        controllerMap.put("/front-controller/v3/member/join", new MemberJoinController());
        controllerMap.put("/front-controller/v3/members", new MemberListController());
        controllerMap.put("/front-controller/v3/member/delete", new MemberDeleteController());

    }

    @Override  // 부모 메소드 재정의: 리턴타입 동일, 메소드 이름동일, 매개변수 동일,
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[dbg] Call! FrontControllerServlet service()");

        String requestURI = request.getRequestURI();

        System.out.println("[dbg] " + requestURI);

        // 요청 URL에 맞는 컨트롤러를 앱에서 꺼내오기
        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 요청과 함께 전달된 데이터(Request Parameter)를 전부 읽어서 맵에 담아 리턴하는 로직.
        // ?name=Kim&age=30...
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);

        // jsp 쪽에서 좀 더 쉽게 데이터를 꺼낼 수 있게
        // Model이 가진 맵을 순회해서 request 객체에 데이터를 세팅
        Map<String, Object> modelMap = mv.getModel().getModelMap();
        for (String key : modelMap.keySet()) {
            request.setAttribute(key, modelMap.get(key));
        }

        // view를 랜더링
        mv.render(request, response);

    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {

        Enumeration<String> parameterNames = request.getParameterNames();

        // 각각의 컨트롤러에게 전달할 파라미터를 담을 맵
        Map<String, String> paramMap = new HashMap<>();

        // 파라미터 요소가 더 이상 조회가 되지 않을때까지 반복을 순회
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();  // key를 얻자.
            String value = request.getParameter(key);  // key를 통해 value를 얻자
            paramMap.put(key, value);   // key/value를 맵에 담자.
        }

        return paramMap;
    }
}
