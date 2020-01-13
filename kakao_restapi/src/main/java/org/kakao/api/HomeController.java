package org.kakao.api;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class HomeController {
	@RequestMapping(value = "*")
	public String index() {
		return "index";
	}

	@RequestMapping("home")
	public String home() {
		return "home";
	}

	private kakao_restapi kakao_restapi = new kakao_restapi();

	@RequestMapping(value = "/oauth", produces = "application/json")
//	@RequestMapping(value = "/oauth")
	public String kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra, HttpSession session,
			HttpServletResponse response) {
		System.out.println("kakao code: " + code);

		// 카카오 rest api 객체 선언
		kakao_restapi kr = new kakao_restapi();
		// 결과값을 node에 담아줌
		JsonNode node = kr.getAccessToken(code);
		// 결과값 출력
		System.out.println(node);
		// 노드 안에 있는 access_token값을 꺼내 문자열로 변환
		JsonNode accesstoken = node.get("access_token");
		   JsonNode userInfo = kr.getKakaoUserInfo(accesstoken);
		      System.out.println(userInfo);
		      String kemail = null;
		      String kname = null;
		      String kgender = null;
		      String kbirthday = null;
		      String kage = null;
		      String kimage = null;
		      // 유저정보 카카오에서 가져오기 Get properties
		      JsonNode properties = userInfo.path("properties");
		      JsonNode kakao_account = userInfo.path("kakao_account");
		      
		      kemail = kakao_account.path("email").asText();
		      kname = properties.path("nickname").asText();
		      kimage = properties.path("profile_image").asText();
		      kgender = kakao_account.path("gender").asText();
		      kbirthday = kakao_account.path("birthday").asText();
		      kage = kakao_account.path("age_range").asText();
		      session.setAttribute("kemail", kemail);
		      session.setAttribute("kname", kname);
		      session.setAttribute("kimage", kimage);
		      session.setAttribute("kgender", kgender);
		      session.setAttribute("kbirthday", kbirthday);
		      session.setAttribute("kage", kage);


		// 세션에 담아준다.
		session.setAttribute("token", accesstoken);

		return "oauth";
	}

	@RequestMapping(value = "/logout", produces = "application/json")
    public String Logout(HttpSession session) {
        //kakao restapi 객체 선언
        kakao_restapi kr = new kakao_restapi();
        //노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
        JsonNode node = kr.Logout(session.getAttribute("token").toString());
        //결과 값 출력
        System.out.println("로그인 후 반환되는 아이디 : " + node.get("id"));
        
        
        System.out.println(node);
        return "redirect:/";
        
        
    }    



	
}
