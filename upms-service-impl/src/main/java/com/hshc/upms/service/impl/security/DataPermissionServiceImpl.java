/**
 * 
 */
package com.hshc.upms.service.impl.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.upms.dao.security.DataPermissionMapper;
import com.hshc.upms.entity.security.DataPermission;
import com.hshc.upms.service.api.security.DataPermissionService;

/**
 * @author zhanghaiyang
 * 权限Service实现类
 */
@Service
public class DataPermissionServiceImpl <T extends BaseEntity, ID extends Serializable> extends BaseServiceImpl<T, ID> implements DataPermissionService<T, ID>{

	@Autowired
	private DataPermissionMapper<T, ID> dataPermissionMapper;
	
	@Override
	public BaseMapper<T, ID> getMapper() {
		return dataPermissionMapper;
	}

	/* (non-Javadoc)
	 * @see com.hshc.upms.service.api.security.DataPermissionService#getRoleDataPermission(int, java.lang.Long)
	 */
	@Override
	public List<DataPermission> getRoleDataPermission(int srId) {
		
		return dataPermissionMapper.getRoleDataPermission(srId);
	}
	
	

}
