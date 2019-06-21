package jp.co.sss.attendance.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="projectCodeMst")
public class Project {
	@Id
	private String projectCode;
	@Column
	private String projectName;
	
	@OneToMany(mappedBy="project")
	private List<Registry> registryList;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Registry> getRegistryList() {
		return registryList;
	}

	public void setRegistryList(List<Registry> registryList) {
		this.registryList = registryList;
	}
	

}
