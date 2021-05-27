package cn.itcast.service.impl;

import cn.itcast.dao.FileDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 查找用户
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public User findUser(User user) throws Exception {
        try {
            User checkUser = userDao.findUser(user);
            return checkUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增用户--注册
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public boolean addUser(User user) throws Exception {
        try {
            userDao.addUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据用户名找用户
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public User findUserByUserName(String username) throws Exception {
        User user = null;
        try {
            user = userDao.findUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }
}
