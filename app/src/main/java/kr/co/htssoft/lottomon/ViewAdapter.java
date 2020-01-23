package kr.co.htssoft.lottomon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import kr.co.htssoft.lottomon.Fragment.HomeFragment;
import kr.co.htssoft.lottomon.Fragment.MapFragment;
import kr.co.htssoft.lottomon.Fragment.MenuFragment;
import kr.co.htssoft.lottomon.Fragment.SettingFragment;

public class ViewAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public ViewAdapter(@NonNull FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new MenuFragment();
            case 2:
                return new MapFragment();
            case 3:
                return new SettingFragment();
                default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }




    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
}
