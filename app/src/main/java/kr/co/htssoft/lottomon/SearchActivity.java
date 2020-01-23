package kr.co.htssoft.lottomon;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import static kr.co.htssoft.lottomon.RodingActivity.searchItems;

public class SearchActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    RecyclerView recyclerView;
    FastScroller fastScroller;
    SearchAdapter adapter;

    private GestureDetector gestureDetector;

    private TextView drwNo;
    private TextView drwNoDate;
    private ImageView drwNo1;
    private ImageView drwNo2;
    private ImageView drwNo3;
    private ImageView drwNo4;
    private ImageView drwNo5;
    private ImageView drwNo6;
    private ImageView drwBNo;
    private TextView auto;
    private TextView firstWinner; //당첨자 수
    private TextView firstAmount;   //1명당 당첨 금액
    private TextView firstTotalAmount; //당첨 총 금액
    private TextView secondWinner;
    private TextView secondAmount;
    private TextView secondTotalAmount;
    private TextView thirdWinner;
    private TextView thirdAmount;
    private TextView thirdTotalAmount;
    private TextView fourthWinner;
    private TextView fourthAmount;
    private TextView fourthTotalAmount;
    private TextView fifthWinner;
    private TextView fifthAmount;
    private TextView fifthTotalAmount;
    private TextView totalSellAmount;
    private TextView oddEven;
    private TextView sum;
    private TextView lastNum;
    private TextView sameLastNum;
    private TextView lastNumSum;
    private TextView numSize;
    private TextView twinNum;
    private ImageView infoDrwNo1;
    private ImageView infoDrwNo2;
    private ImageView infoDrwNo3;
    private ImageView infoDrwNo4;
    private ImageView infoDrwNo5;
    private ImageView infoDrwNo6;
    private ImageView infoDrwBNo;
    private TextView textInfoDrwNo1;
    private TextView textInfoDrwNo2;
    private TextView textInfoDrwNo3;
    private TextView textInfoDrwNo4;
    private TextView textInfoDrwNo5;
    private TextView textInfoDrwNo6;
    private TextView textInfoDrwBNo;

    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        backBtn = findViewById(R.id.search_backBtn);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        fastScroller = findViewById(R.id.fastScroller);
        adapter = new SearchAdapter(RodingActivity.searchItems,this);
        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View vv = inflater.inflate(R.layout.dialog_home, null);

                    drwNo = vv.findViewById(R.id.dlg_tv_drwNo);
                    drwNoDate = vv.findViewById(R.id.dlg_tv_drwDate);
                    drwNo1 = vv.findViewById(R.id.dlg_iv_drwNo1);
                    drwNo2 = vv.findViewById(R.id.dlg_iv_drwNo2);
                    drwNo3 = vv.findViewById(R.id.dlg_iv_drwNo3);
                    drwNo4 = vv.findViewById(R.id.dlg_iv_drwNo4);
                    drwNo5 = vv.findViewById(R.id.dlg_iv_drwNo5);
                    drwNo6 = vv.findViewById(R.id.dlg_iv_drwNo6);
                    drwBNo = vv.findViewById(R.id.dlg_iv_drwBNo);
                    auto = vv.findViewById(R.id.dlg_tv_auto);
                    firstWinner = vv.findViewById(R.id.dlg_tv_firstWinner);
                    firstAmount = vv.findViewById(R.id.dlg_tv_firstAmount);
                    firstTotalAmount = vv.findViewById(R.id.dlg_tv_firstTotalAmount);
                    secondWinner = vv.findViewById(R.id.dlg_tv_secondWinner);
                    secondAmount = vv.findViewById(R.id.dlg_tv_secondAmount);
                    secondTotalAmount = vv.findViewById(R.id.dlg_tv_secondTotalAmount);
                    thirdWinner = vv.findViewById(R.id.dlg_tv_thirdWinner);
                    thirdAmount = vv.findViewById(R.id.dlg_tv_thirdAmount);
                    thirdTotalAmount = vv.findViewById(R.id.dlg_tv_thirdTotalAmount);
                    fourthWinner = vv.findViewById(R.id.dlg_tv_fourthWinner);
                    fourthAmount = vv.findViewById(R.id.dlg_tv_fourthAmount);
                    fourthTotalAmount = vv.findViewById(R.id.dlg_tv_fourthTotalAmount);
                    fifthWinner = vv.findViewById(R.id.dlg_tv_fifthWinner);
                    fifthAmount = vv.findViewById(R.id.dlg_tv_fifthAmount);
                    fifthTotalAmount = vv.findViewById(R.id.dlg_tv_fifthTotalAmount);
                    totalSellAmount = vv.findViewById(R.id.dlg_tv_totalSellAmount);
                    oddEven = vv.findViewById(R.id.dlg_tv_oddEven);
                    sum = vv.findViewById(R.id.dlg_tv_sum);
                    lastNum = vv.findViewById(R.id.dlg_tv_lastNum);
                    sameLastNum = vv.findViewById(R.id.dlg_tv_sameLastNum);
                    lastNumSum = vv.findViewById(R.id.dlg_tv_lastNumSum);
                    numSize = vv.findViewById(R.id.dlg_tv_numSize);
                    twinNum = vv.findViewById(R.id.dlg_tv_twinNum);
                    infoDrwNo1 = vv.findViewById(R.id.dlg_iv_infoDrwNo1);
                    infoDrwNo2 = vv.findViewById(R.id.dlg_iv_infoDrwNo2);
                    infoDrwNo3 = vv.findViewById(R.id.dlg_iv_infoDrwNo3);
                    infoDrwNo4 = vv.findViewById(R.id.dlg_iv_infoDrwNo4);
                    infoDrwNo5 = vv.findViewById(R.id.dlg_iv_infoDrwNo5);
                    infoDrwNo6 = vv.findViewById(R.id.dlg_iv_infoDrwNo6);
                    infoDrwBNo = vv.findViewById(R.id.dlg_iv_infoDrwBNo);
                    textInfoDrwNo1 = vv.findViewById(R.id.dlg_tv_infoDrwNo1);
                    textInfoDrwNo2 = vv.findViewById(R.id.dlg_tv_infoDrwNo2);
                    textInfoDrwNo3 = vv.findViewById(R.id.dlg_tv_infoDrwNo3);
                    textInfoDrwNo4 = vv.findViewById(R.id.dlg_tv_infoDrwNo4);
                    textInfoDrwNo5 = vv.findViewById(R.id.dlg_tv_infoDrwNo5);
                    textInfoDrwNo6 = vv.findViewById(R.id.dlg_tv_infoDrwNo6);
                    textInfoDrwBNo = vv.findViewById(R.id.dlg_tv_infoDrwBNo);

                    Log.e("TAG", searchItems.get(searchItems.size()-1).toString()+"");

                    drwNo.setText(getSearchItem.getDrwNo());

                    String s = getSearchItem.getDrwNoDate();
                    String[] ss = s.split(" ");
                    drwNoDate.setText("("+ss[0].replaceAll("[^0-9]", "")+"-"+ss[1].replaceAll("[^0-9]", "")+"-"+ss[2].replaceAll("[^0-9]", "")+")");

                    int[] drw = new int[7];
                    drw[0] = getSearchItem.getDrwNo1();
                    drw[1] = getSearchItem.getDrwNo2();
                    drw[2] = getSearchItem.getDrwNo3();
                    drw[3] = getSearchItem.getDrwNo4();
                    drw[4] = getSearchItem.getDrwNo5();
                    drw[5] = getSearchItem.getDrwNo6();
                    drw[6] = getSearchItem.getDrwBNo();

                    drwNo1.setImageResource(R.drawable.ball01 + drw[0] - 1);
                    drwNo2.setImageResource(R.drawable.ball01 + drw[1] - 1);
                    drwNo3.setImageResource(R.drawable.ball01 + drw[2] - 1);
                    drwNo4.setImageResource(R.drawable.ball01 + drw[3] - 1);
                    drwNo5.setImageResource(R.drawable.ball01 + drw[4] - 1);
                    drwNo6.setImageResource(R.drawable.ball01 + drw[5] - 1);
                    drwBNo.setImageResource(R.drawable.ball01 + drw[6] - 1);

                    totalSellAmount.setText("총판매금액 : "+getSearchItem.getTotalSellAmount());

                    firstWinner.setText(getSearchItem.getFirstWinner());
                    firstAmount.setText(getSearchItem.getFirstAmount());
                    firstTotalAmount.setText(getSearchItem.getFirstTotalAmount());
                    if(getSearchItem.getAutomaticDrw().equals("") && getSearchItem.getManualDrw().equals("") && getSearchItem.getSemiAutoMaticDrw().equals("")){
                        auto.setVisibility(View.GONE);
                    }else {
                        auto.setText("(");
                        if(!getSearchItem.getAutomaticDrw().equals("")) auto.append("자동"+getSearchItem.getAutomaticDrw());
                        if(!getSearchItem.getManualDrw().equals("")) auto.append("수동"+getSearchItem.getManualDrw());
                        if(!getSearchItem.getSemiAutoMaticDrw().equals("")) auto.append("반자동"+getSearchItem.getSemiAutoMaticDrw());
                        auto.append(")");
                    }

                    secondWinner.setText(getSearchItem.getSecondWinner());
                    secondTotalAmount.setText(getSearchItem.getSecondTotalAmount());
                    secondAmount.setText(getSearchItem.getSecondAmount());

                    thirdWinner.setText(getSearchItem.getThirdWinner());
                    thirdTotalAmount.setText(getSearchItem.getThirdTotalAmount());
                    thirdAmount.setText(getSearchItem.getThirdAmount());

                    fourthWinner.setText(getSearchItem.getFourthWinner());
                    fourthTotalAmount.setText(getSearchItem.getFourthTotalAmount());
                    fourthAmount.setText(getSearchItem.getFourthAmount());

                    fifthWinner.setText(getSearchItem.getFifthWinner());
                    fifthTotalAmount.setText(getSearchItem.getFifthTotalAmount());
                    fifthAmount.setText(getSearchItem.getFifthAmount());

                    int odd =0;
                    int even =0;

                    if(drw[0]%2==0) even++;
                    else odd++;
                    if(drw[1]%2==0) even++;
                    else odd++;
                    if(drw[2]==0) even++;
                    else odd++;
                    if(drw[3]%2==0) even++;
                    else odd++;
                    if(drw[4]%2==0) even++;
                    else odd++;
                    if(drw[5]%2==0) even++;
                    else odd++;

                    oddEven.setText("홀짝 : ");
                    if(!(odd==0)) oddEven.append("홀"+odd);
                    if(!(even==0)) oddEven.append("짝"+even);

                    int a = drw[0]+drw[1]+drw[2]+drw[3]+drw[4]+drw[5];

                    sum.setText("수의 합 : "+a);

                    int[] drwLast = new int[6];
                    int num=0;
                    drwLast[0] = getSearchItem.getDrwNo1()%10;
                    drwLast[1] = getSearchItem.getDrwNo2()%10;
                    drwLast[2] = getSearchItem.getDrwNo3()%10;
                    drwLast[3] = getSearchItem.getDrwNo4()%10;
                    drwLast[4] = getSearchItem.getDrwNo5()%10;
                    drwLast[5] = getSearchItem.getDrwNo6()%10;
                    int[] drwTen = new int[6];
                    drwTen[0] = getSearchItem.getDrwNo1()/10;
                    drwTen[1] = getSearchItem.getDrwNo2()/10;
                    drwTen[2] = getSearchItem.getDrwNo3()/10;
                    drwTen[3] = getSearchItem.getDrwNo4()/10;
                    drwTen[4] = getSearchItem.getDrwNo5()/10;
                    drwTen[5] = getSearchItem.getDrwNo6()/10;

                    lastNum.setText("끝수 : "+(drwLast[0]+"-"+drwLast[1]+"-"+drwLast[2]+"-"+drwLast[3]+"-"+drwLast[4]+"-"+drwLast[5]));

                    sameLastNum.setText("동끝수 : ");

                    for(int i=0 ; i<drwLast.length ; i++){
                        for(int j=0 ; j<i ; j++){
                            if(drwLast[i]==drwLast[j]){
                                if(num>0){
                                    sameLastNum.append(", "+drwLast[i]);
                                    Log.e("TAG", "동끝수추가 : "+drwLast[i]+"  "+i);
                                }else{
                                    sameLastNum.append(drwLast[i]+"");
                                    Log.e("TAG", "동끝수추가여기 : "+drwLast[i]+"  "+i);
                                    num++;
                                }
                            }
                        }
                    }

                    int b = drwLast[0]+drwLast[1]+drwLast[2]+drwLast[3]+drwLast[4]+drwLast[5];

                    lastNumSum.setText("끝수 합 : "+b);

                    int high=0;
                    int low=0;
                    for(int i=0 ; i<6 ; i++){
                        if(drw[i]>22){
                            high++;
                        }else if(drw[i]<=22){
                            low++;
                        }
                    }
                    numSize.setText("고저 : ");
                    if(!(high==0)) numSize.append("고"+high);
                    if(!(low==0)) numSize.append("저"+low);

                    twinNum.setText("쌍수 : ");
                    for(int i=0 ; i<6 ; i++){
                        if(drw[i]<10){
                        }
                        else if(drwTen[i]==drwLast[i]){
                            twinNum.append(drw[i]+"");
                        }
                    }

                    infoDrwNo1.setImageResource(R.drawable.ball01 + drw[0] - 1);
                    infoDrwNo2.setImageResource(R.drawable.ball01 + drw[1] - 1);
                    infoDrwNo3.setImageResource(R.drawable.ball01 + drw[2] - 1);
                    infoDrwNo4.setImageResource(R.drawable.ball01 + drw[3] - 1);
                    infoDrwNo5.setImageResource(R.drawable.ball01 + drw[4] - 1);
                    infoDrwNo6.setImageResource(R.drawable.ball01 + drw[5] - 1);
                    infoDrwBNo.setImageResource(R.drawable.ball01 + drw[6] - 1);

                    builder.setView(vv);
                    AlertDialog dialog =builder.create();
                    dialog.show();
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
