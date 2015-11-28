package com.bgf.lingcaiwang.ui.setting;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.bean.News;
import com.bgf.lingcaiwang.data.SettingServiceCenter;
import com.bgf.lingcaiwang.ui.BannerFragment;
import com.bgf.lingcaiwang.utils.Logger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SettingFragment extends BannerFragment { // Views

    private View mainView;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBannerTitle("设置");
        mainView = inflater.inflate(R.layout.setting_fragment, container, false);
        mainView.findViewById(R.id.ll_news).setOnClickListener(onClick);
        return mainView;
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_news:
                    new GetNews().execute();
                    break;

            }
        }
    };

    class GetNews extends AsyncTask<Void, Void, List<News>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<News> result) {
            super.onPostExecute(result);
        }

        @Override
        protected List<News> doInBackground(Void... params) {
            try {
                return SettingServiceCenter.getNews("");
            } catch (Exception e) {
                Logger.d("SettingFragment", e.toString());
            }
            return null;
        }
    }

}
