package info.Parkhomenko.personaldiary.view.ui;


import android.content.Context;
import android.helper.DateTimePickerEditText;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProviders;

import java.util.Random;

import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.common.CacheManager;
import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.common.Utils;
import info.Parkhomenko.personaldiary.viewmodel.DiaryViewModel;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CRUDActivity extends AppCompatActivity {

    //we'll have several instance fields
    private EditText titleTxt, descriptionTxt,categoryTxt,timeOfDayTxt;
    private TextView headerTxt;
    private DateTimePickerEditText dateTxt;
    private final Context c = CRUDActivity.this;
    private DiaryViewModel diaryViewModel;
    private Diary receivedDiary;
    private final String[] times = {"Ранок","Обід","Вечір"};
    private final String[] categories ={"Університет","Курси","Іноземні мови","Спорт","Саморозвиток"};
    private void initializeWidgets() {
        headerTxt = findViewById(R.id.headerTxt);
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        categoryTxt = findViewById(R.id.categoryTxt);
        timeOfDayTxt = findViewById(R.id.timeOfDayTxt);
        dateTxt = findViewById(R.id.dateTxt);
        dateTxt.setFormat(Utils.DATE_FORMAT);
    }

    private void listenToEditTextClicks(){
        categoryTxt.setOnClickListener(v -> Utils.selectDialogItem(CRUDActivity.this,true,
        categoryTxt));
        timeOfDayTxt.setOnClickListener(v -> Utils.selectDialogItem(CRUDActivity.this,false,
        timeOfDayTxt));
    }

    private void insertDiary(String title, String description, String category, String date,
     String TimeOfDay) {
        Diary diary = new Diary();
        diary.setId(String.valueOf(System.currentTimeMillis()));
        diary.setTitle(title);
        diary.setDescription(description);
        diary.setCategory(category);
        diary.setDate(date);
        diary.setTimeOfDay(TimeOfDay);
        diary.setCategory(categories[new Random().nextInt(categories.length)]);
        Long result = diaryViewModel.insert(diary);
        if (result != null) {
            if (result > -1) {
                CacheManager.DIARIES_DIRTY=true;
                Utils.clearEditTexts(titleTxt, descriptionTxt,dateTxt);
                Utils.show(this, "INSERT SUCCESSFUL");
            } else {
                Utils.show(this, "INSERT UNSUCCESSFUL");
            }
        } else {
            Utils.show(this, "UNSUCCESSFUL. ERROR OCCURED");
        }

    }

    private void insertData() {
        String title, description, date,category,timeOfDay;
        if (Utils.validate(titleTxt, descriptionTxt)) {
            title = titleTxt.getText().toString();
            description = descriptionTxt.getText().toString();
            category = categoryTxt.getText().toString();
            timeOfDay = timeOfDayTxt.getText().toString();

            if (dateTxt.getDate() != null) {
                date = dateTxt.getFormat().format(dateTxt.getDate());
            } else {
                dateTxt.setError("Invalid Date");
                dateTxt.requestFocus();
                return;
            }

            insertDiary(title, description, category,date,timeOfDay);

        }
    }

    private void updateDiary(String title, String description,String category, String date,
    String timeOfDay) {
        receivedDiary.setTitle(title);
        receivedDiary.setDescription(description);
        receivedDiary.setDate(date);
        receivedDiary.setCategory(category);
        receivedDiary.setTimeOfDay(timeOfDay);
        Integer result = diaryViewModel.update(receivedDiary);
        if (result != null) {
            if (result > 0) {
                CacheManager.DIARIES_DIRTY=true;
                Utils.show(this, "UPDATE SUCCESSFUL");
            } else {
                Utils.show(this, "UPDATE UNSUCCESSFUL");
            }
        } else {
            Utils.show(this, "UNSUCCESSFUL. ERROR OCCURED");
        }
    }

    private void updateData() {
        String title, description, date, category, timeOfDay;
        if (Utils.validate(titleTxt, descriptionTxt)) {
            title = titleTxt.getText().toString();
            description = descriptionTxt.getText().toString();
            category = categoryTxt.getText().toString();
            timeOfDay = timeOfDayTxt.getText().toString();

            if (dateTxt.getDate() != null) {
                date = dateTxt.getFormat().format(dateTxt.getDate());
            } else {
                dateTxt.setError("Invalid Date");
                dateTxt.requestFocus();
                return;
            }

            updateDiary(title, description,category, date, timeOfDay);

        }
    }

    private void deleteData() {
        Integer result = diaryViewModel.delete(receivedDiary);
        if (result != null) {
            if (result > 0) {
                CacheManager.DIARIES_DIRTY=true;
                Utils.show(this, "DELETE SUCCESSFUL");
            } else {
                Utils.show(this, "DELETE UNSUCCESSFUL");
            }
        } else {
            Utils.show(this, "UNSUCCESSFUL. ERROR OCCURED");
        }
    }

    @Override
    public void onBackPressed() {
        Utils.showInfoDialog(this, "Warning", "Are you sure you want to exit?");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedDiary == null) {
            getMenuInflater().inflate(R.menu.new_item_menu, menu);
            headerTxt.setText("Додавання задачі");
        } else {
            getMenuInflater().inflate(R.menu.edit_item_menu, menu);
            headerTxt.setText("Редагування задачі");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedDiary != null) {
                    updateData();
                } else {
                    Utils.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedDiary != null) {
                    deleteData();
                } else {
                    Utils.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                Utils.openActivity(this, DiariesActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Attach Base Context
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /**
     * When our activity is resumed we will receive our data and set them to their editing
     * widgets.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Diary o = Utils.receiveDiary(getIntent(), c);
        if (o != null) {
            receivedDiary = o;
            titleTxt.setText(receivedDiary.getTitle());
            descriptionTxt.setText(receivedDiary.getDescription());
            categoryTxt.setText(receivedDiary.getCategory());
            timeOfDayTxt.setText(receivedDiary.getTimeOfDay());

            Object date = receivedDiary.getDate();
            if (date != null) {
                dateTxt.setDate(Utils.giveMeDate(date.toString()));
            }
        } else {
            //Utils.show(c,"Received Diary is Null");
        }
    }

    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);

        this.initializeWidgets();
        this.listenToEditTextClicks();
    }
}
//end














