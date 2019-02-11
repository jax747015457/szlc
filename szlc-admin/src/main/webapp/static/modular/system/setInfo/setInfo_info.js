/**
 * 初始化系统设置详情对话框
 */
var SetInfoInfoDlg = {
    setInfoInfoData : {}
};

/**
 * 清除数据
 */
SetInfoInfoDlg.clearData = function() {
    this.setInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SetInfoInfoDlg.set = function(key, val) {
    this.setInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SetInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SetInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.SetInfo.layerIndex);
}

/**
 * 收集数据
 */
SetInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('paramName')
    .set('keyStr')
    .set('valueStr')
    .set('remark')
    .set('key1');
}

/**
 * 提交添加
 */
SetInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/setInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.SetInfo.table.refresh();
        SetInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.setInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SetInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/setInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.SetInfo.table.refresh();
        SetInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.setInfoInfoData);
    ajax.start();
}

$(function() {

});
