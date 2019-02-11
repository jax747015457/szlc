/**
 * 初始化服务商详情对话框
 */
var ServiceProviderInfoDlg = {
    serviceProviderInfoData : {}
};

/**
 * 清除数据
 */
ServiceProviderInfoDlg.clearData = function() {
    this.serviceProviderInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ServiceProviderInfoDlg.set = function(key, val) {
    this.serviceProviderInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ServiceProviderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ServiceProviderInfoDlg.close = function() {
    parent.layer.close(window.parent.ServiceProvider.layerIndex);
}

/**
 * 收集数据
 */
ServiceProviderInfoDlg.collectData = function() {
    this
    .set('id')
    .set('serverName')
    .set('serverIp')
    .set('publicKey')
    .set('privateKey')
    .set('remark')
    .set('isDelete')
    .set('createTime');
}

/**
 * 提交添加
 */
ServiceProviderInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/serviceProvider/add", function(data){
        Feng.success("添加成功!");
        window.parent.ServiceProvider.table.refresh();
        ServiceProviderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.serviceProviderInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ServiceProviderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/serviceProvider/update", function(data){
        Feng.success("修改成功!");
        window.parent.ServiceProvider.table.refresh();
        ServiceProviderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.serviceProviderInfoData);
    ajax.start();
}

$(function() {

});
