package jp.co.sss.attendance.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="taskCodeMst")
public class Task {
	
	@Id
	private String taskCode;
	
	@Column
	private String taskName;
	
	@OneToMany(mappedBy="task")
	private List<TaskInfoRegistry> registryList;

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<TaskInfoRegistry> getRegistryList() {
		return registryList;
	}

	public void setRegistryList(List<TaskInfoRegistry> taskInfoRegistryList) {
		this.registryList = taskInfoRegistryList;
	}
	
	
}
