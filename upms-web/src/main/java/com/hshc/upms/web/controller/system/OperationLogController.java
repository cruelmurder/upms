/**
 * 
 */
package com.hshc.upms.web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.upms.entity.system.OperationLog;
import com.hshc.upms.service.api.system.OperationLogService;

/**
 * 操作日志模块
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/operationLog")
public class OperationLogController extends BaseCRUDAction<OperationLog, Integer> {

	@Autowired
	OperationLogService<OperationLog,Integer> operationLogService;
	
	@Override
	public BaseService<OperationLog, Integer> getBaseService() {
		return operationLogService;
	}

	@Override
	protected void setDefaultValue(HttpServletRequest request,OperationLog t, String operateFlag) {
		
	}
}
