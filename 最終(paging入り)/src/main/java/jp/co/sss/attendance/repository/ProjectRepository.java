package jp.co.sss.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.attendance.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,String> {
	Project findByProjectName(String ProjectName);
}
