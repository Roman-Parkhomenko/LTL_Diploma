package info.Parkhomenko.personaldiary.common;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.view.ui.DiariesActivity;

public class Utils {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static void show(Context c,String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validate(EditText... editTexts){
        EditText nameTxt = editTexts[0],descriptionTxt = editTexts[1];

        if(nameTxt.getText() == null || nameTxt.getText().toString().isEmpty()){
            nameTxt.setError("Title is Required Please!");
            return false;
        }
        if(descriptionTxt.getText() == null || descriptionTxt.getText().toString().isEmpty()){
            descriptionTxt.setError("Description is Required Please!");
            return false;
        }

        return true;

    }

    public static void clearEditTexts(EditText... editTexts){
        for (EditText editText:editTexts) {
            editText.setText("");
        }
    }
    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    /**
     * This method will allow us show an Info dialog anywhere in our app.
     */
    public static void showInfoDialog(final AppCompatActivity activity, String title,
                                      String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.indigo)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.m_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Relax", v -> {})
                .setNeutralButton("Go Home", v -> openActivity(activity, DiariesActivity.class))
                .setNegativeButton("Go Back", v -> activity.finish())
                .show();
    }
    /**
     * This method will allow us show a single select dialog where we can select and return an
     * item to an edittext.
     */
    public static void selectDialogItem(Context c,boolean timeOfDay,final EditText itemTxt){
        String title = "Вибір категорії";
        String message = "Оберіть категорію для задачі";

         String[] timesOfDay ={"Світанок", "Ранок", "Обід", "Вечір", "Ніч"};

        if(!timeOfDay){
            title = "Вибір пора доби";
            message = "Оберіть пору доби";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(c,
                android.R.layout.simple_list_item_1, timesOfDay);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkDeepOrange)
                .setTitle(title)
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.m_star)
                .setMessage(message)
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> itemTxt.setText(item))
                .show();
    }

    /**
     * This method will allow us convert a string into a java.util.Date object and
     *  return it.
     */
    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * This method will allow us send a serialized diary objec  to a specified
     *  activity
     */
    public static void sendDiaryToActivity(Context c, Diary diary,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("DIARY_KEY", diary);
        c.startActivity(i);
    }

    /**
     * This method will allow us receive a serialized diary, deserialize it and return it,.
     */
    public  static Diary receiveDiary(Intent intent, Context c){
        try {
            return (Diary) intent.getSerializableExtra("DIARY_KEY");
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-DIARY ERROR: "+e.getMessage());
        }
        return null;
    }

    public static ArrayList<Diary> getDiariesForThisTimeOfDay(List<Diary> allDiaries,
    String timeOfDay){
        ArrayList<Diary> filteredDiaries = new ArrayList<>();
        if(allDiaries == null){
            return filteredDiaries;
        }
        for (Diary diary : allDiaries){
            if (diary != null && diary.getTimeOfDay().equalsIgnoreCase(timeOfDay)){
                filteredDiaries.add(diary);
            }
        }
        return filteredDiaries;

    }
    public static ArrayList<Diary> getDiariesForThisCategory(List<Diary> allDiaries,
    String category){
        ArrayList<Diary> filteredDiaries = new ArrayList<>();
        if(allDiaries == null){
            return filteredDiaries;
        }
        for (Diary diary : allDiaries){
            if (diary != null && diary.getCategory().equalsIgnoreCase(category)){
                filteredDiaries.add(diary);
            }
        }
        return filteredDiaries;

    }

    public static ArrayList<Diary> getDiariesForThisDate(List<Diary> allDiaries, String date){
        ArrayList<Diary> filteredDiaries =new ArrayList<>();

        if(allDiaries == null){
            return filteredDiaries;
        }
        for (Diary diary : allDiaries){
            if (diary != null && diary.getDate().equalsIgnoreCase(date)){
                filteredDiaries.add(diary);
            }
        }
        return filteredDiaries;

    }

    public static ArrayList<List<Diary>> getAllDiariesGroupedByDates(List<Diary> allDiaries){
        ArrayList<List<Diary>> diariesGroupedByDates =new ArrayList<>();
        List<Diary> remainingDiaries = new ArrayList<>(allDiaries);

        for (Diary diary : allDiaries){
            if (diary != null && diary.getDate() != null){
                ArrayList<Diary> currentGroupDiaries = Utils.getDiariesForThisDate(
                    remainingDiaries, diary.getDate());
                if(currentGroupDiaries != null && currentGroupDiaries.size() > 0){
                    if(!diariesGroupedByDates.contains(currentGroupDiaries)){
                        diariesGroupedByDates.add(currentGroupDiaries);
                        for (Diary currentInnerChore : currentGroupDiaries){
                            remainingDiaries.remove(currentInnerChore);
                        }
                    }
                }
            }
        }
        Collections.reverse(diariesGroupedByDates);
        return diariesGroupedByDates;

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static    Map<String, Set<Diary>> getAllDiariesGroupedByCategory(List<Diary> allDiaries){
        ArrayList<List<Diary>> getAllDiariesGroupedByCategory = new ArrayList<>();
        List<Diary> remainingDiaries = new ArrayList<>(allDiaries);
        Map<String, Set<Diary>> map2 = new HashMap<>();
        for (Diary allDiary : allDiaries) {
            map2.computeIfAbsent(allDiary.getCategory(), k -> new HashSet<>()).add(allDiary);
        }

        return map2;

    }
}























