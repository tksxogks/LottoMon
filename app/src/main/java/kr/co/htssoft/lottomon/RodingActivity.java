package kr.co.htssoft.lottomon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import kr.co.htssoft.lottomon.Fragment.HomeFragment;

public class RodingActivity extends Activity {

    public static int lotto_round;
    public static ArrayList<SearchItem> searchItems = new ArrayList<>();
    public static JSONArray jsonArray;

    private String drwNo;
    private String drwNoDate;
    private int drwNo1;
    private int drwNo2;
    private int drwNo3;
    private int drwNo4;
    private int drwNo5;
    private int drwNo6;
    private int drwBNo;
    private String automaticDrw;
    private String manualDrw;
    private String semiAutoMaticDrw;
    private String firstWinner; //당첨자 수searchItems
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

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roding);

        iv = findViewById(R.id.iv_Roding);
        Glide.with(this).load(iv).into(iv);

        lottoRound();
        lottoParser();
        lottoListMade();

        Handler hd = new Handler()  ;
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    public void permission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permission, 10);
            }
        }
    }

    //날짜로 로또 회차수 계산하는 메소드
    public void lottoRound(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, 11, 07, 20, 45, 0);
        long criterionTime = calendar.getTimeInMillis();
        long nowTime = System.currentTimeMillis();
        lotto_round = (int)((nowTime - criterionTime) / (1000 * 60 * 60 * 24 * 7)) + 1;
    }

    //로또 데이터 최신 회차 까지 얻어온후 json으로 저장 하는 메소드
    public void lottoParser(){

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("lottoDB.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            String data = buffer.toString();
            jsonArray = new JSONArray(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //로또 회차 리스트 만드는 메소드
    public void lottoListMade(){
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                drwNo = jsonObject.getString("drwNo");
                drwNoDate = "(" + jsonObject.getString("drwNoDate") + " 추첨)";

                drwNo1 = Integer.parseInt(jsonObject.getString("drwNo1"));
                drwNo2 = Integer.parseInt(jsonObject.getString("drwNo2"));
                drwNo3 = Integer.parseInt(jsonObject.getString("drwNo3"));
                drwNo4 = Integer.parseInt(jsonObject.getString("drwNo4"));
                drwNo5 = Integer.parseInt(jsonObject.getString("drwNo5"));
                drwNo6 = Integer.parseInt(jsonObject.getString("drwNo6"));
                drwBNo = Integer.parseInt(jsonObject.getString("drwBNo"));

                automaticDrw = jsonObject.getString("automaticDrw");
                manualDrw = jsonObject.getString("manualDrw");
                semiAutoMaticDrw = jsonObject.getString("semiAutoMaticDrw");

                firstTotalAmount = jsonObject.getString("firstTotalAmount");
                firstAmount = jsonObject.getString("firstAmount");
                firstWinner = jsonObject.getString("firstWinner");

                secondTotalAmount = jsonObject.getString("secondTotalAmount");
                secondAmount = jsonObject.getString("secondAmount");
                secondWinner = jsonObject.getString("secondWinner");

                thirdTotalAmount = jsonObject.getString("thirdTotalAmount");
                thirdAmount = jsonObject.getString("thirdAmount");
                thirdWinner = jsonObject.getString("thirdWinner");

                fourthTotalAmount = jsonObject.getString("fourthTotalAmount");
                fourthAmount = jsonObject.getString("fourthAmount");
                fourthWinner = jsonObject.getString("fourthWinner");

                fifthTotalAmount = jsonObject.getString("fifthTotalAmount");
                fifthAmount = jsonObject.getString("fifthAmount");
                fifthWinner = jsonObject.getString("fifthWinner");

                totalSellAmount = jsonObject.getString("totalSellAmount");
                searchItems.add(new SearchItem(drwNo, drwNoDate, drwNo1, drwNo2, drwNo3, drwNo4, drwNo5, drwNo6, drwBNo, automaticDrw, manualDrw, semiAutoMaticDrw, firstWinner, firstAmount, firstTotalAmount, secondWinner, secondAmount, secondTotalAmount, thirdWinner, thirdAmount, thirdTotalAmount, fourthWinner, fourthAmount, fourthTotalAmount, fifthWinner, fifthAmount, fifthTotalAmount, totalSellAmount));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
