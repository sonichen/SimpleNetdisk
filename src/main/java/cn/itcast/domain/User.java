package cn.itcast.domain;
/**
 * 用户实体类
 *
 */
public class User {

	public static final String NAMESPACE = "username";
	private Integer id;
	private String username;
	private String password;
	private String countSize;
	private String totalSize;
	private String registerTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountSize() {
		return countSize;
	}

	public void setCountSize(String countSize) {
		this.countSize = countSize;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", countSize='" + countSize + '\'' +
				", totalSize='" + totalSize + '\'' +
				", registerTime='" + registerTime + '\'' +
				'}';
	}
}
