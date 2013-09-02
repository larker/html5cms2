package com.larkersos.util;

import java.util.UUID;

/**
 * 工具类 - 编号生成
 * ============================================================================
 * larkersos.com
 * ============================================================================
 */

public class SerialNumberUtil {
	
	public static final String PRODUCT_SN_PREFIX = "SN_";// 货号前缀
	
	public static final String ORDER_SN_PREFIX = "";// 订单编号前缀
	public static final long ORDER_SN_FIRST = 100000L;// 订单编号起始数
	public static final long ORDER_SN_STEP = 1L;// 订单编号步长
	
	public static final String PAYMENT_SN_PREFIX = "";// 支付编号前缀
	public static final long PAYMENT_SN_FIRST = 100000L;// 支付编号起始数
	public static final long PAYMENT_SN_STEP = 1L;// 支付编号步长
	
	public static final String REFUND_SN_PREFIX = "";// 退款编号前缀
	public static final long REFUND_SN_FIRST = 100000L;// 退款编号起始数
	public static final long REFUND_SN_STEP = 1L;// 退款编号步长
	
	public static final String SHIPPING_SN_PREFIX = "";// 发货编号前缀
	public static final long SHIPPING_SN_FIRST = 100000L;// 发货编号起始数
	public static final long SHIPPING_SN_STEP = 1L;// 发货编号步长
	
	public static final String RESHIP_SN_PREFIX = "";// 退货编号前缀
	public static final long RESHIP_SN_FIRST = 100000L;// 退货编号起始数
	public static final long RESHIP_SN_STEP = 1L;// 退货编号步长
	
	public static final String ORGANISE_CODE_PREFIX = "ORG";// 退货编号前缀
	public static final long ORGANISE_CODE_FIRST = 100000L;// 退货编号起始数
	public static final long ORGANISE_CODE_STEP = 1L;// 退货编号步长
	
	
	public static Long lastOrderSnNumber;
	public static Long lastPaymentSnNumber;
	public static Long lastRefundSnNumber;
	public static Long lastShippingSnNumber;
	public static Long lastReshipSnNumber;
	public static Long lastTenpayTransactionIdNumber;
	
	public static Long lastOrganiseCode=0L;

	static {
		// 订单编号
		
		// 支付编号
		
		// 退款编号
		
		// 发货编号
		
		// 退货编号
	}
	
	/**
	 * 生成货号
	 * 
	 * @return 货号
	 */
	public static String buildProductSn() {
		String uuid = UUID.randomUUID().toString();
		return PRODUCT_SN_PREFIX + (uuid.substring(0, 8) + uuid.substring(9, 13)).toUpperCase();
	}
	
	/**
	 * 生成订单编号
	 * 
	 * @return 订单编号
	 */
	public synchronized static String buildOrderSn() {
		lastOrderSnNumber += ORDER_SN_STEP;
		return ORDER_SN_PREFIX + lastOrderSnNumber;
	}
	
	/**
	 * 生成支付编号
	 * 
	 * @return 支付编号
	 */
	public synchronized static String buildPaymentSn() {
		lastPaymentSnNumber += PAYMENT_SN_STEP;
		return PAYMENT_SN_PREFIX + lastPaymentSnNumber;
	}
	
	/**
	 * 生成退款编号
	 * 
	 * @return 退款编号
	 */
	public synchronized static String buildRefundSn() {
		lastRefundSnNumber += REFUND_SN_STEP;
		return REFUND_SN_PREFIX + lastRefundSnNumber;
	}
	
	/**
	 * 生成发货编号
	 * 
	 * @return 发货编号
	 */
	public synchronized static String buildShippingSn() {
		lastShippingSnNumber += SHIPPING_SN_STEP;
		return SHIPPING_SN_PREFIX + lastShippingSnNumber;
	}
	
	/**
	 * 生成退货编号
	 * 
	 * @return 退货编号
	 */
	public synchronized static String buildReshipSn() {
		lastReshipSnNumber += RESHIP_SN_STEP;
		return RESHIP_SN_PREFIX + lastReshipSnNumber;
	}
	
	/**
	 * 生成单位编号
	 * 
	 * @return 单位编号
	 */
	public synchronized static String buildOrganiseCode() {
		return buildOrganiseCode(ORGANISE_CODE_PREFIX);
	}
	public synchronized static String buildOrganiseCode(String prefix) {
		lastOrganiseCode += ORGANISE_CODE_STEP;
		return prefix + lastOrganiseCode;
	}
}