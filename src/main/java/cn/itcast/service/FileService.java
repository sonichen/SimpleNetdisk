package cn.itcast.service;

import cn.itcast.domain.FileCustom;
import cn.itcast.domain.Page;
import cn.itcast.utils.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * 分页返回文件夹的内容
     * @param request
     * @param page
     * @param choosePath
     * @return
     */
    public List<FileCustom> listFilesByPages(HttpServletRequest request,Page page,String choosePath);

    /**
     * 获取路径
     * @param request
     * @return
     */
    public String getRootPath(HttpServletRequest request);


    /**
     * 下载文件
     * @param request
     * @param response
     * @param fileName
     * @param username
     * @param choosePath
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException;

    /**
     * 列出文件
     * @param realPath
     * @return
     */
    public List<FileCustom> listFile(String realPath);

    public String getFileName(HttpServletRequest request, String fileName);
    public String getFileName(HttpServletRequest request, String fileName, String username);

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
    public boolean addDirectory(HttpServletRequest request, String currentPath, String directoryName,String userName);

    /**
     * 列出文件夹下所有文件
     * @param realPath
     * @param request
     * @param username
     * @return
     */
    public List<FileCustom> listFileForApp(String realPath,HttpServletRequest request,String username) ;

    /**
     * 删除文件
     * @param userName
     * @param fileName
     */
    public void deleteFile(HttpServletRequest request,@Param("userName") String userName,@Param("fileName") String fileName,String choosePath);

    /**
     * 分页计算该文件夹下文件数量
     * @param path
     * @param request
     * @param userName
     * @return
     */
    public int total(String path,HttpServletRequest request,String userName);

    /**
     * 新用户注册时新建文件
     * @param request
     * @param namespace
     */
    public void addNewNameSpace(HttpServletRequest request, String namespace);

    /**
     * 插入文件夹信息到数据库
     * @param filePath
     * @param fileName
     * @param userName
     */
    public void insertDirInToDB( String filePath,String fileName, String userName);
    ///////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 上传文件
     * @param file
     * @param request
     * @param username
     * @param response
     * @param choosePath
     * @param url
     * @return
     * @throws Exception
     */
    public boolean uploadByApp(MultipartFile file, HttpServletRequest request, String username, HttpServletResponse response,String choosePath,String url) throws Exception;
    /**
     * 安卓下载--返回文件的url
     * @param request
     * @param response
     * @param fileName
     * @param username
     * @param choosePath
     * @throws IOException
     */
    public String downloadV2(HttpServletRequest request, HttpServletResponse response, String fileName,String username,String choosePath) throws IOException;
    }