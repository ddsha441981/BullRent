package com.example.bullrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

   // Toolbar toolbar;

    Button callSignUpScreen,login_btn;
    ImageView logo_image;
    TextView logo_name,slogan_name;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*------------------------Hide Status Bar----------------------------*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        /*------------------------Hooks----------------------------*/

        logo_image = findViewById(R.id.logo_image);
        logo_name = findViewById(R.id.logo_name);
        slogan_name = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        callSignUpScreen = findViewById(R.id.callSignUpScreen);


        /*------------------------Jump SignUp Activity when Button Click----------------------------*/

        callSignUpScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,SignUp.class);

                 /*------------------------Transition Animation----------------------------*/
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logo_image,"car_trans");
                pairs[1] = new Pair<View,String>(logo_name,"logo_txt");
                pairs[2] = new Pair<View,String>(slogan_name,"logo_desc");
                pairs[3] = new Pair<View,String>(username,"username_trans");
                pairs[4] = new Pair<View,String>(password,"password_trans");
                pairs[5] = new Pair<View,String>(login_btn,"button_trans");
                pairs[6] = new Pair<View,String>(callSignUpScreen,"login_signup_trans");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);

                    startActivity(intent,activityOptions.toBundle());
                }


            }
        });

       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
    }

    /*-------------------------------------Validation----------------------------------*/

    private boolean validateUserName() {
        String valName = username.getEditText().getText().toString();

        if (valName.isEmpty()) {
            username.setError("Field cannot be Empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String valName = password.getEditText().getText().toString();

        if (valName.isEmpty()) {
            password.setError("Field cannot be Empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    //Call Login
    public void loginUser(View view){
        //Validate Login Info
        if(!validateUserName() | !validatePassword()){

            return;
        }else {
            isUser();
        }
    }

    private void isUser() {

        final String userEnteredUserName = username.getEditText().getText().toString().trim();
        final String userEnteredPassword = password.getEditText().getText().toString().trim();

        //FireBase Reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUserName );

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUserName).child("password").getValue(String.class);
                    if(passwordFromDB.equals(userEnteredPassword)){

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUserName).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUserName).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUserName).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUserName).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else{
                    username.setError("No Such User Exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Call SignUp Screen
    public void callSignUpScreen(View view){


    }


}
