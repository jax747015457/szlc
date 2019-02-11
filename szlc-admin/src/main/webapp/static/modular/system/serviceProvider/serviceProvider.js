/**
 * 服务商管理初始化
 */
var ServiceProvider = {
    id: "ServiceProviderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ServiceProvider.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '服务器名称', field: 'serverName', visible: true, align: 'center', valign: 'middle'},
            {title: '服务器IP', field: 'serverIp', visible: true, align: 'center', valign: 'middle'},
            {title: '账号', field: 'publicKey', visible: true, align: 'center', valign: 'middle'},
            {title: 'Key', field: 'privateKey', visible: true, align: 'center', valign: 'middle'},
            {title: '商户密钥', field: 'secretKey', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row) {
                console.log("val:"+value + "___row:"+row)
                    var btn = [
                        '<a href="javascript:void(0);" onclick="ServiceProvider.openSecretKey(\''+value+'\')">查看密钥</button>&nbsp;|&nbsp;'
                        +'<a href="javascript:void(0);" onclick="ServiceProvider.updateDownSecretKey('+row.id+')">更新下载</a>'
                    ];
                    return btn;
                }
            },
            {title: '调用接口次数', field: 'requestApiNum', visible: true, align: 'center', valign: 'middle'},
            {title: '备注说明', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ServiceProvider.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ServiceProvider.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加服务商
 */
ServiceProvider.openAddServiceProvider = function () {
    var index = layer.open({
        type: 2,
        title: '添加服务商',
        area: ['850px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/serviceProvider/serviceProvider_add'
    });
    this.layerIndex = index;
};

ServiceProvider.openAddServiceProvider = function () {
    var index = layer.open({
        type: 2,
        title: '添加服务商',
        area: ['850px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/serviceProvider/serviceProvider_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看服务商详情
 */
ServiceProvider.openServiceProviderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '服务商详情',
            area: ['850px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/serviceProvider/serviceProvider_update/' + ServiceProvider.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除服务商
 */
ServiceProvider.delete = function () {
    if (this.check()) {
        var operation = function() {
            var ajax = new $ax(Feng.ctxPath + "/serviceProvider/delete", function (data) {
                Feng.success("删除成功!");
                ServiceProvider.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("serviceProviderId",ServiceProvider.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否删除 " + ServiceProvider.seItem.serverName + "?", operation);
    }
};

/**
 * 查看密钥
 */
ServiceProvider.openSecretKey = function (secretKey) {
    console.log(secretKey);
    var index = layer.open({
        type: 1,
        title: '商户密钥',
        area: ['550px', '220px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: ""+secretKey
    });
    this.layerIndex = index;
};

/**
 * 更新下载商户密钥
 */
ServiceProvider.updateDownSecretKey = function (id) {

    window.location.href = Feng.ctxPath + "/serviceProvider/updateDownSecretKey?id="+id;
    ServiceProvider.table.refresh();
};

/**
 * 查询服务商列表
 */
ServiceProvider.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    ServiceProvider.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ServiceProvider.initColumn();
    var table = new BSTable(ServiceProvider.id, "/serviceProvider/list", defaultColunms);
    table.setPaginationType("client");
    ServiceProvider.table = table.init();
});
