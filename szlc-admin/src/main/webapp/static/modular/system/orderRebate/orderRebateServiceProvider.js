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
        {field: 'selectItem', radio: false},
        {title: '回款ID', field: 'id', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row) {
                return "<input type='hidden' name='orId' value='"+value+"'>"+value;
            }
         },
        {title: '订单ID', field: 'orderId', visible: true, align: 'center', valign: 'middle'},
        {title: '回款金额', field: 'money', visible: true, align: 'center', valign: 'middle'},
        {title: '回款时间', field: 'rebateTime', visible: true, align: 'center', valign: 'middle'},
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
 * 结算收益回款
 */
OrderRebate.settlement = function () {
    // 获取选中行的回款ID（隐藏回款ID，获取选中复选框的上级、下一兄弟元素、子元素的隐藏ID）
    var ids = "";
    $('input:checkbox[name=btSelectItem]:checked').each(function(i){
        var orId = $(this).parent().next().children("input:hidden[name=orId]").val();
        if(0==i){
            ids = orId;
        }else{
            ids += (","+orId);
        }
    });
    var operation = function() {
        if (ids != '') {
            var ajax = new $ax(Feng.ctxPath + "/orderRebate/settlement", function (data) {
                Feng.success("结算成功!");
                OrderRebate.table.refresh();
            }, function (data) {
                Feng.error("结算失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids",ids);
            ajax.start();
        } else {
            Feng.info("请先选中表格中的某一记录！");
        }
    };
    Feng.confirm("是否结算选中回款?", operation);
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
    var table = new BSTable(OrderRebate.id, "/orderRebate/serviceProviderList", defaultColunms);
    table.queryParams = {"orderId": $("#orderId").val()};
    table.setPaginationType("client");
    OrderRebate.table = table.init();
});
