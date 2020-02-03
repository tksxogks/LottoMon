package kr.co.htssoft.lottomon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import kr.co.htssoft.lottomon.MapItem;
import kr.co.htssoft.lottomon.R;

import static kr.co.htssoft.lottomon.RodingActivity.busan;
import static kr.co.htssoft.lottomon.RodingActivity.chungbuk;
import static kr.co.htssoft.lottomon.RodingActivity.chungnam;
import static kr.co.htssoft.lottomon.RodingActivity.daegu;
import static kr.co.htssoft.lottomon.RodingActivity.daejeon;
import static kr.co.htssoft.lottomon.RodingActivity.gangwon;
import static kr.co.htssoft.lottomon.RodingActivity.gwangju;
import static kr.co.htssoft.lottomon.RodingActivity.gyeongbuk;
import static kr.co.htssoft.lottomon.RodingActivity.gyeonggi;
import static kr.co.htssoft.lottomon.RodingActivity.gyeongnam;
import static kr.co.htssoft.lottomon.RodingActivity.incheon;
import static kr.co.htssoft.lottomon.RodingActivity.jeju;
import static kr.co.htssoft.lottomon.RodingActivity.jeonbuk;
import static kr.co.htssoft.lottomon.RodingActivity.jeonnam;
import static kr.co.htssoft.lottomon.RodingActivity.sejong;
import static kr.co.htssoft.lottomon.RodingActivity.seoul;
import static kr.co.htssoft.lottomon.RodingActivity.ulsan;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentActivity mContext;

    private GoogleMap mMap;
    private MapView mapView = null;
    private  String TAG = MapFragment.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private Location mCurrentLocatiion;

    private LatLng mDefaultLocation = new LatLng(37.5622, 127.0352);
    private boolean mLocationPermissionGranted;

    private int UPDATE_INTERVAL = 1000 * 60 * 1;  // 1분 단위 시간 갱신
    private int FASTEST_UPDATE_INTERVAL = 1000 * 30 ; // 30초 단위로 화면 갱신

    String storeName;
    String storePhoneNumber;
    String storeAddress;
    Double storeLon;
    Double storeLat;

    @Override
    public void onAttach(Activity activity) {
        mContext =(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentLocatiion = savedInstanceState.getParcelable("location");
            CameraPosition mCameraPosition = savedInstanceState.getParcelable("camera_position");
        }
        View layout =  inflater.inflate(R.layout.fragment_map,container,false);
        mapView = layout.findViewById(R.id.map);
        if(mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MapsInitializer.initialize(mContext);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도
                .setInterval(UPDATE_INTERVAL) // 위치가 Update
                .setFastestInterval(FASTEST_UPDATE_INTERVAL); // 위치 획득후의 주기

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setDefaultLocation(); // 지도 초기 위치

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        getIndeMarkerItems();

    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mCurrentLocatiion = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void setDefaultLocation() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15);
        mMap.moveCamera(cameraUpdate);
    }

    String getCurrentAddress(LatLng latlng) {
        List<Address> addressList = null ;
        Geocoder geocoder = new Geocoder( mContext, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);
        } catch (IOException e) {
            Toast. makeText( mContext, "위치로부터 주소를 인식할 수 없습니다. 네트워크가 연결되어 있는지 확인해 주세요."+e, Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
            return "주소 인식 불가" ;
        }

        if (addressList.size() < 1) {
            return "해당 위치에 주소 없음" ;
        }

        Address address = addressList.get(0);
        StringBuilder addressStringBuilder = new StringBuilder();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressStringBuilder.append(address.getAddressLine(i));
            if (i < address.getMaxAddressLineIndex())
                addressStringBuilder.append("\n");
        }

        return addressStringBuilder.toString();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);

                setCurrentLocation(location);
                mCurrentLocatiion = location;
                Log.e("TAG", "Location로딩완료");
            }
        }
    };

    public void setCurrentLocation(Location location) {

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }

    private void getIndeMarkerItems() {


//        String address = getAddress(getContext(), mCurrentLocatiion.getLatitude(), mCurrentLocatiion.getLongitude());
//        String[] mnt = address.split(" ");
//        Log.e("TAG", address);
//        addressNum(mnt[0], mnt[1]);

        storePoint("서울특별시");

    }


    private Marker addMarker(MapItem markerItem) {


        LatLng positionLatLng = new LatLng(markerItem.getLat(), markerItem.getLon());
        Log.e("TAG", markerItem.getStoreName()+" "+markerItem.getLat()+","+markerItem.getLon());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerItem.getStoreName());
        markerOptions.position(positionLatLng);
        markerOptions.snippet(markerItem.getAddress()+"("+markerItem.getPhoneNumber()+")");


        return mMap.addMarker(markerOptions);

    }

    public void addressNum(String dosi, String gunsi){

    }



    public void storePoint(String sido){
        Log.e("TAG", sido);
        switch (sido){
            case "강원도":
                for(int i=0 ; i<gangwon.length ; i++){
                    for(int j=0 ; j<gangwon[i].size() ; j++){

                        storeName = gangwon[i].get(j).getStoreName();
                        storePhoneNumber = gangwon[i].get(j).getPhoneNumber();
                        storeAddress = gangwon[i].get(j).getAddress();
                        storeLon = gangwon[i].get(j).getLon();
                        storeLat = gangwon[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "경기도":
                for(int i=0 ; i<gyeonggi.length ; i++){
                    for(int j=0 ; j<gyeonggi[i].size() ; j++){

                        storeName = gyeonggi[i].get(j).getStoreName();
                        storePhoneNumber = gyeonggi[i].get(j).getPhoneNumber();
                        storeAddress = gyeonggi[i].get(j).getAddress();
                        storeLon = gyeonggi[i].get(j).getLon();
                        storeLat = gyeonggi[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "경상남도":
                for(int i=0 ; i<gyeongnam.length ; i++){
                    for(int j=0 ; j<gyeongnam[i].size() ; j++){

                        storeName = gyeongnam[i].get(j).getStoreName();
                        storePhoneNumber = gyeongnam[i].get(j).getPhoneNumber();
                        storeAddress = gyeongnam[i].get(j).getAddress();
                        storeLon = gyeongnam[i].get(j).getLon();
                        storeLat = gyeongnam[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "경상북도":
                for(int i=0 ; i<gyeongbuk.length ; i++){
                    for(int j=0 ; j<gyeongbuk[i].size() ; j++){
                        storeName = gyeongbuk[i].get(j).getStoreName();
                        storePhoneNumber = gyeongbuk[i].get(j).getPhoneNumber();
                        storeAddress = gyeongbuk[i].get(j).getAddress();
                        storeLon = gyeongbuk[i].get(j).getLon();
                        storeLat = gyeongbuk[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "광주광역시":
                for(int i=0 ; i<gwangju.length ; i++){
                    for(int j=0 ; j<gwangju[i].size() ; j++){
                        storeName = gwangju[i].get(j).getStoreName();
                        storePhoneNumber = gwangju[i].get(j).getPhoneNumber();
                        storeAddress = gwangju[i].get(j).getAddress();
                        storeLon = gwangju[i].get(j).getLon();
                        storeLat = gwangju[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "대구광역시":
                for(int i=0 ; i<daegu.length ; i++){
                    for(int j=0 ; j<daegu[i].size() ; j++){
                        storeName = daegu[i].get(j).getStoreName();
                        storePhoneNumber = daegu[i].get(j).getPhoneNumber();
                        storeAddress = daegu[i].get(j).getAddress();
                        storeLon = daegu[i].get(j).getLon();
                        storeLat = daegu[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "대전광역시":
                for(int i=0 ; i<daejeon.length ; i++){
                    for(int j=0 ; j<daejeon[i].size() ; j++){
                        storeName = daejeon[i].get(j).getStoreName();
                        storePhoneNumber = daejeon[i].get(j).getPhoneNumber();
                        storeAddress = daejeon[i].get(j).getAddress();
                        storeLon = daejeon[i].get(j).getLon();
                        storeLat = daejeon[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "부산광역시":
                for(int i=0 ; i<busan.length ; i++){
                    for(int j=0 ; j<busan[i].size() ; j++){
                        storeName = busan[i].get(j).getStoreName();
                        storePhoneNumber = busan[i].get(j).getPhoneNumber();
                        storeAddress = busan[i].get(j).getAddress();
                        storeLon = busan[i].get(j).getLon();
                        storeLat = busan[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "서울특별시":
                for(int i=0 ; i<seoul.length ; i++){
                    for(int j=0 ; j<seoul[i].size() ; j++){
                        storeName = seoul[i].get(j).getStoreName();
                        storePhoneNumber = seoul[i].get(j).getPhoneNumber();
                        storeAddress = seoul[i].get(j).getAddress();
                        storeLon = seoul[i].get(j).getLon();
                        storeLat = seoul[i].get(j).getLat();

                        Log.e("TAG", storeName+", "+storeAddress+", "+storeLat+", "+storeLon);

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "세종특별자치시":
                for(int i=0 ; i<sejong.size() ; i++) {
                    storeName = sejong.get(i).getStoreName();
                    storePhoneNumber = sejong.get(i).getPhoneNumber();
                    storeAddress = sejong.get(i).getAddress();
                    storeLon = sejong.get(i).getLon();
                    storeLat = sejong.get(i).getLat();

                    MarkerOptions options = new MarkerOptions();
                    options.position(new LatLng(storeLat, storeLon));
                    options.title(storeName);
                    options.snippet(storePhoneNumber);
                    mMap.addMarker(options);
                }

                break;
            case "울산광역시":
                for(int i=0 ; i<ulsan.length ; i++){
                    for(int j=0 ; j<ulsan[i].size() ; j++){
                        storeName = ulsan[i].get(j).getStoreName();
                        storePhoneNumber = ulsan[i].get(j).getPhoneNumber();
                        storeAddress = ulsan[i].get(j).getAddress();
                        storeLon = ulsan[i].get(j).getLon();
                        storeLat = ulsan[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "인천광역시":
                for(int i=0 ; i<incheon.length ; i++){
                    for(int j=0 ; j<incheon[i].size() ; j++){
                        storeName = incheon[i].get(j).getStoreName();
                        storePhoneNumber = incheon[i].get(j).getPhoneNumber();
                        storeAddress = incheon[i].get(j).getAddress();
                        storeLon = incheon[i].get(j).getLon();
                        storeLat = incheon[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "전라남도":
                for(int i=0 ; i<jeonnam.length ; i++){
                    for(int j=0 ; j<jeonnam[i].size() ; j++){
                        storeName = jeonnam[i].get(j).getStoreName();
                        storePhoneNumber = jeonnam[i].get(j).getPhoneNumber();
                        storeAddress = jeonnam[i].get(j).getAddress();
                        storeLon = jeonnam[i].get(j).getLon();
                        storeLat = jeonnam[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "전라북도":
                for(int i=0 ; i<jeonbuk.length ; i++){
                    for(int j=0 ; j<jeonbuk[i].size() ; j++){
                        storeName = jeonbuk[i].get(j).getStoreName();
                        storePhoneNumber = jeonbuk[i].get(j).getPhoneNumber();
                        storeAddress = jeonbuk[i].get(j).getAddress();
                        storeLon = jeonbuk[i].get(j).getLon();
                        storeLat = jeonbuk[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "제주특별자치도":
                for(int i=0 ; i<jeju.length ; i++){
                    for(int j=0 ; j<jeju[i].size() ; j++){
                        storeName = jeju[i].get(j).getStoreName();
                        storePhoneNumber = jeju[i].get(j).getPhoneNumber();
                        storeAddress = jeju[i].get(j).getAddress();
                        storeLon = jeju[i].get(j).getLon();
                        storeLat = jeju[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "충청남도":
                for(int i=0 ; i<chungnam.length ; i++){
                    for(int j=0 ; j<chungnam[i].size() ; j++){
                        storeName = chungnam[i].get(j).getStoreName();
                        storePhoneNumber = chungnam[i].get(j).getPhoneNumber();
                        storeAddress = chungnam[i].get(j).getAddress();
                        storeLon = chungnam[i].get(j).getLon();
                        storeLat = chungnam[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
            case "충청북도":
                for(int i=0 ; i<chungbuk.length ; i++){
                    for(int j=0 ; j<chungbuk[i].size() ; j++){
                        storeName = chungbuk[i].get(j).getStoreName();
                        storePhoneNumber = chungbuk[i].get(j).getPhoneNumber();
                        storeAddress = chungbuk[i].get(j).getAddress();
                        storeLon = chungbuk[i].get(j).getLon();
                        storeLat = chungbuk[i].get(j).getLat();

                        MarkerOptions options = new MarkerOptions();
                        options.position(new LatLng(storeLat, storeLon));
                        options.title(storeName);
                        options.snippet(storePhoneNumber);
                        mMap.addMarker(options);
                    }
                }
                break;
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        } catch (SecurityException e)  {
            Log.e("DeviceLocation E:", e.getMessage());
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(mContext,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public static String getAddress(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("MyCurrentloctionaddress", strReturnedAddress.toString());
            } else {
                Log.w("MyCurrentloctionaddress", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("MyCurrentloctionaddress", "Canont get Address!");
        }

        strAdd = strAdd.substring(5);
        return strAdd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "onStart ");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onStop : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onResume : requestLocationUpdates");
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
            if (mMap!=null)
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onDestroyView : removeLocationUpdates");
            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}