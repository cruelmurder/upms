package com.hshc.upms.entity.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hshc.mdm.entity.mst.MstOrganCompany;
import com.hshc.mdm.entity.security.SecurityPosition;
import com.hshc.mdm.entity.security.SecurityUser;
import com.hshc.upms.vo.user.DataPermissionVo;
import com.hshc.upms.vo.user.PermissionVo;

public class User extends SecurityUser {

	private static final long serialVersionUID = 6337475073048287298L;

	/**
	 * 权限列表
	 */
	private Set<PermissionVo> permList;
	
	/**
	 * 数据权限
	 */
	private DataPermissionVo dataPerm;
	
	/**
	 * 用户岗位
	 */
	private List<SecurityPosition> positionList;
	
	/**
	 * 用户所属机构
	 */
	private List<MstOrganCompany> orgList;
	
	/**
	 * 用户监管机构
	 */
	private List<MstOrganCompany> supOrgList;
	
	public Set<PermissionVo> getPermList() {
		return permList;
	}

	public void setPermList(Set<PermissionVo> permList) {
		this.permList = permList;
	}

	public Set<String> getPerms() {
		Set<PermissionVo> permissionVoSet = getPermList();
		if (permissionVoSet == null) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		for (PermissionVo permissionVo : permissionVoSet) {
			if (StringUtils.isNotEmpty(permissionVo.getHref())) {
				allPerms.add(permissionVo.getHref());
			}
		}
		return allPerms;
	}
	public Set<String> getPermsValue() {
		Set<PermissionVo> permissionVoSet = getPermList();
		if (permissionVoSet == null) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		for (PermissionVo permissionVo : permissionVoSet) {
			if (StringUtils.isNotEmpty(permissionVo.getPermission())) {
				allPerms.add(permissionVo.getPermission());
			}
		}
		return allPerms;
	}
	public List<SecurityPosition> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<SecurityPosition> positionList) {
		this.positionList = positionList;
	}

	public List<MstOrganCompany> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<MstOrganCompany> orgList) {
		this.orgList = orgList;
	}

	public List<MstOrganCompany> getSupOrgList() {
		return supOrgList;
	}

	public void setSupOrgList(List<MstOrganCompany> supOrgList) {
		this.supOrgList = supOrgList;
	}
	/**
	 * 登录用户所属公司
	 * @return
	 */
	public String getCompanyName(){
		String companyName="";
		MstOrganCompany org = null;
		if(orgList!=null && orgList.size()>0){
			org = orgList.get(0);
			if(org.getIsThirdparty()!=null && org.getIsThirdparty()-6==0){
				companyName=org.getCode();
			}else{
				companyName="hshc";
			}
		}
		return companyName;
	}

	public DataPermissionVo getDataPerm() {
		return dataPerm;
	}

	public void setDataPerm(DataPermissionVo dataPerm) {
		this.dataPerm = dataPerm;
	}

}
