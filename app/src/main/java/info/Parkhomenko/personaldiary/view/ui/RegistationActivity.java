package info.Parkhomenko.personaldiary.view.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.data.database.UsersDB;

public class RegistationActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button signup, signin;
    UsersDB DB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registation_activity);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        DB = new UsersDB(this);

        signup.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();

            if(user.equals("")||pass.equals("")||repass.equals(""))
                Toast.makeText(RegistationActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                if(pass.equals(repass)){
                    Boolean checkuser = DB.checkusername(user);
                    if(checkuser==false){
                        Boolean insert = DB.insertData(user, pass);
                        if(insert==true){
                            Toast.makeText(RegistationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegistationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegistationActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistationActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                }
            } });

        signin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}