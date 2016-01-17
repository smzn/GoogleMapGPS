package com.example.mizuno.prog_googlemap;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
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

        // Add a marker in Sydney and move the camera
        LatLng latlng = new LatLng(34.738249,137.9592173);
        mMap.addMarker(new MarkerOptions().position(latlng).title("静岡理工科大学"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
    }

    public void mapLocation(double latitude, double longitude){

        LatLng latlng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latlng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        TextView tv_lat = (TextView)findViewById(R.id.latitude);
        tv_lat.setText("Latitude: " + latitude);

        //経度の表示
        TextView tv_lng = (TextView)findViewById(R.id.longitude);
        tv_lng.setText("Longitude:" + longitude);

        this.mapLocation(latitude, longitude);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {

        if(locationManager != null){
            //位置情報の更新不要の場合は終了
            locationManager.removeUpdates(this);
        }

        super.onPause();
    }

    @Override
    protected void onResume() {

        if(locationManager != null){
            //位置情報の更新を取得
            locationManager.requestLocationUpdates(
                    //permissionにACCESS_FINE_LOCATIONを追加
                    LocationManager.GPS_PROVIDER,
                    //NETWORK_PROVIDERを利用する場合はpermissionにACCESS_COARSE_LOCATIONを追加
                    //LocationManager.NETWORK_PROVIDER
                    //networkから取得する場合こちらに切り替える
                    10000,// 通知のための最小時間間隔（ミリ秒）この場合は10秒に１回
                    0,// 通知のための最小距離間隔（メートル）
                    this
            );
        }

        super.onResume();
    }
}
