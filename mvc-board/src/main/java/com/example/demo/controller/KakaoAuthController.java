package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.KakaoProfile;
import com.example.demo.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RestController
@RequestMapping("/auth/*")
public class KakaoAuthController {
  private WebClient webClient = WebClient.builder().build();

  @GetMapping("kakao/callback")
  public String kakaoCallback(@RequestParam("code") String code, HttpSession session, HttpServletResponse res)
      throws IOException {
    // 1단계 : 인가코드 확인하기 - 리액트서버 처리
    log.info("code : " + code);

    // 2단계 : 토큰발급받기 - 스프링이 처리
    // 화면이 없는 상태에서 post요청을 해야 함
    RestTemplate restTemplate = new RestTemplate();
    // header정보가 필요함
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("client_id", "44927bb1261fe44bf6b58ca626be6a3b");
    map.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
    map.add("code", code);
    // 헤더와 바디를 하나로 묶기
    HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(map, headers);
    // Http요청을 post로 하기
    ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
        tokenRequest, String.class);
    log.info(response.getBody());
    ObjectMapper mapper = new ObjectMapper();
    OAuthToken oauthToken = null;
    try {
      oauthToken = mapper.readValue(response.getBody(), OAuthToken.class);
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (JsonProcessingException jpe) {
      jpe.printStackTrace();
    }
    log.info("Access_Token : " + oauthToken.getAccess_token());

    // 3단계 : 사용자 정보받기
    RestTemplate restTemplate2 = new RestTemplate();
    HttpHeaders headers2 = new HttpHeaders();
    headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
    headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    HttpEntity<MultiValueMap<String, String>> userInfoRequest = new HttpEntity<>(headers2);
    ResponseEntity<String> response2 = restTemplate2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
        userInfoRequest, String.class);
    log.info(response2.getBody());

    ObjectMapper mapper2 = new ObjectMapper();
    KakaoProfile kakaoProfile = null;
    try {
      kakaoProfile = mapper2.readValue(response2.getBody(), KakaoProfile.class);
    } catch (JsonMappingException jme) {
      jme.printStackTrace();
    } catch (JsonProcessingException jpe) {
      jpe.printStackTrace();
    }
    log.info("kakaoProfile : " + kakaoProfile);
    log.info("kakaoProfile id : " + kakaoProfile.id);
    log.info("kakaoProfile profile_image : " + kakaoProfile.properties.profile_image);
    log.info("kakaoProfile email" + kakaoProfile.getKakao_account().getEmail());
    String nickname = kakaoProfile.getKakao_account().getProfile().getNickname();
    log.info("kakaoProfile nickname : " + nickname);
    session.setAttribute("email", nickname);
    log.info("Session email attribute set to: " + session.getAttribute("email"));
    res.sendRedirect("http://localhost:3000/login-success?email=" + kakaoProfile.getKakao_account().getEmail());
    return "카카오 로그인 완료";
  }

  @GetMapping("logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "logout";
  }
}
