@/*
    上传文件参数的说明:
    id : 文件的id
@*/
<div class="form-group">
    <div class="col-sm-4">
        <div id="${id}PreId">
            <div><img width="100px" height="100px"
                @if(isEmpty(fileImg)){
                      src="${ctxPath}/static/img/NoPIC.png">
                @}else{
                      src="${ctxPath}/kaptcha/${fileImg}">
                @}
            </div>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>&nbsp;上传
        </div>
    </div>
    <input type="hidden" id="${id}" value="${fileImg!}"/>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}