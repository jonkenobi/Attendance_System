package jp.co.sss.attendance.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import jp.co.sss.attendance.entity.TaskInfoRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jp.co.sss.attendance.entity.Project;
import jp.co.sss.attendance.entity.Task;
import jp.co.sss.attendance.form.TaskInfoRegistryForm;
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
    public String showRegistrationForm(@ModelAttribute TaskInfoRegistryForm form, Model model, HttpSession session) {
        if (form.getAttendedDate() == null) {
            form.setAttendedDate(FormattedCurrentDate());
        }
        model.addAttribute("ProjectInfo", prepo.findAll());
        model.addAttribute("Tasks", trepo.findAll());
        return "registrationForm";
    }

    static String FormattedCurrentDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDateTime.now();
        return dateFormat.format(currentDate);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute TaskInfoRegistryForm form, BindingResult result, HttpSession
            session, Model model) {
        if (result.hasErrors()) {
            return showRegistrationForm(form, model, session);
        } else {
            Project project = prepo.findByProjectName(form.getProject());
            Task task = trepo.findByTaskName(form.getTask());

            TaskInfoRegistry newTaskInfoRegistry = new TaskInfoRegistry();//new taskInfoRegistry to be saved
            try {
                FormIntoRegistryUtils.saveFormInfoIntoRegistry(newTaskInfoRegistry, form, project, task, session);
                repo.save(newTaskInfoRegistry);
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