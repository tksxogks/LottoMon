package kr.co.htssoft.lottomon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.htssoft.lottomon.CalculatorActivity;
import kr.co.htssoft.lottomon.R;
import kr.co.htssoft.lottomon.SearchActivity;
import kr.co.htssoft.lottomon.SearchItem;

import static kr.co.htssoft.lottomon.RodingActivity.searchItems;

public class MenuFragment extends Fragment {

    TextView searchMenu;
    TextView analysisMenu;
    TextView createMenu;
    TextView calculatorMenu;
    TextView infoMenu;

    TextView drwNoRound;
    TextView drwNoDate;
    ImageView drwNo1;
    ImageView drwNo2;
    ImageView drwNo3;
    ImageView drwNo4;
    ImageView drwNo5;
    ImageView drwNo6;
    ImageView drwBNo;

    private SearchItem searchItem = searchItems.get(searchItems.size()-1);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        drwNoRound = view.findViewById(R.id.menu_drwNo);
        drwNoDate = view.findViewById(R.id.menu_drwDate);
        drwNo1 = view.findViewById(R.id.menu_drwNo1);
        drwNo2 = view.findViewById(R.id.menu_drwNo2);
        drwNo3 = view.findViewById(R.id.menu_drwNo3);
        drwNo4 = view.findViewById(R.id.menu_drwNo4);
        drwNo5 = view.findViewById(R.id.menu_drwNo5);
        drwNo6 = view.findViewById(R.id.menu_drwNo6);
        drwBNo = view.findViewById(R.id.menu_drwBNo);

        searchMenu = view.findViewById(R.id.searchMenu);
        analysisMenu = view.findViewById(R.id.analysisMenu);
        createMenu = view.findViewById(R.id.createMenu);
        calculatorMenu = view.findViewById(R.id.calculatorMenu);
        infoMenu = view.findViewById(R.id.infoMenu);

        lottoInfoLoad();

        calculatorMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CalculatorActivity.class);
                startActivity(intent);
            }
        });

        searchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void lottoInfoLoad(){
        drwNoRound.setText(searchItem.getDrwNo());
        String s = searchItem.getDrwNoDate();
        String[] ss = s.split(" ");
        drwNoDate.setText("("+ss[0].replaceAll("[^0-9]", "")+"-"+ss[1].replaceAll("[^0-9]", "")+"-"+ss[2].replaceAll("[^0-9]", "")+")");
        drwNo1.setImageResource(R.drawable.ball01 + searchItem.getDrwNo1() - 1);
        drwNo2.setImageResource(R.drawable.ball01 + searchItem.getDrwNo2() - 1);
        drwNo3.setImageResource(R.drawable.ball01 + searchItem.getDrwNo3() - 1);
        drwNo4.setImageResource(R.drawable.ball01 + searchItem.getDrwNo4() - 1);
        drwNo5.setImageResource(R.drawable.ball01 + searchItem.getDrwNo5() - 1);
        drwNo6.setImageResource(R.drawable.ball01 + searchItem.getDrwNo6() - 1);
        drwBNo.setImageResource(R.drawable.ball01 + searchItem.getDrwBNo() - 1);
    }

    public MenuFragment() {
    }
}
