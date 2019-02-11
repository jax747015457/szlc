/**
 * 系统消息管理初始化
 */
var MessageInfo = {
    id: "MessageInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MessageInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '用户ID', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '消息内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MessageInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MessageInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加系统消息
 */
MessageInfo.openAddMessageInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统消息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/messageInfo/messageInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看系统消息详情
 */
MessageInfo.openMessageInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '系统消息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/messageInfo/messageInfo_update/' + MessageInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除系统消息
 */
MessageInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/messageInfo/delete", function (data) {
            Feng.success("删除成功!");
            MessageInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("messageInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询系统消息列表
 */
MessageInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MessageInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MessageInfo.initColumn();
    var table = new BSTable(MessageInfo.id, "/messageInfo/list", defaultColunms);
    table.setPaginationType("client");
    MessageInfo.table = table.init();
});
