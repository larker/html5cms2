package com.larkersos.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

/**
 * 工具类 - 文件操作
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public class FilesUtil {

	/**
	 * 文件夹拷贝，将源文件夹下的所有文件及其子文件夹（文件）拷贝到目标文件夹（文件）下
	 * 
	 * @param srcFileUrl
	 *            源文件
	 * @param desFileUrl
	 *            目标文件
	 * @return
	 */
	public static boolean copyDirectiories(String srcFileUrl, String desFileUrl)
			throws IOException {
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		String sourceFile = servletContext.getRealPath(srcFileUrl);
		String targetFile = servletContext.getRealPath(desFileUrl);
		
		// 判断处理文件
		File source = new File(sourceFile);
		if (!source.exists()) {
			System.out.println(source.getAbsolutePath() + "========源文件不存在！=======");
			return false;  
		}
		File target = new File(targetFile);
		if (!target.exists())
			target.mkdirs();// 不存在目标文件就创建

		File[] file = source.listFiles();
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) { // 如果是文件 则读源文件 写入目标文件
					input = new FileInputStream(file[i]);
					output = new FileOutputStream(new File(targetFile + "/"
							+ file[i].getName()));
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) { // 读文件
						output.write(b, 0, len); // 向目标文件写文件
					}
					input.close();
					output.flush();
					output.close();
				} else if (file[i].isDirectory()) { // 如果是文件夹 递归读取子文件或文件夹
					copyDirectiories(srcFileUrl + "/" + file[i].getName(),
							desFileUrl + "/" + file[i].getName());
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
		}
	}

	/**
	 * 文件copy
	 * 
	 */
	public static void copyFile(File sourcefile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);
		BufferedInputStream inbuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len = 0;
		while ((len = inbuff.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		// 刷新此缓冲的输出流
		outbuff.flush();

		// 关闭流
		inbuff.close();
		outbuff.close();
		out.close();
		input.close();
	}

	/**
	 * 文件夹copy
	 * 
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {

		// 新建目标目录
		(new File(targetDir)).mkdirs();

		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());

				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();

				copyDirectiory(dir1, dir2);
			}
		}
	}
}