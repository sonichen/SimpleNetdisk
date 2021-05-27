package cn.itcast.controller;

import cn.itcast.domain.BaseBean;
import cn.itcast.domain.User;
import cn.itcast.service.FileService;
import cn.itcast.service.UserService;
import cn.itcast.utils.UserUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
//----------------------------------------------------------------------------------------------------------------------
    /**
     * 注销，移动端
     * @param request
     * @return
     */
    @RequestMapping(value = "/logoutForApp", method = RequestMethod.POST)
    public void logoutForApp(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        BaseBean data=new BaseBean();
        try{
            request.getSession().invalidate();
            data.setMsg("退出成功");
            data.setCode(100);
        }catch (Exception e){
            data.setMsg("退出失败，原因："+e.getMessage());
            data.setCode(101);
        }
        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println(json);
        // 将对象转化成json字符串
        try {
            response.getWriter().println(json);
            // 将json数据传给客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close();
        }
    }

    /**
     * 移动端--注册
     * @param request
     * @param response
     * @throws Exception
     */
//    @RequestMapping("/registForApp")
    @RequestMapping(value = "/registForApp", method = RequestMethod.POST)
    public void   registForApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        // 获取客户端传过来的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");

        Gson gson=new Gson();
        BaseBean data=new BaseBean();

        if(username == null || password == null){
            data.setCode(201);
            data.setMsg("注册失败，用户名或密码不能为空");
        }else{
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            User isRegUser = userService.findUserByUserName(username);//检查该用户是否存在
            System.out.println(isRegUser);
            //该用户未注册则，创建新用户
            if(isRegUser == null){
                //检查密码是否符合规定
                if(!UserUtils.checkName(username)){
                    data.setCode(203);
                    data.setMsg("注册失败，用户名不合法，必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）不能以数字开头");
                } else if(!UserUtils.checkPwd(password)) {
                    data.setCode(204);
                    data.setMsg("注册失败，密码不合法，必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）");
                }
                //新增用户
                boolean isSuccess = userService.addUser(user);
                if(isSuccess){
                    user.setPassword(password);
                    User exsitUser = userService.findUser(user);
                    HttpSession session = request.getSession();
                    session.setAttribute(User.NAMESPACE, exsitUser.getUsername());
                    session.setAttribute("totalSize", exsitUser.getTotalSize());
                    data.setCode(200);
                    data.setData(exsitUser);
                    fileService.addNewNameSpace(request,user.getUsername());
                    data.setMsg("注册成功");
                }else{
                    data.setCode(202);
                    data.setMsg("注册失败，该用户已经存在");
                }
            }else{
                data.setCode(202);
                data.setMsg("注册失败，该用户已经存在");

            }
        }
        // 将对象转化成json字符串
        String json = gson.toJson(data);
        System.out.println(json);
        try {
            response.getWriter().println(json);
            // 将json数据传给客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close(); // 关闭这个流，不然会发生错误的
        }

    }

    /**
     * 登入-移动端
     * @param request
     * @param response
     * @throws Exception
     */
//    @RequestMapping("/loginForApp")
    @RequestMapping(value = "/loginForApp", method = RequestMethod.POST)
    public void loginForApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取客户端传过来的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html;charset=utf-8");
        // 回传给客户端的json对象
        BaseBean data = new BaseBean();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            data.setCode(302);
            data.setMsg("用户名或密码为空");
            System.out.println("用户名或密码为空");
            return;
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        User exsitUser = userService.findUser(user);
        if (exsitUser==null) {
            data.setCode(301);
            data.setData(user);
            data.setMsg("登入失败,账号不存在");
        } else  {
            // d登入成功
            data.setCode(300);
            data.setData(user);
            data.setMsg("登入成功");
        }
        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println(json);
        // 将对象转化成json字符串
        try {
            response.getWriter().println(json);
            // 将json数据传给客户端
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close();
        }

    }

//    ------------------------------------------------------------------------------------------------------------------

    /**
     * 注册
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/regist")
    public String regist(HttpServletRequest request, User user) throws Exception {
        User check=userService.findUserByUserName(user.getUsername());
        if(check!=null){
            request.setAttribute("msg", "注册失败，该用户注册过");
            return "fail";
        } else{
            boolean isSuccess = userService.addUser(user);
            if(isSuccess){
                request.setAttribute("msg", "注册成功");
                if(user.getUsername() == null || user.getPassword() == null){
                    request.setAttribute("msg", "注册失败，用户名和密码不能为空");
                    return "fail";
                }else if(!(UserUtils.checkName(user.getUsername())&&UserUtils.checkPwd(user.getPassword()))){
                    request.setAttribute("msg", "注册失败，密码或用户名不合法");
                    return "fail";
                }
//                fileService.addDirectory(request,user.getUsername(),user.getPassword());
                fileService.addNewNameSpace(request,user.getUsername());
                return "successRegist";
            }else{
                System.out.println("注册失败");
                request.setAttribute("msg", "注册失败");
                return "fail";
            }
        }
    }
    /**
     * 登录
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, User user) throws Exception {
        User exsitUser = userService.findUser(user);
        if(exsitUser != null){
            HttpSession session = request.getSession();
            session.setAttribute(User.NAMESPACE, exsitUser.getUsername());
            return "workPlaceForApp";
        }else{
            request.setAttribute("msg", "用户名或密码错误");
            return "fail";
        }
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "logout";
    }

//----------------------------------------------------------------------------------------------------------------------
    /**
     * 转跳到安卓工作页面
     * @return
     */
    @RequestMapping("workplaceForApp")
    public String gotoApp(){
        return "workPlaceForApp";
    }
    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/gotoRegist")
    public String gotoRegist(){
        return "regist";
    }
}
