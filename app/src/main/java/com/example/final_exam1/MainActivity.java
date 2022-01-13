package com.example.final_exam1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.opencsv.CSVReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     public static final String TAG = "my application";

        private PermissionManager pm;

        private ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // Permission is granted. Continue the action or workflow in your
                        // app.
                        Log.d(TAG, "Permission granted");
                    } else {
                        Log.e(TAG, "Permission grant failed");
                    }
                });

        private FusedLocationProviderClient fusedLocationProviderClient;

        String tab_names[] = {"All", "Today", "Tomorrow"};



        public static ArrayList<WeatherData> all_data_list = new ArrayList<>();
    private TabLayout tabLayout;
    private TextView textView;
    private ViewPager2 viewPager;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            viewPager = findViewById(R.id.viewPager);
            textView = findViewById(R.id.lastLocationText);
            tabLayout = findViewById(R.id.tabtab);

            pm = new PermissionManager(this);
            pm.getPermission();

            // TODO: TabLayout & ViewPager2 UI 구현
        }

        @Override
        protected void onResume() {
            super.onResume();


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //위도, 경도
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> locInfo = geocoder.getFromLocation(latitude,longitude,2);


                        String adminArea = locInfo.get(0).getAdminArea();
                        String locality = locInfo.get(0).getLocality();

                        textView.setText(adminArea + locality);

                        Log.d(TAG, adminArea);
                        Log.d(TAG, locality);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    new HTTPCommTask().execute();

                }
            });
        }


        /**
         * 도시(adminArea)이름과 시(Locality)이름을 기준으로 x, y 좌표를 반환한다.
         *
         * @param adminArea 도시 이름(예: 충청남도)
         * @param locality  시 이름(예: 아산시, 종로구)
         * @return [x, y] 정수 배열, 찾는 도시 이름과 시 이름이 잘못된 경우 null
         */
        private Integer[] getPosition(String adminArea, String locality) {
            final String filename = "final_exam_data.csv";
            File file = new File(this.getFilesDir(), filename);
            try {
                CSVReader csvReader = new CSVReader(new FileReader(file));
                csvReader.readNext();// skip column header
                String[] line = csvReader.readNext();
                while (line != null) {
                    final String s1 = line[2];
                    final String s2 = line[3];

                    if (adminArea.equals(s1) && locality.equals(s2)) {
                        return new Integer[]{Integer.parseInt(line[5]), Integer.parseInt(line[6])};
                    }

                    line = csvReader.readNext();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

