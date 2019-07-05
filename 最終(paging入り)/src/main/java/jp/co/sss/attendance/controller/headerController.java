package jp.co.sss.attendance.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class headerController {
	@RequestMapping(path="/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
