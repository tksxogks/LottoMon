package kr.co.htssoft.lottomon;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.LinearGradient;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RodingActivity extends Activity {

    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 10;

    File path;
    File file;

    public static int lotto_round;
    public static ArrayList<SearchItem> searchItems = new ArrayList<>();
    public JSONArray jsonArray;

    public static ArrayList<MapItem> gangwon[] = new ArrayList[17]; //강원
    public static ArrayList<MapItem> gyeonggi[] = new ArrayList[42]; //경기
    public static ArrayList<MapItem> gyeongnam[] = new ArrayList[22]; //경남
    public static ArrayList<MapItem> gyeongbuk[] = new ArrayList[24]; //경북
    public static ArrayList<MapItem> gwangju[] = new ArrayList[5]; //광주
    public static ArrayList<MapItem> daegu[] = new ArrayList[8]; //대구
    public static ArrayList<MapItem> daejeon[] = new ArrayList[5]; //대전
    public static ArrayList<MapItem> busan[] = new ArrayList[16]; //부산
    public static ArrayList<MapItem> seoul[] = new ArrayList[25]; //서울
    public static ArrayList<MapItem> sejong = new ArrayList<>(); //세종
    public static ArrayList<MapItem> ulsan[] = new ArrayList[5]; //울산
    public static ArrayList<MapItem> incheon[] = new ArrayList[10]; //인천
    public static ArrayList<MapItem> jeonnam[] = new ArrayList[22]; //전남
    public static ArrayList<MapItem> jeonbuk[] = new ArrayList[15]; //전북
    public static ArrayList<MapItem> jeju[] = new ArrayList[2]; //제주
    public static ArrayList<MapItem> chungnam[] = new ArrayList[16]; //충남
    public static ArrayList<MapItem> chungbuk[] = new ArrayList[14]; //충북

    private String drwNo;
    private String drwNoDate;
    private String drwNo1;
    private String drwNo2;
    private String drwNo3;
    private String drwNo4;
    private String drwNo5;
    private String drwNo6;
    private String drwBNo;
    private String automaticDrw;
    private String manualDrw;
    private String semiAutoMaticDrw;
    private String firstWinner; //당첨자 수
    private String firstAmount;   //1명당 당첨 금액
    private String firstTotalAmount; //당첨 총 금액
    private String secondWinner;
    private String secondAmount;
    private String secondTotalAmount;
    private String thirdWinner;
    private String thirdAmount;
    private String thirdTotalAmount;
    private String fourthWinner;
    private String fourthAmount;
    private String fourthTotalAmount;
    private String fifthWinner;
    private String fifthAmount;
    private String fifthTotalAmount;
    private String totalSellAmount;

    String storeName;
    String phoneNumber;
    String address;
    Double lat;
    Double lon;

    Thread netParser;

    ArrayList<String[]> gunsi = new ArrayList<>();
    String[] dosi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roding);

        path = getExternalFilesDirs("data")[0];
        file = new File(path, "lottoDB.json");

        permissionsCheck();

    }


    public void permissionsCheck() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!hasPermissions(RodingActivity.this, permissions)) {
                    ActivityCompat.requestPermissions(RodingActivity.this, permissions, PERMISSION_ALL);
                }
                // 권한이 허용되어있다면 다음 화면 진행
                else {
                    Log.e("TAG", "퍼미션허용완료");
                    lottoRound();
                    jsonCopy();
                    lottoParser();
                    try {
                        netParser.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mapParser();
                }
            }
        }, 3000);
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                },
                1);
    }

    public void mapParser(){
        try {
            dosi = getAssets().list("map/");
            for (int i = 0; i < dosi.length; i++) {
                String[] g = getAssets().list("map/" + dosi[i] + "/");
                for(int n=0 ; n<g.length ; n++){
                    g[n] = g[n];
                }
                gunsi.add(g);
            }
            for (int i = 0; i < dosi.length; i++) {
                //dosi - 17개
                for (int j = 0; j < gunsi.get(i).length; j++) {
                    //gunsi.get(i) = 군or시
                    if (dosi[i].equals("세종")) {
                    InputStream is = getAssets().open("map/" + dosi[i] + "/" + dosi[i] + ".json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String json = new String(buffer, "UTF-8");
                    jsonArray = new JSONArray(json);

                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                        storeName = jsonObject.getString("storeName");
                        phoneNumber = jsonObject.getString("phoneNumber");
                        address = jsonObject.getString("address");
                        lat = jsonObject.getDouble("latitude");
                        lon = jsonObject.getDouble("latitude");
                        sejong.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                    }
                        Log.e("TAG", gunsi.get(i)[j]+"완료 "+sejong.size()+"개");
                }else{
                        InputStream is = getAssets().open("map/" + dosi[i] + "/" + gunsi.get(i)[j] + "/" + gunsi.get(i)[j] + ".json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    String json = new String(buffer, "UTF-8");
                    jsonArray = new JSONArray(json);

                        switch (dosi[i]) {
                            case "강원":
                                ArrayList<MapItem> mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gangwon[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "경기":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gyeonggi[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "경남":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gyeongnam[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "경북":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gyeongbuk[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "광주":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                gwangju[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "대구":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                daegu[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "대전":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                daejeon[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "부산":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                busan[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "서울":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                seoul[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "울산":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                ulsan[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "인천":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                incheon[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "전남":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                jeonnam[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "전북":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                jeonbuk[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "제주":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                jeju[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "충남":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                chungnam[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                            case "충북":
                                mapItems = new ArrayList<>();
                                for (int l = 0; l < jsonArray.length(); l++) {
                                    try {
                                        JSONObject jsonObject = jsonArray.getJSONObject(l);
                                        storeName = jsonObject.getString("storeName");
                                        phoneNumber = jsonObject.getString("phoneNumber");
                                        address = jsonObject.getString("address");
                                        lat = jsonObject.getDouble("latitude");
                                        lon = jsonObject.getDouble("latitude");
                                        mapItems.add(new MapItem(storeName, phoneNumber, address, lat, lon));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                chungbuk[j] = mapItems;
                                Log.e("TAG", gunsi.get(i)[j]+"완료 "+mapItems.size()+"개");
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(RodingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    public void mapAssetsParse(String dosi){
        try {
            InputStream is = getAssets().open("map/"+dosi+"/"+dosi+".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                storeName = jsonObject.getString("storeName");
                phoneNumber = jsonObject.getString("phoneNumber");
                address = jsonObject.getString("address");
                lat = jsonObject.getDouble("latitude");
                lon = jsonObject.getDouble("latitude");
                sejong.add(new MapItem(storeName, phoneNumber, address, lat, lon));
            }
            Log.e("TAG", "map파싱완료 : "+dosi+"/"+dosi+jsonArray.length());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //날짜로 로또 회차수 계산하는 메소드
    public void lottoRound() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, 11, 07, 20, 45, 0);
        long criterionTime = calendar.getTimeInMillis();
        long nowTime = System.currentTimeMillis();
        lotto_round = (int) ((nowTime - criterionTime) / (1000 * 60 * 60 * 24 * 7)) + 1;
        Log.e("Tag", "라운드로딩완료");
    }

    public void jsonCopy() {
        if (!file.exists()) {
            try {
                InputStream is = getAssets().open("db/lottoDB.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                writer.write(json);
                writer.flush();
                writer.close();
                Log.e("TAG", "파일복사완료");
            } catch (IOException e) {
                Log.e("jsonCopy", "IOException : "+e);
                e.printStackTrace();
            }
        }
    }

    public void lottoParser() {

        netParser = new Thread(){
            @Override
            public void run() {
                try {
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }
                    final String data = buffer.toString();
                    jsonArray = new JSONArray(data);
                    int jsonRound = jsonArray.length();
                    Log.e("TAG", "저장된jsonArray 길이 :"+jsonRound );
                    if (lotto_round > jsonRound) {
                        int j = lotto_round - jsonRound;
                        for (int i = 0; i < j; i++) {
                            Document doc;
                            doc = Jsoup.connect("https://www.dhlottery.co.kr/gameResult.do?method=byWin&drwNo=" + (jsonRound + i + 1)).timeout(10 * 1000).get();
                            Elements contents = doc.select("div.win_result");

                            drwNo = contents.select("strong").get(0).text();

                            String s = contents.select("p.desc").text();
                            drwNoDate = s.substring(1, s.length() - 4);

                            contents = doc.select("div.num");
                            s = contents.select("p").text();
                            String[] ss = s.split(" ");
                            drwNo1 = ss[0];
                            drwNo2 = ss[1];
                            drwNo3 = ss[2];
                            drwNo4 = ss[3];
                            drwNo5 = ss[4];
                            drwNo6 = ss[5];
                            drwBNo = ss[6];

                            contents = doc.select("td.tar");
                            DecimalFormat format = new DecimalFormat("####,####");
                            Long ls = Long.parseLong(contents.get(0).text().replaceAll("[^0-9]", ""));
                            String[] mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    firstTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    firstTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    firstTotalAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    firstTotalAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                firstTotalAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(1).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    firstAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    firstAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    firstAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    firstAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                firstAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(2).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    secondTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    secondTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    secondTotalAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    secondTotalAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                secondTotalAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(3).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    secondAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    secondAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    secondAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    secondAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                secondAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(4).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    thirdTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    thirdTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    thirdTotalAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    thirdTotalAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                thirdTotalAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(5).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    thirdAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    thirdAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    thirdAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    thirdAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                thirdAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(6).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    fourthTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    fourthTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    fourthTotalAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    fourthTotalAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                fourthTotalAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(7).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    fourthAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    fourthAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    fourthAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    fourthAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                fourthAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(8).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    fifthTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    fifthTotalAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    fifthTotalAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    fifthTotalAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                fifthTotalAmount = Integer.parseInt(mnt[0]) + "원";
                            ls = Long.parseLong(contents.get(9).text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3) {
                                if (Integer.parseInt(mnt[2]) == 0)
                                    fifthAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만원";
                                else
                                    fifthAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            } else if (mnt.length == 2) {
                                if (Integer.parseInt(mnt[1]) == 0)
                                    fifthAmount = Integer.parseInt(mnt[0]) + "만원";
                                else
                                    fifthAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            } else if (mnt.length == 1)
                                fifthAmount = Integer.parseInt(mnt[0]) + "원";

                            contents = doc.select("td");
                            firstWinner = contents.eq(2).text() + "명";
                            secondWinner = contents.eq(8).text() + "명";
                            thirdWinner = contents.eq(13).text() + "명";
                            fourthWinner = contents.eq(18).text() + "명";
                            fifthWinner = contents.eq(23).text() + "명";

                            s = contents.eq(5).text();
                            ss = s.split(" ");
                            switch (ss.length) {
                                case 2:
                                    if (ss[1].replaceAll("[0-9]", "").equals("자동"))
                                        automaticDrw = ss[1].replaceAll("[^0-9]", "");
                                    else if (ss[1].replaceAll("[0-9]", "").equals("수동"))
                                        manualDrw = ss[1].replaceAll("[^0-9]", "");
                                    else if (ss[1].replaceAll("[0-9]", "").equals("반자동"))
                                        semiAutoMaticDrw = ss[1].replaceAll("[^0-9]", "");
                                    break;
                                case 3:
                                    if (ss[2].replaceAll("[0-9]", "").equals("반자동")) {
                                        if (ss[1].replaceAll("[0-9]", "").equals("자동")) {
                                            automaticDrw = ss[1].replaceAll("[^0-9]", "");
                                            semiAutoMaticDrw = ss[2].replaceAll("[^0-9]", "");
                                        } else if (ss[1].replaceAll("[0-9]", "").equals("수동")) {
                                            manualDrw = ss[1].replaceAll("[^0-9]", "");
                                            semiAutoMaticDrw = ss[2].replaceAll("[^0-9]", "");
                                        }
                                    } else {
                                        automaticDrw = ss[1].replaceAll("[^0-9]", "");
                                        manualDrw = ss[2].replaceAll("[^0-9]", "");
                                    }
                                    break;
                                case 4:
                                    automaticDrw = ss[1].replaceAll("[^0-9]", "");
                                    manualDrw = ss[2].replaceAll("[^0-9]", "");
                                    semiAutoMaticDrw = ss[3].replaceAll("[^0-9]", "");
                                    break;
                            }
                            contents = doc.select(".list_text_common");
                            ls = Long.parseLong(contents.select("strong").text().replaceAll("[^0-9]", ""));
                            mnt = format.format(ls).split(",");
                            if (mnt.length >= 3)
                                totalSellAmount = Integer.parseInt(mnt[0]) + "억 " + Integer.parseInt(mnt[1]) + "만 " + Integer.parseInt(mnt[2]) + "원";
                            else if (mnt.length == 2)
                                totalSellAmount = Integer.parseInt(mnt[0]) + "만 " + Integer.parseInt(mnt[1]) + "원";
                            else if (mnt.length == 1)
                                totalSellAmount = Integer.parseInt(mnt[0]) + "원";

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("drwNo", drwNo);
                            jsonObject.put("drwNoDate", drwNoDate);
                            jsonObject.put("drwNo1", drwNo1);
                            jsonObject.put("drwNo2", drwNo2);
                            jsonObject.put("drwNo3", drwNo3);
                            jsonObject.put("drwNo4", drwNo4);
                            jsonObject.put("drwNo5", drwNo5);
                            jsonObject.put("drwNo6", drwNo6);
                            jsonObject.put("drwBNo", drwBNo);
                            if (automaticDrw == null) {
                                automaticDrw = "";
                            } else if (manualDrw == null) {
                                manualDrw = "";
                            } else if (semiAutoMaticDrw == null) {
                                semiAutoMaticDrw = "";
                            }
                            jsonObject.put("automaticDrw", automaticDrw);
                            jsonObject.put("manualDrw", manualDrw);
                            jsonObject.put("semiAutoMaticDrw", semiAutoMaticDrw);
                            jsonObject.put("firstWinner", firstWinner);
                            jsonObject.put("firstAmount", firstAmount);
                            jsonObject.put("firstTotalAmount", firstTotalAmount);
                            jsonObject.put("secondWinner", secondWinner);
                            jsonObject.put("secondAmount", secondAmount);
                            jsonObject.put("secondTotalAmount", secondTotalAmount);
                            jsonObject.put("thirdWinner", thirdWinner);
                            jsonObject.put("thirdAmount", thirdAmount);
                            jsonObject.put("thirdTotalAmount", thirdTotalAmount);
                            jsonObject.put("fourthWinner", fourthWinner);
                            jsonObject.put("fourthAmount", fourthAmount);
                            jsonObject.put("fourthTotalAmount", fourthTotalAmount);
                            jsonObject.put("fifthWinner", fifthWinner);
                            jsonObject.put("fifthAmount", fifthAmount);
                            jsonObject.put("fifthTotalAmount", fifthTotalAmount);
                            jsonObject.put("totalSellAmount", totalSellAmount);

                            jsonArray.put(jsonObject);

                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                            writer.write(jsonArray.toString());
                            writer.flush();
                            writer.close();
                        }
                    }
                    lottoListAdd();
                } catch (FileNotFoundException e) {
                    Log.e("TAG", "FileNotFoundException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("TAG", "IOException");
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        netParser.start();
    }

    public void lottoListAdd() {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            String data = buffer.toString();
            jsonArray = new JSONArray(data);
            Log.e("jsonArray길이값", jsonArray.length() + "");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                drwNo = jsonObject.getString("drwNo");
                drwNoDate = "(" + jsonObject.getString("drwNoDate") + " 추첨)";
                int getdrwNo1 = Integer.parseInt(jsonObject.getString("drwNo1"));
                int getdrwNo2 = Integer.parseInt(jsonObject.getString("drwNo2"));
                int getdrwNo3 = Integer.parseInt(jsonObject.getString("drwNo3"));
                int getdrwNo4 = Integer.parseInt(jsonObject.getString("drwNo4"));
                int getdrwNo5 = Integer.parseInt(jsonObject.getString("drwNo5"));
                int getdrwNo6 = Integer.parseInt(jsonObject.getString("drwNo6"));
                int getdrwBNo = Integer.parseInt(jsonObject.getString("drwBNo"));
                automaticDrw = jsonObject.getString("automaticDrw");
                manualDrw = jsonObject.getString("manualDrw");
                semiAutoMaticDrw = jsonObject.getString("semiAutoMaticDrw");
                firstWinner = jsonObject.getString("firstWinner");
                firstAmount = jsonObject.getString("firstAmount");
                firstTotalAmount = jsonObject.getString("firstTotalAmount");
                secondWinner = jsonObject.getString("secondWinner");
                secondAmount = jsonObject.getString("secondAmount");
                secondTotalAmount = jsonObject.getString("secondTotalAmount");
                thirdWinner = jsonObject.getString("thirdWinner");
                thirdAmount = jsonObject.getString("thirdAmount");
                thirdTotalAmount = jsonObject.getString("thirdTotalAmount");
                fourthWinner = jsonObject.getString("fourthWinner");
                fourthAmount = jsonObject.getString("fourthAmount");
                fourthTotalAmount = jsonObject.getString("fourthTotalAmount");
                fifthWinner = jsonObject.getString("fifthWinner");
                fifthAmount = jsonObject.getString("fifthAmount");
                fifthTotalAmount = jsonObject.getString("fifthTotalAmount");
                totalSellAmount = jsonObject.getString("totalSellAmount");
                Log.e("TAG", drwNo + " " + drwNoDate + " " + getdrwNo1 + " "+firstWinner +" "+ firstTotalAmount+" "+firstAmount);
                searchItems.add(new SearchItem(drwNo, drwNoDate, getdrwNo1, getdrwNo2, getdrwNo3, getdrwNo4, getdrwNo5, getdrwNo6, getdrwBNo, automaticDrw, manualDrw, semiAutoMaticDrw, firstWinner, firstAmount, firstTotalAmount, secondWinner, secondAmount, secondTotalAmount, thirdWinner, thirdAmount, thirdTotalAmount, fourthWinner, fourthAmount, fourthTotalAmount, fifthWinner, fifthAmount, fifthTotalAmount, totalSellAmount));
                Log.e("TAG", searchItems.get(searchItems.size()-1).getFirstTotalAmount()+"");
            }
        } catch (FileNotFoundException e) {
            Log.e("TAG", "FileNOTFOUND");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("TAG", "JSONException");
            Log.e("TAG", "JSONExceptio : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("TAG", "IOException");
            e.printStackTrace();
        }
        Log.e("add완료", "add완료" + searchItems.size());
    }

    private void getHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo("패키지이름", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA"); md.update(signature.toByteArray());
                Log.d("TAG","key_hash="+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (Build.VERSION.SDK_INT >= 23) {
            // 퍼미션이 승인되면
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) permissionsCheck();
                // 퍼미션이 승인 거부되면
            else {
                Log.d("TAG", "Permission denied");
                finish();
            }

        }

    }
}


