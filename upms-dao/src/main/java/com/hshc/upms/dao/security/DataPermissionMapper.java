/**
 * 
 */
package com.hshc.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.upms.entity.security.DataPermission;

/**
 * @author zhanghua
 *2016年11月8日
 */
public interface DataPermissionMapper <T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID>  {

	/**
	 * @param srId
	 * @param spId
	 * @return
	 */
	List<DataPermission> getRoleDataPermission(@Param("srId")int srId);
	
}
