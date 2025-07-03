package indeedcoder.recaptchathymeleaf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import indeedcoder.recaptchathymeleaf.model.ReCaptchaResponse;
import indeedcoder.recaptchathymeleaf.model.SampleModel;

@Controller
public class MainController {

	@GetMapping("/")
	public String getHomePage(Model m) {
		SampleModel s = new SampleModel("", null);
		m.addAttribute("backendData", s);
		return "HomePage";
	}

	@PostMapping("/save")
	public String saveData(@ModelAttribute SampleModel sm, @RequestParam("g-recaptcha-response") String reCaptchaResponse, Model m) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String key = "6LcVSXUrAAAAANahPTSo6ca37lpOAhiD1cqWkdlh";
		String q1 = "secret="+key;
		String q2 = "response="+reCaptchaResponse;
		RestTemplate rt = new RestTemplate();
		ResponseEntity<ReCaptchaResponse> resonse = rt.postForEntity(url+"?"+q1+"&"+q2, null, ReCaptchaResponse.class);
		String s = (resonse.getBody().isSuccess())?"PASS":"FAIL";
		m.addAttribute("data", sm);
		m.addAttribute("captchaStatus", s);
		return "ResultPage";
	}

}
