package com.example.pagingtest2;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class UserDataFactory extends DataSource.Factory<Long, User> {

    @NonNull
    @Override
    public DataSource<Long, User> create() {
        return new UserDataSource();
    }
}
