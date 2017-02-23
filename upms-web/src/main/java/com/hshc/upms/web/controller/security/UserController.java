package com.hshc.upms.web.controller.security;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.hshc.common.constants.Constants;
import com.hshc.common.exception.BaseException;
import com.hshc.common.security.encoder.Md5PwdEncoder;
import com.hshc.common.service.BaseService;
import com.hshc.common.util.LogTracking;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.common.web.springmvc.MessageResolver;
import com.hshc.common.web.util.ResponseWorker;
import com.hshc.mdm.entity.security.SecurityUser;
import com.hshc.mdm.service.api.security.SecurityUserService;
import com.hshc.upms.entity.security.User;
import com.hshc.upms.web.controller.util.UserSessionProvider;

/**
 * 用户模块
 * 
 * @author huanggaoren
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/user")
public class UserController extends BaseCRUDAction<SecurityUser, Integer> {

	@Autowired
	SecurityUserService<SecurityUser, Integer> securityUserService;

	@Override
	public BaseService<SecurityUser, Integer> getBaseService() {
		return securityUserService;
	}
	
	@RequestMapping(value = "/updateById", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateById(
			final HttpServletRequest request, ModelMap model) {
		final Map<String, Object> map = Maps.newHashMap();
		try {
			String userData = request.getParameter("rowsData");
			List<SecurityUser> userList = JSONArray.parseArray(userData, SecurityUser.class);
			Integer [] userIds = new Integer[userList.size()];
			for (int i = 0; i < userList.size(); i++) {
				userIds[i] = userList.get(i).getId().intValue();
			}
			final int i = securityUserService.updateById(userIds);
			ResponseWorker<T> worker = new ResponseWorker<T>() {
				@Override
				protected Map<String, Object> processMsg() throws Exception {
					if (i > 0) {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.SUCCESS_ACTIVATE));
						map.put("state", Constants.State.TRUE);
					} else {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.FAILED_ACTIVATE));
						map.put("state", Constants.State.FALSE);
					}
					return map;
				}
			};
			return worker.execute();
		} catch (BaseException e) {
			e.printStackTrace();
			map.put("msg", MessageResolver.getMessage(request,
					Constants.Message.FAILED_UPDATE));
			map.put("state", Constants.State.FALSE);
			LogTracking.errorLog(this.getClass(), "update", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	@RequestMapping(value = "/deleteAccounts", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteByIds(
			final HttpServletRequest request, ModelMap model,
			@RequestParam("ids[]") Integer ids[]) {
		final Map<String, Object> map = Maps.newHashMap();
		try {
			final int i = securityUserService.deleteByIds(ids);
			ResponseWorker<T> worker = new ResponseWorker<T>() {
				@Override
				protected Map<String, Object> processMsg() throws Exception {
					if (i > 0) {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.SUCCESS_DELETE));
						map.put("state", Constants.State.TRUE);
					} else {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.FAILED_DELETE));
						map.put("state", Constants.State.FALSE);
					}
					return map;
				}
			};
			return worker.execute();
		} catch (BaseException e) {
			e.printStackTrace();
			map.put("msg", MessageResolver.getMessage(request,
					Constants.Message.FAILED_DELETE));
			map.put("state", Constants.State.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(
			final HttpServletRequest request, SecurityUser user,Integer positionId,Long comboOrgTreeId,String comboSupOrgTreeIds) { // @RequestBody

		final Map<String, Object> map = Maps.newHashMap();
		try {
			setDefaultValue(request, user, "update");// 临时设置默认值
			final int i = securityUserService.updateUser(user,comboSupOrgTreeIds,comboOrgTreeId,positionId);//
			ResponseWorker<SecurityUser> worker = new ResponseWorker<SecurityUser>() {
				@Override
				protected Map<String, Object> processMsg() throws Exception {
					if (i > 0) {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.SUCCESS_SAVE));
						map.put("state", Constants.State.TRUE);
					} else {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.FAILED_SAVE));
						map.put("state", Constants.State.FALSE);
					}
					return map;
				}
			};
			return worker.execute();
		} catch (BaseException e) {
			e.printStackTrace();
			map.put("msg", MessageResolver.getMessage(request,
					Constants.Message.FAILED_SAVE));
			map.put("state", Constants.State.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> create(
			final HttpServletRequest request, SecurityUser user) { // @RequestBody

		final Map<String, Object> map = Maps.newHashMap();
		try {
			setDefaultValue(request, user, "create");// 临时设置默认值
			final int i = securityUserService.createUser(user);//
			ResponseWorker<SecurityUser> worker = new ResponseWorker<SecurityUser>() {
				@Override
				protected Map<String, Object> processMsg() throws Exception {
					if (i > 0) {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.SUCCESS_SAVE));
						map.put("state", Constants.State.TRUE);
					} else {
						map.put("msg", MessageResolver.getMessage(request,
								Constants.Message.FAILED_SAVE));
						map.put("state", Constants.State.FALSE);
					}
					return map;
				}
			};
			return worker.execute();
		} catch (BaseException e) {
			e.printStackTrace();
			map.put("msg", MessageResolver.getMessage(request,
					Constants.Message.FAILED_SAVE));
			map.put("state", Constants.State.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	protected void setDefaultValue(HttpServletRequest request,SecurityUser user, String operateFlag) {
		
		User loginUser = UserSessionProvider.getUserSerssion(request);
		
		/**
		 * 获取当前时间
		 */
		if ("update".equals(operateFlag)) {
			/**
			 * 暂时修改：此处暂时设置固定值。待系统完善后根据实际登陆者和时间具体填写
			 */
			user.setModifiedBy(loginUser.getAccount());
			user.setModifiedDate(new Date());
			String id = request.getParameter("id");
			String account = request.getParameter("account");
			String enabled = request.getParameter("active");
			if(user.getPassword()!=null && !user.getPassword().equals("")){
				user.setPassword(Md5PwdEncoder.encodePassword(user.getPassword(),account));
			}
			user.setId(Long.parseLong(id));
			user.setAccount(account);
			user.setEnabled("Y".equals(enabled) ? 1L : 0);
		} else if("create".equals(operateFlag)){
			/**
			 * 暂时修改：此处暂时设置固定值。待系统完善后根据实际登陆者和时间具体填写
			 */
			
			String enabled = request.getParameter("active");
			user.setModifiedBy(loginUser.getAccount());
			user.setModifiedDate(new Date());
			user.setInsertBy(loginUser.getAccount());
			user.setInsertDate(new Date());
			user.setEnabled("Y".equals(enabled) ? 1L : 0);//设置enabled默认值1
			
			String password = user.getPassword();
			String account = user.getAccount();
			String encodedPassword = Md5PwdEncoder.encodePassword(password,account);
			user.setPassword(encodedPassword);
		}
	}

}
