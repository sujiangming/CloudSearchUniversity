/**
 * @author Leestar54 
 * http://www.cnblogs.com/leestar54
 */
package com.gk.tools;

public class AppVersion {
	private String appName;// app名称
	private String apkName;// apk名称
	private String versionName;// 给用户看的版本
	private int verCode;// 开发版本号
	private String url;// 下载地址
	private String content;// 描述
	private String sha1;// 验证完整性

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}
}
