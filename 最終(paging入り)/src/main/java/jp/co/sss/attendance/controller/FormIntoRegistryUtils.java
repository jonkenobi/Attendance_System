package jp.co.sss.attendance.controller;

import jp.co.sss.attendance.entity.Project;
import jp.co.sss.attendance.entity.Task;
import jp.co.sss.attendance.entity.TaskInfoRegistry;
import jp.co.sss.attendance.form.TaskInfoRegistryForm;

import javax.servlet.http.HttpSession;

public class FormIntoRegistryUtils {
    public static void saveFormInfoIntoRegistry(TaskInfoRegistry registry, TaskInfoRegistryForm form, Project project, Task
            task, HttpSession session) {
        registry.setAttendedDate(form.getAttendedDate());
        registry.setEndTime(form.getEndTime());
        registry.setStartTime(form.getStartTime());
        registry.setProject(project);
        registry.setTask(task);
        registry.setTaskDescription(form.getTaskDescription());
        registry.setTaskHours(form.getTaskHours());
        registry.setUserId(session.getAttribute("userId").toString());
    }

}
