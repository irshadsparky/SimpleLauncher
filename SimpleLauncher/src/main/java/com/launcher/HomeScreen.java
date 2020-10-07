package com.launcher;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import com.library.AppModel;
import com.library.AppsLoader;

import java.util.ArrayList;

public class HomeScreen extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {
    private AppCompatEditText edittextSearch;
    private RecyclerView recyclerView;
    private AppListAdapter mAdapter;
    private ArrayList<AppModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        recyclerView = findViewById(R.id.recyclerView);
        edittextSearch = findViewById(R.id.edittextSearch);
        mAdapter = new AppListAdapter(getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();

        edittextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    ArrayList<AppModel> searchedItem = new ArrayList<>();
                    for (AppModel datum : data) {
                        if (datum.getLabel().toLowerCase().contains(s.toString().toLowerCase())) {
                            searchedItem.add(datum);
                        }
                    }
                    mAdapter.setData(searchedItem);
                } else {
                    mAdapter.setData(data);
                }
            }
        });
    }


    @NonNull
    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AppsLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> data) {
        this.data = data;
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<AppModel>> loader) {
        mAdapter.setData(null);
    }
}
