var count=0;
var orgData=null;
$(function() {
	/*初始化用户grid高度*/
	$("#user_grid").height($(window).height()-80);
	
	/*用户GRID*/
	$('#userview').myDatagrid({
		"title": "管理员列表",
		"url": "../user/list.json",
		"singleSelect": false, //多选
		"method": "post", //默认为POST
		"fitColumns" : true,
		"columns" : [[
			{field : 'id', 		title : '编号', 	 width : $(this).width()*0.15, align : 'center', sortable : true},
			{field : 'account', title : '账户', 	 width : $(this).width()*0.15, align : 'center', sortable : true}, 
			{field : 'name',	title : '用户名',  width : $(this).width()*0.15, align : 'center'}, 
			{field : 'email',	title : 'E-Mail',width : $(this).width()*0.2, align : 'center'},
			{field : 'userOrganlist',	title : '所属机构', 	 width : $(this).width()*0.4, align : 'center',
				formatter:function(value,row){
					var userOrgList = row.userOrganlist;
					for(i=0;i<userOrgList.length;i++){
						var userOrg = userOrgList[i];
						if(userOrg.orgTye=1){
							return userOrg.name;
						}
					}
					
				}
			},
			{field : 'position.positionName',	title : '岗位', 	 width : $(this).width()*0.4, align : 'center',
				formatter:function(value,row){
					if(row.position!=undefined && row.position!=null){
						return row.position.positionName;
					}
				}
			},
			{field : 'enabled',	title : '状态', width : $(this).width()*0.4, align : 'center',
				formatter:function(value,row){
					return value==1?"<p style='color:green;margin:0 auto;'>激活</p>":"<p style='color:red;margin:0 auto;'>删除</p>";
				}
			},
			{field : 'roles', 		title : '角色', 	 width : $(this).width()*0.15, align : 'center',hidden:true,
				formatter:function(value,row){
					return value==1?"<p style='color:green;margin:0 auto;'>激活</p>":"<p style='color:red;margin:0 auto;'>删除</p>";
				}
			}
		]],
		"toolbar": [{
			id : 'btnrole',
			text : '角色管理',
			iconCls : 'icon-filter',
			handler : function() {
				userRoleHandler();
			}
			,permission: "upms:user:role" //权限名称
		}],
		"system": "upms",
		"model": "user", //当不指定form、dialog的ID，插件会根据该属性来自动匹配页面元素，如修改用户窗口，将自动匹配ID：user_edit_dialog
		"dblClickHandler": "editHandler", //双击行时进行的操作(当定义了onDblClickRow时，此参数将失效)
	});
	$("#password_update").change(function(){
		$("#passwordEdit").val($(this).val());
	});
	/*查询grid*/
	$("#user_search_form").find("#account")[0].disabled = false;
	$("#search_form_submit").on("click", function(){
		$('#userview').datagrid("load", $("#user_search_form").form("formToJson"));
	});
	$("#search_form_reset").on("click", function(){
		$("#user_search_form").form("reset");
	});
	
	//500 350px
	var rh = 500;
	var wh = $(window).height();
	var rsh = 350;
	if(rh>wh){
		rh = wh;
		rsh = rh - 150;
	}
	$("#new_roles").panel({
		closable:false,
		tools:'#urn_tt',
		width: 335,
		height: rsh
	});
	$("#own_roles").panel({
		closable:false,
		tools:'#uro_tt',
		width: 335,
		height: rsh
	});
	
	/*用户角色管理弹出窗口*/
	$("#role_manage").show().dialog({    
	    title: "用户角色管理",    
	    width: 750,  
	    height: rh,    
	    closed: true,    
	    modal: true,
	    buttons:[{
			text:'提交',
			iconCls:'icon-save',
			handler:function(){
	    		getSelectedRoles();
			}
		}]
	});
	$("#add_user_roles").on("click", function(){
		var cks = $("#new_roles").find(".user_role_ck:checked");
		$.each(cks, function(){
			$("#own_roles").append($(this).parent().parent());
		});
	});
	$("#del_user_roles").on("click", function(){
		var cks = $("#own_roles").find(".user_role_ck:checked");
		$.each(cks, function(){
			$("#new_roles").append($(this).parent().parent());
		});
	});
});

/**
 * 添加
 */
function userAddHander(){
	$('#user_add_form').form('clear');
	loadOrg("Add");
}
/**
 * 修改
 * @param rowData 选择的行
 */
function userUpdateHandler(rowData){
	$("#passwordEdit").val('');
	loadOrg("Edit",rowData);
	
}
/**
 * 初始化加载机构
 * @param id
 * @param rowData
 */
function loadOrg(id,rowData){
	if(count==0 && orgData==null){
		count=1;
		$.ajax({     
			url:'../mstOrganCompany/tree_json.json',     
			datatype: "json",//请求页面返回的数据类型     
			type: "get",  
			success:function(data) {//这里的data是由请求页面返回的数据
				orgData=data;
				loadOrgPostForm(id,rowData);
				initUserOrg(rowData);
			}
		 });
	}else{
		loadOrgPostForm(id,rowData);
		if(id=='Edit'){
			initUserOrg(rowData);
		}
	}
}

/**
 * 初始化加载岗位
 * @param id
 * @param rowData
 */
function loadOrgPostForm(id,rowData){
	$("#comboOrgTree"+id).combotree({
		 data:orgData,
		 width:300,
		 onSelect:function(node){
			 $("#comboOrgTree"+id+"Id").val(node.id);
			$.ajax({     
				url: "../position/getOrgPost.json",     
				datatype: "json",//请求页面返回的数据类型     
				type: "POST",  
				data:{orgId:node.id},
				success:function(data) {//这里的data是由请求页面返回的数据
					count=0;
					var postData =data.rows;
					postData.unshift({ "positionName": "请选择岗位", "id": "" });
					$("#position"+id).combobox({
						data:postData,
						valueField:'id',
						textField:'positionName',
						editable:false,
						onLoadSuccess: function () { //加载完成后,设置选中第一项
							if(id=='Add'){
								var val = $(this).combobox("getData");
							}else{
								initUserPost(rowData);
							}
						},
						onChange:function(){//当触发选择事件时
							 var node = $(this).combotree("getValues") 
							$("#position"+id+"Id").val(node);
							 
						}
					});
				},     
				error: function(XMLHttpRequest, textStatus, errorThrown) {
				} 
			});
		 },
		 
	 });
	 $("#comboSupOrgTree"+id).combotree({
		 data:orgData,
		 width:300,
		 multiple:true,
		 onLoadSuccess: function () { //加载完成后,设置选中第一项
			 if(id=='Edit'){
				 initUserSupOrg(rowData);
			 }
		 },
		 onChange:function(){//当触发选择事件时
			 var nodes = $(this).combotree("getValues")
			 var s="";
			 for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i];
			}
			$("#comboSupOrgTree"+id+"Ids").val(s);
		 }
	 })
}

/**
 * 修改时回显用户机构
 * @param rowData
 */
function initUserOrg(rowData){
	var userOrgList = rowData.userOrganlist;
	 for(i=0;i<userOrgList.length;i++){
		supindex=i;
		var userOrg = userOrgList[i];
		if(userOrg.orgType==1){
			$("#comboOrgTreeEdit").combotree("setValue",userOrg.id);
		}
	 }
}
/**
 * 修改时回显用户岗位
 * @param rowData
 */
function initUserPost(rowData){
	if(rowData.position!=null){
		$("#positionEdit").combobox("setValue",rowData.position.id);
	}
		
}
/**
 * 修改时回显用户监管机构
 * @param rowData
 */
function initUserSupOrg(rowData){
	 var supOrg = new Array();
	 var supindex=0;
	 var userOrgList = rowData.userOrganlist;
	 for(i=0;i<userOrgList.length;i++){
		supindex=i;
		var userOrg = userOrgList[i];
		if(userOrg.orgType==2){
			supOrg[supindex] = userOrg.id
		}else{
			$("#comboOrgTreeEdit").combotree("setValues",userOrg.id);
			supindex--;
		}
	 }
	 $("#comboSupOrgTreeEdit").combotree("setValues",supOrg);
}
/**
 * 
 */
function accountActivateHandler(){
	var dataGrid = $('#userview');
	var selectRow = dataGrid.datagrid("getSelected");
	var bMany = false;
	var selectRows = dataGrid.datagrid("getSelections");// var selectRow = $(oGrid).datagrid("getSelected");
	alert(selectRows);
	if (!selectRows || selectRows.length <= 0) {
		$.messager.alert("提示", "&nbsp;&nbsp;请选择一行数据！");
		return;	
	}
	/*$.ajax({
		type:"POST",
		url	:"../user/updateById.json",
		data: {"id": },
		dataType:"json"
	});*/
}
/**
 * 用户角色管理FN
 */
function userRoleHandler(){
	var dataGrid = $('#userview');
	var selectRow = dataGrid.datagrid("getSelected");
	var bMany = false;
	var selectRows = dataGrid.datagrid("getSelections");// var selectRow = $(oGrid).datagrid("getSelected");
	if (!selectRows || selectRows.length <= 0) {
		$.messager.alert("提示", "&nbsp;&nbsp;请选择一行数据！");
		return;
	}
	selectRow = selectRows[0]; // 默认对选中的第一条数据进行操作
	if (selectRows.length > 1) {
		bMany = true;
		var firstIndex = dataGrid.datagrid("getRowIndex", selectRow);
		dataGrid.datagrid("unselectAll");
		dataGrid.datagrid("selectRow", firstIndex);
	}
	roleManager(selectRow, bMany);
}
function roleManager(userInfo, bMany){
	$("#role_user_info").html("账号："+userInfo.account+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名：" + userInfo.name 
			+"<input type='hidden' id='current_user_id' value='"+ userInfo.id+"'/>");
	$("#role_manage").show().dialog("open");
	$("#own_roles").html("");
	$("#new_roles").html("");
	if (bMany) {/* 当用户选中多条数据时，提示用户将默认对其选择的第一条数据进行操作 */
		msg.topCenter("温馨提示", "<span style='color:red;'>*</span>由于您选择了多条数据，系统将默认对您选择的第一条数据进行操作。");
	}
	$.ajax({
		url: "../userRole/roles.json?t="+Math.random(),
		data: {"userId": userInfo.id},
		dataType: "json",
		type: "get",
		cache: false,
		success: function(data){
			if(data){
				roleInit(data.ownRoles, data.newRoles);
			}
		}
	});
}
function roleInit(ownRoles, newRoles){
	var ownStrs = "";
	var newStrs = "";
	$.each(ownRoles, function(i1, n1){
		ownStrs += "<div class='role_list'><div class='role_list_l'><input type='checkbox' class='user_role_ck'/></div>"
			+ "<div class='role_list_c'>"+n1.name+"<input type='hidden' class='user_role_val' value='"+n1.id+"'/></div></div>";
	});
	$.each(newRoles, function(i2, n2){
		newStrs += "<div class='role_list'><div class='role_list_l'><input type='checkbox' class='user_role_ck'/></div>"
			+ "<div class='role_list_c'>"+n2.name+"<input type='hidden' class='user_role_val' value='"+n2.id+"'/></div></div>";
	});
	var $ownRoles = $("#own_roles");
	var $newRoles = $("#new_roles");
	$ownRoles.children(".role_list").remove();//$("#own_roles").html(ownStrs);
	$ownRoles.append($(ownStrs));
	$newRoles.children(".role_list").remove();//$("#new_roles").html(newStrs);
	$newRoles.append($(newStrs));
	$('.role_list_c').on("dblclick", function(){
		var $target = $ownRoles;
		if($(this).parent().parent().attr("id")=="own_roles"){
			$target = $newRoles;
		}
		$(this).parent().appendTo($target);
	});
	$('.role_list_c').draggable({
		proxy:'clone',
		revert:true,
		cursor:'auto',
		onStartDrag:function(){
			$(this).draggable('options').cursor='not-allowed';
			$(this).draggable('proxy').addClass('dp');
		},
		onStopDrag:function(){
			$(this).draggable('options').cursor='auto';
		}
	});
	$('#new_roles, #own_roles').droppable({
		onDragEnter:function(e, source){
			$(source).draggable('options').cursor='auto';
			$(source).draggable('proxy').css('border','1px solid red');
			$(this).addClass('over');
		},
		onDragLeave:function(e, source){
			$(source).draggable('options').cursor='not-allowed';
			$(source).draggable('proxy').css('border','1px solid #ccc');
			$(this).removeClass('over');
		},
		onDrop:function(e, source){
			if($(this).attr("id")!=$(source).parent().parent().attr("id")){
				$(this).append($(source).parent());
			}
			$(this).removeClass('over');
		}
	});
}
/**
 *获取所选角色（ID）
 */
function getSelectedRoles(){
	var userId = $("#current_user_id").val();
	var ownRoles = $("#own_roles").find("input.user_role_val");
	var ownStrs = "";
	$.each(ownRoles, function(i, n){
		ownStrs += $(n).val() + ",";
	});
	if(ownStrs.length > 0){
		ownStrs = ownStrs.substring(0, ownStrs.length-1);
	}
	$.ajax({
		url: "../userRole/roleManager.json",
		data: {"userId": userId, "roleIds":ownStrs},
		dataType: "json",
		type: "get",
		success: function(data){
			msg.bottomRight("提示信息",data.msg);
			$("#role_manage").dialog("close");
		}
	});
}
/**
 *全选、反选
 */
function selectRoles(sType, bChecked){
	if(sType=="new"){
		checkboxSelector($("#new_roles").find(".user_role_ck"), bChecked);
	}else if(sType=="own"){
		checkboxSelector($("#own_roles").find(".user_role_ck"), bChecked);
	}
}
