package jp.co.sss.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.attendance.entity.Task;

public interface TaskRepository extends JpaRepository<Task,String> {
	Task findByTaskName(String taskName);
}
