package info.Parkhomenko.personaldiary.view.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.common.CacheManager;
import info.Parkhomenko.personaldiary.common.Utils;
import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.model.Section;
import info.Parkhomenko.personaldiary.data.repository.DiaryRepository;
import info.Parkhomenko.personaldiary.view.adapter.SectionedExpandableLayoutHelper;
import info.Parkhomenko.personaldiary.view.callbacks.ItemClickListener;
import info.Parkhomenko.personaldiary.viewmodel.DiaryViewModel;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView ;

public class DiariesActivity extends AppCompatActivity
        implements DatePickerListener, ItemClickListener {

     //We define our instance fields
    private RecyclerView rv;
    private HorizontalPicker picker;
    private BottomNavigationView bottomNavigationView;
    private SectionedExpandableLayoutHelper sectionedLayout;
    private boolean defaultPage = true;
    private DiaryViewModel diaryViewModel;
    private boolean isScrolling = false;
    private  ColumnChartView  chartView;
    private ColumnChartData columnChartData;
    private RadioGroup radioGroup;

    /**
     * We initialize our widgets
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initializeViews() {

         radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setVisibility(View.INVISIBLE);
        radioGroup.check(R.id.radio_by_date);
        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            bindDairies();
        });

        rv = findViewById(R.id.mRecyclerView);
        sectionedLayout = new
                SectionedExpandableLayoutHelper(this,rv, this,
                2);

        chartView=(ColumnChartView ) findViewById(R.id.chart);
        chartView.setVisibility(View.INVISIBLE);
        picker= findViewById(R.id.datePicker);
        picker.setListener(this)
                .setDays(120)
                .setOffset(7)
                .setDateSelectedColor(Color.DKGRAY)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.DKGRAY)
                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(Color.GRAY)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY )
                .setUnselectedDayTextColor(getResources().getColor(R.color.primaryTextColor))
                .showTodayButton(true)
                .init();
        picker.setBackgroundColor(Color.LTGRAY);
        picker.setDate(new DateTime());

        bottomNavigationView = findViewById(R.id.mBottomNavigation);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void listenToBottomNavigationClicks(){
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            defaultPage=true;
                            bindDairiesCat();
                            break;
                        case R.id.time_manager:
                            defaultPage=false;
                            bindTimeManager();


                            break;

                        case R.id.action_all_dates:
                            defaultPage=false;
                            bindDairies();

                            break;
                        case R.id.action_helper:
                            defaultPage=false;
                            Utils.openActivity(this, HelperActivity.class);


                            break;


                        case R.id.action_exit:

                            finish();
                            break;
                    }
                    return false;
                });

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private void listenToRecyclerViewScrolls(){
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(isScrolling){
                    if(bottomNavigationView.getVisibility()== View.VISIBLE){
                        bottomNavigationView.setVisibility(View.GONE);
                    }
                    isScrolling=false;
                }else{
                    Handler handler=new Handler();
                    handler.postDelayed(() -> {
                        if(bottomNavigationView.getVisibility()==View.GONE){
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    },3000);
                }
            }
        });

    }



    private void bindTimeManager(){
        picker.setVisibility(View.GONE);
        radioGroup.setVisibility(View.INVISIBLE);

        sectionedLayout=new
                SectionedExpandableLayoutHelper(this, rv, this,
                2);

        List<Column> columns = new ArrayList<Column>();
        List<Diary> diaries = CacheManager.ALL_DIARIES_MEMORY_CACHE;
        ArrayList<List<Diary>> diariesLists = Utils.getAllDiariesGroupedByDates(diaries);
        Collections.reverse(diariesLists);
        List<SubcolumnValue> values;
        int i=0;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (List<Diary> list: diariesLists){
            if(list != null && list.size()>0){
                String dateSection = list.get(0).getDate();
                values = new ArrayList<SubcolumnValue>();
                values.add(new SubcolumnValue(list.size(), ChartUtils.pickColor()));


                axisValues.add(new AxisValue(i).setLabel(dateSection));

                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));

            i=i+1;
            }
        }

        columnChartData = new ColumnChartData(columns);
        columnChartData.setAxisXBottom(new Axis(axisValues));
        columnChartData.setAxisYLeft(Axis.generateAxisFromRange(0,diariesLists.size()+2,1).setHasLines(true));

        chartView.setColumnChartData(columnChartData);
        chartView.setValueSelectionEnabled(true);
        chartView.setZoomType(ZoomType.HORIZONTAL);
        chartView.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindDairiesCat(){
        List<Diary> diaries = CacheManager.ALL_DIARIES_MEMORY_CACHE;
        radioGroup.setVisibility(View.INVISIBLE);
        chartView.setVisibility(View.INVISIBLE);

            picker.setVisibility(View.VISIBLE);
            List<Diary> todayDiaries = Utils.getDiariesForThisDate(diaries,
                    CacheManager.SELECTED_DATE);
        Map<String, Set<Diary>> diariesLists = Utils.getAllDiariesGroupedByCategory(todayDiaries);
               sectionedLayout = new
                SectionedExpandableLayoutHelper(this, rv, this,
                2);
        for (Map.Entry<String, Set<Diary>> entry : diariesLists.entrySet()) {
            String category = entry.getKey();

            if(category==null || category.length()==0){
                category="Без категрої";

            }
            List<Diary> List = new ArrayList<Diary>();
            List.addAll(entry.getValue());
            sectionedLayout.addSection(category + " (" + List.size() + ")", (ArrayList<Diary>) List);

        }
        sectionedLayout.notifyDataSetChanged();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bindDairies(){
        List<Diary> diaries = CacheManager.ALL_DIARIES_MEMORY_CACHE;
        chartView.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        picker.setVisibility(View.GONE);
        switch ( radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_by_date:
                ArrayList<List<Diary>> diariesByDateLists = Utils.getAllDiariesGroupedByDates(diaries);

                sectionedLayout = new
                        SectionedExpandableLayoutHelper(this, rv, this,
                        2);
                for (List<Diary> list: diariesByDateLists){
                    if(list != null && list.size()>0){
                        String dateSection = list.get(0).getDate();
                        sectionedLayout.addSection(dateSection + " (" + list.size() + ")", (ArrayList<Diary>) list);
                    }
                }

                break;
            case R.id.radio_by_category:
                Map<String, Set<Diary>> diariesLists = Utils.getAllDiariesGroupedByCategory(diaries);

                sectionedLayout = new
                        SectionedExpandableLayoutHelper(this, rv, this,
                        2);
                for (Map.Entry<String, Set<Diary>> entry : diariesLists.entrySet()) {
                    String category = entry.getKey();

                    if(category==null || category.length()==0){
                        category="Без категрої";

                    }
                    List<Diary> List = new ArrayList<Diary>();
                    List.addAll(entry.getValue());
                    sectionedLayout.addSection(category + " (" + List.size() + ")", (ArrayList<Diary>) List);

                }

                break;

            default:
                break;
        }
        sectionedLayout.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void reloadDiaries() {
        diaryViewModel.getDiariesLiveData().observe(this, diaries -> {
            if (diaries != null && diaries.size() > 0) {
                CacheManager.ALL_DIARIES_MEMORY_CACHE = diaries;
                CacheManager.DIARIES_DIRTY=false;
                bindDairiesCat();
            }else{
                CacheManager.ALL_DIARIES_MEMORY_CACHE.clear();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diaries_page_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                Utils.openActivity(this, CRUDActivity.class);
                finish();
                return true;
            case R.id.action_exam:
                defaultPage=false;
                Utils.openActivity(this, ExamActivity.class);
                return true;

            case R.id.action_switch_view:
                defaultPage = !defaultPage;
              bindDairies();

                return true;
            case R.id.action_exit:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSelected(DateTime dateSelected) {
            String year = String.valueOf(dateSelected.getYear());
            String month = String.valueOf(dateSelected.getMonthOfYear());
            String day = String.valueOf(dateSelected.getDayOfMonth());

            if(dateSelected.getMonthOfYear() < 10){
                month = "0" + month ;
            }
            if(dateSelected.getDayOfMonth() < 10){
                day = "0" + day;
            }
            CacheManager.SELECTED_DATE = year + "-" + month + "-" + day;

            if(CacheManager.DIARIES_DIRTY){
                reloadDiaries();
            }else{
    //            bindDairies();
                bindDairiesCat();
            }

        }

    @Override
    public void itemClicked(Diary diary) {
        Utils.sendDiaryToActivity(this, diary,DetailActivity.class);
    }

    @Override
    public void itemClicked(Section section) {

       Utils.show(this,section.getName()+" clicked");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);

        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        this.initializeViews();
        this.listenToBottomNavigationClicks();
        this.listenToRecyclerViewScrolls();
    }
}

//end






















