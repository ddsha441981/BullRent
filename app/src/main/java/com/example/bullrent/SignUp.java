package com.example.bullrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*--------------------------Hide Task Bar--------------------------------*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        //Hooks of activity_sign_up.xml file
        regName = findViewById(R.id.res_Name);
        regUsername = findViewById(R.id.res_Username);
        regEmail = findViewById(R.id.res_Email);
        regPhoneNo = findViewById(R.id.res_PhoneNo);
        regPassword = findViewById(R.id.res_Password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);


    }//OnCreate Method end

    /*-------------------------------------Validation----------------------------------*/

    private boolean validateName() {
        String valName = regName.getEditText().getText().toString();
        if (valName.isEmpty()) {
            regName.setError("Field cannot be Empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName() {
        String valName = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (valName.isEmpty()) {
            regUsername.setError("Field cannot be Empty");
            return false;
        } else if (valName.length() >= 15) {
            regUsername.setError("UserName too Long");
            return false;
        } else if (!valName.matches(noWhiteSpace)) {
            regUsername.setError("White Space not Allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String valName = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (valName.isEmpty()) {
            regEmail.setError("Field cannot be Empty");
            return false;
        } else if (!valName.matches(emailPattern)) {
            regEmail.setError("Invaild Email Address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone() {
        String valName = regPhoneNo.getEditText().getText().toString();
        if (valName.isEmpty()) {
            regPhoneNo.setError("Field cannot be Empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String valName = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (valName.isEmpty()) {
            regPassword.setError("Field cannot be Empty");
            return false;
        } /*else if (!valName.matches(passwordVal)) {
            regPassword.setError("password too weak");
            return false;
        }*/ else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


    /*---------------------------save data in firebase on button click------------------------*/
    public void registerUser(View view) {

        /*---------------------------Call Validation Functions------------------------*/
        if(!validateName() | !validatePassword() | !validatePhone() | !validateEmail() | !validateUserName()){

            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference("Users");
        //get All  values
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        //Calling Verify Phone Number Activity
        Intent intent = new Intent(getApplicationContext(),VerifyPhoneNo.class);
        intent.putExtra("phoneNo",phoneNo);
        startActivity(intent);

        //Storing data in database
        UserHelperClass userHelperClass = new UserHelperClass(name, username, email, phoneNo, password);
        databaseReference.child(username).setValue(userHelperClass);

       // Toast.makeText(this,"Your Account has been Created Successfully",Toast.LENGTH_SHORT).show();

        //After Creating Account go back login screen
      /*  Intent intent1 = new Intent(getApplicationContext(),Login.class);
        startActivity(intent1);
        finish();*/
    }


    /*---------------------------User Already Register------------------------*/

    public void alreadyRegisterUser(View view){
        Intent intent1 = new Intent(getApplicationContext(),Login.class);
        startActivity(intent1);
        finish();
    }

}
