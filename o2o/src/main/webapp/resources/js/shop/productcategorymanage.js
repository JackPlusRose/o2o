$(function () {
    //获取从controller层传过来的店铺商铺种类信息
    var listUrl='/o2o/shopAdmin/getProductCategoryList';
    //添加和移除店铺商品种类
    var addUrl='/o2o/shopAdmin/addProductCategorys';
    var deleteUrl='/o2o/shopAdmin/removeProductCategory';

    getList();
    function getList() {
        $.getJSON(listUrl,function (data) {
            if(data.success){
                var productCategories=data.productCategories;
                var item;
                var tempHtml='';
                $('.category-wrap').html('');
                for(i=0;i<productCategories.length;i++){
                    item=productCategories[i];
                    tempHtml+=''
                        +'<div class="row row-product-category now">'
                        +'<div class="col-33 product-category-name">'+item.productCategoryName
                        +'</div>'+'<div class="col-33">'+item.priority
                        +'</div>'+'<div class="col-33"><a href="#" class="button delete" data-id="'
                        +item.productCategoryId
                        +'">删除</a></div>'
                        +'</div>';
                }
                $('.category-wrap').append(tempHtml);
            }
        })
    }
})