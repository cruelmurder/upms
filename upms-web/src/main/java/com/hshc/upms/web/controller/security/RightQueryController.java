package com.hshc.upms.web.controller.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.security.User;
/**
 * 用户权限查询的Controller
 * @author muss
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rightQuery")
public class RightQueryController extends BaseCRUDAction<User,Integer>{

	@Override
	public BaseService<User, Integer> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request, User t,
			String operateFlag) {
		// TODO Auto-generated method stub
		
	}

}
