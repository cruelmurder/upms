package com.hshc.upms.service.impl.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.upms.dao.security.RoleMapper;
import com.hshc.upms.service.api.security.RoleService;

@Service
public class RoleServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements RoleService<T, ID>{

	@Autowired
	RoleMapper<T, ID> roleMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return roleMapper;
	}
	
}
