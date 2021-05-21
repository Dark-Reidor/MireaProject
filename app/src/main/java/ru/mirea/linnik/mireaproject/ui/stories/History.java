package ru.mirea.linnik.mireaproject.ui.stories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.linnik.mireaproject.R;

public class History extends Fragment {
    private RecyclerView stories;
    private AdapterCell adapterCell;
    List<ru.mirea.linnik.mireaproject.ui.stories.Cell> listStories;

    public History() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ru.mirea.linnik.mireaproject.ui.stories.AppDatabase db = App.getInstance().getDatabase();
        HistoryDao historyDao = db.storyDao();
        stories = view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        stories.setLayoutManager(layoutManager);
        stories.setHasFixedSize(true);
        listStories = historyDao.getAll();
        adapterCell = new AdapterCell(listStories);
        stories.setAdapter(adapterCell);
        return view;
    }
}