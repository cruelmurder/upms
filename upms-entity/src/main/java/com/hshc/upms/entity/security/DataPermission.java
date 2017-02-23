/**
 * 
 */
package com.hshc.upms.entity.security;

import java.util.ArrayList;
import java.util.List;

import com.hshc.common.entity.BaseEntity;

/**
 * 数据权限
 * @author zhanghua
 *
 */
public class DataPermission extends BaseEntity{

	private static final long serialVersionUID = 8835457159475814367L;
	
	/**
	 *主键ID 
	 */
	private Integer id;

	/**
	 * 数据权限代码
	 */
	private String spDataCode;
	
	/**
	 * 数据权限名称
	 */
	private String spDataName;
	
	/**
	 * 数据权限描述
	 */
	private String spDataDescribe;
	
	/**
	 * 排序
	 */
	private Integer spDataRank;
	
	/**
	 * 是否选择
	 */
	private Boolean isChecked;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpDataCode() {
		return spDataCode;
	}

	public void setSpDataCode(String spDataCode) {
		this.spDataCode = spDataCode;
	}

	public String getSpDataName() {
		return spDataName;
	}

	public void setSpDataName(String spDataName) {
		this.spDataName = spDataName;
	}

	public String getSpDataDescribe() {
		return spDataDescribe;
	}

	public void setSpDataDescribe(String spDataDescribe) {
		this.spDataDescribe = spDataDescribe;
	}

	public Integer getSpDataRank() {
		return spDataRank;
	}

	public void setSpDataRank(Integer spDataRank) {
		this.spDataRank = spDataRank;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
}
