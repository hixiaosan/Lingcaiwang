package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.utils.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 带有头部banner的fragment
 * 
 * @author Darren
 *
 */

public class BannerFragment extends Fragment {

	public static final String TAG = "BannerFragment";

	public TextView mTvTitle;
	protected ImageButton mBtnImg1;
	protected ImageButton mBtnImg2;
	public ImageButton mBtnRight;
	private ProgressBar mProgress;
	protected TextView mTvRight;

	private View mSavedView = null;
	private boolean mSaveView = true;

	protected OnChildCallbackListener mListener = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnChildCallbackListener) activity;
		} catch (ClassCastException e) {
			Logger.e(TAG, "OnChildCallbackListener not implemented. Banner buttons will be disabled");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mSaveView && mSavedView != null) {
			ViewGroup parent = (ViewGroup) mSavedView.getParent();
			if (parent != null) {
				parent.removeView(mSavedView);
			}
			return mSavedView;
		}
		View view = onCreateContainerView(inflater, container);
		mTvTitle = (TextView) view.findViewById(R.id.header_title);
		mTvTitle.setOnClickListener(onTitleClick);
		mBtnImg1 = (ImageButton) view.findViewById(R.id.header_img_btn1);
		mBtnImg2 = (ImageButton) view.findViewById(R.id.header_img_btn2);
		mBtnRight = (ImageButton) view.findViewById(R.id.banner_right_big_btn);
		mProgress = (ProgressBar) view.findViewById(R.id.header_progress);

		mTvRight = (TextView) view.findViewById(R.id.banner_right_text);

		RelativeLayout contentContainer = (RelativeLayout) view.findViewById(R.id.banner_fragment_container);
		View contentView = onCreateContentView(inflater, contentContainer, savedInstanceState);
		if (contentView != null) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			lp.addRule(RelativeLayout.BELOW, R.id.banner_header);
			lp.topMargin = getResources().getDimensionPixelSize(R.dimen.banner_content_top_offset);
			contentContainer.addView(contentView, 0, lp);
		}
		if (mSaveView) {
			mSavedView = view;
		}
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * 创建总体视图
	 * 
	 * @param inflater
	 * @param container
	 * @return
	 */
	protected View onCreateContainerView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.banner_fragment, container, false);
		return view;
	}

	/**
	 * 创建内容视图
	 * 
	 * @return
	 */
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}

	/**
	 * 标题点击的动作（需设置{@link #setTitleClickable(boolean)}为true）
	 * 
	 * @param v
	 */
	protected void onTitleClick(View v) {
	}

	/**
	 * 菜单按钮点击
	 * 
	 * @param v
	 */
	protected void onProblemButtonClick(View v) {
		if (mListener != null) {
			mListener.onProblemButtonClick();
		}
	}

	/**
	 * 刷新Fragment内的数据
	 */
	public void refresh() {
	}

	/**
	 * 设置标题文字
	 * 
	 * @param strId
	 */
	public void setBannerTitle(int strId) {
		setBannerTitle(getString(strId));
	}

	/**
	 * 设置标题文字
	 * 
	 * @param title
	 */
	public void setBannerTitle(String title) {
		mTvTitle.setText(title);
	}

	/**
	 * 设置是否保存该Fragment的视图
	 * 
	 * @param save
	 */
	public void setSaveView(boolean save) {
		mSaveView = save;
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

	/**
	 * 进度是否显示
	 * 
	 * @return
	 */
	public boolean isProgressShowing() {
		if (mProgress != null) {
			return mProgress.isShown();
		}
		return false;
	}

	private OnClickListener onTitleClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onTitleClick(v);
		}
	};
}
