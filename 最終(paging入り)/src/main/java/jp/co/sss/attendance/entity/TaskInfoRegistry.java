package jp.co.sss.attendance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "taskInfo")
public class TaskInfoRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_items_gen")
    @SequenceGenerator(name = "seq_items_gen", sequenceName = "seq_Task_Id", allocationSize = 1)
    private Integer taskInfoId;

    @Column
    private String userId;

    @Column
    private String attendedDate;

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Column
    private Double taskHours;

    @ManyToOne
    @JoinColumn(name = "projectCode", referencedColumnName = "projectCode")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "taskCode", referencedColumnName = "taskCode")
    private Task task;

    @Column
    private String taskDescription;

    public Integer getTaskInfoId() {
        return taskInfoId;
    }

    public void setTaskInfoId(Integer taskInfoId) {
        this.taskInfoId = taskInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(String attendedDate) {
        this.attendedDate = attendedDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTaskHours() {
        return taskHours;
    }

    public void setTaskHours(Double taskHours) {
        this.taskHours = taskHours;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
