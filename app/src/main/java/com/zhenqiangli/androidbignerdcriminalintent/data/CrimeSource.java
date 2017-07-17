package com.zhenqiangli.androidbignerdcriminalintent.data;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public interface CrimeSource {
    public List<Crime> getCrimeList();
    public void updateCrime(UUID id, Crime crime);
    public void addCrime(Crime crime);
}
