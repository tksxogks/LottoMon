package kr.co.htssoft.lottomon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    ImageView iv_refresh;
    TextView tv_round;
    TextView tv_drtdate;
    ImageView iv_num1;
    ImageView iv_num2;
    ImageView iv_num3;
    ImageView iv_num4;
    ImageView iv_num5;
    ImageView iv_num6;
    ImageView iv_num7;
    TextView tv_winnerinfo;

    BottomNavigationView bnv;

    long lotto_round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.layout_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        iv_refresh = findViewById(R.id.iv_refresh);
        tv_round = findViewById(R.id.tv_round);
        tv_drtdate = findViewById(R.id.tv_drtdate);
        iv_num1 = findViewById(R.id.iv_num1);
        iv_num2 = findViewById(R.id.iv_num2);
        iv_num3 = findViewById(R.id.iv_num3);
        iv_num4 = findViewById(R.id.iv_num4);
        iv_num5 = findViewById(R.id.iv_num5);
        iv_num6 = findViewById(R.id.iv_num6);
        iv_num7 = findViewById(R.id.iv_num7);
        tv_winnerinfo = findViewById(R.id.tv_winnerinfo);

        //새로고침 버튼 리스너
        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_lotto();
            }
        });

        bnv = findViewById(R.id.bnv);
        //바텀 네비게이션뷰 리스너
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bnv_home:
                        break;
                    case R.id.bnv_saved:
                        break;
                    case R.id.bnv_profile:
                        break;
                    case R.id.bnv_setting:
                        break;
                }
                return true;
            }
        });

        //날짜로 로또 회차수 계산하여 lotto_round 변수에 집어넣기
        Calendar calendar = Calendar.getInstance();
        calendar.set(2002, 11, 07, 20, 45, 0);
        long criterionTime = calendar.getTimeInMillis();
        long nowTime = System.currentTimeMillis();
        lotto_round = ((nowTime - criterionTime) / (long) (1000 * 60 * 60 * 24 * 7)) + 1;

        main_lotto();
    }

    public void main_lotto(){
            new Thread(){
                @Override
                public void run() {
                    try {
                        final JSONObject result;
                        HttpsURLConnection connection = (HttpsURLConnection) new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo="+lotto_round).openConnection();
                        connection.setUseCaches(false);
                        InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        String str = "";
                        str = br.readLine();
                        result = new JSONObject(str);

                        if(result.getString("returnValue").equals("success")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        tv_round.setText(result.getString("drwNo")+"회차");
                                        tv_drtdate.setText(result.getString("drwNoDate")+" 추첨");
                                        iv_num1.setImageResource(R.drawable.ball01+result.getInt("drwtNo1")-1);
                                        iv_num2.setImageResource(R.drawable.ball01+result.getInt("drwtNo2")-1);
                                        iv_num3.setImageResource(R.drawable.ball01+result.getInt("drwtNo3")-1);
                                        iv_num4.setImageResource(R.drawable.ball01+result.getInt("drwtNo4")-1);
                                        iv_num5.setImageResource(R.drawable.ball01+result.getInt("drwtNo5")-1);
                                        iv_num6.setImageResource(R.drawable.ball01+result.getInt("drwtNo6")-1);
                                        iv_num7.setImageResource(R.drawable.ball01+result.getInt("bnusNo")-1);

                                        long value = Long.parseLong(result.getString("firstWinamnt"));
                                        DecimalFormat format = new DecimalFormat("####,####");
                                        String s = format.format(value);
                                        String[] mnt = s.split(",");

                                        tv_winnerinfo.setText("1등 : "+mnt[0]+"억 "+Integer.parseInt(mnt[1])+"만 "+Integer.parseInt(mnt[2])+"원 / 총 "+result.getInt("firstPrzwnerCo")+"명");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else if(result.getString("returnValue").equals("fail")){
                            connection = (HttpsURLConnection) new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=" + (lotto_round - 1)).openConnection();
                            isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                            br = new BufferedReader(isr);
                            str = "";
                            str = br.readLine();
                            JSONObject result_fail = new JSONObject(str);
                            try {
                                tv_round.setText(result.getString("drwNo")+"회차");
                                tv_drtdate.setText(result.getString("drwNoDate")+" 추첨");
                                iv_num1.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo1")-1);
                                iv_num2.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo2")-1);
                                iv_num3.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo3")-1);
                                iv_num4.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo4")-1);
                                iv_num5.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo5")-1);
                                iv_num6.setImageResource(R.drawable.ball01+result_fail.getInt("drwtNo6")-1);
                                iv_num7.setImageResource(R.drawable.ball01+result_fail.getInt("bnusNo")-1);
                                tv_winnerinfo.setText("1등 : "+(NumberFormat.getCurrencyInstance(Locale.KOREA).format(result_fail.getInt("firstWinamnt")))+" / 총 "+result_fail.getInt("firstPrzwnerCo")+"명");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void clickSearch(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
}
