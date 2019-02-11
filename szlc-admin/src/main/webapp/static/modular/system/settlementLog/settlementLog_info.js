/**
 * 初始化回款结算记录详情对话框
 */
var SettlementLogInfoDlg = {
    settlementLogInfoData : {}
};

/**
 * 清除数据
 */
SettlementLogInfoDlg.clearData = function() {
    this.settlementLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettlementLogInfoDlg.set = function(key, val) {
    this.settlementLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SettlementLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SettlementLogInfoDlg.close = function() {
    parent.layer.close(window.parent.SettlementLog.layerIndex);
}

/**
 * 收集数据
 */
SettlementLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('spId')
    .set('money')
    .set('adminId')
    .set('createTime');
}

/**
 * 提交添加
 */
SettlementLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settlementLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.SettlementLog.table.refresh();
        SettlementLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settlementLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SettlementLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/settlementLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.SettlementLog.table.refresh();
        SettlementLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.settlementLogInfoData);
    ajax.start();
}

$(function() {

});
