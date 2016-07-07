package com.lnpdit.agriculturalmechanization.page.activity;
import java.io.IOException;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.lnpdit.agriculturalmechanization.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,SurfaceHolder.Callback{  
    private AMap aMap;
    private MapView mMapView = null;
    TextView status_tv;
    TextView voice_tv;
    TextView statistics_tv;
    TextView signal_tv;
    TextView setting_tv;
    TextView more_tv;
    TextView layers_tv;
    Button switch_btn;

    private UiSettings mUiSettings;
    private static Context context = null;  
    private SurfaceView surfaceview;  
    private SurfaceHolder surfaceholder;  
    private Camera camera = null; 
    int cameraPosition = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState); 
      setContentView(R.layout.activity_main);
      
      //获取地图控件引用
      mMapView = (MapView) findViewById(R.id.map);
      //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
      mMapView.onCreate(savedInstanceState);
      
      if (aMap == null) {
          aMap = mMapView.getMap();
          mUiSettings = aMap.getUiSettings();
      }
      mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);// 设置地图logo显示在右下方
       init();
       surfaceview = (SurfaceView)findViewById(R.id.surfaceview);  
       surfaceholder = surfaceview.getHolder();  
       surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);  
       surfaceholder.addCallback(this); 
    }
    
    @SuppressLint("ResourceAsColor")
	private void init(){
        layers_tv = (TextView) findViewById(R.id.layers_tv);
        layers_tv.setClickable(true);
        layers_tv.setOnClickListener(this); 
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        layers_tv.setText(R.string.normal);
            
        status_tv = (TextView) findViewById(R.id.status_tv);
//        status_tv.setBackgroundColor(R.color.btn_green);
        voice_tv = (TextView) findViewById(R.id.voice_tv);
        statistics_tv = (TextView) findViewById(R.id.statistics_tv);
        signal_tv = (TextView) findViewById(R.id.signal_tv);
        setting_tv = (TextView) findViewById(R.id.setting_tv);
        more_tv = (TextView) findViewById(R.id.more_tv);
        switch_btn = (Button) findViewById(R.id.switch_btn);
        status_tv.setClickable(true);
        voice_tv.setClickable(true);
        statistics_tv.setClickable(true);
        signal_tv.setClickable(true);
        setting_tv.setClickable(true);
        more_tv.setClickable(true);
        status_tv.setOnClickListener(this);
        voice_tv.setOnClickListener(this);
        statistics_tv.setOnClickListener(this);
        signal_tv.setOnClickListener(this);
        setting_tv.setOnClickListener(this);
        more_tv.setOnClickListener(this);
        switch_btn.setOnClickListener(this);

    }
    
    @Override
    protected void onDestroy() {
      super.onDestroy();
      //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
      mMapView.onDestroy();
    }
   @Override
   protected void onResume() {
      super.onResume();
      //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
      mMapView.onResume();
      }
   @Override
   protected void onPause() {
      super.onPause();
      //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
      mMapView.onPause();
      }
   @Override
   protected void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
      mMapView.onSaveInstanceState(outState);
    } 

   @SuppressLint("ResourceAsColor")
public void onClick(View v) {
       switch (v.getId()) {
       case R.id.layers_tv:
           /**
            * 选择矢量地图和卫星地图事件的响应
            */
               if (layers_tv.getText().equals(getString(R.string.satellite))) {
                   aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                   layers_tv.setText(R.string.normal);
               } else if (layers_tv.getText().equals(getString(R.string.normal))) {
                   aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                   layers_tv.setText(R.string.satellite);
               }
        break;
       case R.id.status_tv:
           status_tv.setBackgroundResource(R.color.btn_green);
           voice_tv.setBackgroundResource(R.drawable.black_littlebg);
           statistics_tv.setBackgroundResource(R.drawable.black_littlebg);
           signal_tv.setBackgroundResource(R.drawable.black_littlebg);
           setting_tv.setBackgroundResource(R.drawable.black_littlebg);
           more_tv.setBackgroundResource(R.drawable.black_littlebg);
           break;
       case R.id.voice_tv:
           status_tv.setBackgroundResource(R.drawable.black_littlebg);
           voice_tv.setBackgroundResource(R.color.btn_green);
           statistics_tv.setBackgroundResource(R.drawable.black_littlebg);
           signal_tv.setBackgroundResource(R.drawable.black_littlebg);
           setting_tv.setBackgroundResource(R.drawable.black_littlebg);
           more_tv.setBackgroundResource(R.drawable.black_littlebg);
           break;
       case R.id.statistics_tv:
           status_tv.setBackgroundResource(R.drawable.black_littlebg);
           voice_tv.setBackgroundResource(R.drawable.black_littlebg);
           statistics_tv.setBackgroundResource(R.color.btn_green);
           signal_tv.setBackgroundResource(R.drawable.black_littlebg);
           setting_tv.setBackgroundResource(R.drawable.black_littlebg);
           more_tv.setBackgroundResource(R.drawable.black_littlebg);
           break;
       case R.id.signal_tv:
           status_tv.setBackgroundResource(R.drawable.black_littlebg);
           voice_tv.setBackgroundResource(R.drawable.black_littlebg);
           statistics_tv.setBackgroundResource(R.drawable.black_littlebg);
           signal_tv.setBackgroundResource(R.color.btn_green);
           setting_tv.setBackgroundResource(R.drawable.black_littlebg);
           more_tv.setBackgroundResource(R.drawable.black_littlebg);
           break;
       case R.id.setting_tv:
           status_tv.setBackgroundResource(R.drawable.black_littlebg);
           voice_tv.setBackgroundResource(R.drawable.black_littlebg);
           statistics_tv.setBackgroundResource(R.drawable.black_littlebg);
           signal_tv.setBackgroundResource(R.drawable.black_littlebg);
           setting_tv.setBackgroundResource(R.color.btn_green);
           more_tv.setBackgroundResource(R.drawable.black_littlebg);
           break;
       case R.id.more_tv:
           status_tv.setBackgroundResource(R.drawable.black_littlebg);
           voice_tv.setBackgroundResource(R.drawable.black_littlebg);
           statistics_tv.setBackgroundResource(R.drawable.black_littlebg);
           signal_tv.setBackgroundResource(R.drawable.black_littlebg);
           setting_tv.setBackgroundResource(R.drawable.black_littlebg);
           more_tv.setBackgroundResource(R.color.btn_green);
         
           break;
       case R.id.switch_btn:
           switchCamera(surfaceholder);
           break;
    default:
        break;
    }
   }

   @Override
   public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {  
       System.out.println("surfacechanged");  
   } 
   @Override 
   public void surfaceCreated(SurfaceHolder holder) {  
       System.out.println("surfacecreated");  
       //获取camera对象  
       camera = Camera.open();  
       try {  
           //设置预览监听  
           camera.setPreviewDisplay(holder);  
           Camera.Parameters parameters = camera.getParameters();  
             
           if (this.getResources().getConfiguration().orientation   
                       != Configuration.ORIENTATION_LANDSCAPE) {  
               parameters.set("orientation", "portrait");  
               camera.setDisplayOrientation(90);  
               parameters.setRotation(90);  
           } else {  
               parameters.set("orientation", "landscape");  
               camera.setDisplayOrientation(0);  
               parameters.setRotation(0);  
           }  
           camera.setParameters(parameters);  
           //启动摄像头预览  
           camera.startPreview();  
           System.out.println("camera.startpreview");  
             
       } catch (IOException e) {  
           e.printStackTrace();  
           camera.release();  
           System.out.println("camera.release");  
       }  
   }  
   @Override  
   public void surfaceDestroyed(SurfaceHolder arg0) {  
       System.out.println("surfaceDestroyed");  
       if (camera != null) {  
           camera.stopPreview();  
           camera.release();             
       }  
   } 
   
 //切换镜头
   public void switchCamera(SurfaceHolder holder) {
     //切换前后摄像头
       int cameraCount = 0;
       CameraInfo cameraInfo = new CameraInfo();
       cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

       for(int i = 0; i < cameraCount; i++) {
           Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
           if(cameraPosition == 1) {
               //现在是后置，变更为前置
               if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
                   camera.stopPreview();//停掉原来摄像头的预览
                   camera.release();//释放资源
                   camera = null;//取消原来摄像头
                   camera = Camera.open(i);//打开当前选中的摄像头
                   try {
                       camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                   } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
                   camera.startPreview();//开始预览
                   cameraPosition = 0;
                   break;
               }
           } else {
               //现在是前置， 变更为后置
               if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
                   camera.stopPreview();//停掉原来摄像头的预览
                   camera.release();//释放资源
                   camera = null;//取消原来摄像头
                   camera = Camera.open(i);//打开当前选中的摄像头
                   try {
                       camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                   } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
                   camera.startPreview();//开始预览
                   cameraPosition = 1;
                   break;
               }
           }

       }
       }
}  
