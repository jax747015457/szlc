/**
 * 用户信息管理初始化
 */
var UserInfo = {
    id: "UserInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '用户昵称', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
            {title: '头像', field: 'avatar', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                    if (row.avatar == null || row.avatar == '') {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 50px;height:50px;" src="'+ Feng.ctxPath + '/static/img/NoPIC.png" /></a>';
                    } else {
                        return '<a class = "view"  href="javascript:void(0)"><img style="width: 50px;height:50px;" src="' + Feng.ctxPath + '/kaptcha/' + row.avatar + '" /></a>';
                    }
                },
                events: 'operateEvents'},
            // {title: '信用分', field: 'creditScore', visible: true, align: 'center', valign: 'middle'},
            {title: '优先度', field: 'orderBy', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                    if(value == 1){
                        return "已冻结";
                    } else {
                        return "正常";
                    }
                    return btn;
                }
            },
            // {title: '角色类型', field: 'roleType', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'opt', visible: true, align: 'left', valign: 'middle', width: '20%',
                formatter: function (value, row) {
                    var btn = [
                        '<a href="javascript:void(0);" onclick="UserInfo.openUserInfoQrcode('+row.id+')">收款二维码</button>&nbsp;|&nbsp;'
                        +'<a href="javascript:void(0);" onclick="UserInfo.updateOrderBy('+row.id+')">修改优先度</a>&nbsp;|&nbsp;'
                        +'<a href="javascript:void(0);" onclick="UserInfo.updateResetPwd('+row.id+')">重置密码</a>'
                    ];
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
        var imgUrl = row.avatar;
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
UserInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加用户信息
 */
UserInfo.openAddUserInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加用户信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userInfo/userInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户信息详情
 */
UserInfo.openUserInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '用户信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userInfo/userInfo_update/' + UserInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看用户信息页面
 */
UserInfo.openUserInfoView = function (id) {
    var index = layer.open({
        type: 2,
        title: '用户信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userInfo/userInfo_view/' + id
    });
    this.layerIndex = index;
};

/**
 * 删除用户信息
 */
UserInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userInfo/delete", function (data) {
            Feng.success("删除成功!");
            UserInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 启用/冻结
 */
UserInfo.freezeAccount = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userInfo/freeze", function (data) {
            Feng.success("操作成功!");
            UserInfo.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("id", this.seItem.id);
        ajax.start();
    }
};

/**
 * 用户二维码页面
 */
UserInfo.openUserInfoQrcode = function (id) {
    var index = layer.open({
        type: 2,
        title: '用户二维码',
        area: ['900px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userInfo/userInfo_qrcode/' + id
    });
    this.layerIndex = index;
};

/**
 * 修改用户优先度
 */
UserInfo.updateOrderBy = function (id) {
    var index = layer.open({
        type: 2,
        title: '修改优先度',
        area: ['50%', '50%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userInfo/userInfo_orderby/' + id
    });
    this.layerIndex = index;
};

/**
 * 重置用户密码
 */
UserInfo.updateResetPwd = function () {
    if (this.check()) {
        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/userInfo/updateResetPwd", function (data) {
                Feng.success("重置成功!");
                UserInfo.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userInfoId", UserInfo.seItem.id);
            ajax.set("userPwd", "123456");
            ajax.start();
        }
        Feng.confirm("是否重置密码为：123456?", operation);
    }
};

/**
 * 查询用户信息列表
 */
UserInfo.search = function () {
    var queryData = {};
    queryData['phone'] = $("#phone").val();
    queryData['nickName'] = $("#nickName").val();
    UserInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UserInfo.initColumn();
    var table = new BSTable(UserInfo.id, "/userInfo/list", defaultColunms);
    table.setPaginationType("client");
    UserInfo.table = table.init();
});
