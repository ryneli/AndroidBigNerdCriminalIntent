package com.zhenqiangli.androidbignerdcriminalintent.data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class CrimeRepository implements CrimeSource {
    private static CrimeRepository crimeRepository = new CrimeRepository();
    private List<Crime> crimeList = new LinkedList<>();

    @Override
    public Crime getCrime(UUID id) {
        for (Crime crime : crimeList) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    private CrimeRepository() {
    }
    public static CrimeRepository getInstance() {
        return crimeRepository;
    }

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
