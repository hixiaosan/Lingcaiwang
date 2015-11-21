package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 带返回和刷新按钮的activity
 * 
 * @author Darren
 *
 */
@SuppressWarnings("unused")
public class BackActivity extends BaseActivity {

	public static final String TAG = BackActivity.class.getSimpleName();

	private ImageButton mIbBack;
	protected ImageButton mBtnImg1;
	protected ImageButton mBtnImg2;
	protected Button mBtnPink;
	protected Button mBtnRightText;

	private ProgressBar mProgress;
	public TextView mTvText;
	private ImageView mIvPopArrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setContentView(View view) {
		setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setContentView(View view, LayoutParams params) {
		View layout = View.inflate(this, R.layout.activity_back, null);
		mIbBack = (ImageButton) layout.findViewById(R.id.header_back_ibtn);
		mBtnImg1 = (ImageButton) layout.findViewById(R.id.header_img_btn1);
		mBtnImg2 = (ImageButton) layout.findViewById(R.id.header_img_btn2);
		mProgress = (ProgressBar) layout.findViewById(R.id.header_progress);
		mBtnPink = (Button) layout.findViewById(R.id.banner_right_big_btn);
		mBtnRightText = (Button) layout.findViewById(R.id.banner_right_text_btn);

		mTvText = (TextView) layout.findViewById(R.id.header_title);
		mIvPopArrow = (ImageView) layout.findViewById(R.id.header_pop_arrow);
		FrameLayout container = (FrameLayout) layout.findViewById(R.id.back_content);
		if (view != null) {
			container.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else if (getMergedLayoutResId() != 0) {
			LayoutInflater.from(this).inflate(getMergedLayoutResId(), container, true);
		}
		super.setContentView(layout, params);
	}

	/**
	 * 允许使用以merge作为根节点的布局，在这个方法里返回。并让setContentView参数为null
	 * 
	 * @return
	 */
	protected int getMergedLayoutResId() {
		return 0;
	}

	/**
	 * 顶部标题是否有点击气泡
	 * 
	 * @param isHas
	 */
	public void hasPop(boolean isHas) {
		if (isHas) {
			mIvPopArrow.setVisibility(View.VISIBLE);
		} else {
			mIvPopArrow.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 显示进度图标
	 */
	public void showProgress() {
		if (mProgress != null) {
			mProgress.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏进度图标
	 */
	public void hideProgress() {
		if (mProgress != null) {
			mProgress.setVisibility(View.GONE);
		}
	}

	public void setTitle(int strId) {
		setTitle(getString(strId));
	}

	public void setTitle(String title) {
		if (mTvText != null) {
			mTvText.setText(title);
		}
	}

	/**
	 * 返回键点击
	 * 
	 * @param v
	 */
	public void onBackClick(View v) {
		finish();
	}
}
