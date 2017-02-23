package com.hshc.upms.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.system.LoginSuccessLog;
import com.hshc.upms.service.api.system.LoginSuccessService;

/**
 * 登录成功日志显示模块
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/loginSuccess")
public class LoginSuccessController extends BaseCRUDAction<LoginSuccessLog, Integer> {

	@Autowired
	LoginSuccessService<LoginSuccessLog, Integer> loginSuccessService;
	
	@Override
	public BaseService<LoginSuccessLog, Integer> getBaseService() {
		return loginSuccessService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,LoginSuccessLog t, String operateFlag) {
		// TODO Auto-generated method stub
		
	}
}
