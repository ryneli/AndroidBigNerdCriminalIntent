package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import java.util.UUID;

public class CrimeDetailActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_UUID = "crime_uuid";
    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimeDetailActivity.class);
        intent.putExtra(EXTRA_CRIME_UUID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_UUID);
        return CrimeDetailFragment.newInstance(crimeId);
    }
}
