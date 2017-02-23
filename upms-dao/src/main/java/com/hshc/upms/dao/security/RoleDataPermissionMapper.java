/**
 * 
 */
package com.hshc.upms.dao.security;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.mdm.entity.security.SecurityPosition;

/**
 * @author zhanghaiyang
 *
 */
public interface RoleDataPermissionMapper <T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID>  {
	
	SecurityPosition queryByUserId(Long userId);
}
