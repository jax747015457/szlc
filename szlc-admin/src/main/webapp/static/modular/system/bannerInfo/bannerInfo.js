/**
 * Banner信息管理初始化
 */
var BannerInfo = {
    id: "BannerInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BannerInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: 'Banner图', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                    if (row.imgUrl == null || row.imgUrl == '') {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 50px;height:50px;" src="' + Feng.ctxPath + '/static/img/NoPIC.png" /></a>';
                    } else {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 90px;height:50px;" src="' + Feng.ctxPath + '/kaptcha/' + row.imgUrl + '" /></a>';
                    }
                },
                events: 'operateEvents'},
            {title: '排序', field: 'orderby', visible: true, align: 'center', valign: 'middle'},
            {title: '跳转类型', field: 'urlType', visible: true, align: 'center', valign: 'middle'},
            {title: '跳转路径', field: 'urlHtml', visible: true, align: 'center', valign: 'middle'},
            {title: '备注说明', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '是否发布', field: 'isSue', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'opt', visible: true, align: 'left', valign: 'middle', width: '8%',
                formatter: function (value, row) {
                    var btn;
                    if(row.isSue == '已发布'){
                        btn = ['<a href="javascript:void(0);" onclick="BannerInfo.setIsSue('+row.id+', 0)">取消发布</a>'];
                    } else {
                        btn = ['<a href="javascript:void(0);" onclick="BannerInfo.setIsSue('+row.id+', 1)">发布</a>'];
                    }
                    return btn;
                }
            }
    ];
};
/**
 * 图片弹出预览框（可选）
 */
window.operateEvents = {
    'click .view': function (e, value, row) {
        // 设置图片路径
        var imgUrl = row.imgUrl;
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
BannerInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BannerInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加Banner信息
 */
BannerInfo.openAddBannerInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加Banner信息',
        area: ['95%', '95%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bannerInfo/bannerInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看Banner信息详情
 */
BannerInfo.openBannerInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'Banner信息详情',
            area: ['95%', '95%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/bannerInfo/bannerInfo_update/' + BannerInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除Banner信息
 */
BannerInfo.delete = function () {
    if (this.check()) {
        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/bannerInfo/delete", function (data) {
                Feng.success("删除成功!");
                BannerInfo.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("bannerInfoId",BannerInfo.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否删除公告 " + BannerInfo.seItem.title + "?", operation);
    }
};

/**
 * 发布/取消发布
 */
BannerInfo.setIsSue = function (id, isSue) {
    var ajax = new $ax(Feng.ctxPath + "/bannerInfo/update", function (data) {
        Feng.success("操作成功!");
        BannerInfo.table.refresh();
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",id);
    ajax.set("isSue",isSue);
    ajax.start();
}

/**
 * 查询Banner信息列表
 */
BannerInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BannerInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BannerInfo.initColumn();
    var table = new BSTable(BannerInfo.id, "/bannerInfo/list", defaultColunms);
    table.setPaginationType("client");
    BannerInfo.table = table.init();
});
