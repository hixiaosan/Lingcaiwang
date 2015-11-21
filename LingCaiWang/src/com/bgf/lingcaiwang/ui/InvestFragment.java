package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InvestFragment extends BannerFragment {

	// Views
	private View mainView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setBannerTitle("投资");
		mainView = inflater.inflate(R.layout.invest_fragment, container, false);
		return mainView;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
