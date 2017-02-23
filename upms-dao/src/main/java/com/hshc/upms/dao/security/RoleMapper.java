/**
 * 
 */
package com.hshc.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.upms.entity.security.Role;

/**
 * @author zhanghaiyang
 *
 */
public interface RoleMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
	List<Role> getAllRolesByUser(@Param("account") String account);
}
