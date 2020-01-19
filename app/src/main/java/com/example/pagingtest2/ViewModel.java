package com.example.pagingtest2;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class ViewModel extends androidx.lifecycle.ViewModel {

    LiveData<PagedList<User>> getUsers;

    public ViewModel() {
        UserDataFactory userDataFactory = new UserDataFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(UserDataSource.PAGE_SIZE)
                .build();

        getUsers = new LivePagedListBuilder<>(userDataFactory, config).build();
    }
}
