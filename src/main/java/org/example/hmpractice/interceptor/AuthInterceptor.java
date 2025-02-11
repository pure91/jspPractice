package org.example.hmpractice.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 세션에 사용자 정보가 있는지 체크하는 클래스
 *  컴포넌트 어노테이션으로 Spring이 해당 클래스를 자동 관리하게 설정함 */
@Component
public class AuthInterceptor implements HandlerInterceptor { // HandlerInterceptor가 요청을 가로챔

    // 메소드명에 pre가 붙은걸로 유추할 수 있듯이 컨트롤러 실행전에 실행됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        // 정보 없으면 돌려줌
        if (user == null) {
            response.sendRedirect("/users/login");
            return false;
        }
        return true;
    }
}
