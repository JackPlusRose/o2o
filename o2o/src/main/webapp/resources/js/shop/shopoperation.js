$(function () {
    //用于获取店铺的初始信息
    var initUrl = '/o2o/shopAdmin/getShopInitInfo';
    //用于提交用户注册店铺数据
    var registerShopUrl = '/o2o/shopAdmin/registerShop';

    //用于判断是注册店铺还是修改店铺
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;
    //提交获取用户修改店铺信息
    var shopInfoUrl = '/o2o/shopAdmin/getShopById?shopId=' + shopId;
    //提交修改店铺信息路径
    var editShopUrl = '/o2o/shopAdmin/modifyShop';

    //注册店铺信息，调用注册的基本信息
    if(!isEdit){
        getShopInitInfo();
    }
    else{//获取编辑店铺的基本信息
        getShopInfo(shopId)
    }

    //用于获取用户编辑店铺信息
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="' + shop.shopCategory.shopCategoryId + '"selected>' + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                var areaList = data.areaList;
                var item;
                for (i = 0; i < areaList.length; i++) {
                    item = areaList[i];
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                }
                //店铺类别不可以被修改
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');
                //店铺区域类别可以被修改
                $('#area').html(tempAreaHtml);
                //根据用户返回数据库中的信息，获取area的区域信息
                $("#area option[data-id='"+shop.area.areaId+"']").attr('selected','selected');

            }
        });
    };

    //用于获取用户注册店铺信息的必备信息
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                var shopCategoryList = data.shopCategoryList;
                var areaList = data.areaList;
                var item;
                for (i = 0; i < shopCategoryList.length; i++) {
                    item = shopCategoryList[i];
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                }
                for (i = 0; i < areaList.length; i++) {
                    item = areaList[i];
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                }
                $("#shop-category").html(tempHtml);//添加到下拉列表
                $("#area").html(tempAreaHtml);//添加到下拉列表
            }
        });
    };

    // 点击提交按钮获取页面信息
    $('#submit').click(
        function submitInfo() {
            var shop = {};//用于提交json数据
            if (isEdit){
                shop.shopId=shopId;
            }
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId: $('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId: $('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            // 获取图片
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();//将所有的数据添加到formdata中，用于提交
            formData.append('shopImg', shopImg);
            formData.append('shopStr', JSON.stringify(shop));//将shop中信息转化成json格式
            var verifyCodeActual=$('#j_captcha').val();
            if(verifyCodeActual){
                formData.append('verifyCodeActual',verifyCodeActual);
            }

            //idedit 为真则数据提交到编辑的control方法中，否则提交到注册的方法中
                $.ajax({
                    url: isEdit? editShopUrl: registerShopUrl,
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            $.toast('提交成功！');
                        }
                        else {
                            $.toast(data.errMsg);
                        }
                        //无论提交成与否,都更新验证码
                        $('#captcha_img').click();
                    }
                });
        }
    )
});