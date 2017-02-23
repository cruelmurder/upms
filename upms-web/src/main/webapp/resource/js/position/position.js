$(function() {
//	GM_PERMISSIONS=false;	//是否权限控制按钮
	/*初始化用户grid高度*/
	$("#post_grid").height($(window).height()-120);
	$('#postview').myDatagrid({
		url: "../position/list.json",
		singleSelect: false, //多选
		fitColumns : false,//自动大小
		title:'岗位列表',
		method: "post", 
		pageSize:10,  
	    rownumbers:true ,
		columns : [[
		    {field : 'id',title : 'id', 	width : 180, align : 'center',hidden:true},  
		    
		    {field : 'positionCode',title : '岗位代码', 	width : 180, align : 'center'},
		    {field : 'positionName',title : '岗位名称', 	width : 180, align : 'center'}, 
		    {field : 'positionDescribe',title : '描述', 	width : 180, align : 'center'}
			
		]],
		model: "post", //当不指定form、dialog的ID，插件会根据该属性来自动匹配页面元素，如修改数据字典窗口，将自动匹配ID：dataDictionary_edit_dialog
		dblClickHandler: "editHandler", //双击行时进行的操作(当定义了onDblClickRow时，此参数将失效),
		
		addHandler: {
			"enable": true,
			"title": "新增", 
			"form": {"submitUrl": "../position/create.do"},
			permission: "mdm:position:add" //权限名称
		},
		editHandler: {
			"enable": true,
			"title": "修改", 
			"form": {"submitUrl": "../position/update.do"},
			permission: "mdm:position:edit" //权限名称
		},
		removeHandler: {
			"enable": true,
			"title": "删除",
			"removeUrl": "../position/deleteByIds.json", 
			"idField": "id",
			"idParams": "ids",
			permission: "mdm:position:remove" //权限名称
		}
	});
	/*查询grid*/
	$("#search_form_submit").on("click", function(){
		$('#postview').datagrid("load", $("#post_search_form").form("formToJson"));
	});
	$("#search_form_reset").on("click", function(){
		$("#post_search_form").form("reset");
	});
});

