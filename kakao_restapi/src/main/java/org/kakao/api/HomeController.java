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

		// īī�� rest api ��ü ����
		kakao_restapi kr = new kakao_restapi();
		// ������� node�� �����
		JsonNode node = kr.getAccessToken(code);
		// ����� ���
		System.out.println(node);
		// ��� �ȿ� �ִ� access_token���� ���� ���ڿ��� ��ȯ
		JsonNode accesstoken = node.get("access_token");
		   JsonNode userInfo = kr.getKakaoUserInfo(accesstoken);
		      System.out.println(userInfo);
		      String kemail = null;
		      String kname = null;
		      String kgender = null;
		      String kbirthday = null;
		      String kage = null;
		      String kimage = null;
		      // �������� īī������ �������� Get properties
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


		// ���ǿ� ����ش�.
		session.setAttribute("token", accesstoken);

		return "oauth";
	}

	@RequestMapping(value = "/logout", produces = "application/json")
    public String Logout(HttpSession session) {
        //kakao restapi ��ü ����
        kakao_restapi kr = new kakao_restapi();
        //��忡 �α׾ƿ��� ������� ����� �Ű������� ���ǿ� �մ� token�� ������ ���ڿ��� ��ȯ
        JsonNode node = kr.Logout(session.getAttribute("token").toString());
        //��� �� ���
        System.out.println("�α��� �� ��ȯ�Ǵ� ���̵� : " + node.get("id"));
        
        
        System.out.println(node);
        return "redirect:/";
        
        
    }    



	
}
