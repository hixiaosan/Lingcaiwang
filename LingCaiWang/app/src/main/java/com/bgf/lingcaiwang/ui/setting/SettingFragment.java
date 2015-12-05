package com.bgf.lingcaiwang.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.ui.BannerFragment;

public class SettingFragment extends BannerFragment { // Views

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_news:
                    startActivity(new Intent(getActivity(), NewsActivity.class));
                    break;
                case R.id.ll_billboard:
                    startActivity(new Intent(getActivity(), BillBoardActivity.class));
                    break;
            }
        }
    };
    private View mainView;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBannerTitle("设置");
        mainView = inflater.inflate(R.layout.setting_fragment, container, false);
        mainView.findViewById(R.id.ll_news).setOnClickListener(onClick);
        mainView.findViewById(R.id.ll_billboard).setOnClickListener(onClick);
        return mainView;
    }
}
