package com.hshc.upms.dao.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.upms.entity.security.User;

public interface UserMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {
	
	/**
	 * 做用户权限验证
	 * author：gqs
	 */
    public String validateUserPermission(@Param("suId") long suId);
    
}
