package com.example.lovei.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lovei on 2017/1/7.
 */

public class BaseActivity extends AppCompatActivity {
    private ForceOfflineReceiver receiver;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        ActivityController.addActivity(this);
    }

    public void onResume(){
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.lovei.broadcastbestpractice.FORCE_OFFLINE");
        receiver=new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    public void onPause(){
        super.onPause();
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        ActivityController.removeActivities(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver{
        public void onReceive(final Context context,Intent intent){
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline.Please try to login again");
            builder.setCancelable(false);
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    ActivityController.finishAll();
                    Intent intent=new Intent(context,LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }
}
