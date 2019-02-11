/**
 * 初始化系统公告详情对话框
 */
var NoticeInfoInfoDlg = {
    noticeInfoInfoData : {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
NoticeInfoInfoDlg.clearData = function() {
    this.noticeInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoInfoDlg.set = function(key, val) {
    this.noticeInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
NoticeInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.NoticeInfo.layerIndex);
}

/**
 * 收集数据
 */
NoticeInfoInfoDlg.collectData = function() {
    this.noticeInfoInfoData['content'] = NoticeInfoInfoDlg.editor.getContent();
    this
    .set('id')
    .set('title')
    .set('coverPlan')
    .set('createTime');
}

/**
 * 提交添加
 */
NoticeInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/noticeInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.NoticeInfo.table.refresh();
        NoticeInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
NoticeInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/noticeInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.NoticeInfo.table.refresh();
        NoticeInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("noticeInfoInfoForm", NoticeInfoInfoDlg.validateFields);

    // 初始化编辑器
    NoticeInfoInfoDlg.editor = UM.getEditor('editor');

    // 初始化图片上传
    var imageUp = new $WebUploadImage("coverPlan");
    imageUp.setUploadBarId("progressBar");
    imageUp.init();
});
