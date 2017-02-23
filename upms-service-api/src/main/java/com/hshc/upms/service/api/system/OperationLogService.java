/**
 * 
 */
package com.hshc.upms.service.api.system;

import java.io.Serializable;

import com.hshc.common.entity.BaseEntity;
import com.hshc.common.service.BaseService;

/**
 * @author zhanghaiyang
 * 操作日志Service
 */
public interface OperationLogService<T extends BaseEntity, ID extends Serializable> extends BaseService<T, ID> {

}
