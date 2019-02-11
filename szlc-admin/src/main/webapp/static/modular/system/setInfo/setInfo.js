/**
 * 系统设置管理初始化
 */
var SetInfo = {
    id: "SetInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SetInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        // {title: 'ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '参数名', field: 'paramName', visible: true, align: 'center', valign: 'middle'},
        {title: '键值', field: 'keyStr', visible: true, align: 'center', valign: 'middle'},
        {title: '参数值', field: 'valueStr', visible: true, align: 'center', valign: 'middle'},
        {title: '备注说明', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SetInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SetInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加系统设置
 */
SetInfo.openAddSetInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统设置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/setInfo/setInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看系统设置详情
 */
SetInfo.openSetInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '系统设置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/setInfo/setInfo_update/' + SetInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除系统设置
 */
SetInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/setInfo/delete", function (data) {
            Feng.success("删除成功!");
            SetInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("setInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询系统设置列表
 */
SetInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SetInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SetInfo.initColumn();
    var table = new BSTable(SetInfo.id, "/setInfo/list", defaultColunms);
    table.setPaginationType("client");
    SetInfo.table = table.init();
});
