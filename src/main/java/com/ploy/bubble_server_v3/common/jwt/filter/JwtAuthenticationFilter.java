package com.ploy.bubble_server_v3.common.jwt.filter;

import com.ploy.bubble_server_v3.common.jwt.Jwt;
import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final Jwt jwt;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("getAuthentication is null");
            String token = getAccessToken(httpServletRequest);

            log.info("토큰 : " + token);

            if (token != null) {
                try {
                    Jwt.Claims claims = verify(token);
                    Long memberId = claims.getUserId();
                    Role[] roles = claims.getRoles();  // roles가 Role[] 배열로 반환됨
                    List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                            .map(role -> new SimpleGrantedAuthority(role.getRole())) // ROLE_ 접두사를 제거한 role 사용
                            .collect(Collectors.toList()); // 권한 리스트로 변환

                    log.info("userId : " + memberId);
                    log.info("roles : " + Arrays.toString(roles));

                    if (memberId != null) {
                        // 권한을 포함한 인증 객체 생성
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(memberId, null, authorities);
                        log.info("authentication : " + authentication.toString());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    log.warn("Jwt 처리중 문제가 발생하였습니다 : {}", e.getMessage());
                }
            }
        } else {
            log.debug("이미 인증 객체가 존재합니다 : {}",
                    SecurityContextHolder.getContext().getAuthentication());
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getAccessToken(HttpServletRequest request) {
        log.warn("리퀘스트 로그 시작");

        log.warn("Method: " + request.getMethod());
        log.warn("Request URI: " + request.getRequestURI());
        log.warn("Query String: " + request.getQueryString());

        log.warn("Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("헤더: " + headerName + " 값: " + request.getHeader(headerName));
        }

        String authorizationHeader = request.getHeader("Authorization");
        log.info("Raw Authorization Header : " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Authorization 헤더에 Bearer 토큰 존재");
            String token = authorizationHeader.substring(7);
            try {
                String decodedToken = URLDecoder.decode(token, StandardCharsets.UTF_8);
                log.info("Decoded accessToken : " + decodedToken);
                return decodedToken;
            } catch (Exception e) {
                log.error("엑세스 토큰 디코딩 실패: " + e.getMessage(), e);
            }
        } else {
            log.warn("Authorization 헤더가 null이거나 Bearer 토큰이 없습니다.");
        }

        return null;
    }

    private Jwt.Claims verify(String token) {
        return jwt.verify(token);
    }
}