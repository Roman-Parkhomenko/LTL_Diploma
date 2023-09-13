package info.Parkhomenko.personaldiary.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.common.CacheManager;
import info.Parkhomenko.personaldiary.common.Utils;
import info.Parkhomenko.personaldiary.viewmodel.DiaryViewModel;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {

    private ImageView mLogo;
    private TextView mainTitle, subTitle;

    private void initializeWidgets() {
        mLogo = findViewById(R.id.mLogo);
        mainTitle = findViewById(R.id.mainTitle);
        subTitle = findViewById(R.id.subTitle);
    }

    /**
     * THIS METHOD IS OPTIONAL. YOU CAN EXCLUDE IT.
     * Let's go to our DashBoard after 1 second
     */
    private void proceed() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(500);
                    Utils.openActivity(SplashActivity.this, RegistationActivity.class);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void preloadDiaries() {
        DiaryViewModel diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        diaryViewModel.getDiariesLiveData().observe(this, diaries -> {
            if (diaries != null && diaries.size() > 0) {
                CacheManager.ALL_DIARIES_MEMORY_CACHE = diaries;
                CacheManager.DIARIES_DIRTY = false;
            }
            proceed();
        });
    }

    private void showSplashAnimation() {
        Animation bottomToTop = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        mLogo.startAnimation(bottomToTop);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mainTitle.startAnimation(fadeIn);
        subTitle.startAnimation(fadeIn);

        this.preloadDiaries();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.initializeWidgets();
        this.showSplashAnimation();
    }

}

















