/**
 * 初始化微信收款监控详情对话框
 */
var CWechatDataInfoDlg = {
    cWechatDataInfoData : {}
};

/**
 * 清除数据
 */
CWechatDataInfoDlg.clearData = function() {
    this.cWechatDataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CWechatDataInfoDlg.set = function(key, val) {
    this.cWechatDataInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CWechatDataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CWechatDataInfoDlg.close = function() {
    parent.layer.close(window.parent.CWechatData.layerIndex);
}

/**
 * 收集数据
 */
CWechatDataInfoDlg.collectData = function() {
    this
    .set('id')
    .set('receiver')
    .set('money')
    .set('collectinfo')
    .set('rtime')
    .set('isUpload');
}

/**
 * 提交添加
 */
CWechatDataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cWechatData/add", function(data){
        Feng.success("添加成功!");
        window.parent.CWechatData.table.refresh();
        CWechatDataInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cWechatDataInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CWechatDataInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cWechatData/update", function(data){
        Feng.success("修改成功!");
        window.parent.CWechatData.table.refresh();
        CWechatDataInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cWechatDataInfoData);
    ajax.start();
}

$(function() {

});
