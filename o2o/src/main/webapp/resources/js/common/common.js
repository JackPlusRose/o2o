// 共同使用的验证码
function changeVerifyCode(img) {
    img.src="../Kaptcha?"+Math.floor(Math.random()*100);
}

//根据url获取提交的请求的参数
function getQueryString(name) {
    var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r=window.location.search.substr(1).match(reg);
    if(r!=null){
        return decodeURIComponent(r[2]);
    }
    return '';
}