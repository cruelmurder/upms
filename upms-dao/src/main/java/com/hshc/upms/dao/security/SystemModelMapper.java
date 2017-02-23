/**
 * 
 */
package com.hshc.upms.dao.security;

import java.io.Serializable;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;

/**
 * @author zhanghaiyang
 *
 */
public interface SystemModelMapper <T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
	
}
