package info.Parkhomenko.personaldiary.view.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.model.Section;
import info.Parkhomenko.personaldiary.view.callbacks.ItemClickListener;
import info.Parkhomenko.personaldiary.view.callbacks.SectionStateChangeListener;

public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    //data list
    private LinkedHashMap<Section, ArrayList<Diary>> mSectionDataMap = new LinkedHashMap<>();
    private ArrayList<Object> mDataArrayList = new ArrayList<>();
    private HashMap<String, Section> mSectionMap = new HashMap<>();
    private String text;

    //adapter
    private SectionedExpandableGridAdapter mSectionedExpandableGridAdapter;

    //recycler view
    RecyclerView mRecyclerView;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView,
                                        ItemClickListener itemClickListener,
                                           int gridSpanCount) {

        //setting the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        mSectionedExpandableGridAdapter = new SectionedExpandableGridAdapter(context, mDataArrayList,
                gridLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mSectionedExpandableGridAdapter);

        mRecyclerView = recyclerView;
    }

    private void generateDataList () {
        mDataArrayList.clear();
        for (Map.Entry<Section, ArrayList<Diary>> entry : mSectionDataMap.entrySet()) {
            Section key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }
    public void notifyDataSetChanged() {
        generateDataList();
        mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }
    public void addSection(String section, ArrayList<Diary> diaries) {
        Section newSection;
        mSectionMap.put(section, (newSection = new Section(section)));
        mSectionDataMap.put(newSection, diaries);
    }


    public void addItem(String section, Diary diary) {
        Objects.requireNonNull(mSectionDataMap.get(mSectionMap.get(section))).add(diary);
    }

    public void removeItem(String section, Diary diary) {
        Objects.requireNonNull(mSectionDataMap.get(mSectionMap.get(section))).remove(diary);
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    @Override
    public void onSectionStateChanged(Section section, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            section.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
//end