package fr.pasco.aymeric.suivaa;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.common.StringUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.pasco.aymeric.suivaa.adapters.VisitsAdapter;
import fr.pasco.aymeric.suivaa.entities.Doctor;
import fr.pasco.aymeric.suivaa.entities.Office;
import fr.pasco.aymeric.suivaa.entities.Visit;
import fr.pasco.aymeric.suivaa.network.ApiService;
import fr.pasco.aymeric.suivaa.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CreateVisitActivity extends AppCompatActivity {

    ApiService service;
    TokenManager tokenManager;
    Call<List<Doctor>> doctorsCall;
    Call<Visit> call;

    private CalendarView createVisitDate;
    private Spinner createVisitDoctor;
    private CheckBox createVisitAppointment;
    private EditText createVisitArrivingHour;
    private EditText createVisitArrivingMinute;
    private EditText createVisitStartHour;
    private EditText createVisitStartMinute;
    private EditText createVisitDepartureHour;
    private EditText createVisitDepartureMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_create);

        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if (tokenManager.getToken() == null) {
            startActivity(new Intent(CreateVisitActivity.this, LoginActivity.class));
            finish();
        }
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        createVisitDate = findViewById(R.id.create_visit_date);
        createVisitDoctor = findViewById(R.id.create_visit_doctor);
        createVisitAppointment = findViewById(R.id.create_visit_appointment);
        createVisitArrivingHour = findViewById(R.id.create_visit_arriving_hour);
        createVisitArrivingMinute = findViewById(R.id.create_visit_arriving_minute);
        createVisitStartHour = findViewById(R.id.create_visit_start_hour);
        createVisitStartMinute = findViewById(R.id.create_visit_start_minute);
        createVisitDepartureHour = findViewById(R.id.create_visit_departure_hour);
        createVisitDepartureMinute = findViewById(R.id.create_visit_departure_minute);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        doctorsCall = service.getDoctors();
        doctorsCall.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()) {
                    GpsTracker gpsTracker = new GpsTracker(CreateVisitActivity.this);

                    ArrayList<Doctor> doctors = new ArrayList<>();
                    float minDistance = Float.MAX_VALUE;

                    Location currentLocation = new Location(gpsTracker.getLocation());
                    Location doctorLocation = new Location("dummy");

                    for (Doctor doctor : response.body()) {
                        doctorLocation.setLatitude(doctor.getOffice().getLatitude());
                        doctorLocation.setLongitude(doctor.getOffice().getLongitude());
                        if (minDistance < currentLocation.distanceTo(doctorLocation)) {
                            doctors.add(doctors.size(), doctor);
                        } else {
                            doctors.add(0, doctor);
                            minDistance = currentLocation.distanceTo(doctorLocation);
                        }
                        doctorLocation.reset();
                    }

                    ArrayAdapter<Doctor> adapter = new ArrayAdapter<>(CreateVisitActivity.this, R.layout.spinner, doctors);
                    createVisitDoctor.setAdapter(adapter);
                } else {
                    tokenManager.deleteToken();
                    startActivity(new Intent(CreateVisitActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Toast.makeText(CreateVisitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_save_visit)
    void createVisit() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Doctor selectedDoctor = (Doctor)createVisitDoctor.getSelectedItem();
        Visit visit = new Visit(
                sdf.format(new Date(createVisitDate.getDate())),
                createVisitAppointment.isChecked() ? 1 : 0,
                createVisitArrivingHour.getText().toString() + ":" + createVisitArrivingMinute.getText().toString() + ":00",
                createVisitStartHour.getText().toString() + ":" + createVisitStartMinute.getText().toString() + ":00",
                createVisitDepartureHour.getText().toString() + ":" + createVisitDepartureMinute.getText().toString() + ":00",
                selectedDoctor.getId()
        );
        call = service.createVisit(visit);
        call.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                //Visit responseVisit = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(CreateVisitActivity.this,
                            String.format("Visite créée!"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CreateVisitActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
                Toast.makeText(CreateVisitActivity.this,
                        "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (doctorsCall != null) {
            doctorsCall.cancel();
            doctorsCall = null;
        }
        if (call != null) {
            call.cancel();
            call = null;
        }
    }

}
