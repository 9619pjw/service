package org.delivery.api.filter;
// 필터를 통해서 로그 수집함

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // request 형변환 ... request 내용을 읽어버리면 뒷단에서 다시 한번 더 읽을 수 없도록 되어있기 때문에 이를 캐싱해줄수 있는 repo 클래스가 있음
        var req = new ContentCachingRequestWrapper( (HttpServletRequest) request );
        var res = new ContentCachingResponseWrapper( (HttpServletResponse) response );

        log.info("INIT URI : {}", req.getRequestURI());


        /* ↑ 필터 실행 전 request 들어옴 */

        chain.doFilter(req, res); // doFilter에 req, res 객체 넘겨줌

        /* ↓ 필터 실행 후 response 나감 ... 로그 생성 */

        // request 정보
        // header 정보 수집
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();


        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue = req.getHeader(headerKey);

            // authorization-token : ??? , user-agent : ??? 형식
            headerValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });


        var requestBody = new String(req.getContentAsByteArray());
        var uri = req.getRequestURI();
        var method = req.getMethod();

        // request 정보 로그로 출력함.
        log.info(">>>>> uri : {} , method : {} , header : {} , body : {}", uri, method, headerValues, requestBody);


        // response 정보
        var responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey -> {
            var headerValue = res.getHeader(headerKey);

            responseHeaderValues
                    .append("[")
                    .append(headerKey)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        var responseBody = new String(res.getContentAsByteArray());

        // response 정보 로그로 출력함.
        log.info("<<<<< uri : {} , method : {} , header : {} , body : {}", uri, method, responseHeaderValues, responseBody);

        // 읽어온 responseBody 값을 초기화함.
        res.copyBodyToResponse();
    }
}