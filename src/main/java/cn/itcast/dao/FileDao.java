package cn.itcast.dao;

import cn.itcast.domain.FileCustom;
import cn.itcast.domain.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface FileDao {

	/**
	 * 插入文件
	 * @param filePath
	 * @param fileName
	 * @param userName
	 * @param fileType
	 * @param fileSize
	 * @throws Exception
	 */
	@Insert("insert into file(filePath,fileName,userName,lastTime,fileType,fileSize) values(#{filePath},#{fileName},#{userName},now(),#{fileType},#{fileSize})")
	void insertFiles(@Param("filePath") String filePath,@Param("fileName")String fileName, @Param("userName") String userName,@Param("fileType")String fileType,@Param("fileSize")String fileSize) throws Exception;

	/**
	 * 分页查询文件
	 * @param page
	 * @return
	 */
	@Select("select * from file where userName =#{userName} and  filePath regexp #{filePath} limit #{start}, #{count};")
	public List<FileCustom> list(Page page);

	/**
	 * 删除文件
	 * @param userName
	 * @param fileName
	 */
	@Delete("delete from file where userName=#{userName} and fileName=#{fileName};")
	public void deleteFile(@Param("userName") String userName,@Param("fileName") String fileName);

	/**
	 * 文件夹存入数据库
	 * @param filePath
	 * @param fileName
	 * @param userName
	 */
	@Insert("insert into file(filePath,fileName,userName,lastTime,fileType,fileSize) values(#{filePath},#{fileName},#{userName},now(),'folder','-')")
	public void insertDirInToDB(@Param("filePath") String filePath,@Param("fileName")String fileName, @Param("userName") String userName);
////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 插入文件
	 * @param filePath
	 * @param fileName
	 * @param userName
	 * @param fileType
	 * @param fileSize
	 * @throws Exception
	 */
	@Insert("insert into file(filePath,fileName,userName,lastTime,fileType,fileSize,url) values(#{filePath},#{fileName},#{userName},now(),#{fileType},#{fileSize},#{url})")
	public  void insertFilesV2(@Param("filePath") String filePath,@Param("fileName")String fileName, @Param("userName") String userName,@Param("fileType")String fileType,@Param("fileSize")String fileSize,@Param("url") String url) throws Exception;

	/**
	 * 安卓端下载--找文件对应的url
	 * @param filePath
	 * @param fileName
	 * @param userName
	 * @return
	 */
	@Select("select url from file where userName =#{userName} and  filePath regexp #{filePath} and fileName=#{fileName};")
	public String findUrl(@Param("filePath") String filePath,@Param("fileName")String fileName, @Param("userName") String userName);

}