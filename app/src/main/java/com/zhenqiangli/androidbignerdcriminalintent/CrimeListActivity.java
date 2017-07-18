package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class CrimeListActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeListActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
