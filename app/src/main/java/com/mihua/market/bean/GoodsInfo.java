package com.mihua.market.bean;

import java.util.List;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/10/13
 */
public class GoodsInfo {

    /**
     * goods_list : [{"cart_id":"12202","buyer_id":"14507","store_id":"126","store_name":"万能居生鲜超市","goods_id":"34750","goods_name":"巧渍柠檬洗洁精3L 洗涤灵无磷 不伤手 强效去油无残留","goods_price":"29.00","goods_num":"2","goods_image":"126_05283072966773795.jpg","bl_id":"0","state":true,"storage_state":true,"goods_commonid":"106464","gc_id":"245","transport_id":"0","goods_freight":"15.00","goods_vat":"0","goods_storage":"4997","goods_storage_alarm":"5","is_fcode":"0","have_gift":"0","groupbuy_info":null,"xianshi_info":null,"goods_total":"58.00","goods_image_url":"http://shop.trqq.com/data/upload/shop/store/goods/126/126_05283072966773795_240.jpg"}]
     * store_goods_total : 58.00
     * store_mansong_rule_list : null
     * store_voucher_list : []
     * freight : 1
     * store_name : 万能居生鲜超市
     */

    private String store_goods_total;
    private Object store_mansong_rule_list;
    private String freight;
    private String store_name;
    /**
     * cart_id : 12202
     * buyer_id : 14507
     * store_id : 126
     * store_name : 万能居生鲜超市
     * goods_id : 34750
     * goods_name : 巧渍柠檬洗洁精3L 洗涤灵无磷 不伤手 强效去油无残留
     * goods_price : 29.00
     * goods_num : 2
     * goods_image : 126_05283072966773795.jpg
     * bl_id : 0
     * state : true
     * storage_state : true
     * goods_commonid : 106464
     * gc_id : 245
     * transport_id : 0
     * goods_freight : 15.00
     * goods_vat : 0
     * goods_storage : 4997
     * goods_storage_alarm : 5
     * is_fcode : 0
     * have_gift : 0
     * groupbuy_info : null
     * xianshi_info : null
     * goods_total : 58.00
     * goods_image_url : http://shop.trqq.com/data/upload/shop/store/goods/126/126_05283072966773795_240.jpg
     */

    private List<GoodsListBean> goods_list;
    private List<?> store_voucher_list;

    public String getStore_goods_total() {
        return store_goods_total;
    }

    public void setStore_goods_total(String store_goods_total) {
        this.store_goods_total = store_goods_total;
    }

    public Object getStore_mansong_rule_list() {
        return store_mansong_rule_list;
    }

    public void setStore_mansong_rule_list(Object store_mansong_rule_list) {
        this.store_mansong_rule_list = store_mansong_rule_list;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public List<?> getStore_voucher_list() {
        return store_voucher_list;
    }

    public void setStore_voucher_list(List<?> store_voucher_list) {
        this.store_voucher_list = store_voucher_list;
    }

    public static class GoodsListBean {
        private String cart_id;
        private String buyer_id;
        private String store_id;
        private String store_name;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String bl_id;
        private boolean state;
        private boolean storage_state;
        private String goods_commonid;
        private String gc_id;
        private String transport_id;
        private String goods_freight;
        private String goods_vat;
        private String goods_storage;
        private String goods_storage_alarm;
        private String is_fcode;
        private String have_gift;
        private Object groupbuy_info;
        private Object xianshi_info;
        private String goods_total;
        private String goods_image_url;

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getBl_id() {
            return bl_id;
        }

        public void setBl_id(String bl_id) {
            this.bl_id = bl_id;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public boolean isStorage_state() {
            return storage_state;
        }

        public void setStorage_state(boolean storage_state) {
            this.storage_state = storage_state;
        }

        public String getGoods_commonid() {
            return goods_commonid;
        }

        public void setGoods_commonid(String goods_commonid) {
            this.goods_commonid = goods_commonid;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getTransport_id() {
            return transport_id;
        }

        public void setTransport_id(String transport_id) {
            this.transport_id = transport_id;
        }

        public String getGoods_freight() {
            return goods_freight;
        }

        public void setGoods_freight(String goods_freight) {
            this.goods_freight = goods_freight;
        }

        public String getGoods_vat() {
            return goods_vat;
        }

        public void setGoods_vat(String goods_vat) {
            this.goods_vat = goods_vat;
        }

        public String getGoods_storage() {
            return goods_storage;
        }

        public void setGoods_storage(String goods_storage) {
            this.goods_storage = goods_storage;
        }

        public String getGoods_storage_alarm() {
            return goods_storage_alarm;
        }

        public void setGoods_storage_alarm(String goods_storage_alarm) {
            this.goods_storage_alarm = goods_storage_alarm;
        }

        public String getIs_fcode() {
            return is_fcode;
        }

        public void setIs_fcode(String is_fcode) {
            this.is_fcode = is_fcode;
        }

        public String getHave_gift() {
            return have_gift;
        }

        public void setHave_gift(String have_gift) {
            this.have_gift = have_gift;
        }

        public Object getGroupbuy_info() {
            return groupbuy_info;
        }

        public void setGroupbuy_info(Object groupbuy_info) {
            this.groupbuy_info = groupbuy_info;
        }

        public Object getXianshi_info() {
            return xianshi_info;
        }

        public void setXianshi_info(Object xianshi_info) {
            this.xianshi_info = xianshi_info;
        }

        public String getGoods_total() {
            return goods_total;
        }

        public void setGoods_total(String goods_total) {
            this.goods_total = goods_total;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }
    }
}
