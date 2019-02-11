/**
 * 初始化Banner信息详情对话框
 */
var BannerInfoInfoDlg = {
    bannerInfoInfoData : {},
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
BannerInfoInfoDlg.clearData = function() {
    this.bannerInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoInfoDlg.set = function(key, val) {
    this.bannerInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BannerInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.BannerInfo.layerIndex);
}

/**
 * 收集数据
 */
BannerInfoInfoDlg.collectData = function() {
    this.bannerInfoInfoData['content'] = BannerInfoInfoDlg.editor.getContent();
    this
    .set('id')
    .set('title')
    .set('imgUrl')
    .set('orderby')
    .set('urlType')
    .set('urlHtml')
    .set('remark')
    .set('isSue')
    .set('isDelete')
    .set('createTime');
}

/**
 * 提交添加
 */
BannerInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bannerInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.BannerInfo.table.refresh();
        BannerInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BannerInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bannerInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.BannerInfo.table.refresh();
        BannerInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("noticeInfoInfoForm", BannerInfoInfoDlg.validateFields);

    // 初始化编辑器
    BannerInfoInfoDlg.editor = UM.getEditor('editor');

    // 初始化图片上传
    var imageUp = new $WebUploadImage("imgUrl");
    imageUp.setUploadBarId("progressBar");
    imageUp.init();
});
