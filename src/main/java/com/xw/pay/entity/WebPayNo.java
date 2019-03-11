package com.xw.pay.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * web_pay_no
 * ping++支付单号记录表
 * @author EntityGenerateTool
 */
public class WebPayNo implements Serializable{
	private static final long serialVersionUID = 1L;
	/** no_id VARCHAR */
	protected java.lang.String noId;//
	/** no_order CHAR */
	protected java.lang.String noOrder;//预约单ID
	/** no_charge VARCHAR */
	protected java.lang.String noCharge;//ping++返回的chargeId
	/** no_user CHAR */
	protected java.lang.String noUser;//用户ID
	/** no_subject VARCHAR */
	protected java.lang.String noSubject;//商品标题
	/** no_amount FLOAT */
	protected java.lang.Float noAmount;//支付金额(元）
	/** data_state CHAR */
	protected java.lang.String dataState;//数据状态 init:初始化  pay:已支付 applyRefund:申请退款    refunded:已退款
	/** create_time TIMESTAMP */
	protected java.util.Date createTime;//创建时间
	/** pay_date TIMESTAMP */
	protected java.util.Date payDate;//支付时间
	/** transaction_no VARCHAR */
	protected java.lang.String transactionNo;//支付交易号
	/** apply_date TIMESTAMP */
	protected java.util.Date applyDate;//申请退订时间
	/** refund_date TIMESTAMP */
	protected java.util.Date refundDate;//退订时间
	protected java.lang.String payUserName;//支付人
	protected java.lang.String payUserPhone;//支付人手机号
	protected java.lang.String payChannel;//支付渠道
	
	
	
	public WebPayNo() {
        super();
    }

    public WebPayNo(String noId, String noOrder, String noCharge, String noUser, String noSubject, String dataState,
            Date createTime, String payUserName, String payUserPhone, String payChannel) {
        super();
        this.noId = noId;
        this.noOrder = noOrder;
        this.noCharge = noCharge;
        this.noUser = noUser;
        this.noSubject = noSubject;
        this.dataState = dataState;
        this.createTime = createTime;
        this.payUserName = payUserName;
        this.payUserPhone = payUserPhone;
        this.payChannel = payChannel;
    }

    public java.lang.String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(java.lang.String payChannel) {
        this.payChannel = payChannel;
    }

    public java.lang.String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(java.lang.String payUserName) {
        this.payUserName = payUserName;
    }

    public java.lang.String getPayUserPhone() {
        return payUserPhone;
    }

    public void setPayUserPhone(java.lang.String payUserPhone) {
        this.payUserPhone = payUserPhone;
    }

    /**
	 * @Title: no_id
	 * @Description: 
	 * @return java.lang.String
	 */
	public java.lang.String getNoId() {
		return noId;
	}

	/**
	 * @Title: no_id
	 * @Description: 
	 * @param java.lang.String
	 */
	public void setNoId(java.lang.String noId) {
		this.noId = noId;
	}
	/**
	 * @Title: no_order
	 * @Description: 预约单ID
	 * @return java.lang.String
	 */
	public java.lang.String getNoOrder() {
		return noOrder;
	}

	/**
	 * @Title: no_order
	 * @Description: 预约单ID
	 * @param java.lang.String
	 */
	public void setNoOrder(java.lang.String noOrder) {
		this.noOrder = noOrder;
	}
	/**
	 * @Title: no_charge
	 * @Description: ping++返回的chargeId
	 * @return java.lang.String
	 */
	public java.lang.String getNoCharge() {
		return noCharge;
	}

	/**
	 * @Title: no_charge
	 * @Description: ping++返回的chargeId
	 * @param java.lang.String
	 */
	public void setNoCharge(java.lang.String noCharge) {
		this.noCharge = noCharge;
	}
	/**
	 * @Title: no_user
	 * @Description: 用户ID
	 * @return java.lang.String
	 */
	public java.lang.String getNoUser() {
		return noUser;
	}

	/**
	 * @Title: no_user
	 * @Description: 用户ID
	 * @param java.lang.String
	 */
	public void setNoUser(java.lang.String noUser) {
		this.noUser = noUser;
	}
	/**
	 * @Title: no_subject
	 * @Description: 商品标题
	 * @return java.lang.String
	 */
	public java.lang.String getNoSubject() {
		return noSubject;
	}

	/**
	 * @Title: no_subject
	 * @Description: 商品标题
	 * @param java.lang.String
	 */
	public void setNoSubject(java.lang.String noSubject) {
		this.noSubject = noSubject;
	}
	/**
	 * @Title: no_amount
	 * @Description: 支付金额(元）
	 * @return java.lang.Float
	 */
	public java.lang.Float getNoAmount() {
		return noAmount;
	}

	/**
	 * @Title: no_amount
	 * @Description: 支付金额(元）
	 * @param java.lang.Float
	 */
	public void setNoAmount(java.lang.Float noAmount) {
		this.noAmount = noAmount;
	}
	/**
	 * @Title: data_state
	 * @Description: 数据状态 init:初始化  pay:已支付 applyRefund:申请退款    refunded:已退款
	 * @return java.lang.String
	 */
	public java.lang.String getDataState() {
		return dataState;
	}

	/**
	 * @Title: data_state
	 * @Description: 数据状态 init:初始化  pay:已支付 applyRefund:申请退款    refunded:已退款
	 * @param java.lang.String
	 */
	public void setDataState(java.lang.String dataState) {
		this.dataState = dataState;
	}
	/**
	 * @Title: create_time
	 * @Description: 创建时间
	 * @return java.util.Date
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * @Title: create_time
	 * @Description: 创建时间
	 * @param java.util.Date
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Title: pay_date
	 * @Description: 支付时间
	 * @return java.util.Date
	 */
	public java.util.Date getPayDate() {
		return payDate;
	}

	/**
	 * @Title: pay_date
	 * @Description: 支付时间
	 * @param java.util.Date
	 */
	public void setPayDate(java.util.Date payDate) {
		this.payDate = payDate;
	}
	/**
	 * @Title: transaction_no
	 * @Description: 支付宝交易号
	 * @return java.lang.String
	 */
	public java.lang.String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * @Title: transaction_no
	 * @Description: 支付宝交易号
	 * @param java.lang.String
	 */
	public void setTransactionNo(java.lang.String transactionNo) {
		this.transactionNo = transactionNo;
	}
	/**
	 * @Title: apply_date
	 * @Description: 申请退订时间
	 * @return java.util.Date
	 */
	public java.util.Date getApplyDate() {
		return applyDate;
	}

	/**
	 * @Title: apply_date
	 * @Description: 申请退订时间
	 * @param java.util.Date
	 */
	public void setApplyDate(java.util.Date applyDate) {
		this.applyDate = applyDate;
	}
	/**
	 * @Title: refund_date
	 * @Description: 退订时间
	 * @return java.util.Date
	 */
	public java.util.Date getRefundDate() {
		return refundDate;
	}

	/**
	 * @Title: refund_date
	 * @Description: 退订时间
	 * @param java.util.Date
	 */
	public void setRefundDate(java.util.Date refundDate) {
		this.refundDate = refundDate;
	}

	/**
	 * @Title: 克隆
	 * @Description: JAVA对象的克隆
	 * @param com.hfjy.base.entity.WebPayNo
	 */
	@Override
	protected WebPayNo clone() throws CloneNotSupportedException {
		return (WebPayNo)super.clone();
	}
}
