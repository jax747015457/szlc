/**
 * 回款监控数据管理初始化
 */
var MonitorData = {
    id: "MonitorDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MonitorData.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '用户姓名', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '监控类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
        {title: '记录时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MonitorData.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MonitorData.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加回款监控数据
 */
MonitorData.openAddMonitorData = function () {
    var index = layer.open({
        type: 2,
        title: '添加回款监控数据',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/monitorData/monitorData_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看回款监控数据详情
 */
MonitorData.openMonitorDataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '回款监控数据详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/monitorData/monitorData_update/' + MonitorData.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除回款监控数据
 */
MonitorData.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/monitorData/delete", function (data) {
            Feng.success("删除成功!");
            MonitorData.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("monitorDataId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询回款监控数据列表
 */
MonitorData.search = function () {
    var queryData = {};
    queryData['nickName'] = $("#nickName").val();
    queryData['phone'] = $("#phone").val();
    queryData['type'] = $("#type").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    MonitorData.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MonitorData.initColumn();
    var table = new BSTable(MonitorData.id, "/monitorData/list", defaultColunms);
    table.setPaginationType("client");
    MonitorData.table = table.init();
});
