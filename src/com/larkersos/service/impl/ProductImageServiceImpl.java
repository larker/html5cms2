package com.larkersos.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.larkersos.bean.ProductImage;
import com.larkersos.bean.SystemConfig;
import com.larkersos.service.ProductImageService;
import com.larkersos.util.CommonUtil;
import com.larkersos.util.ImageUtil;
import com.larkersos.util.SystemConfigUtil;

/**
 * Service实现类 - 商品图片
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

@Service
public class ProductImageServiceImpl implements ProductImageService {
	
	public ProductImage buildProductImage(File uploadProductImageFile) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		String sourceProductImageFormatName = ImageUtil.getImageFormatName(uploadProductImageFile);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtil.getUUID();
		
		String sourceProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourceProductImageFormatName;
		String bigProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.BIG_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String smallProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.SMALL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String thumbnailProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.THUMBNAIL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;

		
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceProductImagePath));
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigProductImagePath));
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallProductImagePath));
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailProductImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

		File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
		File bigProductImageParentFile = bigProductImageFile.getParentFile();
		File smallProductImageParentFile = smallProductImageFile.getParentFile();
		File thumbnailProductImageParentFile = thumbnailProductImageFile.getParentFile();

		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		if (!bigProductImageParentFile.exists()) {
			bigProductImageParentFile.mkdirs();
		}
		if (!smallProductImageParentFile.exists()) {
			smallProductImageParentFile.mkdirs();
		}
		if (!thumbnailProductImageParentFile.exists()) {
			thumbnailProductImageParentFile.mkdirs();
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(uploadProductImageFile);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(uploadProductImageFile, sourceProductImageFile);
			// 商品图片（大）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, bigProductImageFile, systemConfig.getBigProductImageHeight(), systemConfig.getBigProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, smallProductImageFile, systemConfig.getSmallProductImageHeight(), systemConfig.getSmallProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
			ImageUtil.zoom(srcBufferedImage, thumbnailProductImageFile, systemConfig.getThumbnailProductImageHeight(), systemConfig.getThumbnailProductImageWidth());
		

//			// 分组织单位保存上传图片 larkersos
//			String UPLOAD_ORG_FILE_DIR = File.separator + "html" + File.separator + SystemConfigUtil.getDepartmentId();
//			File sourceProductImageFile_ORG = new File(ServletActionContext.getServletContext().getRealPath(UPLOAD_ORG_FILE_DIR + sourceProductImagePath));
//			File bigProductImageFile_ORG = new File(ServletActionContext.getServletContext().getRealPath(UPLOAD_ORG_FILE_DIR +bigProductImagePath));
//			File smallProductImageFile_ORG = new File(ServletActionContext.getServletContext().getRealPath(UPLOAD_ORG_FILE_DIR +smallProductImagePath));
//			File thumbnailProductImageFile_ORG = new File(ServletActionContext.getServletContext().getRealPath(UPLOAD_ORG_FILE_DIR +thumbnailProductImagePath));
//			// 将上传图片复制到对应的组织单位图片目录
//			FileUtils.copyFile(sourceProductImageFile, sourceProductImageFile_ORG);
//			FileUtils.copyFile(bigProductImageFile, bigProductImageFile_ORG);
//			FileUtils.copyFile(smallProductImageFile, smallProductImageFile_ORG);
//			FileUtils.copyFile(thumbnailProductImageFile, thumbnailProductImageFile_ORG);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ProductImage productImage = new ProductImage();
		productImage.setId(uuid);
		productImage.setSourceProductImagePath(sourceProductImagePath);
		productImage.setBigProductImagePath(bigProductImagePath);
		productImage.setSmallProductImagePath(smallProductImagePath);
		productImage.setThumbnailProductImagePath(thumbnailProductImagePath);
		return productImage;
	}
	
	public void deleteFile(ProductImage productImage) {
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSourceProductImagePath()));
		if (sourceProductImageFile.exists()) {
			sourceProductImageFile.delete();
		}
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getBigProductImagePath()));
		if (bigProductImageFile.exists()) {
			bigProductImageFile.delete();
		}
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSmallProductImagePath()));
		if (smallProductImageFile.exists()) {
			smallProductImageFile.delete();
		}
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getThumbnailProductImagePath()));
		if (thumbnailProductImageFile.exists()) {
			thumbnailProductImageFile.delete();
		}
	}

}