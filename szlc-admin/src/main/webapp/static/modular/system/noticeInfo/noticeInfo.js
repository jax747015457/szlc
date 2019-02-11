/**
 * 系统公告管理初始化
 */
var NoticeInfo = {
    id: "NoticeInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
NoticeInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            // {title: 'ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '封面图', field: 'coverPlan', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                    if (row.coverPlan == null || row.coverPlan == '') {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 50px;height:50px;" src="' + Feng.ctxPath + '/static/img/NoPIC.png" /></a>';
                    } else {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 90px;height:50px;" src="' + Feng.ctxPath + '/kaptcha/' + row.coverPlan + '" /></a>';
                    }
                },
                events: 'operateEvents'},
            {title: '消息内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 图片弹出预览框（可选）
  */
window.operateEvents = {
    'click .view': function (e, value, row) {
        // 设置图片路径
        var imgUrl = row.coverPlan;
        if(imgUrl != "") {
            imgUrl = Feng.ctxPath + '/kaptcha/' + imgUrl;// 设置图片路径
        } else {
            imgUrl = Feng.ctxPath + '/static/img/NoPIC.png';// 默认无图
        }
        layer.open({
            type: 1,
            title: false,
            closeBtn: 0,
            area: 'auto',
            skin: 'layui-layer-nobg', //没有背景色
            shadeClose: true,
            content: '<img src="' + imgUrl + '" height="100%" width="100%" />'
        });
    },
};

/**
 * 检查是否选中
 */
NoticeInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        NoticeInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加系统公告
 */
NoticeInfo.openAddNoticeInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统公告',
        area: ['95%', '95%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/noticeInfo/noticeInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看系统公告详情
 */
NoticeInfo.openNoticeInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '系统公告详情',
            area: ['95%', '95%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/noticeInfo/noticeInfo_update/' + NoticeInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除系统公告
 */
NoticeInfo.delete = function () {
    if (this.check()) {
        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/noticeInfo/delete", function (data) {
                Feng.success("删除成功!");
                NoticeInfo.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("noticeInfoId", NoticeInfo.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否删除公告 " + NoticeInfo.seItem.title + "?", operation);
    }
};

/**
 * 查询系统公告列表
 */
NoticeInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    NoticeInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = NoticeInfo.initColumn();
    var table = new BSTable(NoticeInfo.id, "/noticeInfo/list", defaultColunms);
    table.setPaginationType("client");
    NoticeInfo.table = table.init();
});
