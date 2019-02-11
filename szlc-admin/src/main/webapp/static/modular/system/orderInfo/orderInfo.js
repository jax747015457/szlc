/**
 * 订单信息管理初始化
 */
var OrderInfo = {
    id: "OrderInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '订单编号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '理财产品', field: 'productName', visible: true, align: 'center', valign: 'middle'},
        {title: '理财编号', field: 'productNo', visible: true, align: 'center', valign: 'middle'},
        {title: '用户姓名', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '购买金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
        {title: '收益金额', field: 'incomeAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'opt', visible: true, align: 'left', valign: 'middle',
            formatter: function (value, row) {
                var btn = [
                    '<a href="javascript:void(0);" onclick="OrderInfo.openOrderRebate('+row.id+')">回款详情</a>'
                ];
                return btn;
            }
        }
    ];
};

/**
 * 检查是否选中
 */
OrderInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OrderInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单信息
 */
OrderInfo.openAddOrderInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderInfo/orderInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单信息详情
 */
OrderInfo.openOrderInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderInfo/orderInfo_update/' + OrderInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看订单回款详情
 */
OrderInfo.openOrderRebate = function (orderId) {
    console.log(orderId);
    var index = layer.open({
        type: 2,
        title: '订单收益回款详情',
        area: ['95%', '90%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderRebate?orderId=' + orderId
    });
    this.layerIndex = index;
};

/**
 * 删除订单信息
 */
OrderInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/orderInfo/delete", function (data) {
            Feng.success("删除成功!");
            OrderInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单信息列表
 */
OrderInfo.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['productName'] = $("#productName").val();
    queryData['productNo'] = $("#productNo").val();
    queryData['status'] = $("#status").val();
    queryData['nickName'] = $("#nickName").val();
    queryData['phone'] = $("#phone").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    OrderInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = OrderInfo.initColumn();
    var table = new BSTable(OrderInfo.id, "/orderInfo/list", defaultColunms);
    table.setPaginationType("client");
    OrderInfo.table = table.init();
});
