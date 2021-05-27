package cn.itcast.service;

import cn.itcast.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    /**
     * 查找用户，根据用户名和密码
     * @param user
     * @return
     * @throws Exception
     */
    User findUser(User user) throws Exception;

    /**
     * 插入用户
     * @param user
     * @throws Exception
     */
    boolean addUser(User user) throws Exception;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     * @throws Exception
     */
    User findUserByUserName(String username) throws Exception;

}
