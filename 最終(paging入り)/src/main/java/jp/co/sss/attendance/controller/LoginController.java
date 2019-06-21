package jp.co.sss.attendance.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.attendance.form.LoginForm;

@Controller
public class LoginController {
	@RequestMapping(path="/login")
	public String showLoginPage(@ModelAttribute LoginForm form) {
		return "loginPage";
	}
	
	@RequestMapping(path="/login", method = RequestMethod.POST)
	public String doLogin(@Valid@ModelAttribute LoginForm form,BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return showLoginPage(form);
		}
		if (form.getPw().toLowerCase().contains("jona")) {
			//go to main menu gamen
			//TODO maybe session setattribute thingy
			session.setAttribute("userId", form.getUserId());
			System.out.println(session.getAttribute("userId"));
			System.out.println("contains jona");
			return "redirect:/menu";
		}
		else {
			//TODO error message, stay on page.
			return"loginPage";
		}
	}
}
;