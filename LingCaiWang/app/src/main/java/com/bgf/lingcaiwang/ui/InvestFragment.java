package com.bgf.lingcaiwang.ui;

import com.bgf.lingcaiwang.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InvestFragment extends BannerFragment {

    // Views
    private View mainView;
    private RadioGroup mRgInvest;
    // private RadioButton mRbProjectDirect;
    // private RadioButton mRbDebtTransfer;
    private ImageView mIvProject;
    private ImageView mIvDebt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setBannerTitle("投资");
        mainView = inflater.inflate(R.layout.invest_fragment, container, false);
        initViews();
        return mainView;
    }

    private void initViews() {
        mRgInvest = (RadioGroup) mainView.findViewById(R.id.rg_invest);
        // mRbProjectDirect = (RadioButton)
        // mainView.findViewById(R.id.rb_invest_direct);
        // mRbDebtTransfer = (RadioButton)
        // mainView.findViewById(R.id.rb_debt_transfer);
        mIvProject = (ImageView) mainView.findViewById(R.id.iv_project);
        mIvDebt = (ImageView) mainView.findViewById(R.id.iv_debt);

        mRgInvest.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onCheckChange(checkedId);
            }
        });
    }

    private void onCheckChange(int checkedId) {

        switch (checkedId) {
            case R.id.rb_invest_direct:
                mIvProject.setVisibility(View.VISIBLE);
                mIvDebt.setVisibility(View.GONE);
                break;
            case R.id.rb_debt_transfer:
                mIvProject.setVisibility(View.GONE);
                mIvDebt.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
