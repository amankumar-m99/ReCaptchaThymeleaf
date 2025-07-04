package indeedcoder.recaptchathymeleaf.controller;

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
	public String saveData(@ModelAttribute SampleModel sm,
			@RequestParam("g-recaptcha-response") String reCaptchaResponse, Model m) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		String key = "6LcVSXUrAAAAANahPTSo6ca37lpOAhiD1cqWkdlh";
		String query = "secret=" + key + "&" + "response=" + reCaptchaResponse;
		String finalUrl = url + "?" + query;
		RestTemplate rt = new RestTemplate();
		ReCaptchaResponse response = rt.postForEntity(finalUrl, null, ReCaptchaResponse.class).getBody();
		String s = (response.isSuccess()) ? "PASS" : "FAIL";
		m.addAttribute("data", sm);
		m.addAttribute("captchaStatus", s);
		m.addAttribute("captchaData", response);
		return "ResultPage";
	}

}
