package com.zhenqiangli.androidbignerdcriminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeRepository;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {
  private static final String TAG = "CrimeListFragment";
  private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
  private static final int REQUEST_CRIME_DETAIL = 1;
  private RecyclerView crimeListView;
  private View noCrimeView;
  private CrimeListAdapter crimeListAdapter;
  private boolean subtitleShown = false;
  List<Crime> crimeList;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    crimeListView = (RecyclerView) inflater.inflate(R.layout.fragment_crime_list, container, false);
    crimeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    if (savedInstanceState != null) {
      subtitleShown = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
    }
    noCrimeView = inflater.inflate(R.layout.fragment_no_crime, container, false);
    crimeList = CrimeRepository.getInstance().getCrimeList();
    updateUi();


    if (crimeList.size() == 0) {
      return noCrimeView;
    } else {
      return crimeListView;
    }
  }

  private void showCrimeList() {
    crimeListView.setVisibility(View.VISIBLE);
    noCrimeView.setVisibility(View.INVISIBLE);
  }

  private void showNoCrime() {
    crimeListView.setVisibility(View.INVISIBLE);
    noCrimeView.setVisibility(View.VISIBLE);
  }

  @Override
  public void onResume() {
    super.onResume();
    updateUi();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_crime_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.new_crime:
        Crime crime = new Crime();
        CrimeRepository.getInstance().addCrime(crime);
        Intent intent = CrimeDetailPagerActivity
            .newIntent(getActivity(), crime.getId());
        startActivity(intent);
        return true;
      case R.id.show_subtitle:
        if (item.getTitle().equals(getString(R.string.show_subtitle))) {
          item.setTitle(getString(R.string.hide_subtitle));
          subtitleShown = true;
        } else if (item.getTitle().equals(getString(R.string.hide_subtitle))) {
          item.setTitle(getString(R.string.show_subtitle));
          subtitleShown = false;
        }
        updateSubtitle();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleShown);
  }

  private void updateSubtitle() {
    AppCompatActivity activity = (AppCompatActivity) getActivity();

    if (subtitleShown) {
      int crimeCount = CrimeRepository.getInstance().getCrimeList().size();
      String subtitle = getResources().getQuantityString(R.plurals.subtitle_format,
          crimeCount, crimeCount);
      activity.getSupportActionBar().setSubtitle(subtitle);
    } else {
      activity.getSupportActionBar().setSubtitle(null);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CRIME_DETAIL) {
      boolean modified = data.getBooleanExtra(CrimeDetailFragment.RESULT_MODIFIED, true);
      UUID crimeId = (UUID) data.getSerializableExtra(CrimeDetailFragment.RESULT_CRIME_ID);
      if (modified) {
        crimeListAdapter.notifyItemChanged(crimeId);
      }
    }
  }

  private void updateUi() {
    crimeList = CrimeRepository.getInstance().getCrimeList();
    Log.d(TAG, "updateUi: " + crimeList.size());
    if (crimeList.size() == 0) {
      showNoCrime();
      return;
    } else {
      showCrimeList();
      // not return
    }
    crimeListAdapter = new CrimeListAdapter(crimeList);
    crimeListView.setAdapter(crimeListAdapter);

    updateSubtitle();
  }

  private class CrimeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Crime> crimeList;

    public CrimeListAdapter(List<Crime> crimeList) {
      this.crimeList = crimeList;
    }

    public void notifyItemChanged(UUID crimeId) {
      for (int i = 0; i < crimeList.size(); i++) {
        if (crimeList.get(i).getId().equals(crimeId)) {
          notifyItemChanged(i);
        }
      }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      return new CrimeViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
      holder.bind(crimeList.get(position));
      holder.itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = CrimeDetailPagerActivity.newIntent(getActivity(),
              crimeList.get(position).getId());
          startActivityForResult(intent, REQUEST_CRIME_DETAIL);
        }
      });
    }

    @Override
    public int getItemCount() {
      return crimeList.size();
    }
  }

  class CrimeViewHolder extends BaseViewHolder {
    @BindView(R.id.item_crime_title) TextView title;
    @BindView(R.id.item_crime_date) TextView date;
    @BindView(R.id.item_crime_solved)
    ImageView solved;

    public CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.item_crime, parent, false));
      ButterKnife.bind(this, this.itemView);
    }

    public void bind(Crime crime) {
      title.setText(crime.getTitle());
      date.setText(crime.getSimpleDate());
      if (crime.isSolved()) {
        solved.setVisibility(View.INVISIBLE);
      } else {
        solved.setVisibility(View.VISIBLE);
      }
    }
  }

  abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View v) {
      super(v);
    }

    abstract void bind(Crime crime);
  }
}
