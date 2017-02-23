package com.hshc.upms.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.system.LoginFailLog;
import com.hshc.upms.service.api.system.LoginFailService;

/**
 * 登录失败日志显示模块
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/loginFail")
public class LoginFailController extends BaseCRUDAction<LoginFailLog, Integer>{

	@Autowired
	LoginFailService<LoginFailLog, Integer> loginFailService;
	
	@Override
	public BaseService<LoginFailLog, Integer> getBaseService() {
		return loginFailService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,LoginFailLog t, String operateFlag) {
		// TODO Auto-generated method stub
		
	}
}
