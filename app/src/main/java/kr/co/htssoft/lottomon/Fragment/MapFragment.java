package kr.co.htssoft.lottomon.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.htssoft.lottomon.MainActivity;
import kr.co.htssoft.lottomon.R;



public class MapFragment extends Fragment{
    private FusedLocationProviderClient mFusedLocationClient;

    public LocationManager locationManager;

    private MapView mapView;

    public double longitude; //경도
    public double latitude; //위도
    public double altitude; //고도
    public float accuracy; //정확도
    public String provider; //위치제공자
    ImageView gps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        setLocation(view);
        getLocation();

        return view;
    }

    public void setLocation(View v) {
        gps = v.findViewById(R.id.gps);
        gps.bringToFront();

        mapView = new MapView(getContext());
        ViewGroup mapViewContainer = v.findViewById(R.id.map_View);
        mapViewContainer.addView(mapView);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    public void getLocation() {
        try {
            // GPS 제공자의 정보가 바뀌면 콜백
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    100, // 통지 시간
                    1, // 통지거리
                    mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    100, // 통지 시간
                    1, // 통지거리
                    mLocationListener);
        } catch (SecurityException e) {
            Log.e("SecurityException", e+"");
        }
    }

    public void myLocation(double latitude, double longitude) {
        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        mapView.setZoomLevel(4, true);
        mapView.zoomIn(true);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재 위치");
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setShowDisclosureButtonOnCalloutBalloon(false);
        mapView.selectPOIItem(marker, true);
        mapView.addPOIItem(marker);
    }

    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            //위치값 바뀌면 자동 변경
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            altitude = location.getAltitude();
            accuracy = location.getAccuracy();
            provider = location.getProvider();

            //currentLocation = getAddress(getContext(), latitude, longitude); 주소불러오기

            myLocation(latitude, longitude);

            locationManager.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

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
}


/*
public class MapFragment extends Fragment implements MapView.OpenAPIKeyAuthenticationResultListener, MapViewEventListener,
        MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    MapView mapView;

    LocationManager locationManager;
    MapPointBounds mapPointBounds = new MapPointBounds();
    Location location = null;
    MapPOIItem marker = new MapPOIItem();

    ImageView gps;
    boolean gpsSelect=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        gps = view.findViewById(R.id.gps);
        gps.bringToFront();

        mapView = new MapView(view.getContext());
        mapView.setOpenAPIKeyAuthenticationResultListener(this);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        getMyLocation();
        autoLocation();


        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gpsSelect==false) {
                    gps.setImageResource(R.drawable.gpsselected);
                    mapView.setShowCurrentLocationMarker(false);

                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                    MapPOIItem[] poiItems = mapView.getPOIItems();
                    if(poiItems.length>0) mapView.selectPOIItem(poiItems[0], false);

                    gpsSelect=true;
                }else if(gpsSelect==true){
                    gps.setImageResource(R.drawable.gps);
                    mapView.setShowCurrentLocationMarker(true);
                    getMyLocation();
                    gpsSelect=false;
                }
            }
        });

        ViewGroup mapViewContainer = view.findViewById(R.id.map_View);
        mapViewContainer.addView(mapView);


        return view;
    }


    public void getMyLocation(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, );
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, );

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//비용지불 감수
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);//정확도를 요하는가?
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);//베터리 소모량
        criteria.setAltitudeRequired(true);//고도에 대한 위치 필요한가?

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        while (location==null){
            if (locationManager.isProviderEnabled("gps")) {
                location = locationManager.getLastKnownLocation("gps");
                Log.e("TAG", "GPS"+location);
            }else if( locationManager.isProviderEnabled("network")){
                location = locationManager.getLastKnownLocation("network");
                Log.e("TAG", "네트워크"+location);
            }
        }


        if(location==null){
            Toast.makeText(getActivity(), "위치정보가 없습니다", Toast.LENGTH_SHORT).show();
        }else{
            //위도, 경도 얻어오기
            double latitude= location.getLatitude();
            double longitude= location.getLongitude();

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
            mapView.setZoomLevel(4, true);
            mapView.zoomIn(true);

            marker.setItemName("현재 위치");
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
            marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
            marker.setShowDisclosureButtonOnCalloutBalloon(false);
            mapView.selectPOIItem(marker, true);
            mapView.addPOIItem(marker);


        }
    }

    public void autoLocation(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if(locationManager.isProviderEnabled("gps")){
            locationManager.requestLocationUpdates("gps", 5000, 2, locationListener);
        }else if(locationManager.isProviderEnabled("network")){
            locationManager.requestLocationUpdates("network", 5000, 2, locationListener);
        }
    }

    LocationListener locationListener= new LocationListener(){
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public MapFragment() {
    }

    @Override
    public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView, int i, String s) {
        //api key인증 성공시 발동되는 메소드

    }

    @Override
    public void onLoadMapView() {
        //맵이 불러와질때 발동되는 메소드
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        //트래킹 모드가 ON일 때 사용자의 현재 위치가 업데이트 될 때 불려지는 메소드
        double latitude= location.getLatitude();
        double longitude= location.getLongitude();

        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //마커를 클릭했을때 불려지는 메소드
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

}

 */