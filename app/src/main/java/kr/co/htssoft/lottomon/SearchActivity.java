package kr.co.htssoft.lottomon;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;

public class SearchActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    FastScroller fastScroller;
    SearchAdapter adapter;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        fastScroller = findViewById(R.id.fastScroller);
        adapter = new SearchAdapter(RodingActivity.searchItems,this);
        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        adapter.notifyItemInserted(0);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //터치한 뷰 가져오기
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && gestureDetector.onTouchEvent(e)){
                    int currentPosition = rv.getChildAdapterPosition(childView);
                    SearchItem getSearchItem = RodingActivity.searchItems.get(currentPosition);
                    Toast.makeText(SearchActivity.this, getSearchItem.getDrwNo(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

}
