package com.zhenqiangli.androidbignerdcriminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.zhenqiangli.androidbignerdcriminalintent.data.Crime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class CrimeDetailFragment extends Fragment {
    private Crime crime;
    @BindView(R.id.item_title)
    EditText titleEditText;
    @BindView(R.id.item_date)
    Button dateItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        ButterKnife.bind(this, v);

        dateItem.setText(crime.getSimpleDate());
        dateItem.setEnabled(false);
        return v;
    }

    @OnTextChanged(R.id.item_title)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        crime.setTitle(s.toString());
    }

    @OnCheckedChanged(R.id.item_solved)
    public void onCheckedChanged(CompoundButton b, boolean isChecked) {
        crime.setSolved(isChecked);
    }
}
