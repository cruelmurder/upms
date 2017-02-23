package com.hshc.upms.dao.mdm;

import java.io.Serializable;
import java.util.List;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;

/**
 * 
 * @author guoqiushi
 *
 */
public interface MstRegionMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {

    /**
     * 通过此方法获得区域编码表mst_organ_company表中的数据，
     * 
     * @param date 修改时间。若date不为空，则返回date之后的增量数据，若date为空则返回全量数据
     * @return 当查询有结果时，返回实体对象数组且数组长度不为0.当查询无结果时，返回长度为0的实体对象数组（查询无结果返回对象不为NULL）。
     */
    public List<T> queryAllListByEntity();

}
