package kr.co.htssoft.lottomon.Fragment;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import kr.co.htssoft.lottomon.R;

public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);
    }

    public SettingFragment() {
    }
}
