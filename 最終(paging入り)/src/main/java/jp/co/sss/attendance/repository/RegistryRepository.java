package jp.co.sss.attendance.repository;

import jp.co.sss.attendance.entity.Project;
import jp.co.sss.attendance.entity.TaskInfoRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<TaskInfoRegistry, Integer> {
//	List<TaskInfoRegistry> findByOrderByAttendedDate();
	Page<TaskInfoRegistry> findAllByUserId(String userId, Pageable pageable);
	Page<TaskInfoRegistry> findAllByUserIdAndProject(String userId, Project project, Pageable pageable);
	@Override
	Page<TaskInfoRegistry> findAll(Pageable pageable);
}
