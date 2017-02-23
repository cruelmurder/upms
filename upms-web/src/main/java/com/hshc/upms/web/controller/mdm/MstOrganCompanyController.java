/**
 * 
 */
package com.hshc.upms.web.controller.mdm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hshc.common.service.BaseService;
import com.hshc.common.util.BaseResponse;
import com.hshc.common.util.JsonUtil;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.mdm.dto.mst.OrganTree;
import com.hshc.mdm.entity.mst.MstOrganCompany;
import com.hshc.mdm.entity.mst.MstRegion;
import com.hshc.mdm.service.api.mst.MstOrganCompanyService;
import com.hshc.upms.entity.security.User;
import com.hshc.upms.web.controller.util.UserSessionProvider;

/**
 * 组织机构码表相关controller
 * 
 * @author Lipeilong
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/mstOrganCompany")
public class MstOrganCompanyController extends BaseCRUDAction<MstOrganCompany, Integer> {

    @Autowired
    MstOrganCompanyService<MstOrganCompany, Integer> mstOrganCompanyService;

    @Override
    public BaseService<MstOrganCompany, Integer> getBaseService() {
        return mstOrganCompanyService;
    }

    @Override
    protected void setDefaultValue(HttpServletRequest paramHttpServletRequest, MstOrganCompany paramT, String paramString) {
        // TODO Auto-generated method stub
    }

    /**
     * 从缓存里取出树形三级机构编码，用于省市县的三级联动
     * 
     * @param request
     * @param page
     * @param rows
     * @param t
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value = {"getRegion"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRegion(HttpServletRequest request, Long page, Integer rows, T t, String sort, String order) {
        ServletContext context = request.getSession().getServletContext();
        List<MstRegion> regions = (List<MstRegion>) context.getAttribute("region");
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("json", JsonUtil.objectToJSON(regions));
        return maps;
    }

    /**
     * 加载所有节点
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "tree_json", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public List<OrganTree> getList(HttpServletRequest request) {
        return mstOrganCompanyService.getList();
    }

    /**
     * 根据父节点获取子树节点
     * 
     * @param request
     * @param pid
     * @return
     */
    @RequestMapping(value = "get_child_node", method = RequestMethod.GET)
    @ResponseBody
    public List<OrganTree> getChildList(HttpServletRequest request, Long pid) {
        return mstOrganCompanyService.getChildList(pid);
    }

    /**
     * 根据ID获取节点
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "get_node", method = RequestMethod.GET)
    @ResponseBody
    public MstOrganCompany getNode(Long id) {
        return mstOrganCompanyService.getNode(id);
    }

    /**
     * 删除树形节点
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "delete_node", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteNode(HttpServletRequest request, Long id) {
        BaseResponse baseResponse = BaseResponse.getInstance();
        int num = mstOrganCompanyService.deleteNode(id);
        if (num >= 0) {
            baseResponse.setFlag(true);
        }
        return baseResponse;
    }

    /**
     * 添加节点
     * 
     * @param resource
     * @return
     */
    @RequestMapping(value = "add_node", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addNode(HttpServletRequest request, MstOrganCompany mstOrganCompany) {
        User loginUser = UserSessionProvider.getUserSerssion(request);
        BaseResponse baseResponse = BaseResponse.getInstance();
        mstOrganCompany.setInsertBy(loginUser.getAccount());
        mstOrganCompany.setModifiedBy(loginUser.getAccount());
        int num = mstOrganCompanyService.addNode(mstOrganCompany);
        if (num >= 0) {
            baseResponse.setFlag(true);
        }
        return baseResponse;
    }

    /**
     * 更新树形节点信息
     * 
     * @param resource
     * @return
     */
    @RequestMapping(value = "update_node", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateNode(HttpServletRequest request, MstOrganCompany mstOrganCompany) {
        User loginUser = UserSessionProvider.getUserSerssion(request);
        mstOrganCompany.setModifiedBy(loginUser.getAccount());
        mstOrganCompany.setModifiedDate(new Date());
        BaseResponse baseResponse = BaseResponse.getInstance();
        int num = mstOrganCompanyService.updateNode(mstOrganCompany);
        if (num >= 0) {
            baseResponse.setFlag(true);
        }
        return baseResponse;
    }

    /**
     * 加载树形
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "treegrid_json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getTreeGridMap(HttpServletRequest request) {
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("total", this.mstOrganCompanyService.getList().size());
        maps.put("rows", this.mstOrganCompanyService.getList());
        return maps;
    }

    /**
     * 三级联动，根据不同的省取出相关市，根据不同的市，取出相关的县，并拼装map返回
     * 
     * @param provinceCode 省级编码
     * @param cityCode 市级编码
     * @param getLevel 参数1:取得省级，2：取得市级，3：取得县级
     * @return
     */
    @RequestMapping(value = {"getProvinceArray"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getProvinceArray(HttpServletRequest request, Long page, Integer rows, T t, String sort, String order) {

        String provinceCode = request.getParameter("provinceCode");
        String cityCode = request.getParameter("cityCode");
        String getLevel = request.getParameter("getLevel");// 1:取得省级，2：取得市级，3：取得县级

        Map<String, Object> maps = Maps.newHashMap();

        ServletContext context = request.getSession().getServletContext();

        List<MstRegion> result = new ArrayList<MstRegion>();

        if ("1".equals(getLevel)) {
            List<MstRegion> provinces = (List<MstRegion>) context.getAttribute("region");
            result.addAll(provinces);
        }

        if ("2".equals(getLevel) && (!"".equals(provinceCode) || provinceCode != null)) {

            Map<String, List<MstRegion>> citys = (Map<String, List<MstRegion>>) context.getAttribute("cityMap");
            result.addAll(citys.get(provinceCode));
        }

        if ("3".equals(getLevel) && (!"".equals(cityCode) || cityCode != null)) {
            Map<String, List<MstRegion>> countys = (Map<String, List<MstRegion>>) context.getAttribute("countyMap");
            result.addAll(countys.get(cityCode));
        }

        if (result != null && result.size() > 0) {
            MstRegion mr = new MstRegion();
            mr.setCode("");
            mr.setName("请选择");
            result.add(0, mr);
        }
        maps.put("json", result);
        return maps;
    }

    /**
     * 激活状态为不可用的机构
     * 
     * @return
     */
    @RequestMapping(value = {"active"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getDataDictionaryCode(HttpServletRequest request, Long page, Integer rows, T t, String sort, String order) {
        Map<String, Object> maps = Maps.newHashMap();
        User loginUser = UserSessionProvider.getUserSerssion(request);
        String activeId = request.getParameter("activeId");
        MstOrganCompany organ = mstOrganCompanyService.getNode(Long.parseLong(activeId));
        organ.setModifiedBy(loginUser.getAccount());
        organ.setModifiedDate(new Date());
        organ.setStatus("1");
        int num = mstOrganCompanyService.updateNode(organ);
        if (num >= 0) {
            maps.put("msg", "激活成功");
            maps.put("state", true);
        } else {
            maps.put("msg", "激活失败");
            maps.put("state", false);
        }
        return maps;
    }

    @RequestMapping(value = {"logicDeleteByIds"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> logicDeleteByIds(HttpServletRequest request, Long page, Integer rows, T t, String sort, String order) {
        Map<String, Object> maps = Maps.newHashMap();
        Map<String, Object> result = Maps.newHashMap();
        User loginUser = UserSessionProvider.getUserSerssion(request);
        String ids = request.getParameter("ids");
        System.out.println("ids : " + ids.split(","));
        maps.put("ids", ids.split(","));
        maps.put("modifiedDate", new Date());
        maps.put("modifiedBy", "test");
        int num = mstOrganCompanyService.logicDeleteByIds(maps);
        if (num >= 0) {
            maps.put("msg", "删除成功");
            maps.put("state", true);
        } else {
            maps.put("msg", "删除失败");
            maps.put("state", false);
        }
        return result;
    }

}
