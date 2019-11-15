package kr.co.htssoft.lottomon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.htssoft.lottomon.CalculatorActivity;
import kr.co.htssoft.lottomon.R;
import kr.co.htssoft.lottomon.SearchActivity;

public class MenuFragment extends Fragment {

    TextView searchMenu;
    TextView analysisMenu;
    TextView createMenu;
    TextView calculatorMenu;
    TextView infoMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        searchMenu = view.findViewById(R.id.searchMenu);
        analysisMenu = view.findViewById(R.id.analysisMenu);
        createMenu = view.findViewById(R.id.createMenu);
        calculatorMenu = view.findViewById(R.id.calculatorMenu);
        infoMenu = view.findViewById(R.id.infoMenu);

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

    public MenuFragment() {
    }
}
