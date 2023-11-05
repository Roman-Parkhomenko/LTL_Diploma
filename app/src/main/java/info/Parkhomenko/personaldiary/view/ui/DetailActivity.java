    package info.Parkhomenko.personaldiary.view.ui;

    import android.content.Context;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.NavUtils;

    import com.google.android.material.appbar.CollapsingToolbarLayout;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;

    import info.Parkhomenko.personaldiary.R;
    import info.Parkhomenko.personaldiary.data.model.Diary;
    import info.Parkhomenko.personaldiary.common.Utils;
    import io.github.inflationx.viewpump.ViewPumpContextWrapper;

    public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

        //Let's define our instance fields
        private TextView titleTV,descriptionTV,dateTV,descriptionTV2,dificultyTV,markAsDoneButton;
        private FloatingActionButton editFAB;
        private Diary receivedDiary;
        private CollapsingToolbarLayout mCollapsingToolbarLayout;

        /**
         * Let's initialize our widgets
         */
        private void initializeWidgets(){
            titleTV= findViewById(R.id.titleTV);
            descriptionTV= findViewById(R.id.descriptionTV);
            dateTV= findViewById(R.id.dateTV);
            dificultyTV = findViewById(R.id.dificultyTV);
            descriptionTV2= findViewById(R.id.descriptionTV2);
            editFAB=findViewById(R.id.editFAB);
            editFAB.setOnClickListener(this);
            mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
        }

        /**
         * We will now receive and show our data to their appropriate views.
         */
        private void receiveAndShowData(){
             receivedDiary = Utils.receiveDiary(getIntent(),DetailActivity.this);

             if(receivedDiary != null){
                 titleTV.setText(receivedDiary.getTitle());
                 descriptionTV.setText(receivedDiary.getDescription());
                 descriptionTV2.setText(receivedDiary.getCategory());
                 dateTV.setText(receivedDiary.getDate());
                 dificultyTV.setText(String.valueOf(receivedDiary.getDificulty()));

                 mCollapsingToolbarLayout.setTitle(receivedDiary.getTitle());
                 mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                 getColor(R.color.white));
             }
        }

        /**
         * Let's inflate our menu for the detail page
         */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.detail_page_menu, menu);
            return true;
        }

        /**
         * When a menu item is selected we want to navigate to the appropriate page
         */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    Utils.sendDiaryToActivity(this, receivedDiary,CRUDActivity.class);
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
         * When FAB button is clicked we want to go to the editing page
         */
        @Override
        public void onClick(View v) {
            int id =v.getId();
            if(id == R.id.editFAB){
                Utils.sendDiaryToActivity(this, receivedDiary,CRUDActivity.class);
                finish();
            }
        }
        /**
         * Let's once again override the attachBaseContext. We do this for our
         * Calligraphy library
         */
        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        }

        /**
         * Let's finish the current activity when back button is pressed
         */
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            this.finish();
        }

        /**
         * Our onCreate method
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);

            initializeWidgets();
            receiveAndShowData();
        }

    }
//end














