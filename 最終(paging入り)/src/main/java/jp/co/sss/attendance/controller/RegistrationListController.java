package jp.co.sss.attendance.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.transform.sax.SAXSource;

import jp.co.sss.attendance.entity.TaskInfoRegistry;
import jp.co.sss.attendance.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jp.co.sss.attendance.entity.Project;
import jp.co.sss.attendance.entity.Task;
import jp.co.sss.attendance.form.TaskInfoRegistryForm;
import jp.co.sss.attendance.repository.ProjectRepository;
import jp.co.sss.attendance.repository.RegistryRepository;
import jp.co.sss.attendance.repository.TaskRepository;

@Controller
public class RegistrationListController {
    @Autowired
    RegistryRepository repo;
    @Autowired
    ProjectRepository prepo;
    @Autowired
    TaskRepository trepo;

    @RequestMapping(path = "/registrationList")
    public String initialRegistrationList(Model model, HttpSession session) {
        return "redirect:/registrationList/1";
    }

    //	Accessing the 登録一覧画面
//	the pagination i do here is a lazy version that doesn't properly use the Service classes 
    @RequestMapping(path = "/registrationList/{pageNo}")
    public String showRegistrationsList(@PathVariable int pageNo, Model model, HttpSession session) {
        final int PAGE_SIZE = 5;
        int currentPage = pageNo - 1; //change pageNo to page index
//       TODO
        String userId = String.valueOf(session.getAttribute("userId"));
        Pageable currentPageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("attendedDate"));
        Page<TaskInfoRegistry> taskInfoByUserPaged = repo.findAllByUserId(userId, currentPageable);
        int totalPages = taskInfoByUserPaged.getTotalPages();
        model.addAttribute("totalPages", totalPages);
//		An arraylist of the page indexes for thymeleaf use 
        ArrayList<Integer> pages = new ArrayList<Integer>();
        for (int i = 0; i < totalPages; i++) {
            pages.add(i + 1);
        }
        model.addAttribute("pagesList", pages);
        model.addAttribute("CurrentPage", currentPage);
        try {
            model.addAttribute("LastPage", pages.get(pages.size() - 1));
        } catch (ArrayIndexOutOfBoundsException e) {
            //happens when user has no records, pass and printStackTrace, bad code
            e.printStackTrace();
        }
        model.addAttribute("taskInfo", taskInfoByUserPaged);
        return "registrationList";
    }
    ///////  //	Paging example code//////
    //	Page<TaskInfoRegistry> bleh=RegistryService.getPaginatedRegistries(Pagaeable);
//        if(totalPages > 0) {
//            List<TaskInfoRegistry> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
    @RequestMapping(path="/registrationList/search/{pageNo}", method = RequestMethod.POST)
        public String Search(@RequestParam("searchProject") String project, @PathVariable int pageNo, Model model, HttpSession session) {
        System.out.println(project);
        final int PAGE_SIZE = 5;
        int currentPage = pageNo - 1; //change pageNo to page index
//       TODO
        String userId = String.valueOf(session.getAttribute("userId"));
        Pageable currentPageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("attendedDate"));
        Page<TaskInfoRegistry> taskInfoByUserPaged = repo.findAllByUserIdAndProject(userId, prepo.findByProjectName(project), currentPageable);
        int totalPages = taskInfoByUserPaged.getTotalPages();
        model.addAttribute("totalPages", totalPages);
//		An arraylist of the page indexes for thymeleaf use
        ArrayList<Integer> pages = new ArrayList<Integer>();
        for (int i = 0; i < totalPages; i++) {
            pages.add(i + 1);
        }
        model.addAttribute("pagesList", pages);
        model.addAttribute("CurrentPage", currentPage);
        try {
            model.addAttribute("LastPage", pages.get(pages.size() - 1));
        } catch (ArrayIndexOutOfBoundsException e) {
            //happens when user has no records, pass and printStackTrace, bad code
            e.printStackTrace();
        }
        model.addAttribute("taskInfo", taskInfoByUserPaged);
        return "registrationList";
    }
    @RequestMapping(path = "/registrationForm/edit/input/{id}", method = RequestMethod.POST)
    public String showEditScreenForSelectedData(@PathVariable int id, @ModelAttribute TaskInfoRegistryForm form, Model model, HttpSession session) {
        TaskInfoRegistry Edit_Item = repo.getOne(id);
        form.setAttendedDate(Edit_Item.getAttendedDate());
        form.setEndTime(Edit_Item.getEndTime());
        form.setStartTime(Edit_Item.getStartTime());
        form.setProject(Edit_Item.getProject().getProjectName());
        form.setTask(Edit_Item.getTask().getTaskName());
        form.setTaskDescription(Edit_Item.getTaskDescription());
        form.setTaskHours(Edit_Item.getTaskHours());

        model.addAttribute("ProjectInfo", prepo.findAll());
        model.addAttribute("Tasks", trepo.findAll());
        model.addAttribute("Id", id);
        return "registrationEditForm";
    }

    //	編集画面で入力した内容をDBに更新の動作
    @RequestMapping(path = "/registrationForm/edit/complete/{id}", method = RequestMethod.POST)
    public String SaveNewTaskInfo(@PathVariable int id, @Valid @ModelAttribute TaskInfoRegistryForm form, BindingResult result,
                                  Model model, HttpSession session) {
        Project project = prepo.findByProjectName(form.getProject());
        Task task = trepo.findByTaskName(form.getTask());

        TaskInfoRegistry newTaskInfoRegistry = repo.getOne(id);
        try {
            FormIntoRegistryUtils.saveFormInfoIntoRegistry(newTaskInfoRegistry, form, project, task, session);
            if (result.hasErrors()) {
                return showEditScreenForSelectedData(id, form, model, session);
            }
            repo.save(newTaskInfoRegistry);
            return "redirect:/registrationList";
        } catch (DataIntegrityViolationException e) {
            System.out.println("data integrity violated ");
            session.setAttribute("error", "データの保存に失敗しました");
            return showEditScreenForSelectedData(id, form, model, session);
        } catch (Exception e) {
            session.setAttribute("error", "データの保存に失敗しました");
            return showEditScreenForSelectedData(id, form, model, session);
        }
    }

    @RequestMapping(path = "/registrationForm/delete/{id}", method = RequestMethod.POST)
    public String DeleteTask(@PathVariable int id) {
        repo.deleteById(id);
        return "redirect:/registrationList";
    }
}


