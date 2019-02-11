/**
 * 初始化转账二维码详情对话框
 */
var UserQrcodeInfoDlg = {
    userQrcodeInfoData : {}
};

/**
 * 清除数据
 */
UserQrcodeInfoDlg.clearData = function() {
    this.userQrcodeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserQrcodeInfoDlg.set = function(key, val) {
    this.userQrcodeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserQrcodeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserQrcodeInfoDlg.close = function() {
    parent.layer.close(window.parent.UserQrcode.layerIndex);
}

/**
 * 收集数据
 */
UserQrcodeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('qrCodeAli')
    .set('qrCodeWx')
    .set('qrCodeWxMonitor');
}

/**
 * 提交添加
 */
UserQrcodeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userQrcode/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserQrcode.table.refresh();
        UserQrcodeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userQrcodeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserQrcodeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userQrcode/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserQrcode.table.refresh();
        UserQrcodeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userQrcodeInfoData);
    ajax.start();
}

$(function() {

});
