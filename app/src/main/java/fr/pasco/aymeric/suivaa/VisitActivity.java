package fr.pasco.aymeric.suivaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.pasco.aymeric.suivaa.entities.Visit;
import fr.pasco.aymeric.suivaa.entities.VisitResponse;
import fr.pasco.aymeric.suivaa.network.ApiService;
import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VisitActivity extends AppCompatActivity {

    private static final String TAG = "VisitActivity";

    @BindView(R.id.visit_visit_date)
    TextView visit_date;

    ApiService service;
    TokenManager tokenManager;
    Call<VisitResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if (tokenManager.getToken() == null) {
            startActivity(new Intent(VisitActivity.this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);
    }

    @OnClick(R.id.btn_vists)
    void getVisits() {
        call = service.visits();
        call.enqueue(new Callback<VisitResponse>() {
            @Override
            public void onResponse(Call<VisitResponse> call, Response<VisitResponse> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    visit_date.setText(response.body().getData().get(0).getVisit_date());
                } else {
                    tokenManager.deleteToken();
                    startActivity(new Intent(VisitActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<VisitResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }
}
