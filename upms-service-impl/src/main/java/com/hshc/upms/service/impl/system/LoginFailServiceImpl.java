package com.hshc.upms.service.impl.system;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.upms.dao.system.LoginFailMapper;
import com.hshc.upms.service.api.system.LoginFailService;

/**
 * 
 * @author guoqiushi
 *
 */
@Service
public class LoginFailServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements LoginFailService<T, ID>{
	
	@Autowired
	LoginFailMapper<T, ID> loginFailMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return loginFailMapper;
	}
}
