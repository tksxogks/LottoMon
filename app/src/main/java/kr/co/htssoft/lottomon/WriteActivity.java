package kr.co.htssoft.lottomon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

public class WriteActivity extends AppCompatActivity {

    LinearLayout write_ll;
    ImageView write_iv_num1;
    ImageView write_iv_num2;
    ImageView write_iv_num3;
    ImageView write_iv_num4;
    ImageView write_iv_num5;
    ImageView write_iv_num6;
    ImageView write_iv_num7;
    TextView writeInfo;
    LinearLayout write_ll_7;
    LinearLayout write_ll_14;
    LinearLayout write_ll_21;
    LinearLayout write_ll_28;
    LinearLayout write_ll_35;
    LinearLayout write_ll_42;
    LinearLayout write_ll_45;
    ViewGroup view;
    Button btn;

    private ImageView backBtn;

    ArrayList<WriteNumberItem> numberItems = new ArrayList<>();
    ArrayList<Integer> num = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        write_ll = findViewById(R.id.write_ll);
        write_iv_num1 = findViewById(R.id.write_iv_num1);
        write_iv_num2 = findViewById(R.id.write_iv_num2);
        write_iv_num3 = findViewById(R.id.write_iv_num3);
        write_iv_num4 = findViewById(R.id.write_iv_num4);
        write_iv_num5 = findViewById(R.id.write_iv_num5);
        write_iv_num6 = findViewById(R.id.write_iv_num6);
        write_iv_num7 = findViewById(R.id.write_iv_num7);
        writeInfo = findViewById(R.id.writeInfo);
        write_ll_7 = findViewById(R.id.write_ll_7);
        write_ll_14 = findViewById(R.id.write_ll_14);
        write_ll_21 = findViewById(R.id.write_ll_21);
        write_ll_28 = findViewById(R.id.write_ll_28);
        write_ll_35 = findViewById(R.id.write_ll_35);
        write_ll_42 = findViewById(R.id.write_ll_42);
        write_ll_45 = findViewById(R.id.write_ll_45);
        view = findViewById(R.id.ll);
        backBtn = findViewById(R.id.write_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickNumBtn(View view) {

        if (view.isSelected()) {
            view.setSelected(false);
            for (int i = 0; i < num.size(); i++) {
                if (num.get(i) == Integer.parseInt(view.getTag().toString())) {
                    write_ll.findViewWithTag(num.get(i)+"I").setVisibility(View.GONE);
                    num.remove(i);
                    break;
                }
            }

        } else if (!view.isSelected()) {
            if (num.size() == 7) return;
            view.setSelected(true);
            num.add(Integer.parseInt(view.getTag().toString()));
            Collections.sort(num);
            for (int i = 0; i < num.size(); i++) {
                imageDrawer(i);
            }

        }

        return;
    }

    void imageDrawer(int size) {
        switch (size) {
            case 0:
                write_iv_num1.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num1.setVisibility(View.VISIBLE);
                write_iv_num1.setTag(num.get(size)+"I");
                break;
            case 1:
                write_iv_num2.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num2.setVisibility(View.VISIBLE);
                write_iv_num2.setTag(num.get(size)+"I");
                break;
            case 2:
                write_iv_num3.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num3.setVisibility(View.VISIBLE);
                write_iv_num3.setTag(num.get(size)+"I");
                break;
            case 3:
                write_iv_num4.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num4.setVisibility(View.VISIBLE);
                write_iv_num4.setTag(num.get(size)+"I");
                break;
            case 4:
                write_iv_num5.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num5.setVisibility(View.VISIBLE);
                write_iv_num5.setTag(num.get(size)+"I");
                break;
            case 5:
                write_iv_num6.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num6.setVisibility(View.VISIBLE);
                write_iv_num6.setTag(num.get(size)+"I");
                break;
            case 6:
                write_iv_num7.setImageResource(R.drawable.ball01 + num.get(size) - 1);
                write_iv_num7.setVisibility(View.VISIBLE);
                write_iv_num7.setTag(num.get(size)+"I");
                break;
        }
    }

    public void clickConfirmBtn(View view) {
       // Toast.makeText(this, numberItems.size() + "회의 당첨을 확인 중 입니다.", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, num.get(0)+"", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, write_ll_7.findViewWithTag(num.get(0))+"", Toast.LENGTH_SHORT).show();

    }

    public void clickInputBtn(View view) {
        if (num.size() < 7) {
            Toast.makeText(this, "7개의 번호를 입력하세요(현재 : " + num.size() + ")", Toast.LENGTH_SHORT).show();
            return;
        } else if (num.size() == 7) {
            numberItems.add(new WriteNumberItem(num.get(0), num.get(1), num.get(2), num.get(3), num.get(4), num.get(5), num.get(6)));
            writeInfo.append(numberItems.size() + "회 입력 완료" + num + "\n");

            for (int i = 0; i < num.size(); i++) maxcheck(i);
            write_iv_num1.setVisibility(View.GONE);
            write_iv_num2.setVisibility(View.GONE);
            write_iv_num3.setVisibility(View.GONE);
            write_iv_num4.setVisibility(View.GONE);
            write_iv_num5.setVisibility(View.GONE);
            write_iv_num6.setVisibility(View.GONE);
            write_iv_num7.setVisibility(View.GONE);
            num.clear();

        }
    }

    public void maxcheck(int i) {
        if (num.get(i) <= 7) write_ll_7.findViewWithTag(num.get(i).toString()).setSelected(false);
        else if (num.get(i) <= 14) write_ll_14.findViewWithTag(num.get(i).toString()).setSelected(false);
        else if (num.get(i) <= 21) write_ll_21.findViewWithTag(num.get(i).toString()).setSelected(false);
        else if (num.get(i) <= 28) write_ll_28.findViewWithTag(num.get(i).toString()).setSelected(false);
        else if (num.get(i) <= 35) write_ll_35.findViewWithTag(num.get(i).toString()).setSelected(false);
        else if (num.get(i) <= 42) write_ll_42.findViewWithTag(num.get(i).toString()).setSelected(false);
        else write_ll_45.findViewWithTag(num.get(i).toString()).setSelected(false);
    }
}

