package com.zhenqiangli.androidbignerdcriminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeRepository;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/19/17.
 */

public class CrimeDetailPagerActivity extends AppCompatActivity {
  private static final String TAG = "CrimeDetailPagerAct";
  private static final String EXTRA_CRIME_ID = "crime_id";
  private List<Crime> crimeList;
  @BindView(R.id.crime_detail_view_pager)
  ViewPager viewPager;

  public static Intent newIntent(Context context, UUID crimeId) {
    Intent intent = new Intent(context, CrimeDetailPagerActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_detail_pager);
    ButterKnife.bind(this);

    UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

    crimeList = CrimeRepository.getInstance().getCrimeList();
    FragmentManager fm = getSupportFragmentManager();
    viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
      @Override
      public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);
        Crime crime = crimeList.get(position);
        return CrimeDetailFragment.newInstance(crime.getId());
      }

      @Override
      public int getCount() {
        return crimeList.size();
      }
    });

    for (int i = 0; i < crimeList.size(); i++) {
      if (crimeList.get(i).getId().equals(crimeId)) {
        viewPager.setCurrentItem(i);
        break;
      }
    }
  }
}
