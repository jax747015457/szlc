/**
 * 初始化系统消息详情对话框
 */
var MessageInfoInfoDlg = {
    messageInfoInfoData : {}
};

/**
 * 清除数据
 */
MessageInfoInfoDlg.clearData = function() {
    this.messageInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageInfoInfoDlg.set = function(key, val) {
    this.messageInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MessageInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MessageInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.MessageInfo.layerIndex);
}

/**
 * 收集数据
 */
MessageInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('title')
    .set('content')
    .set('createTime');
}

/**
 * 提交添加
 */
MessageInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.MessageInfo.table.refresh();
        MessageInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MessageInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/messageInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.MessageInfo.table.refresh();
        MessageInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.messageInfoInfoData);
    ajax.start();
}

$(function() {

});
