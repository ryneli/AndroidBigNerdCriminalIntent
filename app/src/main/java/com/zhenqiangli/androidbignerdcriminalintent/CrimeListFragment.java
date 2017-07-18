package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

  private class CrimeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<Crime> crimeList;
    private Toast toastCrime;
    private static final int ITEM_CRIME = 1;
    private static final int ITEM_CRIME_PLUS = 2;
    public CrimeListAdapter(List<Crime> crimeList) {
      this.crimeList = crimeList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      if (viewType == ITEM_CRIME) {
        return new CrimeViewHolder(inflater, parent);
      } else {
        return new CrimePlusViewHolder(inflater, parent);
      }
    }

    @Override
    public int getItemViewType(int position) {
      if (crimeList.get(position).isRequirePolice()) {
        return ITEM_CRIME_PLUS;
      } else {
        return ITEM_CRIME;
      }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
      holder.bind(crimeList.get(position));
      holder.itemView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (toastCrime != null) {
            toastCrime.cancel();
          }
          toastCrime = Toast.makeText(getActivity(),
              crimeList.get(position).toString(), Toast.LENGTH_SHORT);
          toastCrime.show();
        }
      });
    }

    @Override
    public int getItemCount() {
      return crimeList.size();
    }
  }

  class CrimePlusViewHolder extends BaseViewHolder {
    @BindView(R.id.item_crime_title) TextView title;
    @BindView(R.id.item_crime_date) TextView date;

    public CrimePlusViewHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.item_crime_plus, parent, false));
      ButterKnife.bind(this, this.itemView);
    }

    public void bind(Crime crime) {
      title.setText(crime.getTitle());
      date.setText(crime.getDate().toString());
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
      date.setText(crime.getDate().toString());
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
