package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;
import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeRepository;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeSource;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class CrimeDetailFragment extends Fragment {
    private Crime crime;
    private CrimeSource crimeSource = CrimeRepository.getInstance();
    private static final String ARGS_CRIME_UUID = "args_crime_uuid";
    public static final String RESULT_MODIFIED = "result_modified";
    public static final String RESULT_CRIME_ID = "result_crime_id";
    @BindView(R.id.item_title)
    EditText titleEditText;
    @BindView(R.id.item_date)
    Button dateItem;

    public static CrimeDetailFragment newInstance(UUID crimeId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_CRIME_UUID, crimeId);
        CrimeDetailFragment fragment = new CrimeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeDetailActivity.EXTRA_CRIME_UUID);
        crime = crimeSource.getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.bind(this, v);

        titleEditText.setHint(crime.getTitle());
        dateItem.setText(crime.getSimpleDate());
        dateItem.setEnabled(false);
        setResult(false);
        return v;
    }

    private void setResult(boolean modified) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_MODIFIED, modified);
        intent.putExtra(RESULT_CRIME_ID, crime.getId());
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    @OnTextChanged(R.id.item_title)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        crime.setTitle(s.toString());
        setResult(true);
    }

    @OnCheckedChanged(R.id.item_solved)
    public void onCheckedChanged(CompoundButton b, boolean isChecked) {
        crime.setSolved(isChecked);
        setResult(true);
    }
}
