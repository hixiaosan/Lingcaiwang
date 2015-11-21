package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends BannerFragment { // Views
	private View mainView;

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setBannerTitle("设置");
		mainView = inflater.inflate(R.layout.setting_fragment, container, false);
		return mainView;
	}

}
