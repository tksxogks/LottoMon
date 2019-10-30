package kr.co.htssoft.lottomon;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchAdapter adapter;

    ArrayList<SearchItem> searchItems = new ArrayList<>();

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

    JSONArray jsonArray;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new SearchAdapter(searchItems, this);
        recyclerView.setAdapter(adapter);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

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

        searchItems.clear();
        lottoParser(0, 882);

    }

    void lottoParser(int start, int end) {
            for (int i = start; i < end; i++) {
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
                searchItems.add(new SearchItem(drwNo, drwNoDate, drwNo1, drwNo2, drwNo3, drwNo4, drwNo5, drwNo6, drwBNo, firstAmount, firstWinner, secondAmount, secondWinner, thirdAmount, thirdWinner, fourthAmount, fourthWinner, fifthAmount, fifthWinner));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyItemInserted(0);
            }

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                //터치한 뷰 가져오기
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Toast.makeText(SearchActivity.this, "터치됨!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

}

