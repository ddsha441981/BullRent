package com.example.bullrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout userFullname, userEmail, userPhoneNo, userPassword;
    TextView userFullNameLabel, UserNameLabel;
    //Global variable to hold user data inside this activity
    String _USERNAME, _NAME, _PHONENO,_EMAIL,_PASSWORD;

   /* TextInputEditText userProfileName, userProfileEmail, UserProfilePhone, UserProfilePassword;
    Button btn_ProfileUpdate;*/

   DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //Hooks
        userFullNameLabel = findViewById(R.id.full_name_label);
        UserNameLabel = findViewById(R.id.userName_label);
        userFullname = findViewById(R.id.user_fullName);
        userEmail = findViewById(R.id.user_email);
        userPhoneNo = findViewById(R.id.user_phoneNo);
        userPassword = findViewById(R.id.user_password);

        //Show All Data from database
        showAllUserData();
    }

    //data from firebase
    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
       _NAME = intent.getStringExtra("name");
       _PHONENO = intent.getStringExtra("phoneNo");
       _PASSWORD = intent.getStringExtra("password");
        _EMAIL= intent.getStringExtra("email");


        userFullNameLabel.setText(_USERNAME);
        UserNameLabel.setText(_NAME);
        userFullname.getEditText().setText(_NAME);
        userEmail.getEditText().setText(_EMAIL);
        userPhoneNo.getEditText().setText(_PHONENO);
        userPassword.getEditText().setText(_PASSWORD);
    }

    //Update User
    public void updateUserDetails(View view){

        if(isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneNumber()){

            Toast.makeText(this, "Data has been updated!!!", Toast.LENGTH_SHORT).show();
        }
        else{

            Toast.makeText(this, "Data is same Can not be  Updated", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isPhoneNumber() {

        if(!_PHONENO.equals(userPhoneNo.getEditText().getText().toString())){
            databaseReference.child(_PHONENO).child("phoneNo").setValue(userPhoneNo.getEditText().getText().toString());
            _PHONENO = userPhoneNo.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {

        if(!_EMAIL.equals(userEmail.getEditText().getText().toString())){
            databaseReference.child(_EMAIL).child("email").setValue(userEmail.getEditText().getText().toString());
            _EMAIL = userEmail.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordChanged() {

        if(!_PASSWORD.equals(userPassword.getEditText().getText().toString())){
            databaseReference.child(_PASSWORD).child("password").setValue(userPassword.getEditText().getText().toString());
            _PASSWORD = userPassword.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {

        if(!_NAME.equals(userFullname.getEditText().getText().toString())){
            databaseReference.child(_USERNAME).child("name").setValue(userFullname.getEditText().getText().toString());
            _NAME = userFullname.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
}
