/**
 * 微信收款监控管理初始化
 */
var CWechatData = {
    id: "CWechatDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CWechatData.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '收款人', field: 'receiver', visible: true, align: 'center', valign: 'middle'},
            {title: '金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
            {title: '收集信息', field: 'collectinfo', visible: true, align: 'center', valign: 'middle'},
            {title: '监控时间', field: 'rtime', visible: true, align: 'center', valign: 'middle'},
            {title: '是否上传', field: 'isUpload', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CWechatData.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CWechatData.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加微信收款监控
 */
CWechatData.openAddCWechatData = function () {
    var index = layer.open({
        type: 2,
        title: '添加微信收款监控',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cWechatData/cWechatData_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看微信收款监控详情
 */
CWechatData.openCWechatDataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '微信收款监控详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cWechatData/cWechatData_update/' + CWechatData.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除微信收款监控
 */
CWechatData.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cWechatData/delete", function (data) {
            Feng.success("删除成功!");
            CWechatData.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("cWechatDataId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询微信收款监控列表
 */
CWechatData.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CWechatData.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CWechatData.initColumn();
    var table = new BSTable(CWechatData.id, "/cWechatData/list", defaultColunms);
    table.setPaginationType("client");
    CWechatData.table = table.init();
});
