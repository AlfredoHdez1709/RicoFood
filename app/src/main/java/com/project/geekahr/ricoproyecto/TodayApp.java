package com.project.geekahr.ricoproyecto;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.MessageDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by geekahr on 10/15/16.
 */

public class TodayApp extends Application {
    public TodayApp() throws PackageManager.NameNotFoundException {
    }

    @Override
    public  void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        printKeyHash();
    }


    public  void printKeyHash()
        {
            try {
                PackageInfo info = getPackageManager().getPackageInfo("com.project.geekahr.ricoproyecto", PackageManager.GET_SIGNATURES);
                for (Signature signature: info.signatures)
                {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.e("Hash Key", Base64.encodeToString(md.digest(),Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {

            } catch (NoSuchAlgorithmException e) {

            }
        }
}
