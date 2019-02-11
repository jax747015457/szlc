/**
 * 初始化回款监控数据详情对话框
 */
var MonitorDataInfoDlg = {
    monitorDataInfoData : {}
};

/**
 * 清除数据
 */
MonitorDataInfoDlg.clearData = function() {
    this.monitorDataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MonitorDataInfoDlg.set = function(key, val) {
    this.monitorDataInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MonitorDataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MonitorDataInfoDlg.close = function() {
    parent.layer.close(window.parent.MonitorData.layerIndex);
}

/**
 * 收集数据
 */
MonitorDataInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('type')
    .set('money')
    .set('createTime');
}

/**
 * 提交添加
 */
MonitorDataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/monitorData/add", function(data){
        Feng.success("添加成功!");
        window.parent.MonitorData.table.refresh();
        MonitorDataInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.monitorDataInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MonitorDataInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/monitorData/update", function(data){
        Feng.success("修改成功!");
        window.parent.MonitorData.table.refresh();
        MonitorDataInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.monitorDataInfoData);
    ajax.start();
}

$(function() {

});
