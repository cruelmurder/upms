package com.hshc.upms.service.impl.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hshc.common.dao.BaseMapper;
import com.hshc.common.entity.BaseEntity;
import com.hshc.common.security.encoder.Md5PwdEncoder;
import com.hshc.common.service.impl.BaseServiceImpl;
import com.hshc.mdm.entity.mst.MstOrganCompany;
import com.hshc.mdm.entity.security.SecurityPosition;
import com.hshc.upms.dao.mdm.MstOrganCompanyMapper;
import com.hshc.upms.dao.security.PermissionMapper;
import com.hshc.upms.dao.security.PositionMapper;
import com.hshc.upms.dao.security.RoleMapper;
import com.hshc.upms.dao.security.UserMapper;
import com.hshc.upms.entity.security.Permission;
import com.hshc.upms.entity.security.User;
import com.hshc.upms.service.api.security.UserService;
import com.hshc.upms.vo.user.DataPermissionVo;
import com.hshc.upms.vo.user.PermissionVo;

@Service
public class UserServiceImpl<T extends BaseEntity, ID extends Serializable>
		extends BaseServiceImpl<T, ID> implements UserService<T, ID> {

	@Autowired
	UserMapper<T, ID> userMapper;
	
	@Autowired
	RoleMapper<T, ID> roleMapper;
	
	@Autowired
	private PermissionMapper<T, ID> permissionMapper;
	
	@Autowired
	private MstOrganCompanyMapper<T, ID> mstOrganCompanyMapper;
 	
	@Autowired
	private PositionMapper<T, ID> positionMapper;
	
	private static final String SU_ACTIVE_YES = "Y";
	private static final Long SU_ENABLED_YES = 1l;
	

	@Override
	public BaseMapper<T, ID> getMapper() {
		return userMapper;
	}

	/**
	 * 校验登录
	 */
	@Override
	public Map<String, Object> validateLoginUser(User adminUser) {
		Map<String,Object> resultMap = Maps.newHashMap();
		String code = null;
		String message = null;
		boolean successFlag = false;
	        User queryCondition  =  new User();
	        queryCondition.setAccount(adminUser.getAccount());
	        @SuppressWarnings("unchecked")
			User getUser = (User) userMapper.queryByEntity((T) queryCondition);
	        
	        if (getUser != null) { 
	        	String isActive = getUser.getActive();
	        	Long isEnabled = getUser.getEnabled();
	        	String loginPassword = Md5PwdEncoder.encodePassword(adminUser.getPassword(),adminUser.getAccount());
	        	
	        	if(getUser.getPassword().equals(loginPassword)){
	        		if((null!=isActive)&&(isActive.equals(SU_ACTIVE_YES))){
		        		code = "2000";
		        		message = "OK";
		        		successFlag = true;
		        	}else{
		        		code = "2006";
		        		message = "该用户已失效，不允许登录!";
		        	}
	        		if((null!=isEnabled)&&(isEnabled.equals(SU_ENABLED_YES))){
		        		code = "2000";
		        		message = "OK";
		        		successFlag = true;
		        	}else{
		        		code = "2006";
		        		message = "该用户未激活，不允许登录!";
		        	}
	        		
	        	}else{
	        		code = "2002";
	        		message = "登录用户密码错误!";
	        	}
	        } else {  
	        	code = "2001";
        		message = "该用户在系统中不存在!请注册新用户!";
	        }  
	        
	        resultMap.put("ErrorCode", code);
	        resultMap.put("ErrorMessage", message);
	        resultMap.put("success", successFlag);
			
		return resultMap;
	}
	

	/**
	 * 查出登录用户的权限，初始化到该用户对象中
	 */
	@Override
	public User initLoginUserInfo(User adminUser) {
		

		if(null!=adminUser){
			String account = adminUser.getAccount();

			/**
			 * 初始化所有的权限permList
			 */
			List<Permission> permissionList = permissionMapper.getAllPermissionByUserAccount(account, adminUser.getSysId());
			Set<PermissionVo> permissionSet = Sets.newHashSet();
			if(null!=permissionList&&permissionList.size()>0){
				for(Permission permission : permissionList){
					String permissionStr = permission.getPermission();
					String href = permission.getHref();
					PermissionVo perm = new PermissionVo(href,permissionStr);
					List<String> list = new ArrayList<String>();
					permissionSet.add(perm);
				}
			}
			adminUser.setPermList(permissionSet);
		
			//初始化用户所属机构
			List<MstOrganCompany> list = mstOrganCompanyMapper.queryListByUserId(adminUser.getId());
			List<MstOrganCompany> orgList = new ArrayList<MstOrganCompany>();
			List<MstOrganCompany> supOrgList =  new ArrayList<MstOrganCompany>();
			for(MstOrganCompany org:list){
				if(org.getOrgType()==1){
					orgList.add(org);
				}else if(org.getOrgType()==2){
					supOrgList.add(org);
				}
			}
			adminUser.setOrgList(orgList);
			//初始化用户监管机构
			
			adminUser.setSupOrgList(supOrgList);
			//初始化用户岗位
			List<SecurityPosition> positionList = positionMapper.queryByUserId(adminUser.getId());
			adminUser.setPositionList(positionList);
			/**
			 * 初始化用户所有的数据权限
			 */
			List<String> dataPermissionList = permissionMapper.getAllDataPermissionByUserAccount(account, adminUser.getSysId());
			List<String> dataOrgList = new ArrayList<String>();
			DataPermissionVo dataPermissionVo = new DataPermissionVo();
			if(dataPermissionList.contains("1000")){
				//所有权限
				dataPermissionVo =null;
			}else{
				if(dataPermissionList.contains("1001")){
					//本机构
					dataOrgList.add(adminUser.getOrgList().get(0).getCode());
				}else if(dataPermissionList.contains("1003")){
					//本人
					dataPermissionVo.setIsSelf(true);
				}
				//下属机构
				if(dataPermissionList.contains("1002")){
					getOrgChildCode(adminUser.getOrgList().get(0),dataOrgList);
				}
				//兼管机构
				if(dataPermissionList.contains("1004")){
					for(MstOrganCompany org:adminUser.getSupOrgList()){
						dataOrgList.add(org.getCode());
					}
				}
			}
			if(dataPermissionVo!=null){
				dataPermissionVo.setOrgCodeList(dataOrgList);
			}
			adminUser.setDataPerm(dataPermissionVo);
		}
		return adminUser;
	}



	@Override
	public String validateUserPermission(long suId) {
		return userMapper.validateUserPermission(suId);
	}
	
	private void getOrgChildCode(MstOrganCompany org,List<String> orgCodeList){
		
		orgCodeList.add(org.getCode());
		List<MstOrganCompany> childOrgList = mstOrganCompanyMapper.getChildNodeList(org.getId());
		for(MstOrganCompany childOrg:childOrgList){
			getOrgChildCode(childOrg,orgCodeList);
		}
	}
}
