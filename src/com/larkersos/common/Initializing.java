package com.larkersos.common;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.larkersos.util.SystemConfigUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 初始化
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public class Initializing implements InitializingBean {
	// 明文
	private String configFile = SystemConfigUtil.CONFIG_FILE_NAME;
	private String keyFile = "key";
	
	@Resource
	private ServletContext servletContext;
 
	@Override
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			String enCodeKeyFile = keyFile + "A==";
			// larkersos.xml : bGFya2Vyc29zLnhtbA==
			if (!StringUtils.equals(StringUtils.substring(enCodeKeyFile, 8), "c29zLnhtbA==")) {
				throw new ExceptionInInitializerError();
			}
			servletContext.setAttribute(new String(bASE64Decoder.decodeBuffer("Q09ORklHX0ZJTEVfTkFNRV9LRVk=")), configFile);
		}
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

	public static void main(String[] keyFile) {
		BASE64Encoder bASE64Encoder = new BASE64Encoder();
		BASE64Decoder bASE64Decoder = new BASE64Decoder();
		
		// bGFya2Vyc29zLnhtbA==
		System.out.println("larkersos.xml getBytes="+bASE64Encoder.encode("larkersos.xml".getBytes()));
		
		System.out.println("http://www.larksersos.com getBytes="+bASE64Encoder.encode("http://www.larksersos.com".getBytes()));
		
		System.out.println("CONFIG_FILE_NAME_KEY getBytes="+bASE64Encoder.encode("CONFIG_FILE_NAME_KEY".getBytes()));
		
		try {
			System.out.println("U0hPUFhYX0tFWQ decodeBuffer="+bASE64Decoder.decodeBuffer("U0hPUFhYX0tFWQ=="));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}