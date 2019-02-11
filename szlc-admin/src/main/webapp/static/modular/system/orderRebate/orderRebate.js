/**
 * 收益回款管理初始化
 */
var OrderRebate = {
    id: "OrderRebateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderRebate.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        // {title: '订单ID', field: 'orderId', visible: true, align: 'center', valign: 'middle'},
        // {title: '回款ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '订单编号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '理财产品', field: 'productName', visible: true, align: 'center', valign: 'middle'},
        // {title: '理财编号', field: 'productNo', visible: true, align: 'center', valign: 'middle'},
        {title: '用户姓名', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '回款金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
        // {title: '回款时间', field: 'rebateTime', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '确认时间', field: 'confirmTime', visible: true, align: 'center', valign: 'middle'},
        {title: '备注说明', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'opt', visible: true, align: 'left', valign: 'middle',
            formatter: function (value, row) {
                var btn = [
                    '<a href="javascript:void(0);" onclick="OrderRebate.openCancelException('+row.id+')">平台确认</a>'
                ];
                return btn;
            }
        }
    ];
};

/**
 * 检查是否选中
 */
OrderRebate.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OrderRebate.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加收益回款
 */
OrderRebate.openAddOrderRebate = function () {
    var index = layer.open({
        type: 2,
        title: '添加收益回款',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderRebate/orderRebate_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看收益回款详情
 */
OrderRebate.openOrderRebateDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '收益回款详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderRebate/orderRebate_update/' + OrderRebate.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 取消异常状态
 */
OrderRebate.openCancelException = function (rebateId) {
    var index = layer.open({
        type: 2,
        title: '平台确认到账',
        area: ['55%', '55%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderRebate/orderRebate_cancelExcetion/' + rebateId
    });
    this.layerIndex = index;
};

/**
 * 删除收益回款
 */
OrderRebate.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/orderRebate/delete", function (data) {
            Feng.success("删除成功!");
            OrderRebate.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderRebateId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询收益回款列表
 */
OrderRebate.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['status'] = $("#status").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    OrderRebate.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = OrderRebate.initColumn();
    var table = new BSTable(OrderRebate.id, "/orderRebate/list", defaultColunms);
    table.queryParams = {"orderId": $("#orderId").val()};
    table.setPaginationType("client");
    OrderRebate.table = table.init();
});
