package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhenqiangli on 7/18/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
  protected abstract Fragment createFragment();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_fragment);

    FragmentManager fm = getFragmentManager();
    Fragment f = fm.findFragmentById(R.id.fragment_container);

    if (f == null) {
      f = createFragment();
      fm.beginTransaction().add(R.id.fragment_container, f).commit();
    }
  }
}
