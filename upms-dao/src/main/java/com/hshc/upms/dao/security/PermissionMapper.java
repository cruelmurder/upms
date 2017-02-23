/**
 * 
 */
package com.hshc.upms.dao.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.upms.entity.security.Permission;

/**
 * @author zhanghaiyang
 *
 */
public interface PermissionMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID>  {
	
	List<Permission> getPermissionButtonListByParentId(@Param("parentId")Long parentId,@Param("sysId")Long sysId);
	
	/**
	 * 根据账号查到该用户的所有权限
	 * @param account
	 * @return
	 */
	List<Permission> getAllPermissionByUserAccount(@Param("account") String account,@Param("sysId") Long sysId);
	
	List<Permission> getPermissionByRoleId(@Param("srId") Long srId,@Param("sysId") Long sysId);
	
	List<String> getAllDataPermissionByUserAccount(@Param("account") String account,@Param("sysId")Long sysId);

	/**
	 * @param srId
	 * @param spId
	 * @return
	 */
	List<Map<String, Object>> getPermissionDataListByParentId(@Param("srId")Long srId);

	/**
	 * @param srId
	 * @param spId
	 */
	void deleteRoleDataPermission(@Param("srId")Long srId); 
	
	
}
