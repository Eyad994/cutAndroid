package com.jamalonexpress.testhcut;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.linroid.filtermenu.library.FilterMenu;
import com.linroid.filtermenu.library.FilterMenuLayout;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(RetrofitClient.getClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        FilterMenuLayout layout = findViewById(R.id.filter_menu);
        FilterMenu menu = new FilterMenu.Builder(this)
                //.addItem(R.drawable.facebook)
                .inflate(R.menu.menu_filter)//inflate  menu resource
                .attach(layout)
                .withListener(new FilterMenu.OnMenuChangeListener() {
                    @Override
                    public void onMenuItemClick(View view, int position) {
                        switch (position) {
                            case 0:
                                Toast.makeText(MapsActivity.this, "1", Toast.LENGTH_SHORT).show();
                                getAllProviders();
                                break;
                            case 1:
                                Toast.makeText(MapsActivity.this, "2", Toast.LENGTH_SHORT).show();
                                mMap.clear();
                                break;
                            case 2:
                                Toast.makeText(MapsActivity.this, "3", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                Toast.makeText(MapsActivity.this, "XXX", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }

                    @Override
                    public void onMenuCollapse() {
                    }

                    @Override
                    public void onMenuExpand() {
                    }
                })
                .build();


//        CircleMenu circleMenu = findViewById(R.id.circle_menu);
//        circleMenu.setMainMenu(Color.RED, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_disabled)
//                .addSubMenu(Color.BLUE, R.drawable.facebook)
//                .addSubMenu(Color.RED, R.drawable.pinterest)
//                .addSubMenu(Color.BLUE, R.drawable.linkedin)
//                .addSubMenu(Color.BLUE, R.drawable.twitter)
//                .addSubMenu(Color.BLACK, R.drawable.vk)
//                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
//                    @Override
//                    public void onMenuSelected(int i) {
//
//                        Toast.makeText(MapsActivity.this, "Selected " + arrayName[i], Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onMenuSelected: " + arrayName[i].equals("Facebook"));
//                    }
//                });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.scissor);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Bitmap smallIcon = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        // Bitmap testMethod = resizeImage(getApplicationContext(), R.drawable.scissor,100,100);
        LatLng sydney = new LatLng(31.902765, 35.889524);
        LatLng amman = new LatLng(31.904623, 35.887657);
        LatLng AmmanCenter = new LatLng(31.953838, 35.910577);
        // mMap.clear();
        mMap.addMarker(new MarkerOptions().position(AmmanCenter).title("Amman Center")).setAlpha(0.0f);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"))
                //  .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                .setIcon(BitmapDescriptorFactory.fromBitmap(smallIcon));
        mMap.addMarker(new MarkerOptions().position(amman).title("Marker in Amman"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AmmanCenter, 12));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d(TAG, "onMarkerClick: " + marker.getTitle());

                @SuppressLint("ResourceType") AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this, R.style.AlertDialogTheme);
                builder.setMessage("hi eyad");
                builder.setTitle(marker.getTitle());
                builder.setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
                //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));

                return false;
            }
        });
    }

    public void getAllProviders() {
        Call<List<Provider>> call = jsonPlaceHolderApi.getLatLng();

        call.enqueue(new Callback<List<Provider>>() {
            @Override
            public void onResponse(Call<List<Provider>> call, Response<List<Provider>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.code());
                    return;
                }

                List<Provider> providers = response.body();
                LatLng latLng;
                assert providers != null;
                for (Provider provider : providers) {
                    latLng = new LatLng(provider.getLatitude(), provider.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Api Marker"));
                }
            }

            @Override
            public void onFailure(Call<List<Provider>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public Bitmap resizeImage(Context context, int resId, int height, int width) {

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(resId);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Bitmap changeSize = Bitmap.createScaledBitmap(bitmap, height, width, false);

        return changeSize;
    }
}
