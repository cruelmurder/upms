/**
 * 
 */
package com.hshc.upms.web.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hshc.common.service.BaseService;
import com.hshc.common.web.controller.BaseCRUDAction;
import com.hshc.mdm.entity.security.SecurityPosition;
import com.hshc.mdm.service.api.security.SecurityPositionService;

/**
 * 部门管理模块的Controller 
 * @author zhanghaiyang
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/position")
public class PositionController extends BaseCRUDAction<SecurityPosition, Integer>{

	@Autowired
	SecurityPositionService<SecurityPosition, Integer> positionService;
	
	@Override
	public BaseService<SecurityPosition, Integer> getBaseService() {
		return positionService;
	}
	
	@Override
	protected void setDefaultValue(HttpServletRequest request,SecurityPosition t, String operateFlag) {
		
	}
	@RequestMapping(value = "orgPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orgPost(HttpServletRequest request,Integer orgId) {
		 Map<String,Object> map = Maps.newHashMap();
		 List<SecurityPosition> postList = positionService.queryListByOrgId(orgId);
		 map.put("rows", postList);
		 map.put("seccess", true);
		 return map;
	 }
	@RequestMapping(value = "getOrgPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrgPost(HttpServletRequest request,Integer orgId) {
		 Map<String,Object> map = Maps.newHashMap();
		 List<SecurityPosition> postList = positionService.queryPostByOrgId(orgId);
		 map.put("rows", postList);
		 map.put("seccess", true);
		 return map;
	 }
	@RequestMapping(value = "makeOrgPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> makeOrgPost(HttpServletRequest request,Integer orgId,String dataStr) {
		 Map<String,Object> map = Maps.newHashMap();
		 /**
		  * 数据授权
		  */
		 if((null!=orgId)&&(null!=dataStr)){
			 positionService.deleteOrgPost(orgId);
			 List<Map<String,Object>> orgPostList = new ArrayList<Map<String,Object>>();
			 if(!"".equals(dataStr.trim())){//idStr:以","号分隔开的SP_ID字符串
				 String[] dataCodeArr = dataStr.split(",");
				 for(String dataCode:dataCodeArr){
					 Map<String,Object> dataCodeMap = new HashMap<String,Object>();
					 dataCodeMap.put("orgId", orgId);
					 dataCodeMap.put("postId", dataCode);
					 orgPostList.add(dataCodeMap);
				 }
				 saveOrgPost(orgPostList);
				 map.put("msg", "操作成功！");	
			 }
		 }else if((null!=orgId) && (null == dataStr || "".equals(dataStr.trim()))){
				 
			 positionService.deleteOrgPost(orgId);
			 map.put("msg", "已删除机构下所有岗位！");
		 }
		 map.put("success", true);
		 return map;
	 }
	
	/**
	 * 添加角色菜单的数据权限
	 * 
	 * @param spId
	 * @param srId
	 */
	private void saveOrgPost(List<Map<String,Object>> orgPostList){
		
		
		if(null!=orgPostList && orgPostList.size()>0){
			for(Map<String,Object> map:orgPostList){
				positionService.createOrgPost(map);//添加新的授权信息
			}
		}
	}
}
