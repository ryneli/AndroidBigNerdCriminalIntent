package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeRepository;
import java.util.List;

public class CrimeListFragment extends Fragment {

  private RecyclerView crimeListView;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    crimeListView = (RecyclerView) inflater.inflate(R.layout.fragment_crime_list, container, false);
    crimeListView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUi();
    return crimeListView;
  }



  private void updateUi() {
    List<Crime> crimeList = CrimeRepository.getInstance().getCrimeList();
    crimeListView.setAdapter(new CrimeListAdapter(crimeList));
  }

  private class CrimeListAdapter extends RecyclerView.Adapter<CrimeViewHolder> {
    private List<Crime> crimeList;
    public CrimeListAdapter(List<Crime> crimeList) {
      this.crimeList = crimeList;
    }

    @Override
    public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      return new CrimeViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(CrimeViewHolder holder, int position) {
      holder.bind(crimeList.get(position));
    }

    @Override
    public int getItemCount() {
      return crimeList.size();
    }
  }

  static class CrimeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_crime_title) TextView title;
    @BindView(R.id.item_crime_date) TextView date;

    public CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.item_crime, parent, false));
      ButterKnife.bind(this, this.itemView);
    }

    public void bind(Crime crime) {
      title.setText(crime.getTitle());
      date.setText(crime.getDate().toString());
    }
  }
}
