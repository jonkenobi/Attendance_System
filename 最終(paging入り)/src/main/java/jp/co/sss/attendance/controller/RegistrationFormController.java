package jp.co.sss.attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.attendance.entity.Project;
import jp.co.sss.attendance.entity.Registry;
import jp.co.sss.attendance.entity.Task;
import jp.co.sss.attendance.form.RegistryForm;
import jp.co.sss.attendance.repository.ProjectRepository;
import jp.co.sss.attendance.repository.RegistryRepository;
import jp.co.sss.attendance.repository.TaskRepository;

@Controller
public class RegistrationFormController {
	@Autowired
	RegistryRepository repo;
	@Autowired
	ProjectRepository prepo;
	@Autowired
	TaskRepository trepo;

//	load registration Form page
	@RequestMapping(path = "/registrationForm")
	public String showRegistrationForm(@ModelAttribute RegistryForm form, Model model, HttpSession session) {

		System.out.println(form.getTaskDescription());
		if (form.getAttendedDate() == null) {
			System.out.println(form.getAttendedDate() + "is date");
			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			form.setAttendedDate(date.format(now));
		}

//		model.addAttribute("today", date.format(now));
		model.addAttribute("ProjectInfo", prepo.findAll());
		model.addAttribute("Tasks", trepo.findAll());
		return "registrationForm";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute RegistryForm form, BindingResult result, HttpSession session,
			Model model) {
		if (result.hasErrors()) {
			
			System.out.println("input error");
			return showRegistrationForm(form, model, session);
			
		} else {
			// means no errors, register the data and redirect
			Project project = new Project();
			project = prepo.findByProjectName(form.getProject());

			Task task = new Task();
			task = trepo.findByTaskName(form.getTask());

//			restructuring date format into yyyy/mm/dd
			String[] split_date = form.getAttendedDate().split("/");
			String date = String.join("-", split_date);
//			New registry to be saved 
			Registry newRegistry = new Registry();
			try {
				newRegistry.setAttendedDate(date);
				newRegistry.setEndTime(form.getEndTime());
				newRegistry.setStartTime(form.getStartTime());
				newRegistry.setProject(project);
				newRegistry.setTask(task);
				newRegistry.setTaskDescription(form.getTaskDescription());
				newRegistry.setTaskHours(form.getTaskHours());
				newRegistry.setUserId(session.getAttribute("userId").toString());

				repo.save(newRegistry);
				return "redirect:/registrationList";
			} catch (DataIntegrityViolationException e) {
				System.out.println("data integrity violated ");
				session.setAttribute("error", "データの保存に失敗しました");
				return showRegistrationForm(form, model, session);
			} catch (Exception e) {
				session.setAttribute("error", "データの保存に失敗しました");
				return showRegistrationForm(form, model, session);
			}
		}
	}

}