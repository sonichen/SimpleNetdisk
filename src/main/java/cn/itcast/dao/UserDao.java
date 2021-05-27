package cn.itcast.dao;

import cn.itcast.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    /**
     * 查找用户，根据用户名和密码
     * @param user
     * @return
     * @throws Exception
     */
    @Select("select * from user where username = #{username} and password = #{password}")
    User findUser(User user) throws Exception;

    /**
     * 插入用户
     * @param user
     * @throws Exception
     */
    @Insert("insert into user(username, password,registerTime) values(#{username}, #{password},now())")
    void addUser(User user) throws Exception;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select * from user where username = #{username}")
    User findUserByUserName(String username) throws Exception;

    /**
     * 更新用户存储文件的容量
     * @param countSize
     * @param username
     * @throws Exception
     */
    @Update("update user set countSize = #{countSize} where username = #{username}")
    void reSize( @Param("countSize") String countSize,@Param("username") String username) throws Exception;

    /**
     * 获取用户的文件存储容量
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select countSize from user where username = #{username}")
    String getCountSize (String username) throws Exception;

}