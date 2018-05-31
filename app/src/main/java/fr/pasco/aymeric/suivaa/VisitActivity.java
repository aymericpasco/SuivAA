/*package fr.pasco.aymeric.suivaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.pasco.aymeric.suivaa.entities.VisitResponse;
import fr.pasco.aymeric.suivaa.network.ApiService;
import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setContentView(R.layout.old_activity_visit);

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
}*/
package fr.pasco.aymeric.suivaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.pasco.aymeric.suivaa.adapters.VisitsAdapter;
import fr.pasco.aymeric.suivaa.entities.Visit;
import fr.pasco.aymeric.suivaa.network.ApiService;
import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitActivity extends AppCompatActivity {

    private static final String TAG = "VisitActivity";

    //@BindView(R.id.visit_visit_date)
    //TextView visit_date;

    ApiService service;
    TokenManager tokenManager;
    Call<List<Visit>> call;

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

    @OnClick(R.id.btn_visits)
    void getVisits() {
        call = service.getVisits();
        final RecyclerView visitsRecyclerView = findViewById(R.id.visits_recycler_view);
        call.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                // Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    Iterable<Visit> visitsIterable = Iterables.filter(response.body(), new Predicate<Visit>() {
                        @Override
                        public boolean apply(Visit input) {
                            return !input.getVisitDate().trim().equals("");
                        }
                    });

                    HashSet<Visit> visits = Sets.newHashSet(visitsIterable);
                    VisitsAdapter visitsAdapter = new VisitsAdapter(VisitActivity.this, new ArrayList(visits));
                    visitsRecyclerView.setAdapter(visitsAdapter);
                    visitsRecyclerView.setLayoutManager(new LinearLayoutManager(VisitActivity.this));
                } else {
                    tokenManager.deleteToken();
                    startActivity(new Intent(VisitActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {
                Toast.makeText(VisitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_visit_add)
    void toCreateVisit() {
        startActivity(new Intent(VisitActivity.this, CreateVisitActivity.class));
        finish();
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