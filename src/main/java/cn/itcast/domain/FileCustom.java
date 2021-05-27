package cn.itcast.domain;

import java.io.File;

/**
 * 文件实体类
 */
public class FileCustom {
	/**
	 * 文件相对前缀
	 */
	public static final String PREFIX = "WEB-INF" + File.separator + "file" + File.separator;

	private String fileName;
	private String fileType;
	private String fileSize;
	private String lastTime;
	private String filePath;
	private String currentPath;
	private String url;
	public String getCurrentPath() {
		return currentPath;
	}
	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "FileCustom{" +
				"fileName='" + fileName + '\'' +
				", fileType='" + fileType + '\'' +
				", fileSize='" + fileSize + '\'' +
				", lastTime='" + lastTime + '\'' +
				", filePath='" + filePath + '\'' +
				", currentPath='" + currentPath + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
