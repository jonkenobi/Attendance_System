package jp.co.sss.attendance.controller;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MenuController {
	@RequestMapping(path = "/menu")
	public String showMenu(HttpSession session) {
		//clear the error message in session when going back to main menu
		session.setAttribute("error", null);
		return "menu";
	}
}