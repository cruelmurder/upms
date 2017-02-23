/**
 * 
 */
package com.hshc.upms.web.controller.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.security.DataPermission;
import com.hshc.upms.service.api.security.DataPermissionService;

/**
 * 
 * 角色管理模块
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/dataPermissionManage")
public class DataPermissionManageController extends BaseCRUDAction<DataPermission, Integer> {

	@Autowired
	DataPermissionService<DataPermission, Integer> dataPermissionService;

	@Override
	public BaseService<DataPermission, Integer> getBaseService() {
		return dataPermissionService;
	}
	
	/**
	 * 加载数据权限列表
	 * @param response
	 * @param parentId
	 * @param srId
	 * @param sysId
	 * @return
	 */
	@RequestMapping(value = "dataPermissionList", method = RequestMethod.POST)
	@ResponseBody
	public List<DataPermission> getButtonList(
			HttpServletResponse response, Long srId,
			Long sysId) {

		List<DataPermission> queryList = dataPermissionService
					.getRoleDataPermission(srId.intValue());
		
		return queryList;
	}
	
	/* (non-Javadoc)
	 * @see com.hshc.common.web.controller.BaseCRUDAction#setDefaultValue(javax.servlet.http.HttpServletRequest, com.hshc.common.entity.BaseEntity, java.lang.String)
	 */
	@Override
	protected void setDefaultValue(HttpServletRequest request, DataPermission t, String operateFlag) {
		// TODO Auto-generated method stub
		
	}

}
