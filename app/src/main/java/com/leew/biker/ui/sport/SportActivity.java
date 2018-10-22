package com.leew.biker.ui.sport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.leew.biker.R;
import com.leew.biker.base.BaseActivity;
import com.leew.biker.util.LogUtils;
import com.leew.biker.util.PropertyAnimation;

import java.util.ArrayList;
import java.util.List;
;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * author:Leew
 * date:2018/10/18  15:22
 * vesion:1.0
 * description:
 */
public class SportActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.mapview)
    MapView mMapview;
    @BindView(R.id.sport_guide)
    Button mSportGuide;
    @BindView(R.id.sport_speed)
    TextView mSportSpeed;
    @BindView(R.id.sport_km)
    TextView mSportKm;
    @BindView(R.id.sport_time)
    TextView mSportTime;
    @BindView(R.id.childlayout)
    LinearLayout mChildlayout;
    @BindView(R.id.arrowhead)
    ImageView mArrowhead;
    @BindView(R.id.showhideview)
    RelativeLayout mShowhideview;
    @BindView(R.id.parentlayout)
    RelativeLayout mParentlayout;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomlayout;


    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private List<LatLng> latLngs = new ArrayList<>();
    private Polyline polyline;
    private PolylineOptions options;
    private AMap aMap;
    private int height;
    private int sheight, lheight;

    private PropertyAnimation propertyAnimation;


    public static void start(Context context) {
        Intent intent = new Intent(context, SportActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sport;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        WindowManager windowManager = getWindowManager();
        height = windowManager.getDefaultDisplay().getHeight();
        mMapview.onCreate(savedInstanceState);
        propertyAnimation = new PropertyAnimation(this);
        mArrowhead.setOnClickListener(v -> {
            if (mShowhideview.getVisibility() == View.GONE) {
                propertyAnimation.animateOpen(mShowhideview);
                propertyAnimation.animationIvOpen(mArrowhead);
                sheight = mBottomlayout.getHeight();
                if(lheight == 0){
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,height-sheight);
                    mMapview.setLayoutParams(params);
                }else{
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,height-lheight);
                    mMapview.setLayoutParams(params);
                }

            } else {
                propertyAnimation.animateClose(mShowhideview);
                propertyAnimation.animationIvClose(mArrowhead);
                lheight = mBottomlayout.getHeight();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,height-sheight);
                mMapview.setLayoutParams(params);
            }
        });

        //导航
        mSportGuide = findViewById(R.id.sport_guide);
        mSportGuide.setOnClickListener(v -> {
            AmapNaviPage.getInstance().showRouteActivity(activity, new AmapNaviParams(null), new INaviInfoCallback() {
                @Override
                public void onInitNaviFailure() {

                }

                @Override
                public void onGetNavigationText(String s) {

                }

                @Override
                public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

                }

                @Override
                public void onArriveDestination(boolean b) {

                }

                @Override
                public void onStartNavi(int i) {

                }

                @Override
                public void onCalculateRouteSuccess(int[] ints) {

                }

                @Override
                public void onCalculateRouteFailure(int i) {

                }

                @Override
                public void onStopSpeaking() {

                }

                @Override
                public void onReCalculateRoute(int i) {

                }

                @Override
                public void onExitPage(int i) {

                }

                @Override
                public void onStrategyChanged(int i) {

                }

                @Override
                public View getCustomNaviBottomView() {
                    return null;
                }

                @Override
                public View getCustomNaviView() {
                    return null;
                }

                @Override
                public void onArrivedWayPoint(int i) {

                }
            });
        });
        initmaps();

    }

    private void initmaps() {
        aMap = mMapview.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));//设置缩放1-20


        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(10000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapview.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapview.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapview.onSaveInstanceState(outState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                aMapLocation.getLatitude();//获取纬度
//                aMapLocation.getLongitude();//获取经度
//                aMapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(aMapLocation.getTime());
//                df.format(date);//定位时间
                latLngs.add(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                options = new PolylineOptions().addAll(latLngs).width(10).color(Color.RED);
                polyline = aMap.addPolyline(options);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtils.e("location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
