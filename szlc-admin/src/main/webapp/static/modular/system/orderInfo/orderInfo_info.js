/**
 * 初始化订单信息详情对话框
 */
var OrderInfoInfoDlg = {
    orderInfoInfoData : {}
};

/**
 * 清除数据
 */
OrderInfoInfoDlg.clearData = function() {
    this.orderInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderInfoInfoDlg.set = function(key, val) {
    this.orderInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrderInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.OrderInfo.layerIndex);
}

/**
 * 收集数据
 */
OrderInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('productId')
    .set('userId')
    .set('money')
    .set('annualRate')
    .set('incomeAmount')
    .set('createTime')
    .set('incomeTime')
    .set('status');
}

/**
 * 提交添加
 */
OrderInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.OrderInfo.table.refresh();
        OrderInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrderInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.OrderInfo.table.refresh();
        OrderInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderInfoInfoData);
    ajax.start();
}

$(function() {

});
