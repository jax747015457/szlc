/**
 * 理财产品管理初始化
 */
var ProductInfo = {
    id: "ProductInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '理财产品', field: 'productName', visible: true, align: 'center', valign: 'middle'},
            {title: '理财编号', field: 'productNo', visible: true, align: 'center', valign: 'middle'},
            {title: '七日年化率', field: 'annualRate', visible: true, align: 'center', valign: 'middle'},
            {title: '起投金额', field: 'minMoney', visible: true, align: 'center', valign: 'middle'},
            {title: '累计资金', field: 'addsMoney', visible: true, align: 'center', valign: 'middle'},
            {title: '购买用户数', field: 'userNum', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加理财产品
 */
ProductInfo.openAddProductInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加理财产品',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productInfo/productInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看理财产品详情
 */
ProductInfo.openProductInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '理财产品详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productInfo/productInfo_update/' + ProductInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除理财产品
 */
ProductInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productInfo/delete", function (data) {
            Feng.success("删除成功!");
            ProductInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 启用/冻结
 */
ProductInfo.freeze = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productInfo/freeze", function (data) {
            Feng.success("操作成功!");
            ProductInfo.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询理财产品列表
 */
ProductInfo.search = function () {
    var queryData = {};
    queryData['productName'] = $("#productName").val();
    queryData['productNo'] = $("#productNo").val();
    queryData['status'] = $("#status").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    ProductInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductInfo.initColumn();
    var table = new BSTable(ProductInfo.id, "/productInfo/list", defaultColunms);
    table.setPaginationType("client");
    ProductInfo.table = table.init();
});
