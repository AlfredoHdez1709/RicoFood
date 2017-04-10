package com.project.geekahr.ricoproyecto.Notificaciones;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by geekahr on 24/10/16.
 */

public class InstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "NOTICIAS";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: " + token);
    }
}
