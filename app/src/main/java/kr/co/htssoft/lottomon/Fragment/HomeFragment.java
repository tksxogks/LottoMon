package kr.co.htssoft.lottomon.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

import kr.co.htssoft.lottomon.R;
import kr.co.htssoft.lottomon.RodingActivity;
import kr.co.htssoft.lottomon.WriteActivity;

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
    Button qrBtn;

    Activity activity;

    Button writeBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_round = view.findViewById(R.id.tv_round);
        tv_drtdate = view.findViewById(R.id.tv_drtdate);
        iv_num1 = view.findViewById(R.id.iv_num1);
        iv_num2 = view.findViewById(R.id.iv_num2);
        iv_num3 = view.findViewById(R.id.iv_num3);
        iv_num4 = view.findViewById(R.id.iv_num4);
        iv_num5 = view.findViewById(R.id.iv_num5);
        iv_num6 = view.findViewById(R.id.iv_num6);
        iv_num7 = view.findViewById(R.id.iv_num7);
        qrBtn = view.findViewById(R.id.qrBtn);
        writeBtn = view.findViewById(R.id.writeBtn);

        main_lotto();

        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
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
        new Thread() {
            @Override
            public void run() {
                try {
                    final JSONObject result;
                    HttpsURLConnection connection = (HttpsURLConnection) new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=" + RodingActivity.lotto_round).openConnection();
                    connection.setUseCaches(false);
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String str = "";
                    str = br.readLine();
                    result = new JSONObject(str);


                    if (result.getString("returnValue").equals("success")) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv_round.setText(result.getString("drwNo") + "회차");
                                    tv_drtdate.setText(result.getString("drwNoDate") + " 추첨");
                                    iv_num1.setImageResource(R.drawable.ball01 + result.getInt("drwtNo1") - 1);
                                    iv_num2.setImageResource(R.drawable.ball01 + result.getInt("drwtNo2") - 1);
                                    iv_num3.setImageResource(R.drawable.ball01 + result.getInt("drwtNo3") - 1);
                                    iv_num4.setImageResource(R.drawable.ball01 + result.getInt("drwtNo4") - 1);
                                    iv_num5.setImageResource(R.drawable.ball01 + result.getInt("drwtNo5") - 1);
                                    iv_num6.setImageResource(R.drawable.ball01 + result.getInt("drwtNo6") - 1);
                                    iv_num7.setImageResource(R.drawable.ball01 + result.getInt("bnusNo") - 1);

                                    long value = Long.parseLong(result.getString("firstWinamnt"));
                                    DecimalFormat format = new DecimalFormat("####,####");
                                    String s = format.format(value);
                                    String[] mnt = s.split(",");

                                    //tv_winnerinfo.setText("1등 : "+mnt[0]+"억 "+Integer.parseInt(mnt[1])+"만 "+Integer.parseInt(mnt[2])+"원 / 총 "+result.getInt("firstPrzwnerCo")+"명");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else if (result.getString("returnValue").equals("fail")) {
                        connection = (HttpsURLConnection) new URL("https://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo=" + (RodingActivity.lotto_round - 1)).openConnection();
                        isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                        br = new BufferedReader(isr);
                        str = "";
                        str = br.readLine();
                        JSONObject result_fail = new JSONObject(str);
                        try {
                            tv_round.setText(result.getString("drwNo") + "회차");
                            tv_drtdate.setText("("+result.getString("drwNoDate") + " 추첨)");
                            iv_num1.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo1") - 1);
                            iv_num2.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo2") - 1);
                            iv_num3.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo3") - 1);
                            iv_num4.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo4") - 1);
                            iv_num5.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo5") - 1);
                            iv_num6.setImageResource(R.drawable.ball01 + result_fail.getInt("drwtNo6") - 1);
                            iv_num7.setImageResource(R.drawable.ball01 + result_fail.getInt("bnusNo") - 1);
                            // tv_winnerinfo.setText("1등 : "+(NumberFormat.getCurrencyInstance(Locale.KOREA).format(result_fail.getInt("firstWinamnt")))+" / 총 "+result_fail.getInt("firstPrzwnerCo")+"명");
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            activity = (Activity) context;
        }
    }

}