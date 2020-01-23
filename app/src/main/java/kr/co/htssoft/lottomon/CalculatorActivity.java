package kr.co.htssoft.lottomon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.parser.Parser;

import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    TextView prizeMoney;
    TextView incomeTax;
    TextView localTax;
    TextView resultPrizeMoney;
    TextView easyPrizeMoney;
    TextView easyResultPrizeMoney;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        backBtn = findViewById(R.id.calculator_backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void clickBtn(View view) {
        prizeMoney = findViewById(R.id.prizeMoney);
        incomeTax = findViewById(R.id.incomeTax);
        localTax = findViewById(R.id.localTax);
        resultPrizeMoney = findViewById(R.id.resultPrizeMoney);
        easyPrizeMoney = findViewById(R.id.easyPrizeMoney);
        easyResultPrizeMoney = findViewById(R.id.easyResultPrizeMoney);
        switch (view.getId()){
            case R.id.one:
                if(prizeMoney.getText().toString().equals("0")){
                    prizeMoney.setText("1");
                }else prizeMoney.append("1");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.two:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("2");
                else prizeMoney.append("2");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.three:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("3");
                else prizeMoney.append("3");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.four:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("4");
                else prizeMoney.append("4");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.five:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("5");
                else prizeMoney.append("5");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.six:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("6");
                else prizeMoney.append("6");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.seven:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("7");
                else prizeMoney.append("7");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.eight:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("8");
                else prizeMoney.append("8");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.nine:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("9");
                else prizeMoney.append("9");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.zero:
                if(prizeMoney.getText().toString().equals("0")) prizeMoney.setText("0");
                else prizeMoney.append("0");
                if(prizeMoney.length() > 3){
                    long value = Long.parseLong(prizeMoney.getText().toString().replace(",", ""));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }
                break;
            case R.id.allClear:
                prizeMoney.setText("0");
                break;
            case R.id.back:
                String s = prizeMoney.getText().toString().replaceAll("[^0-9]", "");
                if (s.length()==1){
                    prizeMoney.setText("0");
                }else if(!s.isEmpty() && !s.equals("0")){
                    long value = Long.parseLong(s.substring(0, s.length()-1));
                    DecimalFormat format = new DecimalFormat("###,###");
                    prizeMoney.setText(format.format(value));
                }else prizeMoney.setText("0");
                break;
        }

        long value = Long.parseLong(prizeMoney.getText().toString().replaceAll("[^0-9]", ""));
        DecimalFormat easyFormat = new DecimalFormat("####,####");
        String s = easyFormat.format(value);
        String[] mnt = s.split(",");
        if(mnt.length>=3) {
            if(Integer.parseInt(mnt[1])==0 && Integer.parseInt(mnt[2])==0) easyPrizeMoney.setText("( "+Integer.parseInt( mnt[0])+"억원 )");
            else if(Integer.parseInt(mnt[1])==0) easyPrizeMoney.setText("( "+mnt[0]+"억 "+Integer.parseInt(mnt[2])+"원 )");
            else if(Integer.parseInt(mnt[2])==0) easyPrizeMoney.setText("( "+mnt[0]+"억 "+Integer.parseInt(mnt[1])+"만원 )");
            else easyPrizeMoney.setText("( "+(mnt[0])+"억 "+Integer.parseInt(mnt[1])+"만 "+Integer.parseInt(mnt[2])+"원 )");
        }
        else if(mnt.length==2) {
            if(Integer.parseInt(mnt[1])==0) easyPrizeMoney.setText("( "+mnt[0]+"만원 )");
            else easyPrizeMoney.setText("( "+Integer.parseInt(mnt[0])+"만 "+Integer.parseInt(mnt[1])+"원 )");
        }
        else if(mnt.length==1) easyPrizeMoney.setText("( "+Integer.parseInt(mnt[0])+"원 )");

        //기타소득세
        if(!prizeMoney.equals(0)){
            Long income = Long.parseLong(prizeMoney.getText().toString().replaceAll("[^0-9]", "")) - 1000;
            if(income >= 300000000){ //3억 이상
                income = income-300000000;
                income = (long)(income*0.3);
                DecimalFormat format = new DecimalFormat("###,###");
                incomeTax.setText("-"+ format.format(income+60000000));
            }else if(income <= 50000){ //5만원 이하
                incomeTax.setText("0");
            }else{ //3억 이하
                income = (long)(income*0.2);
                DecimalFormat format = new DecimalFormat("###,###");
                incomeTax.setText("-"+ format.format(income));
            }
        }

        //지방세
        if(!prizeMoney.equals(0)){
            Long local = Long.parseLong(prizeMoney.getText().toString().replaceAll("[^0-9]", "")) - 1000;
            if(local >= 300000000){ //3억 이상
                local = local-300000000;
                local = (long)(local*0.03);
                DecimalFormat format = new DecimalFormat("###,###");
                localTax.setText("-"+ format.format(local+6000000));
            }else if(local <= 50000){ //5만원 이하
                localTax.setText("0");
            }else{ //3억 이하
                local = (long)(local*0.02);
                DecimalFormat format = new DecimalFormat("###,###");
                localTax.setText("-"+ format.format(local));
            }
        }

        //세후당첨금
        long p = Long.parseLong(prizeMoney.getText().toString().replaceAll("[^0-9]", ""));
        long i = Long.parseLong(incomeTax.getText().toString().replaceAll("[^0-9]", ""));
        long l = Long.parseLong(localTax.getText().toString().replaceAll("[^0-9]", ""));
        long r = p-i-l;
        DecimalFormat format = new DecimalFormat("###,###");
        resultPrizeMoney.setText(format.format(r));

        value = Long.parseLong(resultPrizeMoney.getText().toString().replaceAll("[^0-9]", ""));
        s = easyFormat.format(value);
        String[] mnt1 = s.split(",");
        if(mnt1.length>=3) {
            if(Integer.parseInt(mnt1[1])==0 && Integer.parseInt(mnt1[2])==0) easyResultPrizeMoney.setText("( "+Integer.parseInt(mnt1[0])+"억원 )");
            else if(Integer.parseInt(mnt1[1])==0) easyResultPrizeMoney.setText("( "+mnt1[0]+"억 "+Integer.parseInt(mnt1[2])+"원 )");
            else if(Integer.parseInt(mnt1[2])==0) easyResultPrizeMoney.setText("( "+mnt1[0]+"억 "+Integer.parseInt(mnt1[1])+"만원 )");
            else easyResultPrizeMoney.setText("( "+(mnt1[0])+"억 "+Integer.parseInt(mnt1[1])+"만 "+Integer.parseInt(mnt1[2])+"원 )");
        }
        else if(mnt1.length==2) {
            if(Integer.parseInt(mnt1[1])==0) easyResultPrizeMoney.setText("( "+mnt1[0]+"만원 )");
            else easyResultPrizeMoney.setText("( "+Integer.parseInt(mnt1[0])+"만 "+Integer.parseInt(mnt1[1])+"원 )");
        }
        else if(mnt1.length==1) easyResultPrizeMoney.setText("( "+Integer.parseInt(mnt1[0])+"원 )");
    }
}


