package com.bgf.lingcaiwang.ui.setting;

import android.os.Bundle;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.ui.BackActivity;

public class BillBoardActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_board);
        setTitle(R.string.title_billboard);
    }

}
