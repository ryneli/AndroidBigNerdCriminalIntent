package com.zhenqiangli.androidbignerdcriminalintent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Fragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by zhenqiangli on 7/18/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CrimeListActivityTest {
  private CrimeListActivity activity;

  @Before
  public void setUp() throws Exception {
    activity = Robolectric.buildActivity(CrimeListActivity.class)
        .create()
        .resume()
        .get();
  }

  @Test
  public void notNull() {
    assertNotNull(activity);
  }

  @Test
  public void hasCrimeListFragment() {
    Fragment f = activity.getFragmentManager().findFragmentById(R.id.fragment_container);
    assertNotNull(f);
    assertEquals(f.getClass().getCanonicalName(), CrimeListFragment.class.getCanonicalName());
  }

  @Test
  public void exampleTest() {
    assertEquals(1, 1);
  }
}
