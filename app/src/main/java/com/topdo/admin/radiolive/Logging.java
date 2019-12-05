package com.topdo.admin.radiolive;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.topdo.admin.radiolive.Activity.MainActivity;
import com.topdo.admin.radiolive.Activity.SplashScreen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Logging extends AppCompatActivity {

    private TextView submit,alterTxt,alterBtn,title;
    private LinearLayout linAlter;
    private Dialog myDialog;
    Context context;
    private FirebaseAuth mAuth;
    Pref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);
        context=this;
        mAuth = FirebaseAuth.getInstance();
        pref=new Pref(context);

        initUi();
    }

    private void initUi() {

        submit=findViewById(R.id.submit);
        alterTxt=findViewById(R.id.alterTxt);
        alterBtn=findViewById(R.id.alterBtn);
        title=findViewById(R.id.title);
        linAlter=findViewById(R.id.lin_altr);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NetworkChecking.hasConnection(context)) {
                    if(isvalidemail(((EditText) findViewById(R.id.username)).getText().toString().trim()) &&
                            !TextUtils.isEmpty(((EditText) findViewById(R.id.password)).getText().toString().trim()))
                    {
                        StartDialog();
                        UserInfo userInfo = new UserInfo();
                        userInfo.setUserMobileNo(((EditText) findViewById(R.id.mobile)).getText().toString().trim());
                        userInfo.setUserName(((EditText) findViewById(R.id.name)).getText().toString().trim());
                        userInfo.setLicensekey(((EditText) findViewById(R.id.password)).getText().toString().trim());
                        userInfo.setUserName(((EditText) findViewById(R.id.username)).getText().toString().trim());
                        userInfo.setLastLogin(getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        userInfo.setCreateDate(getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        if(title.getText().toString().equals("Sign In"))
                        {
                            userInfo.setRequestType("1");
                            loginUser(userInfo);
                        }
                        else{
                                if(!userInfo.getUserName().isEmpty())
                                {
                                    userInfo.setRequestType("2");
                                    loginUser(userInfo);
                                }
                                else Toast.makeText(context, "Incorrect Name", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(context, "Incorrect Email / Password", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(context, "Check your Internet", Toast.LENGTH_SHORT).show();
            }
        });

        alterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alterBtn.getText().toString().equals("Create Account"))
                {
                    alterBtn.setText("SignIn");
                    alterTxt.setText("Already have account , ");
                    submit.setText("Sign Up");
                    title.setText("Create Account");
                    linAlter.setVisibility(View.VISIBLE);
                }else {
                    alterBtn.setText("Create Account");
                    alterTxt.setText("Don't have account , ");
                    submit.setText("SignIn");
                    title.setText("Sign In");
                    linAlter.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if(pref.getBoolean("admin"))
        {
            Intent i = new Intent(Logging.this, MainActivity.class);
            startActivity(i);
        }else {
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            try {
                Log.w("Rabby", currentUser.getEmail());
                if (!currentUser.getEmail().isEmpty()) {
                    Intent i = new Intent(Logging.this, MainActivity.class);
                    startActivity(i);
                }
            } catch (Exception e) {
            }
            //updateUI(currentUser);
        }
    }

    private void StartDialog(){
        myDialog = new Dialog(context);
        myDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().setDimAmount(0.8f);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.progress_dialog);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    private void loginUser(final UserInfo userInfo)
    {
        pref.set("admin",false);
        if(userInfo.getUserName().equals("admin@gmail.com") & userInfo.getLicensekey().equals("proadmin"))
        {
            pref.set("admin",true);
            finish();
            Intent i = new Intent(Logging.this, MainActivity.class);
            startActivity(i);

        }
       else if(userInfo.getRequestType().equals("1"))
       {
           mAuth.signInWithEmailAndPassword(userInfo.getUserName(), userInfo.getLicensekey())
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Log.d("Rabby", "signInWithEmail:success");
                               FirebaseUser user = mAuth.getCurrentUser();
                               pref.set("username",userInfo.getUserName());
                               myDialog.dismiss();
                               finish();
                               Intent i = new Intent(Logging.this, MainActivity.class);
                               startActivity(i);

                           } else {
                               // If sign in fails, display a message to the user.
                               Log.w("Rabby", "signInWithEmail:failure", task.getException());
                               Toast.makeText(context, "Authentication failed.",Toast.LENGTH_SHORT).show();
                               myDialog.dismiss();

                           }
                       }
                   });

       }else
       {
           if(userInfo.getLicensekey().trim().length()>=6) {
               mAuth.createUserWithEmailAndPassword(userInfo.getUserName(), userInfo.getLicensekey())
                       .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   Log.d("Rabby", "createUserWithEmail:success");
                                   FirebaseUser user = mAuth.getCurrentUser();
                                   pref.set("username", userInfo.getUserName());
                                   myDialog.dismiss();
                                   finish();
                                   Intent i = new Intent(Logging.this, MainActivity.class);
                                   startActivity(i);
                                   //updateUI(user);
                               } else {
                                   // If sign in fails, display a message to the user.
                                   Log.w("Rabby", "createUserWithEmail:failure", task.getException());
                                   Toast.makeText(context, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();
                                   myDialog.dismiss();
                                   //updateUI(null);
                               }

                           }
                       });
           }else Toast.makeText(context, "Password length minimum 6", Toast.LENGTH_SHORT).show();
       }
    }

    public String getCurrentDate(String format) {
        //"yyyy-MM-dd"
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(format);
        String strDate = ""+ mdformat.format(calendar.getTime());
        return strDate;
    }

    public boolean isvalidemail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
