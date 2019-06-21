package jp.co.sss.attendance.form;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.co.sss.attendance.util.Login;



@Login
public class LoginForm {
	@NotBlank(message="ユーザ名を入力してください")
	@Size(max=6,message="6文字以内で入力してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$|^$", message = "半角英数字で入力してください")
	private String userId;
	
	@NotBlank(message="パスワードを入力してください")
	private String pw;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
