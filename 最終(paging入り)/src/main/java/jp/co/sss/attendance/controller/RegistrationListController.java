package jp.co.sss.attendance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		final int PAGE_SIZE = 4;
		int currentPage = pageNo - 1;
		
		Pageable currentPageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("attendedDate"));
		
		int totalPages = repo.findAll(currentPageable).getTotalPages();
		model.addAttribute("totalPages", totalPages);		
//		An arraylist of the page indexes for thymeleaf use 
		ArrayList<Integer> pages = new ArrayList<Integer>();
		for (int i=0;i<totalPages;i++) {
			pages.add(i+1);
		}	
		model.addAttribute("pagesList", pages);
		model.addAttribute("CurrentPage", currentPage);
		model.addAttribute("LastPage", pages.get(pages.size()-1));
		model.addAttribute("taskInfo", repo.findAll(currentPageable));
		return "registrationList";
		
		//		Page<Registry> bleh=RegistryService.getPaginatedRegistries(Pagaeable);
//		
//        if(totalPages > 0) {
//            List<Registry> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
	}

//　　編集ボタンを押下し、編集画面に遷移する.選択したデータは初期値として表示される
	@RequestMapping(path = "/registrationForm/edit/input/{id}", method = RequestMethod.POST)
	public String InputEdit(@PathVariable int id, @ModelAttribute RegistryForm form, Model model, HttpSession session) {

		Registry Edit_Item = repo.getOne(id);
//		restructuring date format into yyyy/mm/dd
		String[] split_date = Edit_Item.getAttendedDate().split("-");
		String date = String.join("/", split_date);

		form.setAttendedDate(date);
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
	public String CompleteEdit(@PathVariable int id, @Valid @ModelAttribute RegistryForm form, BindingResult result,
			Model model, HttpSession session) {

		Project project = prepo.findByProjectName(form.getProject());
		Task task = trepo.findByTaskName(form.getTask());

//		New registry to be saved 
		Registry newRegistry = repo.getOne(id);
//		restructuring date format into yyyy/mm/dd
		String[] split_date = form.getAttendedDate().split("/");
		String date = String.join("-", split_date);
		try {
			newRegistry.setAttendedDate(date);
			newRegistry.setEndTime(form.getEndTime());
			newRegistry.setStartTime(form.getStartTime());
			newRegistry.setProject(project);
			newRegistry.setTask(task);
			newRegistry.setTaskDescription(form.getTaskDescription());
			newRegistry.setTaskHours(form.getTaskHours());
			newRegistry.setUserId(session.getAttribute("userId").toString());
			if (result.hasErrors()) {
				return InputEdit(id, form, model, session);
			}
			repo.save(newRegistry);
			return "redirect:/registrationList";
		} catch (DataIntegrityViolationException e) {
			System.out.println("data integrity violated ");
			session.setAttribute("error", "データの保存に失敗しました");
			return InputEdit(id, form, model, session);
		} catch (Exception e) {
			session.setAttribute("error", "データの保存に失敗しました");
			return InputEdit(id, form, model, session);
		}

	}

//	削除動作
	@RequestMapping(path = "/registrationForm/delete/{id}", method = RequestMethod.POST)
	public String DeleteTask(@PathVariable int id, RegistryForm form) {
		repo.deleteById(id);
		return "redirect:/registrationList";
	}
}
