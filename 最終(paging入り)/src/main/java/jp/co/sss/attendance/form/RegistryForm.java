package jp.co.sss.attendance.form;




import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import jp.co.sss.attendance.errorMsgs.*;
import jp.co.sss.attendance.util.EndBeforeStart;

@EndBeforeStart
public class RegistryForm {
	final String INVALID_TIME = errorMsgs.INVALID_TIME;
	@NotBlank(message = errorMsgs.EMPTY_DATE)
	@Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}", message = errorMsgs.YMD_FORMAT_DATE)
	@Pattern(regexp = "^(2[0-9]{3})/(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])$", message = errorMsgs.INVALID_DATE)
//	@Pattern(regexp = "^[a-zA-Z0-9/]+$|^$", message = "半角英数字で入力してください")
	private String attendedDate;

	@Pattern(regexp = "\\d\\d:\\d\\d|^$", message = errorMsgs.HM_FORMAT_TIME)
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d|60)|^$", message = INVALID_TIME)
//	@Pattern(regexp = "^[a-zA-Z0-9:]+$|^$", message = "半角英数字で入力してください")
	private String startTime;
	@Pattern(regexp = "\\d\\d:\\d\\d|^$", message = errorMsgs.HM_FORMAT_TIME)
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d|60)|^$", message = INVALID_TIME)
//	@AssertTrue 
//	public boolean isTimeValid() {
//	if (startTime==null||endTime==null) {
//		return true;
//	}
//	else {	
//		return startTime.compareTo(endTime)<=0;
//		}
//	}
	private String endTime;
	@NotBlank(message = errorMsgs.UNCHOSEN_PROJECT)
	private String project;
	@NotBlank(message = errorMsgs.UNCHOSEN_TASK)
	private String task;
	@Digits(integer = 3, fraction = 1, message = INVALID_TIME)
	@DecimalMin(value = "0.1", inclusive = true, message = INVALID_TIME)
	private Double taskHours;
	@Size(max = 25, message = "25文字以内で入力してください")
	private String taskDescription;

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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
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

}
