package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;
import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeRepository;
import com.zhenqiangli.androidbignerdcriminalintent.data.CrimeSource;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class CrimeDetailFragment extends Fragment {
    private static final String TAG = "CrimeDetailFragment";
    private Crime crime;
    private CrimeSource crimeSource = CrimeRepository.getInstance();
    private static final String ARGS_CRIME_UUID = "args_crime_uuid";
    private static final String DIALOG_DATE = "DialogDate";
    public static final String RESULT_MODIFIED = "result_modified";
    public static final String RESULT_CRIME_ID = "result_crime_id";
    private static final int REQUEST_DATE = 0x1;
    @BindView(R.id.item_title)
    EditText titleEditText;
    @BindView(R.id.item_date)
    Button dateItem;
    @BindView(R.id.item_solved)
    CheckBox solved;

    public static CrimeDetailFragment newInstance(UUID crimeId) {
        Log.d(TAG, "newInstance: " + crimeId);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_CRIME_UUID, crimeId);
        CrimeDetailFragment fragment = new CrimeDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        dateItem.setText(crime.getSimpleDate());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARGS_CRIME_UUID);
        Log.d(TAG, "onCreate: " + crimeId);
        crime = crimeSource.getCrime(crimeId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.bind(this, v);

        titleEditText.setHint(crime.getTitle());
        updateDate();
        dateItem.setEnabled(true);
        dateItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CrimeDetailFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
        solved.setChecked(crime.isSolved());
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
