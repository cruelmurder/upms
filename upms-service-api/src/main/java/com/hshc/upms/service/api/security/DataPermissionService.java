/**
 * 
 */
package com.hshc.upms.service.api.security;

import java.io.Serializable;
import java.util.List;

import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.BaseService;
import com.hshc.upms.entity.security.DataPermission;

/**
 * @author zhanghua
 *2016年11月8日
 */
public interface DataPermissionService <T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID>  {

	/**
	 * @param intValue
	 * @param sysId
	 * @return
	 */
	List<DataPermission> getRoleDataPermission(int srId);
	

}
