package com.apoint.incheon.controller;

import com.apoint.incheon.dto.ResponseDTO;
import com.apoint.incheon.dto.request.GoogleLoginDTO;
import com.apoint.incheon.dto.request.KakaoLoginDTO;
import com.apoint.incheon.dto.request.NaverLoginDTO;
import com.apoint.incheon.model.User;
import com.apoint.incheon.service.AuthService;
import com.apoint.incheon.service.OauthService;
import com.apoint.incheon.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final AuthService authService;

    @PostMapping("/kakao")
    public ResponseEntity<ResponseDTO> loginKakaoUser(
            @RequestBody KakaoLoginDTO kakaoLoginDTO,
            HttpServletResponse response
    ) {
        User user = oauthService.loginKakaoUser(kakaoLoginDTO.getCode());

        Map<String, Cookie> cookies = authService.createCookie(user);
        response.addCookie(cookies.get(JwtUtil.ACCESS_TOKEN_NAME));
        response.addCookie(cookies.get(JwtUtil.REFRESH_TOKEN_NAME));

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(200)
                        .message("카카오 로그인 성공")
                        .data(user)
                        .build()
        );
    }

    @PostMapping("/google")
    public ResponseEntity<ResponseDTO> loginGoogleUser(
            @RequestBody GoogleLoginDTO googleLoginDTO,
            HttpServletResponse response
    ) {
        User user = oauthService.loginGoogleUser(googleLoginDTO.getIdToken());

        Map<String, Cookie> cookies = authService.createCookie(user);
        response.addCookie(cookies.get(JwtUtil.ACCESS_TOKEN_NAME));
        response.addCookie(cookies.get(JwtUtil.REFRESH_TOKEN_NAME));

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(200)
                        .message("구글 로그인 성공")
                        .data(user)
                        .build()
        );
    }

    @PostMapping("/naver")
    public ResponseEntity<ResponseDTO> loginNaverUser(
            @RequestBody NaverLoginDTO naverLoginDTO,
            HttpServletResponse response
    ) {
        User user = oauthService.loginNaverUser(naverLoginDTO.getToken());

        Map<String, Cookie> cookies = authService.createCookie(user);
        response.addCookie(cookies.get(JwtUtil.ACCESS_TOKEN_NAME));
        response.addCookie(cookies.get(JwtUtil.REFRESH_TOKEN_NAME));

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(200)
                        .message("네이버 로그인 성공")
                        .data(user)
                        .build()
        );
    }
}
