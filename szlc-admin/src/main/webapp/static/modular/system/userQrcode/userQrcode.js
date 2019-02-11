/**
 * 转账二维码管理初始化
 */
var UserQrcode = {
    id: "UserQrcodeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserQrcode.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '用户ID', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '支付宝转账码', field: 'qrCodeAli', visible: true, align: 'center', valign: 'middle'},
            {title: '微信转账码', field: 'qrCodeWx', visible: true, align: 'center', valign: 'middle'},
            {title: '监控收款通知微信码', field: 'qrCodeWxMonitor', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserQrcode.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserQrcode.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加转账二维码
 */
UserQrcode.openAddUserQrcode = function () {
    var index = layer.open({
        type: 2,
        title: '添加转账二维码',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userQrcode/userQrcode_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看转账二维码详情
 */
UserQrcode.openUserQrcodeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '转账二维码详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userQrcode/userQrcode_update/' + UserQrcode.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除转账二维码
 */
UserQrcode.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userQrcode/delete", function (data) {
            Feng.success("删除成功!");
            UserQrcode.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userQrcodeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询转账二维码列表
 */
UserQrcode.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UserQrcode.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UserQrcode.initColumn();
    var table = new BSTable(UserQrcode.id, "/userQrcode/list", defaultColunms);
    table.setPaginationType("client");
    UserQrcode.table = table.init();
});
