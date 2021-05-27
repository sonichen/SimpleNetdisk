package cn.itcast.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件工具类
 */
public class FileUtils {
	/**
	 * 计算文件大小
	 * @param size
	 * @return
	 */
	public static String getDataSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.0");
		if (size < 1024) {
			return size + "B";
		} else if (size < 1024 * 1024) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024 * 1024 * 1024) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else {
			return "-";
		}
	}

	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String formatTime(long time){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
	}

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	/**
	 * 支持的文件类型
	 */
	static{
		FILE_TYPE_MAP.put("jpg", "image"); //JPEG (jpg)
		FILE_TYPE_MAP.put("png", "image"); //PNG (png)
		FILE_TYPE_MAP.put("gif", "image"); //GIF (gif)
		FILE_TYPE_MAP.put("tif", "image"); //TIFF (tif)
		FILE_TYPE_MAP.put("bmp", "image"); //16色位图(bmp)
		FILE_TYPE_MAP.put("bmp", "image"); //24位位图(bmp)
		FILE_TYPE_MAP.put("bmp", "image"); //256色位图(bmp)

		FILE_TYPE_MAP.put("html", "document"); //HTML (html)
		FILE_TYPE_MAP.put("htm" , "document"); //HTM (htm)
		FILE_TYPE_MAP.put("css" , "document"); //css
		FILE_TYPE_MAP.put("js"  , "document"); //js
		FILE_TYPE_MAP.put("ini" , "document");
		FILE_TYPE_MAP.put("txt" , "document");
		FILE_TYPE_MAP.put("jsp" , "document");//jsp文件
		FILE_TYPE_MAP.put("sql" , "document");//xml文件
		FILE_TYPE_MAP.put("xml" , "document");//xml文件
		FILE_TYPE_MAP.put("java", "document");//java文件
		FILE_TYPE_MAP.put("bat" , "document");//bat文件
		FILE_TYPE_MAP.put("mxp" , "document");//bat文件
		FILE_TYPE_MAP.put("properties", "document");//bat文件
		FILE_TYPE_MAP.put("doc", "document"); //MS Excel 注意：word、msi 和 excel的文件头一样
		FILE_TYPE_MAP.put("docx", "document");//docx文件
		FILE_TYPE_MAP.put("vsd", "document"); //Visio 绘图
		FILE_TYPE_MAP.put("mdb", "document"); //MS Access (mdb)
		FILE_TYPE_MAP.put("pdf", "document"); //Adobe Acrobat (pdf)
		FILE_TYPE_MAP.put("xlsx", "document");//docx文件
		FILE_TYPE_MAP.put("xls", "document");//docx文件
		FILE_TYPE_MAP.put("pptx", "document");//docx文件
		FILE_TYPE_MAP.put("ppt", "document");//docx文件
		FILE_TYPE_MAP.put("wps", "document");//WPS文字wps、表格et、演示dps都是一样的

		FILE_TYPE_MAP.put("zip", "comp");
		FILE_TYPE_MAP.put("rar", "comp");
		FILE_TYPE_MAP.put("gz" , "comp");//gz文件

		FILE_TYPE_MAP.put("class", "file");//bat文件
		FILE_TYPE_MAP.put("jar", "file");
		FILE_TYPE_MAP.put("exe", "file");//可执行文件
		FILE_TYPE_MAP.put("mf", "file");//MF文件
		FILE_TYPE_MAP.put("chm", "file");//bat文件
		FILE_TYPE_MAP.put("torrent", "file");
		FILE_TYPE_MAP.put("wpd", "file"); //WordPerfect (wpd)
		FILE_TYPE_MAP.put("dbx", "file"); //Outlook Express (dbx)
		FILE_TYPE_MAP.put("pst", "file"); //Outlook (pst)
		FILE_TYPE_MAP.put("qdf", "file"); //Quicken (qdf)
		FILE_TYPE_MAP.put("pwl", "file"); //Windows Password (pwl)
		FILE_TYPE_MAP.put("ram", "file"); //Real Audio (ram)
	}

	/**
	 * 获取文件类型
	 * @param file
	 * @return
	 */
	public static String getFileType(File file) {
		if(file.isDirectory()){
			return "folder";
		}
		String fileName = file.getPath();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String fileType = FILE_TYPE_MAP.get(suffix);
		return fileType == null ? "file" : fileType;
	}


}
