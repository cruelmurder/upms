package com.hshc.upms.service.impl.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.upms.dao.security.UserRoleMapper;
import com.hshc.upms.entity.security.Role;
import com.hshc.upms.service.api.security.UserRoleService;

@Service
public class UserRoleServiceImpl<T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements UserRoleService<T, ID> {

	@Autowired
	UserRoleMapper<T, ID> userRoleMapper;

	/**
	 * 根据用户ID查找用户的角色
	 */
	@Override
	public List<Role> getRolesByUserId(Map<String, String> userinfo) {
		return userRoleMapper.getRolesByUserId(userinfo);
	}

	/**
	 * 根据用户ID查找用户未有的角色
	 */
	@Override
	public List<Role> getRolesByNotUserId(Map<String, String> userinfo) {
		return userRoleMapper.getRolesByNotUserId(userinfo);

	}

	/**
	 * 查询用户已有角色和未有角色
	 */
	public Map<String, List<Role>> selectRoleManager(String userId) {
		Map<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("userId", userId);
		List<Role> ownRoles = userRoleMapper.getRolesByUserId(userinfo);
		List<Role> newRoles = userRoleMapper.getRolesByNotUserId(userinfo);
		Map<String, List<Role>> roles = new HashMap<String, List<Role>>();
		roles.put("ownRoles", ownRoles);
		roles.put("newRoles", newRoles);
		return roles;
	}

	/**
	 * 修改用户角色
	 */
	public void saveOrUpdateUserRoles(String userId, String roleIds) {
		Map<String, Object> userRoles = new HashMap<String, Object>();
		userRoles.put("userId", Integer.parseInt(userId));
		// 删除已有角色
		userRoleMapper.deleteRoleByUser(userRoles);
		String[] ids = null;
		if (roleIds != null && roleIds.length() > 0) {
			ids = roleIds.split(",");
		}
		if(ids !=null && ids.length>0){
			userRoles.put("roleIds", ids);
			userRoleMapper.insertRolesForUser(userRoles);
		}
	}

	@Override
	public BaseMapper<T, ID> getMapper() {
		return userRoleMapper;
	}

	@Override
	public List<Map<String, Object>> queryMasLocList() {
		return userRoleMapper.queryMasLocList();
	}
	
}
