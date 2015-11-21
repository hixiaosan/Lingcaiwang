package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {
	
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews() {
		
		ImageButton mIbBack = (ImageButton) findViewById(R.id.header_back_ibtn);
		mIbBack.setVisibility(View.GONE);

	}
}
