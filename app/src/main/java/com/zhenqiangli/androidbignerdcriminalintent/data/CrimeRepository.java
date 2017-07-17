package com.zhenqiangli.androidbignerdcriminalintent.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class CrimeRepository implements CrimeSource {
    private List<Crime> crimeList = Collections.emptyList();
    @Override
    public List<Crime> getCrimeList() {
        return crimeList;
    }

    @Override
    public void updateCrime(UUID id, Crime crime) {
        for (Crime c : crimeList) {
            if (c.getId().equals(id)) {
                c = crime;
            }
        }
    }

    @Override
    public void addCrime(Crime crime) {
        crimeList.add(crime);
    }
}
