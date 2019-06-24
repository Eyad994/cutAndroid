package com.jamalonexpress.testhcut;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import androidx.fragment.app.FragmentActivity;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    String arrayName[] = {"Facebook", "Twitter",
            "Youtube", "Linkedin", "Pinterest"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CircleMenu circleMenu = findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.RED,R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_disabled)
                .addSubMenu(Color.BLUE, R.drawable.facebook)
                .addSubMenu(Color.RED, R.drawable.pinterest)
                .addSubMenu(Color.BLUE, R.drawable.linkedin)
                .addSubMenu(Color.BLUE, R.drawable.twitter)
                .addSubMenu(Color.BLACK, R.drawable.vk)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        Toast.makeText(MapsActivity.this, "Selected"+ arrayName[i], Toast.LENGTH_SHORT).show();
                    }
                });
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
        mMap.addMarker(new MarkerOptions().position(AmmanCenter).title("Amman Center")).setAlpha(0.0f);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"))
                //  .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
             .setIcon(BitmapDescriptorFactory.fromBitmap(smallIcon));
        mMap.addMarker(new MarkerOptions().position(amman).title("Marker in Amman"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AmmanCenter, 12));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d(TAG, "onMarkerClick: "+ marker);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setMessage("hi eyad");
                        builder.setTitle("title");
                        builder.setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                     //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));

                return false;
            }
        });
    }

    public Bitmap resizeImage(Context context, int resId, int height, int width){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(resId);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Bitmap changeSize = Bitmap.createScaledBitmap(bitmap, height, width, false);

        return changeSize;
    }
}
