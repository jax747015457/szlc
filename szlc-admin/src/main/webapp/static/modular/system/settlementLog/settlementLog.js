/**
 * 回款结算记录管理初始化
 */
var SettlementLog = {
    id: "SettlementLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettlementLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '结算ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            // {title: '服务商ID', field: 'spId', visible: true, align: 'center', valign: 'middle'},
            {title: '结算金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
            // {title: '管理员ID', field: 'adminId', visible: true, align: 'center', valign: 'middle'},
            {title: '结算时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结算状态', field: 'status', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                    if(value == 1){
                        return "待结算";
                    }
                    if(value == 2){
                        return "已结算";
                    }
                }
             },
            {title: '结算备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
SettlementLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SettlementLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加回款结算记录
 */
SettlementLog.openAddSettlementLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加回款结算记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settlementLog/settlementLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看回款结算记录详情
 */
SettlementLog.openSettlementLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '回款结算记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/settlementLog/settlementLog_update/' + SettlementLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除回款结算记录
 */
SettlementLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/settlementLog/delete", function (data) {
            Feng.success("删除成功!");
            SettlementLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("settlementLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 导出回款结算记录
 */
SettlementLog.exportSettlementLog = function () {
    if (this.check()) {
        window.location.href = Feng.ctxPath + "/settlementLog/exportSettlementLog?settlementId="+this.seItem.id;
    }
};

/**
 * 查询回款结算记录列表
 */
SettlementLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SettlementLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SettlementLog.initColumn();
    var table = new BSTable(SettlementLog.id, "/settlementLog/list", defaultColunms);
    table.setPaginationType("client");
    SettlementLog.table = table.init();
});
