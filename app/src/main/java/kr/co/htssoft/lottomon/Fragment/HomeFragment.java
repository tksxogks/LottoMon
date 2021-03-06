package kr.co.htssoft.lottomon.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

import kr.co.htssoft.lottomon.R;
import kr.co.htssoft.lottomon.SearchItem;
import kr.co.htssoft.lottomon.WriteActivity;

import static kr.co.htssoft.lottomon.RodingActivity.gangwon;
import static kr.co.htssoft.lottomon.RodingActivity.searchItems;

public class HomeFragment extends Fragment {

    TextView tv_round;
    TextView tv_drtdate;
    ImageView iv_num1;
    ImageView iv_num2;
    ImageView iv_num3;
    ImageView iv_num4;
    ImageView iv_num5;
    ImageView iv_num6;
    ImageView iv_num7;
    ImageView qrBtn;
    ImageView writeBtn;
    Activity activity;
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

    private SearchItem searchItem = searchItems.get(searchItems.size()-1);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        qrBtn = view.findViewById(R.id.qrBtn);
        writeBtn = view.findViewById(R.id.writeBtn);

        tv_round = view.findViewById(R.id.tv_round);
        tv_drtdate = view.findViewById(R.id.tv_drtdate);
        iv_num1 = view.findViewById(R.id.iv_num1);
        iv_num2 = view.findViewById(R.id.iv_num2);
        iv_num3 = view.findViewById(R.id.iv_num3);
        iv_num4 = view.findViewById(R.id.iv_num4);
        iv_num5 = view.findViewById(R.id.iv_num5);
        iv_num6 = view.findViewById(R.id.iv_num6);
        iv_num7 = view.findViewById(R.id.iv_num7);
        totalSellAmount = view.findViewById(R.id.home_totalSellAmount);
        auto = view.findViewById(R.id.home_auto);
        firstWinner = view.findViewById(R.id.home_firstWinner);
        firstAmount = view.findViewById(R.id.home_firstAmount);
        firstTotalAmount = view.findViewById(R.id.home_firstTotalAmount);
        secondWinner = view.findViewById(R.id.home_secondWinner);
        secondAmount = view.findViewById(R.id.home_secondAmount);
        secondTotalAmount = view.findViewById(R.id.home_secondTotalAmount);
        thirdWinner = view.findViewById(R.id.home_thirdWinner);
        thirdAmount = view.findViewById(R.id.home_thirdAmount);
        thirdTotalAmount = view.findViewById(R.id.home_thirdTotalAmount);
        fourthWinner = view.findViewById(R.id.home_fourthWinner);
        fourthAmount = view.findViewById(R.id.home_fourthAmount);
        fourthTotalAmount = view.findViewById(R.id.home_fourthTotalAmount);
        fifthWinner = view.findViewById(R.id.home_fifthWinner);
        fifthAmount = view.findViewById(R.id.home_fifthAmount);
        fifthTotalAmount = view.findViewById(R.id.home_fifthTotalAmount);
        oddEven = view.findViewById(R.id.home_oddEven);
        sum = view.findViewById(R.id.home_sum);
        lastNum = view.findViewById(R.id.home_lastNum);
        sameLastNum = view.findViewById(R.id.home_sameLastNum);
        lastNumSum = view.findViewById(R.id.home_lastNumSum);
        numSize = view.findViewById(R.id.home_numSize);
        twinNum = view.findViewById(R.id.home_twinNum);
        infoDrwNo1 = view.findViewById(R.id.home_iv_infoDrwNo1);
        infoDrwNo2 = view.findViewById(R.id.home_iv_infoDrwNo2);
        infoDrwNo3 = view.findViewById(R.id.home_iv_infoDrwNo3);
        infoDrwNo4 = view.findViewById(R.id.home_iv_infoDrwNo4);
        infoDrwNo5 = view.findViewById(R.id.home_iv_infoDrwNo5);
        infoDrwNo6 = view.findViewById(R.id.home_iv_infoDrwNo6);
        infoDrwBNo = view.findViewById(R.id.home_iv_infoDrwBNo);
        textInfoDrwNo1 = view.findViewById(R.id.home_tv_infoDrwNo1);
        textInfoDrwNo2 = view.findViewById(R.id.home_tv_infoDrwNo2);
        textInfoDrwNo3 = view.findViewById(R.id.home_tv_infoDrwNo3);
        textInfoDrwNo4 = view.findViewById(R.id.home_tv_infoDrwNo4);
        textInfoDrwNo5 = view.findViewById(R.id.home_tv_infoDrwNo5);
        textInfoDrwNo6 = view.findViewById(R.id.home_tv_infoDrwNo6);
        textInfoDrwBNo = view.findViewById(R.id.home_tv_infoDrwBNo);

        Log.e("Tag", searchItem.getDrwNo());
        main_lotto();

        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setBeepEnabled(true);//바코드 인식시 소리
                intentIntegrator.initiateScan();
                Toast.makeText(getActivity(), "클릭", Toast.LENGTH_SHORT).show();
            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WriteActivity.class);
                startActivity(intent);

            }
        });

        return  view;
    }

    public void main_lotto() {

        tv_round.setText(searchItem.getDrwNo());
        tv_drtdate.setText(searchItem.getDrwNoDate());
        iv_num1.setImageResource(R.drawable.ball01 + searchItem.getDrwNo1() - 1);
        iv_num2.setImageResource(R.drawable.ball01 + searchItem.getDrwNo2() - 1);
        iv_num3.setImageResource(R.drawable.ball01 + searchItem.getDrwNo3() - 1);
        iv_num4.setImageResource(R.drawable.ball01 + searchItem.getDrwNo4() - 1);
        iv_num5.setImageResource(R.drawable.ball01 + searchItem.getDrwNo5() - 1);
        iv_num6.setImageResource(R.drawable.ball01 + searchItem.getDrwNo6() - 1);
        iv_num7.setImageResource(R.drawable.ball01 + searchItem.getDrwBNo() - 1);
        int[] drw = new int[7];
        drw[0] = searchItem.getDrwNo1();
        drw[1] = searchItem.getDrwNo2();
        drw[2] = searchItem.getDrwNo3();
        drw[3] = searchItem.getDrwNo4();
        drw[4] = searchItem.getDrwNo5();
        drw[5] = searchItem.getDrwNo6();
        drw[6] = searchItem.getDrwBNo();
        totalSellAmount.setText("총판매금액 : "+searchItem.getTotalSellAmount());
        firstWinner.setText(searchItem.getFirstWinner());
        firstAmount.setText(searchItem.getFirstAmount());
        firstTotalAmount.setText(searchItem.getFirstTotalAmount());
        if(searchItem.getAutomaticDrw().equals("") && searchItem.getManualDrw().equals("") && searchItem.getSemiAutoMaticDrw().equals("")){
            auto.setVisibility(View.GONE);
        }else {
            auto.setText("(");
            if(!searchItem.getAutomaticDrw().equals("")) auto.append("자동"+searchItem.getAutomaticDrw());
            if(!searchItem.getManualDrw().equals("")) auto.append("수동"+searchItem.getManualDrw());
            if(!searchItem.getSemiAutoMaticDrw().equals("")) auto.append("반자동"+searchItem.getSemiAutoMaticDrw());
            auto.append(")");
        }

        secondWinner.setText(searchItem.getSecondWinner());
        secondTotalAmount.setText(searchItem.getSecondTotalAmount());
        secondAmount.setText(searchItem.getSecondAmount());

        thirdWinner.setText(searchItem.getThirdWinner());
        thirdTotalAmount.setText(searchItem.getThirdTotalAmount());
        thirdAmount.setText(searchItem.getThirdAmount());

        fourthWinner.setText(searchItem.getFourthWinner());
        fourthTotalAmount.setText(searchItem.getFourthTotalAmount());
        fourthAmount.setText(searchItem.getFourthAmount());

        fifthWinner.setText(searchItem.getFifthWinner());
        fifthTotalAmount.setText(searchItem.getFifthTotalAmount());
        fifthAmount.setText(searchItem.getFifthAmount());

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
        drwLast[0] = searchItem.getDrwNo1()%10;
        drwLast[1] = searchItem.getDrwNo2()%10;
        drwLast[2] = searchItem.getDrwNo3()%10;
        drwLast[3] = searchItem.getDrwNo4()%10;
        drwLast[4] = searchItem.getDrwNo5()%10;
        drwLast[5] = searchItem.getDrwNo6()%10;
        int[] drwTen = new int[6];
        drwTen[0] = searchItem.getDrwNo1()/10;
        drwTen[1] = searchItem.getDrwNo2()/10;
        drwTen[2] = searchItem.getDrwNo3()/10;
        drwTen[3] = searchItem.getDrwNo4()/10;
        drwTen[4] = searchItem.getDrwNo5()/10;
        drwTen[5] = searchItem.getDrwNo6()/10;

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

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            activity = (Activity) context;
        }
    }

}