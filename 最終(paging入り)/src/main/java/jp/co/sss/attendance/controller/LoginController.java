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
    @RequestMapping(path = "/login")
    public String showLoginPage(@ModelAttribute LoginForm form) {
        return "loginPage";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return showLoginPage(form);
        }
        if (loginVerificationPwContainsKeyword(form.getPw())) {
            session.setAttribute("userId", form.getUserId());
            return "redirect:/menu";
        } else {
            //TODO error message, stay on page.
            return "loginPage";
        }
    }

    private boolean loginVerificationPwContainsKeyword(String pw) {
        return pw.toLowerCase().contains("jona");
    }
}
