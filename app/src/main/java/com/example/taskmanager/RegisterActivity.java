package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextDate;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextConfirmPwd;

    private ProgressBar progressBar;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonRegisterGenderSelected;

    private static final String TAG = "RegisterActivity";

    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();


//        Toast.makeText(RegisterActivity.this, "Ты можешь зарегистрироваться сейчас",
//                Toast.LENGTH_SHORT);


        //use clearCheck() for clearing all checked radiobutton when activity
        //is started or resumed

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.clearCheck();

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                //obtain the entered data
                String textName = editTextName.getText().toString();
                String textLastName = editTextLastName.getText().toString();
                String textEmail = editTextEmail.getText().toString();
                String textDate = editTextDate.getText().toString();
                String textPhone = editTextPhone.getText().toString();
                String textPassword = editTextPassword.getText().toString();
                String textConfirmPsw = editTextConfirmPwd.getText().toString();

                //cant obtain the value before verifying if any button was selected or not
                String textGender;



                /*  //validate phone number using matcher and pattern (regular expression)
                String phoneRegex = "[6-9][0-9](9)"; //first no. can be {6,8,9} and rest nos.can be any no.
                Matcher phoneMatcher;
                Pattern phonePattern = Pattern.compile(phoneRegex);
                phoneMatcher = phonePattern.matcher(textPhone);*/

                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите имя",
                            Toast.LENGTH_SHORT).show();
                    editTextName.setError("Имя обязательно для заполнения");
                    editTextName.requestFocus();
                } else if (TextUtils.isEmpty(textLastName)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите фамилию",
                            Toast.LENGTH_SHORT).show();
                    editTextLastName.setError("Фамилия обязательна для заполнения");
                    editTextLastName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите почту",
                            Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Почта обязательна");
                    editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите почту еще раз",
                            Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Необходим действительный адрес почты");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите дату рождения",
                            Toast.LENGTH_SHORT).show();
                    editTextDate.setError("Дата рождения обязательна");
                    editTextDate.requestFocus();
                } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста выберете свой пол",
                            Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Пол обязателен");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPhone)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите номер телефона",
                            Toast.LENGTH_SHORT).show();
                    editTextPhone.setError("Номер телефона обязателен");
                    editTextPhone.requestFocus();
                } else if (textPhone.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите номер телефона еще раз",
                            Toast.LENGTH_SHORT).show();
                    editTextPhone.setError("Номер не действительный");
                    editTextPhone.requestFocus();
//                } else if (!phoneMatcher.find()) {
//                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите номер телефона еще раз",
//                            Toast.LENGTH_SHORT).show();
//                    editTextPhone.setError("Номер телефона должен состоять из 11 цифр");
//                    editTextPhone.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите пароль",
                            Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Пароль обязателен");
                    editTextEmail.requestFocus();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите другой (>=6) пароль",
                            Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Пароль слишком слабый");
                    editTextPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPsw)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста подтвердите пароль",
                            Toast.LENGTH_SHORT).show();
                    editTextConfirmPwd.setError("Подтверждение пароля обязательно");
                    editTextConfirmPwd.requestFocus();
                } else if (!textPassword.equals(textConfirmPsw)) {
                    Toast.makeText(RegisterActivity.this, "Пожалуйста введите одинаковые пароли пароль",
                            Toast.LENGTH_SHORT).show();
                    editTextConfirmPwd.setError("Подтверждение паролья обязательно");
                    editTextConfirmPwd.requestFocus();
                    //clear the entered passwords
                    editTextPassword.clearComposingText();
                    editTextConfirmPwd.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textName, textLastName, textEmail, textDate, textGender, textPhone, textPassword);
                }

            }
        });

        //setting up datePicker on editText
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //datePicker Dialog
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    //month begin with 0 because month + 1
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


    }

    private void initViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDate = findViewById(R.id.editTextDate);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPwd = findViewById(R.id.editTextConfirmPwd);
        progressBar = findViewById(R.id.progressBar);
    }


    //register user using the credentials given
    private void registerUser(String textName, String textLastName, String textEmail, String textDate,
                              String textGender, String textPhone, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //create user profile
        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnCompleteListener(RegisterActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser = auth.getCurrentUser();

                                    //update display name and lastName of user
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(textName).setDisplayName(textLastName).build();
                                    firebaseUser.updateProfile(profileChangeRequest);

                                    //enter user data into the firebase realtime database
                                    UserDetails writeUserDetails = new UserDetails(textDate, textGender, textPhone);

                                    //extracting user reference from database for "registered users"
                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                //send verification email
                                                firebaseUser.sendEmailVerification();

                                                Toast.makeText(RegisterActivity.this, "Пользователь успешно зарегистрирован." +
                                                                "Пожалуйста проверьте свою почту",
                                                        Toast.LENGTH_SHORT).show();

//                            //open user profile after successful registration
                                                //UserProfileActivity
                                                Intent intent = new Intent(RegisterActivity.this,
                                                        MainActivity.class);
                                                //to prevent user from returning back to register activity on pressing back button after registration
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish(); //to close register activity
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Регистрация не удалась." +
                                                        "Попробуйте снова", Toast.LENGTH_SHORT).show();
                                                //hide progress bar whatever user creation is successful or failed
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        editTextPassword.setError(getString(R.string.pas_weak));
                                        editTextPassword.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        editTextPassword.setError(getString(R.string.pas_invalid_use));
                                        editTextPassword.requestFocus();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        editTextPassword.setError(getString(R.string.user_exist));
                                        editTextPassword.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    //hide progress bar whatever user creation is successful or failed
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
    }


}