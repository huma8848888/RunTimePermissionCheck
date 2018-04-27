package com.example.master.runtimepermissioncheck;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;


/*安卓进行动态权限申请申请的是权限组，权限组里有一个被通过即所有都通过，
不在危险权限里的权限只要是注册了的都被同意。
通过一个按钮来触发权限注册功能，后期移植时候可自行更改。
ADD_VOICEMAIL不能注册，原因不明
但是其他常用的权限都注册了，可以通过注释的方式去掉自己不想要的权限*/
public class MainActivity extends AppCompatActivity {

    int LengthForAll;
    private boolean CheckPermission(String PermissionCheck){
        if (ContextCompat.checkSelfPermission(MainActivity.this, PermissionCheck)!= PackageManager.PERMISSION_GRANTED){
            //如果用户之前未同意权限，则执行onRequestPermissionResult
            return true;
        }else
            return false;//用户之前申请过相关权限，执行动作
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button) findViewById(R.id.button);
        final String[] PermissionGroup=new String[]{/*可以在此自由的添加自己想要申请的权限*/
                Manifest.permission.READ_CALENDAR,//读写日历
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.CAMERA,//照相机和摄像机
                Manifest.permission.READ_CONTACTS,//读写联系人
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.ACCESS_FINE_LOCATION,//GPS及WIFI,基站定位
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,//录音（麦克风）
                Manifest.permission.READ_PHONE_STATE,//电话相关权限
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.WRITE_CALL_LOG,
                //Manifest.permission.ADD_VOICEMAIL,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.BODY_SENSORS,//身体传感器
                Manifest.permission.SEND_SMS,//短信读写
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,//存取外部存储设备数据
                Manifest.permission.WRITE_EXTERNAL_STORAGE,


        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=PermissionGroup.length;//获取权限申请数组的长度
                LengthForAll=k;//把数组长度赋值给全局变量用于遍历数组
                for (int i=0;i<k;i++){

                    if (CheckPermission(PermissionGroup[i])){
                        ActivityCompat.requestPermissions(MainActivity.this,PermissionGroup,1);
                        //if user have not access the permission
                    }else {
                        Toast.makeText(getApplicationContext(),"you have allowed the permission before.",Toast.LENGTH_SHORT).show();
                        //do something
                    }

                }

            }
        });
    }


    @Override/*grantresult作为请求同意或拒绝码，0位始终由系统赋值，无需更改*/
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*GrantResults数组只有在全部经用户确认后才能有权限列表填充进来，未确认完之前都是空*/
        if (grantResults.length!=0){
            for (int i=0;i<LengthForAll;i++){
                switch (requestCode){
                    case 1:if (grantResults.length>0&&grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        Log.d("222","you allowed the permission");
                    }else {
                        Log.d("222","you denied the permission");
                        //do something if user denied the permission
                    }
                        break;
                }
            }
        }

        if (permissions.length!=0){/*permissions数组只有在全部经用户确认后才能有权限列表填充进来，未确认完之前都是空*/
            int i=permissions.length;
            for (int j=0;j<i;j++){
                Log.d("222",permissions[j].toString());

            }
        }

    }
}
