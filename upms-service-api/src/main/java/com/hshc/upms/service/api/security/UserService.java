package com.hshc.upms.service.api.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.BaseService;
import com.hshc.mdm.entity.security.SecurityUser;
import com.hshc.upms.entity.security.User;

public interface UserService<T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID> {
	
	public User initLoginUserInfo(User adminUser);
	
	public Map<String,Object> validateLoginUser(User adminUser);
	
	/**
	 * 自提收货 用户权限验证
	 * author：gqs
	 */
    public String validateUserPermission(long suId);
}
