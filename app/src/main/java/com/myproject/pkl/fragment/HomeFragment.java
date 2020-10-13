package com.myproject.pkl.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.myproject.pkl.MyUtils;
import com.myproject.pkl.adapter.ListHistoryAdapter;
import com.myproject.pkl.R;
import com.myproject.pkl.activity.PhotoActivity;
import com.myproject.pkl.model.Database;
import com.myproject.pkl.model.IdentifiedObject;

import java.util.List;


public class HomeFragment extends Fragment {

    private View v;
    private RecyclerView rvHistory;
    private List<IdentifiedObject> listHistory;
    private Database database;
    private ListHistoryAdapter listHistoryAdapter;
    private ExtendedFloatingActionButton fab;
    private TextView tvGetStarted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        database = Database.getInstance(getContext());
        new HomeFragment.ReadDB().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (v == null){
            v = inflater.inflate(R.layout.fragment_home, container, false);
            fab = v.findViewById(R.id.fab_add_photo);
            rvHistory = v.findViewById(R.id.rv_history);
            tvGetStarted = v.findViewById(R.id.tv_get_started);
            rvHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && !fab.isExtended()
                            && recyclerView.computeVerticalScrollOffset() == 0
                    ) {
                        fab.extend();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (dy != 0 && fab.isExtended()) {
                        fab.shrink();
                    }
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PhotoActivity.class);
                    startActivity(intent);
                }
            });

        }
        return v;
    }
    private void showRecyclerList(){
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        listHistoryAdapter = new ListHistoryAdapter(listHistory);
        rvHistory.setAdapter(listHistoryAdapter);
    }
    private class ReadDB extends AsyncTask<Void, Void, List<IdentifiedObject>> {
        @Override
        protected List<IdentifiedObject> doInBackground(Void... voids) {
            List<IdentifiedObject> list = database.identifiedObjectDao().getIdentifiedObjectList();
            return list;
        }
        protected void onPostExecute(List<IdentifiedObject> list) {
            listHistory = list;
            if (listHistory.size() > 0) tvGetStarted.setVisibility(View.GONE);
            else tvGetStarted.setVisibility(View.VISIBLE);
            showRecyclerList();
        }
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.filter_menu,menu);
//        MenuItem searchitem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchitem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText!=null){
//                    listHistoryAdapter.getFilter().filter(newText);
//                }else {
//                    listHistoryAdapter.getFilter();
//                }
//                return true;
//            }
//        });
//    }
}