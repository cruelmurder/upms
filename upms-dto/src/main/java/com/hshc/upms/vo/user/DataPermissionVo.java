/**
 * 
 */
package com.hshc.upms.vo.user;

import java.util.List;

import com.hshc.common.entity.BaseSerializable;

/**
 * 数据权限列表
 * @author zhanghua
 * 
 */
public class DataPermissionVo implements BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7939792532221719015L;
	
	/**
	 * 机构数据权限列表
	 */
	private List<String> orgCodeList;
	/**
	 * 是否只查询本人数据
	 */
	private Boolean isSelf=false;
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	public Boolean getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}
	

}
