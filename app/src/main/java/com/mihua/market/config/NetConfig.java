package com.mihua.market.config;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/8/31
 */
public interface NetConfig {

    // 超市首页模块的请求地址

    // 超市首页 banner 页面 get 请求
    String DISCOVERBANNER ="http://api.markapp.cn/v131/singles/banner";

    // 超市分类 post 请求 baseUrl 请求参数 agent_id = 101
    String BASEHOMECLASSIFY ="http://123.57.81.236/sixmarket/sixmarket/index.php/Webservice/v410/index_show";


    //-------------------------------------------------------
    // 分类模块地址   post 请求 baseUrl 请求参数 agent_id = 101
    String CLASSIFYDATA = "http://123.57.81.236/sixmarket/sixmarket/index.php/Webservice/v410/goods_type_list";


    //-------------------------------------------------------
    // 商品列表baseUrl   点击右边的分类模块(传点击的Id)
    String GOODSLISTFROMCLASSIFY = "http://123.57.81.236/sixmarket/sixmarket/index.php/Webservice/v410/get_index_goodslist";

    // 首页中分类图标   点击详情
    String GOODSLISTFROMHOME= "http://123.57.81.236/sixmarket/sixmarket/index.php/Webservice/v410/index_type_goods_list";

    // 模拟购物车的数据
    String STRING = "{\"code\":200,\"datas\":{\"cart_list\":{\"market_food\":[{\"cart_id\":\"10843\",\"buyer_id\":\"14507\",\"store_id\":\"126\",\"store_name\":\"万能居生鲜超市\",\"goods_id\":\"28807\",\"goods_name\":\"公仔四包面牛肉汤面 388g 4包实惠装 速食面快餐面即食面油炸方便面\",\"goods_price\":\"8.30\",\"goods_num\":\"1\",\"goods_image\":\"126_05270745977349933.jpg\",\"bl_id\":\"0\",\"goods_image_url\":\"http://shop.trqq.com/data/upload/shop/store/goods/126/126_05270745977349933_240.jpg\",\"goods_sum\":\"8.30\"},{\"cart_id\":\"11442\",\"buyer_id\":\"14507\",\"store_id\":\"126\",\"store_name\":\"万能居生鲜超市\",\"goods_id\":\"34750\",\"goods_name\":\"巧渍柠檬洗洁精3L 洗涤灵无磷 不伤手 强效去油无残留\",\"goods_price\":\"29.00\",\"goods_num\":\"1\",\"goods_image\":\"126_05283072966773795.jpg\",\"bl_id\":\"0\",\"goods_image_url\":\"http://shop.trqq.com/data/upload/shop/store/goods/126/126_05283072966773795_240.jpg\",\"goods_sum\":\"29.00\"}],\"cart_list\":[{\"cart_id\":\"10844\",\"buyer_id\":\"14507\",\"store_id\":\"1\",\"store_name\":\"泰润自营\",\"goods_id\":\"17911\",\"goods_name\":\"泰润集团二周年纪念T恤  包邮！ 红色 M\",\"goods_price\":\"28.00\",\"goods_num\":\"1\",\"goods_image\":\"1_05230344878836062.jpg\",\"bl_id\":\"0\",\"goods_image_url\":\"http://shop.trqq.com/data/upload/shop/store/goods/1/1_05230344878836062_240.jpg\",\"goods_sum\":\"28.00\"}]},\"sum\":\"213.30\"}}";
}
