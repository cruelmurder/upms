package com.hshc.upms.dao.mdm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.mdm.entity.mst.MstOrganCompany;

/**
 * 
 * @author guoqiushi
 *
 */
public interface MstOrganCompanyMapper<T extends BaseEntity, ID extends Serializable> extends BaseMapper<T, ID> {

    /**
     * 获取一级机构
     * 
     * @return 返回list，如果没查到返回空数组
     */
    public List<MstOrganCompany> getRootList();

    /**
     * 根据parent_id取得所有子节点
     * 
     * @param parent_id 父节点
     * @return 返回list，如果没查到返回空数组
     */
    public List<MstOrganCompany> getChildNodeList(@Param("parentId") long parentId);

    /**
     * 根据id取得节点
     * 
     * @param id
     * @return 如果没查到返回null
     */
    public MstOrganCompany getNode(@Param("id") long id);

    /**
     * 根据ID逻辑删除某节点
     * 
     * @param id
     * @return 操作成功返回1，否则返回0
     */
    public int deleteMstOrganCompanyLogically(@Param("id") long id);

    /**
     * 根据ID修改某节点
     * 
     * @param mstOrganCompany
     * @return 操作成功返回1，否则返回0
     */
    public int updateNode(@Param("mstOrganCompany") MstOrganCompany mstOrganCompany);

    /**
     * 根据ID添加某节点
     * 
     * @param mstOrganCompany
     * @return 操作成功返回1，否则返回0
     */
    public int addNode(@Param("mstOrganCompany") MstOrganCompany mstOrganCompany);

    /**
     * 通过此方法获得组织机构表mst_organ_company表中的数据，
     * 
     * @param date 修改时间。若date不为空，则返回date之后的增量数据，若date为空则返回全量数据
     * @return 当查询有结果时，返回实体对象数组且数组长度不为0.当查询无结果时，返回长度为0的实体对象数组（查询无结果返回对象不为NULL）。
     */
    public List<MstOrganCompany> getMstOrganCompanyList(@Param("updateTime") Date updateTime);
    
    
    public List<MstOrganCompany> queryListByUserId(@Param("userId") Long userId );

}
