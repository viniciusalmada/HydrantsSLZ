package br.com.viniciusalmada.hidrantesslz;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vinicius-almada on 19/05/17.
 */

public class HydrantsSLZ extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
