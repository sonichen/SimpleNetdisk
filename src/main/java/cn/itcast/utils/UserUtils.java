package cn.itcast.utils;

import javax.servlet.http.HttpServletRequest;

import cn.itcast.domain.User;
import org.junit.Test;

/**
 * 用户工具类
 */
public class UserUtils {
	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	public static String getUsername(HttpServletRequest request){
		return (String) request.getSession().getAttribute(User.NAMESPACE);
	}

	/**
	 * 用户名验证
	 * 必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
	 * 不能以数字开头
	 * @param name
	 * @return
	 */
	public static boolean checkName(String name) {
		String regExp = "^[^0-9][\\w_]{5,9}$";
		if(name.matches(regExp)) {
			return true;
		}else {
			return false;
		}
	}


	/**
	 * 密码验证
	 * 必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）
	 * @param pwd
	 * @return
	 */
	public static boolean checkPwd(String pwd) {
		String regExp = "^[\\w_]{6,20}$";
		if(pwd.matches(regExp)) {
			return true;
		}
		return false;
	}
}
