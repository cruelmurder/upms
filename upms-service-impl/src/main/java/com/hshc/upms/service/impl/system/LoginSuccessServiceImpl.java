package com.hshc.upms.service.impl.system;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.upms.dao.system.LoginSuccessMapper;
import com.hshc.upms.service.api.system.LoginSuccessService;

/**
 * 
 * @author guoqiushi
 *
 */
@Service
public class LoginSuccessServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements LoginSuccessService<T, ID>{

	@Autowired
	LoginSuccessMapper<T, ID> loginSuccessMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return loginSuccessMapper;
	}
	
}
