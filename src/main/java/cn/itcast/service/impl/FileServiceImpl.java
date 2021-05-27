package cn.itcast.service.impl;

import cn.itcast.dao.FileDao;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.BaseBean;
import cn.itcast.domain.FileCustom;
import cn.itcast.domain.Page;
import cn.itcast.service.FileService;
import cn.itcast.utils.FileUtils;
import cn.itcast.utils.UserUtils;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("fileService")
public class FileServiceImpl implements FileService {
    /**
     * 文件相对前缀
     */
    public static final String PREFIX = "upload"+File.separator;

    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserDao userDao;
//----------------------------------------------------------------------------------------------------------------------

    /**
     * 安卓端--上传文件
     * @param file
     * @param request
     * @param username
     * @param response
     * @param choosePath
     * @param url
     * @return
     * @throws Exception
     */
    public boolean uploadByApp(MultipartFile file, HttpServletRequest request, String username, HttpServletResponse response,String choosePath,String url) throws Exception {
        System.out.println("url=="+url);
        //确定好路径,默认路径是以该用户名命名的文件夹
        if(choosePath==null){
            choosePath="";
        }else{
            choosePath=File.separator+choosePath;
        }
        String path = request.getSession().getServletContext().getRealPath("upload")+"\\"+username+choosePath;

        String fileName = file.getOriginalFilename();
        List<FileCustom> lists=listFileForApp(path,request,username);
        //一个文件夹中的文件名字唯一
        for (FileCustom fileCustom:lists){
            if(fileCustom.getFileName().equals(fileName)){
                // 把文件的名称设置唯一值，uuid
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName = uuid+"_"+fileName;
                break;
            }
        }

        File dir = new File(path,fileName);
        String  fileType=FileUtils.getFileType(dir);//获取文件类型
        //检查文件类型是否正确，支持图片，文档，音频，文件等【具体类型再FileUtils里】
        boolean check=fileType.equals("image")||fileType.equals("document")||fileType.equals("comp")
                ||fileType.equals("file");
        if (!check){
            return false;
        }
        //文件大小
        String getDataSize=(String)(FileUtils.getDataSize(file.getSize()));
        //如果该路径不存在，则创建
        if(!dir.exists()){
            dir.mkdirs();
        }

        try{
            file.transferTo(dir);
            fileDao.insertFilesV2(path,fileName,username,fileType,getDataSize,url);
            reSizeV2(request,username);
//            reSize(request);
            System.out.println("文件上传成功");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 安卓下载--返回文件的url
     * @param request
     * @param response
     * @param fileName
     * @param username
     * @param choosePath
     * @return
     * @throws IOException
     */
    public String downloadV2(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException {
        String url=null;
        System.out.println("==============="+choosePath);
        System.out.println("==============="+username);
        System.out.println("==============="+fileName);
        boolean check=(choosePath==null);
        if(check){
            choosePath="";
        }
//        处理路径，用正则表达式来找
        String path=null;
        if(check){
            path=username+"\\\\$";
        }else {
            choosePath=dealWithStrings(choosePath);
            path="^.*"+choosePath+"$";
        }
        String url1=fileDao.findUrl(path,fileName,username);
        System.out.println("--------------"+url1);

        return url1;
    }


    /**
     * 文件下载
     * @param request
     * @param response
     * @param fileName
     * @param username
     * @param choosePath
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException {
        //确定要下载哪个路径下文件
        if(choosePath==null){
            choosePath="";
        }else{
            choosePath+=File.separator;
        }
        String path = request.getSession().getServletContext().getRealPath("upload")+File.separator+username+File.separator+choosePath+fileName;
        //得到要下载的文件
        File file = new File(path);
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html><body><script type='text/javascript'>alert('您要下载的资源已被删除！');</script></body></html>");
            response.getWriter().close();
            System.out.println("您要下载的资源已被删除！！");
            return;
        }
        //转码，免得文件名中文乱码
        fileName = URLEncoder.encode(fileName,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(path);
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        // 关闭输出流
        out.close();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 分页返回文件夹下文件
     * @param request
     * @param page
     * @param choosePath
     * @return
     */
    public List<FileCustom> listFilesByPages(HttpServletRequest request, Page page, String choosePath) {
        String username= UserUtils.getUsername(request);
        boolean check=choosePath.isEmpty();
        if(check){
            choosePath="";
        }
//        处理路径，用正则表达式来找
        String path=null;
       if(check){
           path=username+"\\\\$";
       }else {
           choosePath=dealWithStrings(choosePath);
          path="^.*"+choosePath+"$";
       }
        page.setFilePath(path);
        page.setUserName(username);
        List<FileCustom> lists=fileDao.list(page);
        System.out.println("分页返回文件夹下文件"+lists);
        return lists;
    }

    /**
     * 处理字符串
     * @param choosePath
     * @return
     */
    public String dealWithStrings(String choosePath){
        String choosePath1=choosePath;
        for (int i=0;i<choosePath.length();i++){
            if (choosePath.charAt(i)=='\\'){
                System.out.println(i);
                choosePath1=choosePath1.substring(0,i)+"\\\\"+choosePath1.substring(1+i);
            }
        }
        return choosePath1;
    }

    /**
     * 计算文件夹下的文件数量
     * @param path
     * @param request
     * @param userName
     * @return
     */
    public int total(String path,HttpServletRequest request,String userName){
        String preFix = getRootPath(request) + userName + File.separator;
        path=preFix+path;
        int total=0;
        List<FileCustom> list=listFileForApp(path,request,userName);
        for (int i=0;i<list.size();i++){
           total++;
        }
        return total;
    }

    /**
     * 获取路径下的文件类别
     *
     * @param realPath
     *            路径
     * @return
     */
    public List<FileCustom> listFileForApp(String realPath,HttpServletRequest request,String username) {

        String preFix = getRootPath(request) + username + File.separator;

        File[] files = new File(realPath).listFiles();
        List<FileCustom> lists = new ArrayList<FileCustom>();
        if (files != null) {
            for (File file : files) {
                FileCustom custom = new FileCustom();
                custom.setFileName(file.getName());
                custom.setLastTime(FileUtils.formatTime(file.lastModified()));

                custom.setCurrentPath(realPath.replace(preFix, ""));
                if (file.isDirectory()) {
                    custom.setFileSize("-");
                    custom.setFileType("folder");
                } else {
                    custom.setFileSize(FileUtils.getDataSize(file.length()));
                    custom.setFileType(FileUtils.getFileType(file));
                }
                lists.add(custom);
//                }
            }
        }
        return lists;
    }

    /**
     * 一次性获取文件夹下所有文件
     *
     * @param realPath
     *            路径
     * @return
     */
    public List<FileCustom> listFile(String realPath) {
        File[] files = new File(realPath).listFiles();
        List<FileCustom> lists = new ArrayList<FileCustom>();
        if (files != null) {
            for (File file : files) {
                FileCustom custom = new FileCustom();
                custom.setFileName(file.getName());
                custom.setLastTime(FileUtils.formatTime(file.lastModified()));
                custom.setCurrentPath(realPath);
                if (file.isDirectory()) {
                    custom.setFileSize("-");
                } else {
                    custom.setFileSize(FileUtils.getDataSize(file.length()));
                }
                custom.setFileType(FileUtils.getFileType(file));
                lists.add(custom);
            }
        }
        return lists;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 获取文件根路径
     *
     * @param request
     * @return
     */
    public String getRootPath(HttpServletRequest request) {
        String rootPath = request.getSession().getServletContext().getRealPath("/") + PREFIX;
        System.out.println("获取文件跟路径"+rootPath);
        return rootPath;
    }

    /**
     * 获取文件路径
     *
     * @param request
     * @param fileName
     * @return
     */
    public  String getFileName(HttpServletRequest request, String fileName) {
        if (fileName == null) {
            fileName = "";
        }
        String username = UserUtils.getUsername(request);
        return getRootPath(request) + username + File.separator + fileName;
    }

    /**
     * 根据用户名获取文件路径
     *
     * @param request
     * @param fileName
     * @param username
     * @return
     */
    public String getFileName(HttpServletRequest request, String fileName, String username) {
        System.out.println("根据用户名获取文件路径"+username);
        if (username == null) {
            return getFileName(request, fileName);
        }
        if (fileName == null) {
            fileName = "";
        }
        System.out.println(getRootPath(request) + username + File.separator + fileName);
        return getRootPath(request) + username + File.separator + fileName;
    }

    /**
     * 新建的文件夹存入数据库
     * @param filePath
     * @param fileName
     * @param userName
     */
    public void insertDirInToDB( String filePath,String fileName, String userName){
        try{
            fileDao.insertDirInToDB(filePath,fileName,userName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 新建文件夹
     *
     * @param request
     * @param currentPath
     *            当前路径
     * @param directoryName
     *            文件夹名
     * @return
     */
    public boolean addDirectory(HttpServletRequest request, String currentPath, String directoryName,String userName) {
        String path=getFileName(request, currentPath);
        System.out.println(path);
        File file = new File(path, directoryName);
        boolean check=true;
        if(!file.exists()){
            check=file.mkdir();
        }else{
            return false;
        }
        if(check){
            try{
                insertDirInToDB(path,directoryName,userName);
                return  check;
            }catch (Exception e){
                return false;
            }
        }else {
            return false;
        }
    }
    /**
     * 新用户新建文件
     *
     * @param request
     * @param namespace
     */
    public void addNewNameSpace(HttpServletRequest request, String namespace) {
        String fileName = getRootPath(request);
        File file = new File(fileName, namespace);
        file.mkdir();
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 删除文件
     * @param request
     * @param userName
     * @param fileName
     */
    public void deleteFile(HttpServletRequest request,String userName,String fileName,String choosePath) {
        String path=getRootPath(request)+File.separator+userName+File.separator+choosePath;
        File file=new File(path,fileName);
       try{
           delFile(file);
           fileDao.deleteFile(userName,fileName);
           System.out.println("删除成功");
       }catch (Exception e){
           e.printStackTrace();
       }
        String getDataSize=FileUtils.getDataSize(file.length());
        System.out.println("删除的大小"+getDataSize);
        reSizeV2(request,userName);
//        reSize(request);
    }

    /**
     * 删除文件或文件夹
     * @param srcFile
     * @throws Exception
     */
    private void delFile(File srcFile) throws Exception {

        //如果是文件，直接删除
        if (!srcFile.isDirectory()) {
            srcFile.delete();
            return;
        }
        //如果是文件夹，再遍历
        File[] listFiles = srcFile.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                delFile(file);
            } else {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        if (srcFile.exists()) {
            srcFile.delete();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 递归统计用户文件大小
     *
     * @param srcFile
     *
     * @return
     */
    private long countFileSize(File srcFile) {
        System.out.println("scrFile"+srcFile);
        File[] listFiles = srcFile.listFiles();
        if (listFiles == null) {
            return 0;
        }
        long count = 0;
        for (File file : listFiles) {
            if (file.isDirectory()) {
                count += countFileSize(file);
            } else {
                count += file.length();
            }
        }
        return count;
    }
    /**
     * 统计用户文件大小
     *
     * @param request
     * @return
     */
    public String countFileSize(HttpServletRequest request) {
        long countFileSize = countFileSize(new File(getFileName(request, null)));
        System.out.println("统计用户文件大小"+countFileSize);
        return FileUtils.getDataSize(countFileSize);
    }
    /**
     * 更新文件大小
     *
     * @param request
     */
    public void reSize(HttpServletRequest request) {
        String userName = UserUtils.getUsername(request);
        System.out.println("用户名"+userName);
        String data=countFileSize(request);
        System.out.println(data);
        try {
            userDao.reSize(data,userName);
        } catch (Exception e) {
            System.out.println("更新数据失败");
            System.out.println(e.getMessage());
        }
    }

    /**
     * 连接安卓端时用
     * @param request
     * @param userName
     */
    public void reSizeV2(HttpServletRequest request,String userName) {
        String path = request.getSession().getServletContext().getRealPath("upload")+"\\"+userName;
        String data=countFileSize1(path);
        System.out.println(data);
        try {
            userDao.reSize(data,userName);
        } catch (Exception e) {
            System.out.println("更新数据失败");
            System.out.println(e.getMessage());
        }
    }
    public String countFileSize1(String path) {
        long countFileSize = countFileSize(new File(path));
        System.out.println("统计用户文件大小"+countFileSize);
        return FileUtils.getDataSize(countFileSize);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
