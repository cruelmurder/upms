package com.hshc.upms.web.controller.security;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.security.Role;
import com.hshc.upms.entity.security.UserRole;
import com.hshc.upms.service.api.security.UserRoleService;
import com.google.common.collect.Maps;

/**
 * 用户角色Controller
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/userRole")
public class UserRoleController extends BaseCRUDAction<UserRole, Integer> {

	@Autowired
	UserRoleService<UserRole, Integer> userRoleService;
	
	@RequestMapping(value = "queryMasLocList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryMasLocList() {
		return userRoleService.queryMasLocList();
	}
	/**
	 * 角色管理
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "roleManager", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> roleManager(
			@RequestParam("userId") String userId,
			@RequestParam("roleIds") String roleIds) {
		Map<String, String> msg = Maps.newHashMap();
		try {
			userRoleService.saveOrUpdateUserRoles(userId, roleIds);
			msg.put("msg", "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("msg", "修改失败！");
		}
		return msg;
	}

	/**
	 * 获取某个用户的所有角色
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Role>> roles(@RequestParam("userId") String userId) {
		if (userId != null && userId.length() > 0) {
			Map<String, List<Role>> roles = userRoleService
					.selectRoleManager(userId);
			return roles;
		}
		return null;
	}

	@Override
	public BaseService<UserRole, Integer> getBaseService() {
		return userRoleService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,UserRole t, String operateFlag) {
	}
}
