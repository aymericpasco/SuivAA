package fr.pasco.aymeric.suivaa;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;

public class MyApp extends Application {

    //public static ApiManager apiManager;
    //public  static RetrofitBuilder retrofitBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        //apiManager = ApiManager.getInstance();
    }

}
