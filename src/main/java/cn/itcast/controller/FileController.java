package cn.itcast.controller;

import cn.itcast.domain.BaseBean;
import cn.itcast.domain.FileCustom;
import cn.itcast.domain.Page;
import cn.itcast.service.FileService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
//----------------------------------------------------------------------------------------------------------------------
    /**
     * 文件上传(安卓端接口)
     * @param request
     * @param response
     * @param file
     * @param username
     * @param choosePath
     * @throws IOException
     */
    @RequestMapping(value="/uploadForAppV2",method= RequestMethod.POST)
    public void uploadByAppV2(HttpServletRequest request, HttpServletResponse response,@RequestBody MultipartFile file,String username,String choosePath,String url) throws IOException {
        System.out.println("url==="+url);
        System.out.println("用户名"+username);
        System.out.println("choosePath=="+choosePath);
        response.setContentType("text/html;charset=utf-8");
        BaseBean data=new BaseBean();
        Gson gson=new Gson();
        try {
            fileService.uploadByApp(file,request,username,response,choosePath,url);
            data.setCode(1000);
            data.setMsg("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            data.setCode(1001);
            data.setMsg("上传失败:"+e.getMessage());
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
            response.getWriter().close();
        }
    }

    /**
     * 安卓下载--返回文件的url
     * @param request
     * @param response
     * @param fileName
     * @param username
     * @param choosePath
     * @throws IOException
     */
    @RequestMapping(value="/downloadOnAppV2",method= RequestMethod.GET)
    public void downloadOnAppV2(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException {
        System.out.println("开始下载");
        System.out.println("文件名"+fileName);
        System.out.println("用户名"+username);
        System.out.println("路径"+choosePath);

        response.setContentType("text/html;charset=utf-8");
        BaseBean data=new BaseBean();
        Gson gson=new Gson();
        try{
            String url=fileService.downloadV2(request,response,fileName,username,choosePath);
            System.out.println(username+"的"+fileName+"下载成功");
            data.setCode(2000);
            data.setData(url);
            data.setMsg("下载成功");
            System.out.println(data);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(2001);
            data.setMsg("下载失败:");
        }
        String json = gson.toJson(data);
        System.out.println(json);
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
     * 网页下载文件
     */
    @RequestMapping(value="/download",method= RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        BaseBean data=new BaseBean();
        Gson gson=new Gson();
        try{
            fileService.download(request, response, fileName, username,choosePath);
            System.out.println("下载过程");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

//----------------------------------------------------------------------------------------------------------------------


    /**
     * 分页返回文件夹内容（安卓端口）
     * @param request

     * @return
     */
    @RequestMapping(value="/listFilesByPages",method= RequestMethod.POST)
    public @ResponseBody List<FileCustom>  listFilesByPages(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        String start=request.getParameter("start");
        String end=request.getParameter("end");
        String choosePath=request.getParameter("choosePath");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Page page=new Page();
        page.setStart(Integer.parseInt(start));
        page.setLast(Integer.parseInt(end));
        List<FileCustom> lists=null;
//        Gson gson=new Gson();
//        int total =0;
//        BaseBean data=new BaseBean();
        try{
            lists = fileService.listFilesByPages(request,page,choosePath);
            return lists;
//            total = fileService.total(choosePath,request,userName);
//            page.calculateLast(total);
//            data.setCode(total);
//            String details="";
//            for(FileCustom fileCustom:lists){
//                details+=fileCustom.getFileName()+"/n";
//            }
//            System.out.println(details);
//            data.setMsg(details);
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 文件夹下文件数量
     * @param request
     * @param response
     * @param userName
     * @param choosePath
     * @throws IOException
     */
    @RequestMapping(value="/getTotal",method= RequestMethod.POST)
    public void   getTotal(HttpServletRequest request, HttpServletResponse response , String userName, String choosePath) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Gson gson=new Gson();
        int total =0;
        BaseBean data=new BaseBean();
        try{
            total = fileService.total(choosePath,request,userName);
            data.setCode(7000);
            data.setData(total);
            data.setMsg("文件夹下文件数据获取成功");
        }catch (Exception e){
            data.setCode(7001);
            data.setMsg("文件夹下文件数据获取失败");
        }
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 一次性获取文件列表
     *
     * @param path
     *            路径
     * @return Json对象
     */
    @RequestMapping(value="/getAllFilesInDir",method= RequestMethod.POST)
    public @ResponseBody List<FileCustom> getAllFilesInDir(HttpServletRequest request, String path, String username) {
        String realPath = fileService.getFileName(request, path,username);
        List<FileCustom> listFile = fileService.listFile(realPath);
        return listFile;
    }

    /**
     * 新建文件夹
     *
     * @param currentPath
     *            当前路径
     * @param directoryName
     *            文件夹名
     * @return Json对象
     */
    @RequestMapping(value="/addDirectory",method= RequestMethod.POST)
    public @ResponseBody BaseBean addDirectory(HttpServletRequest request,HttpServletResponse  response,String currentPath,String directoryName,String userName) {
        try {
           boolean check= fileService.addDirectory(request, currentPath, directoryName, userName);
           if(check){
               return new BaseBean(336, directoryName+",添加成功");
           }else {
               return new BaseBean(331, directoryName+",添加失败" );
           }

        } catch (Exception e) {
            return new BaseBean(331, directoryName+",添加失败" );
        }
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param username
     * @param fileName
     */
    @RequestMapping(value="/deleteFiles",method= RequestMethod.POST)
    public void delete(HttpServletRequest request, HttpServletResponse response,String username,String fileName,String choosePath) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Gson gson=new Gson();
        BaseBean data=new BaseBean();
       try{
           fileService.deleteFile(request,username,fileName,choosePath);
           data.setCode(5000);
           data.setMsg(fileName+",删除成功");

       }catch (Exception e){
           data.setCode(5001);
           data.setMsg(fileName+",删除失败");
       }
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////






}

