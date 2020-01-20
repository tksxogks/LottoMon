package kr.co.htssoft.lottomon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import kr.co.htssoft.lottomon.Fragment.HomeFragment;
import kr.co.htssoft.lottomon.Fragment.MapFragment;
import kr.co.htssoft.lottomon.Fragment.MenuFragment;
import kr.co.htssoft.lottomon.Fragment.SettingFragment;


public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    ViewAdapter adapter;

    BottomNavigationView bnv;

    HomeFragment homeFragment;
    MenuFragment menuFragment;
    MapFragment mapFragment;
    SettingFragment settingFragment;

    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        bnv = findViewById(R.id.bnv);
        //바텀 네비게이션뷰 리스너
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bnv_home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.bnv_menu:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.bnv_map:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.bnv_setting:
                        pager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //스크롤될때
            }

            @Override
            public void onPageSelected(int position) {
                //페이지가 선택될때
                if(menuItem !=null){
                    menuItem.setChecked(false);
                }else{
                    bnv.getMenu().getItem(0).setChecked(false);
                }
                bnv.getMenu().getItem(position).setChecked(true);
                menuItem = bnv.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //스크롤이변경될때
            }
        });
        setupViewPager(pager);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new ViewAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        mapFragment = new MapFragment();
        settingFragment = new SettingFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(menuFragment);
        adapter.addFragment(mapFragment);
        adapter.addFragment(settingFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(this, "잘못된 QR코드 입니다.", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                try {
                    Toast.makeText(this, "스캔완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents().toString()));
                    startActivityForResult(intent, 0);
                } catch (Exception e){
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

}
