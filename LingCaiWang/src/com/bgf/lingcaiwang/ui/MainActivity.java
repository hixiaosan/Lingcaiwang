package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.utils.FragmentHelper;
import com.bgf.lingcaiwang.utils.Logger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity implements OnChildCallbackListener {

	private static final String TAG = "MainActivity";

	private FragmentHelper mFmHelper;

	// Views
	/**
	 * 当前所在的button
	 */
	private View currentButton;
	private ImageButton mIbHome;
	private ImageButton mIbInvest;
	private ImageButton mIbLeverage;
	private ImageButton mIbAccount;
	private ImageButton mIbSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews() {

		mFmHelper = new FragmentHelper(this, getSupportFragmentManager(), R.id.main_fragment);

		mIbHome = (ImageButton) findViewById(R.id.ibtn_home);
		mIbInvest = (ImageButton) findViewById(R.id.ibtn_invest);
		mIbLeverage = (ImageButton) findViewById(R.id.ibtn_leverage);
		mIbAccount = (ImageButton) findViewById(R.id.ibtn_account);
		mIbSettings = (ImageButton) findViewById(R.id.ibtn_settings);

		mIbInvest.setOnClickListener(onTabClickListener);
		mIbHome.setOnClickListener(onTabClickListener);
		mIbLeverage.setOnClickListener(onTabClickListener);
		mIbAccount.setOnClickListener(onTabClickListener);
		mIbSettings.setOnClickListener(onTabClickListener);
		mIbHome.performClick();
	}

	private boolean checkLogin() {
		return true;
	}

	/**
	 * @author Robot 更新tab按钮状态
	 */
	private void setButton(View v) {
		if (currentButton != null && currentButton.getId() != v.getId()) {
			currentButton.setEnabled(true);
		}
		v.setEnabled(false);
		currentButton = v;
	}

	@Override
	public void switchTo(String id, Intent intent) {
		if (id != null && intent != null) {
			mFmHelper.switchFragment(id, intent);
			Logger.d(TAG, "switched to fragment:" + id);
		}
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProblemButtonClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRequestLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		// 显示退出提示框
		new AlertDialog.Builder(this).setTitle("提示").setMessage("是否确定退出?")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	OnClickListener onTabClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			String fragmentId = null;
			Intent intent = null;
			switch (v.getId()) {

			case R.id.ibtn_home:
				fragmentId = "HomeFragment";
				intent = new Intent(MainActivity.this, HomeFragment.class);
				setButton(mIbHome);
				break;

			case R.id.ibtn_invest:
				fragmentId = "InvestFragment";
				intent = new Intent(MainActivity.this, InvestFragment.class);
				setButton(mIbInvest);
				break;

			case R.id.ibtn_leverage:
				if (checkLogin()) {
					fragmentId = "LeverageFragment";
					intent = new Intent(MainActivity.this, LeverageFragment.class);
					setButton(mIbLeverage);
				}
				break;

			case R.id.ibtn_account:
				if (checkLogin()) {
					fragmentId = "AccountFragment";
					intent = new Intent(MainActivity.this, AccountFragment.class);
					setButton(mIbAccount);
				}
				break;

			case R.id.ibtn_settings:
				fragmentId = "SettingFragment";
				intent = new Intent(MainActivity.this, SettingFragment.class);
				setButton(mIbSettings);
				break;
			}

			if (fragmentId != null && intent != null) {
				switchTo(fragmentId, intent);
			}

		}

	};
}
