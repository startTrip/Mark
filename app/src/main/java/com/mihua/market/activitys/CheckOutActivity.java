package com.mihua.market.activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.mihua.market.R;
import com.mihua.market.adapter.CheckOrderStoreAdapter;
import com.mihua.market.bean.GoodsInfo;
import com.mihua.market.pay.AlipayHelper;
import com.mihua.market.pay.PayResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckOutActivity extends AppCompatActivity {

    private static final int SDK_PAY_FLAG = 1;
    private RelativeLayout mChangeAdressLayout;
    private ImageView mTvBack;
    private TextView mName;
    private TextView mPhoneNumber;
    private TextView mAddress_info;
    private OkHttpClient mClient;
    private String mCart_id;
    private String mIfcart;
    private String address_id;
    private String city_id;
    private String area_id;
    private String vat_hash;
    private String freight_hash;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                JSONObject jsonObject = (JSONObject) msg.obj;
                try {
                JSONObject store_cart_list = jsonObject.getJSONObject("store_cart_list");
                    if (store_cart_list != null) {

                        String string = store_cart_list.getString("126");

                        GoodsInfo goodsInfo = JSON.parseObject(string, GoodsInfo.class);
                        int size = goodsInfo.getGoods_list().size();
                        mStoreData.add(goodsInfo);
                        mCheckOrderStoreAdapter.addDatas(mStoreData);

                        JSONObject jsonObject1 = store_cart_list.optJSONObject("126");
                        String string1 = jsonObject1.getString("store_goods_total");
                        mCheckMoney.setText(string1);
                        mGoodsSum.setText(size+"");
                    }
                    // 设置地址信息
                    JSONObject add_js = jsonObject
                            .getJSONObject("address_info");
                    address_id = add_js.optString("address_id");
                    city_id = add_js.optString("city_id");
                    area_id = add_js.optString("area_id");

                    mName.setText(add_js.getString("true_name"));
                    mAddress_info.setText(add_js.optString("area_info")
                            + " " + add_js.optString("address"));
                    mPhoneNumber.setText("  "
                            + add_js.getString("mob_phone"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private ArrayList<GoodsInfo> mStoreData;
    private CheckOrderStoreAdapter mCheckOrderStoreAdapter;
    private ArrayList<GoodsInfo> mList;
    private ListView mStoreListView;
    private TextView mGoodsSum;
    private TextView mCheckMoney;
    private Button mCheckSubmit;
    private String key="f6d38622c20799e1cef01c110f2d600a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        initView();
        initData();

        setListener();
    }

    private void initView() {

        mChangeAdressLayout = (RelativeLayout) findViewById(R.id.check_address_layout);
        mTvBack = (ImageView) findViewById(R.id.checkout_back);
        mName = (TextView) findViewById(R.id.receiver_name);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mAddress_info = (TextView) findViewById(R.id.address_info);

        mStoreListView = (ListView) findViewById(R.id.checkout_store_list);

        mGoodsSum = (TextView) findViewById(R.id.check_goods_sum);
        mCheckMoney = (TextView) findViewById(R.id.check_sum_money);
        mCheckSubmit = (Button)findViewById(R.id.bt_check_submit);
    }


    private void initData() {

        Intent intent = getIntent();
        mCart_id = intent.getStringExtra("cart_id");
        mIfcart = intent.getStringExtra("ifcart");

        mClient = new OkHttpClient();

        mStoreData = new ArrayList<>();
        mList = new ArrayList<>();

        mCheckOrderStoreAdapter = new CheckOrderStoreAdapter(this, mList);

        mStoreListView.setAdapter(mCheckOrderStoreAdapter);

        loadOnlineBuyStep1Data();
    }

    private void setListener() {
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 点击提交订单付款
        mCheckSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击提交订单
                loadOnlineBuyStep2Data();
                zhifuboPay();
            }
        });
    }

    private void loadOnlineBuyStep2Data() {

//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("key", key);
//        builder.add("cart_id", mCart_id);     // 商品的 id 和 数量
//        builder.add("ifcart", mIfcart);
//        builder.add("address_id", address_id);
//        builder.add("vat_hash", vat_hash);
//        builder.add("offpay_hash", offpay_hash);
//        builder.add("offpay_hash_batch", offpay_hash_batch);
//        builder.add("pay_name", "online");
//        builder.add("invoice_id", invoice_id);
//        builder.add("voucher", voucher);
//        builder.add("pd_pay", available[1]);
//        builder.add("rcb_pay", available[0]);
//        builder.add("password", "");
//        builder.add("fcode", "");
//        Request request = new Request.Builder().url("http://shop.trqq.com/mobile/index.php?act=member_buy&op=buy_step1").post(builder.build()).build();
//        Call call = mClient.newCall(request);
//

    }

    private void loadOnlineBuyStep1Data(){

        Log.d("cart_id",mCart_id);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key", key);
        builder.add("cart_id", mCart_id);     // 商品的 id 和 数量
        builder.add("ifcart", mIfcart);
        Request request = new Request.Builder().url("http://shop.trqq.com/mobile/index.php?act=member_buy&op=buy_step1").post(builder.build()).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(string);
                    JSONObject jsonObjects = jsonObject1.optJSONObject(("datas"));
                    //发票信息Hash
                    vat_hash = jsonObjects.optString("vat_hash");
                    // 运费 Hash, 选择地区时作为提交
                    freight_hash = jsonObjects
                            .optString("freight_hash");

                    Message message = new Message();
                    message.obj = jsonObjects;
                    message.what = 1;
                    mHandler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 支付宝付款
    private void zhifuboPay() {

        if (TextUtils.isEmpty(AlipayHelper.PARTNER) || TextUtils.isEmpty(AlipayHelper.RSA_PRIVATE) || TextUtils.isEmpty(AlipayHelper.SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        // 订单  ！！！！ 需要设置为自己的信息，在下面
        String orderInfo =
                AlipayHelper.getOrderInfo
                        ("测试的商品", // 商品名称
                                "该测试商品的详细描述", // 商品描述
                                "0.01");// 商品价格

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = AlipayHelper.sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         * 最终，支付宝使用 payInfo 来进行支付的操作
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + AlipayHelper.getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                //  支付宝内部的内对象，用于发送请求
                PayTask alipay = new PayTask(CheckOutActivity.this);
                // 调用支付接口，获取支付结果
                // pay() 这个方法实际发起支付
                // 返回值，包含支付成功失败的信息。
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mAlipayHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    // 支付后的消息
    @SuppressLint("HandlerLeak")
    private Handler mAlipayHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(CheckOutActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(CheckOutActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(CheckOutActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    finish();
                    break;
                }
                default:
                    break;
            }
        };
    };

}
