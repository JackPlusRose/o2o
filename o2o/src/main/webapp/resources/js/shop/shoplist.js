$(function () {
    getlist();
    function getlist(e) {
        $.ajax({
            url:"/o2o/shopAdmin/getShopList",
            type:"get",
            dataType:"json",
            success:function (data) {
                if(data.success){
                    handleList(data);
                    handleUser(data);
                }
            }
        });
    }
    function handleUser(data) {
        var user=data.user;
        $('#user-name').text(user.name);
    }
    function handleList(data) {
        var html='';
        var shopList=data.shopList;
        for(var i=0;i<shopList.length;i++){
            shop=shopList[i];
            html+='<div class="row row-shop"><div class="col-40">'
                +shop.shopName+'</div><div class="col-40">'
                +shopStatus(shop.enableStatus)+'</div><div class="col-20"> '
                +goShop(shop.enableStatus,shop.shopId)+'</div></div>';
        }
        $('.shop-wrap').html(html);
    }
    function shopStatus(status) {
        if(status==0){
            return '审核中';
        }else if(status==-1){
            return '店铺非法';
        }else if(status==1){
            return '审核通过';
        }
    }
    function goShop(status,id) {
        if(status==1){
            return '<a href="/o2o/shopadmin/shopManagement?shopId='+id+'">进入</a>';
        }else{
            return '';
        }
    }
});
