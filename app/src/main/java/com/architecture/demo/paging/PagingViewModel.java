package com.architecture.demo.paging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.architecture.demo.room.DemoRoomDatabase;
import com.architecture.demo.room.StudentDao;
import com.architecture.demo.room.StudentEntity;
import com.architecture.demo.util.CONSTANT;

import java.util.List;

/**
 * Created by cym on 18-9-12.
 */
public class PagingViewModel extends AndroidViewModel {
    private LiveData<PagedList<StudentEntity>> data;

    public PagingViewModel(@NonNull Application application) {
        super(application);
        StudentDataSourceFactory studentDataSourceFactory = new StudentDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(15).setPageSize(15).setEnablePlaceholders(false).build();
        LivePagedListBuilder<Integer, StudentEntity> livePagedListBuilder = new LivePagedListBuilder<>(studentDataSourceFactory, config).setBoundaryCallback(new ConcertBoundaryCallback());
        data = livePagedListBuilder.build();
    }

    public LiveData<PagedList<StudentEntity>> getData() {
        return data;
    }

    class ConcertBoundaryCallback extends PagedList.BoundaryCallback<StudentEntity> {
        // Requests initial data from the network, replacing all content currently
        // in the database.
        @Override
        public void onZeroItemsLoaded() {
            Log.i(CONSTANT.TAG_PAGING, "onZeroItemsLoaded:");
        }

        // Requests additional data from the network, appending the results to the
        // end of the database's existing data.
        @Override
        public void onItemAtEndLoaded(@NonNull StudentEntity itemAtEnd) {
            Log.i(CONSTANT.TAG_PAGING, "itemAtEnd:" + itemAtEnd.toString());
        }
    }

    class StudentDataSourceFactory extends DataSource.Factory<Integer, StudentEntity> {
        @Override
        public DataSource<Integer, StudentEntity> create() {
            Log.i(CONSTANT.TAG_PAGING, "Factory_create:");
            return new MDataSource();
        }
    }

    class MDataSource extends PositionalDataSource<StudentEntity> {
        StudentDao studentDao;

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<StudentEntity> callback) {
            if (studentDao == null) {
                studentDao = DemoRoomDatabase.getInstance(getApplication()).studentDao();
            }
            int startPosition = 0;
            List<StudentEntity> all = studentDao.getByLimit(startPosition, params.pageSize);
            int count = studentDao.getCount();
            callback.onResult(all, startPosition, count);
            String log = "startPosition:" + params.requestedStartPosition + ",pageSize:" + params.pageSize + ",loadSize:" + params.requestedLoadSize + ",count:" + count;
            Log.i(CONSTANT.TAG_PAGING, "loadInitial:" + log);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull PositionalDataSource.LoadRangeCallback<StudentEntity> callback) {
            if (studentDao == null) {
                studentDao = DemoRoomDatabase.getInstance(getApplication()).studentDao();
            }
            List<StudentEntity> all = studentDao.getByLimit(params.startPosition, params.loadSize);
            callback.onResult(all);
            String log = "startPosition:" + params.startPosition + ",loadSize:" + params.loadSize;
            Log.i(CONSTANT.TAG_PAGING, "loadRange:" + log);
        }
    }
}
