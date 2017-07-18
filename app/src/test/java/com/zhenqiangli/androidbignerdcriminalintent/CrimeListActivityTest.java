package com.zhenqiangli.androidbignerdcriminalintent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Fragment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by zhenqiangli on 7/18/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CrimeListActivityTest {
  static {
    System.setProperty("robolectric.logging","stdout");
    ShadowLog.stream = System.out; // show logger’s output
  }
  private CrimeListActivity activity;
  private ActivityController<CrimeListActivity> activityController;

  @Before
  public void setUp() throws Exception {
    activityController = Robolectric.buildActivity(CrimeListActivity.class);
    activity = activityController.get();
    activityController.setup();
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
}
