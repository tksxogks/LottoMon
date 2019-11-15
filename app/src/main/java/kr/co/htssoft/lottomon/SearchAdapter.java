package kr.co.htssoft.lottomon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter implements SectionTitleProvider {

    ArrayList<SearchItem> searchItems;
    Context context;

    public SearchAdapter(ArrayList<SearchItem> searchItems, Context context) {
        this.searchItems = searchItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.search_itme, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder vh = (ViewHolder) holder;

        SearchItem searchItem = searchItems.get(position);

        vh.card_tv_drwNo.setText(searchItem.getDrwNo());
        vh.card_tv_drwNoDate.setText(searchItem.getDrwNoDate());
        vh.card_iv_drwNo1.setImageResource(R.drawable.ball01+searchItem.getDrwNo1()-1);
        vh.card_iv_drwNo2.setImageResource(R.drawable.ball01+searchItem.getDrwNo2()-1);
        vh.card_iv_drwNo3.setImageResource(R.drawable.ball01+searchItem.getDrwNo3()-1);
        vh.card_iv_drwNo4.setImageResource(R.drawable.ball01+searchItem.getDrwNo4()-1);
        vh.card_iv_drwNo5.setImageResource(R.drawable.ball01+searchItem.getDrwNo5()-1);
        vh.card_iv_drwNo6.setImageResource(R.drawable.ball01+searchItem.getDrwNo6()-1);
        vh.card_iv_drwBNo.setImageResource(R.drawable.ball01+searchItem.getDrwBNo()-1);
        vh.card_tv_firstAmount.setText(searchItem.getFirstAmount());
        vh.card_tv_firstWinner.setText(searchItem.getFirstWinner());
        vh.card_tv_secondAmount.setText(searchItem.getSecondAmount());
        vh.card_tv_secondWinner.setText(searchItem.getSecondWinner());
        vh.card_tv_thirdAmount.setText(searchItem.getThirdAmount());
        vh.card_tv_thirdWinner.setText(searchItem.getThirdWinner());
        vh.card_tv_fourthAmount.setText(searchItem.getFourthAmount());
        vh.card_tv_fourthWinner.setText(searchItem.getFourthWinner());
        vh.card_tv_fifthAmount.setText(searchItem.getFifthAmount());
        vh.card_tv_fifthWinner.setText(searchItem.getFifthWinner());

    }



    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    @Override
    public String getSectionTitle(int position) {
        SearchItem searchItem = searchItems.get(position);
        return searchItem.getDrwNo();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView card_tv_drwNo;
        TextView card_tv_drwNoDate;
        ImageView card_iv_drwNo1;
        ImageView card_iv_drwNo2;
        ImageView card_iv_drwNo3;
        ImageView card_iv_drwNo4;
        ImageView card_iv_drwNo5;
        ImageView card_iv_drwNo6;
        ImageView card_iv_drwBNo;
        TextView card_tv_firstAmount;
        TextView card_tv_firstWinner;
        TextView card_tv_secondAmount;
        TextView card_tv_secondWinner;
        TextView card_tv_thirdAmount;
        TextView card_tv_thirdWinner;
        TextView card_tv_fourthAmount;
        TextView card_tv_fourthWinner;
        TextView card_tv_fifthAmount;
        TextView card_tv_fifthWinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card_tv_drwNo = itemView.findViewById(R.id.card_tv_drwNo);
            card_tv_drwNoDate = itemView.findViewById(R.id.card_tv_drwNoDate);
            card_iv_drwNo1 = itemView.findViewById(R.id.card_iv_drwNo1);
            card_iv_drwNo2 = itemView.findViewById(R.id.card_iv_drwNo2);
            card_iv_drwNo3 = itemView.findViewById(R.id.card_iv_drwNo3);
            card_iv_drwNo4 = itemView.findViewById(R.id.card_iv_drwNo4);
            card_iv_drwNo5 = itemView.findViewById(R.id.card_iv_drwNo5);
            card_iv_drwNo6 = itemView.findViewById(R.id.card_iv_drwNo6);
            card_iv_drwBNo = itemView.findViewById(R.id. card_iv_drwBNo);
            card_tv_firstAmount = itemView.findViewById(R.id.card_tv_firstAmount);
            card_tv_firstWinner = itemView.findViewById(R.id.card_tv_firstWinner);
            card_tv_secondAmount = itemView.findViewById(R.id.card_tv_secondAmount);
            card_tv_secondWinner = itemView.findViewById(R.id.card_tv_secondWinner);
            card_tv_thirdAmount = itemView.findViewById(R. id.card_tv_thirdAmount);
            card_tv_thirdWinner = itemView.findViewById(R.id.card_tv_thirdWinner);
            card_tv_fourthAmount = itemView.findViewById(R.id.card_tv_fourthAmount);
            card_tv_fourthWinner = itemView.findViewById(R.id.card_tv_fourthWinner);
            card_tv_fifthAmount = itemView.findViewById(R.id.card_tv_fifthAmount);
            card_tv_fifthWinner = itemView.findViewById(R.id.card_tv_fifthWinner);
        }
    }
}
