/**
 * 商户信息管理初始化
 */
var MchContactInfo = {
    id: "MchContactInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MchContactInfo.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
            {title: '用户编号', field: 'iId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户姓名', field: 'sTureName', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号码', field: 'sPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '邮箱', field: 'sEmail', visible: true, align: 'center', valign: 'middle'},
            {title: '收款银行', field: 'sCashBank', visible: true, align: 'center', valign: 'middle'},
            {title: '收款账号', field: 'sCashAccount', visible: true, align: 'center', valign: 'middle'},
            {title: '开户地址', field: 'sCashAddr', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证号', field: 'sIdNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '网站名称', field: 'sSiteName', visible: true, align: 'center', valign: 'middle'},
            {title: '站点地址', field: 'sSiteHttp', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'dUpdateTime', visible: true, align: 'center', valign: 'middle'},
            {title: '更新操作人', field: 'sUpdateUserName', visible: true, align: 'center', valign: 'middle'}

    ];
};

/**
 * 检查是否选中
 */
UserInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 查询用户信息列表
 */
MchContactInfo.search = function () {
    /*var queryData = {};
    queryData['sPhone'] = $("#sPhone").val();
    queryData['sTureName'] = $("#sTureName").val();
    UserInfo.table.refresh({query: queryData});*/
	
	var defaultColunms = MchContactInfo.initColumn();
    var table = new BSTable(MchContactInfo.id, "/mchContactInfo/list", defaultColunms);
    table.setPaginationType("client");
    MchContactInfo.table = table.init();
};

$(function () {
    var defaultColunms = MchContactInfo.initColumn();
    var table = new BSTable(MchContactInfo.id, "/mchContactInfo/list", defaultColunms);
    table.setPaginationType("client");
    MchContactInfo.table = table.init();
});
