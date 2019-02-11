/**
 * 初始化收益回款详情对话框
 */
var OrderRebateInfoDlg = {
    orderRebateInfoData : {}
};

/**
 * 清除数据
 */
OrderRebateInfoDlg.clearData = function() {
    this.orderRebateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderRebateInfoDlg.set = function(key, val) {
    this.orderRebateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderRebateInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrderRebateInfoDlg.close = function() {
    parent.layer.close(window.parent.OrderRebate.layerIndex);
}

/**
 * 收集数据
 */
OrderRebateInfoDlg.collectData = function() {
    this
    .set('id')
    .set('rebateNo')
    .set('money')
    .set('orderId')
    .set('rebateTime')
    .set('qrCodeAli')
    .set('qrCodeWx')
    .set('confirmTime')
    .set('status')
    .set('remark')
    .set('createTime');
}

/**
 * 提交添加
 */
OrderRebateInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderRebate/add", function(data){
        Feng.success("添加成功!");
        window.parent.OrderRebate.table.refresh();
        OrderRebateInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderRebateInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrderRebateInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderRebate/update", function(data){
        Feng.success("修改成功!");
        window.parent.OrderRebate.table.refresh();
        OrderRebateInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderRebateInfoData);
    ajax.start();
}

$(function() {

});
