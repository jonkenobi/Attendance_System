package jp.co.sss.attendance.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.attendance.entity.Registry;

public interface RegistryRepository extends JpaRepository<Registry, Integer> {
	List<Registry> findByOrderByAttendedDate();
	Page<Registry> findAll(Pageable pageable);
}
