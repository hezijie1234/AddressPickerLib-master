package com.ywp.addresspickerdemo;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ywp.addresspickerlib.AddressPickerView;
import com.ywp.addresspickerlib.YwpAddressBean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTvAddress;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initJsonData();
        mTvAddress = findViewById(R.id.tvAddress);
        mTvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressPickerPop();
            }
        });
        AddressPickerView addressView = findViewById(R.id.apvAddress);
        YwpAddressBean ywpAddressBean = new YwpAddressBean();
        ywpAddressBean.setProvince(mProvinceItem);
        ywpAddressBean.setCity(mCityItem);
        ywpAddressBean.setDistrict(mAreaItem);
        addressView.initData(ywpAddressBean);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                mTvAddress.setText(address);
            }
        });
    }

    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("weiwei_areainfo.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len = -1;
            while ((len = is.read(by)) != -1) {
                //此json不需要设置编码
                sb.append(new String(by, 0, len));//根据字节长度设置编码
            }
            is.close();//关闭流
            jsonString = sb.toString();
            loadCityJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //所有数据
    private List<CityInfo.RECORDSBean> dataList = new ArrayList<>();
    //所有的省
    private List<CityInfo.RECORDSBean> provinceList = new ArrayList<>();
    //所有的城市
    private List<CityInfo.RECORDSBean> cityList = new ArrayList<>();
    //所有的区
    private List<CityInfo.RECORDSBean> areaList = new ArrayList<>();
    private List<YwpAddressBean.AddressItemBean> mProvinceItem = new ArrayList<>();
    private List<YwpAddressBean.AddressItemBean> mCityItem = new ArrayList<>();
    private List<YwpAddressBean.AddressItemBean> mAreaItem = new ArrayList<>();

    private void loadCityJson() {
        Gson gson = new Gson();
        CityInfo cityInfo = gson.fromJson(jsonString, CityInfo.class);
        dataList.addAll(cityInfo.getRECORDS());
        for (CityInfo.RECORDSBean bean : dataList){
            YwpAddressBean.AddressItemBean itemBean = new YwpAddressBean.AddressItemBean();
            switch (bean.getArealevel()){
                case 1:
//                    provinceList.add(bean);
//                    itemBean.setP("86000000");
                    itemBean.setN(bean.getName());
                    itemBean.setI(String.valueOf(bean.getId()));
                    mProvinceItem.add(itemBean);
                    break;
                case 2:
//                    Log.e("111", "loadCityJson: " + bean.getParent_id() );
                    itemBean.setP(String.valueOf(bean.getParent_id()));
                    itemBean.setN(bean.getName());
                    itemBean.setI(String.valueOf(bean.getId()));
                    mCityItem.add(itemBean);
                    break;
                case 3:
                    itemBean.setP(String.valueOf(bean.getParent_id()));
                    itemBean.setN(bean.getName());
                    itemBean.setI(String.valueOf(bean.getId()));
                    mAreaItem.add(itemBean);
                    break;
            }
        }


    }




    /**
     * 显示地址选择的pop
     */
    private void showAddressPickerPop() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
        AddressPickerView addressView = rootView.findViewById(R.id.apvAddress);
        YwpAddressBean ywpAddressBean = new YwpAddressBean();
        ywpAddressBean.setProvince(mProvinceItem);
        ywpAddressBean.setCity(mCityItem);
        ywpAddressBean.setDistrict(mAreaItem);
        addressView.initData(ywpAddressBean);
        addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                mTvAddress.setText(address);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(rootView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAsDropDown(mTvAddress);

    }
}
