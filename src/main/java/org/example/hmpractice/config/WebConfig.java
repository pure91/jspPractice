package org.example.hmpractice.config;

import org.example.hmpractice.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 어노테이션만봐도 알다시피 설정 클래스고
 *  인터셉터를 등록해서 로그인한 사람만 볼 수 있는 페이지를 설정하는 클래스 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/users/userList") // 보호할 경로 지정(로그인한 사람만 접근)
                .excludePathPatterns("/users", "/users/registerPage", "/users/loginPage"); // 예외 경로
    }
}
