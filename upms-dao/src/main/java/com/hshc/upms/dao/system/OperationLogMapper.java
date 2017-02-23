/**
 * 
 */
package com.hshc.upms.dao.system;

import java.io.Serializable;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;


/**
 * @author zhanghaiyang
 *
 */
public interface OperationLogMapper<T extends BaseEntity, ID extends Serializable>
extends BaseMapper<T, ID> {
}
