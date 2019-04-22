package com.example.dell5559.offstage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText un,pass;
    Button login,fp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(new SessionManager(LoginActivity.this).isLoggedin()){
            startActivity(new Intent(LoginActivity.this,HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        un = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        fp = (Button)findViewById(R.id.forgotPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Backk bk = new Backk(LoginActivity.this);
                bk.execute();

            }
        });

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(un.toString())){
                    un.setError("Field Required");
                }
                else {
                    backpassword bkpwd = new backpassword(LoginActivity.this);
                    bkpwd.execute();
                };
            }
        });


    }

    class Backk extends AsyncTask<String,Void,String> implements com.example.dell5559.offstage.Backk {

        String url ;
        Context context;
        ProgressDialog progressDialog;

        Backk(Context con) {
            context = con;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            url = getString(R.string.url) + "login.php";

            Log.e("========>",url);

            Database db = new Database(url);

            String postdata = db.encodeData("name",un.getText().toString())+" & "+db.encodeData("password",pass.getText().toString());

            db.writedb(postdata);

            String result = db.readdb();
            Log.e("========>",result);

            if(!result.equalsIgnoreCase("Register First")){

                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if(jsonObject.getString("password").equals("")){
                        result = "setpassword";
                        progressDialog.dismiss();
                    }else{
                        session(result);
                        progressDialog.dismiss();
                        startActivity(new Intent(context,HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                progressDialog.dismiss();
            }

            db.disconnect();

            return result;
        }
        @Override
        protected void onPostExecute(String result) {

            if(result.equalsIgnoreCase("Register First")) {
                Toast.makeText(context, "Enter Correct Data", Toast.LENGTH_LONG).show();
            }else if(result.equals("setpassword")){
                pass.setError("Enter New Password");
                un.setEnabled(false);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(pass.getText())){
                            pass.setError("Enter Password");
                        }else{
                            backkpassword bkpass = new backkpassword(context);
                            bkpass.execute();
                        }
                    }
                });
            }

        }
        public void session(String result){
            SessionManager sm = new SessionManager(context);

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String sid = jsonObject.getString("student_id");
                String pass = jsonObject.getString("password");
                String role = jsonObject.getString("role");
                String event_id = jsonObject.getString("event_id");
                String name =  jsonObject.getString("student_name");
                String mbNo =  jsonObject.getString("student_contact");
                String emial =  jsonObject.getString("student_email");

                sm.createSession(sid,name,pass,role,event_id,emial,mbNo);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Enter correct data",Toast.LENGTH_LONG).show();
            }
        }
    }

    class backkpassword extends AsyncTask<String,Void,String>{
        Context con ;
        ProgressDialog progressDialog;
        backkpassword(Context con){
            this.con = con;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(con);
            progressDialog.setMessage("Saving Changes");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            login.setEnabled(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {

            un.setEnabled(true);
            login.setEnabled(true);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Backk bk = new Backk(LoginActivity.this);
                    bk.execute();

                }
            });

            progressDialog.dismiss();

        }

            @Override
        protected String doInBackground(String... strings) {

                final String urll = getString(R.string.url)+"changePassword.php";

                    Database db2 = new Database(urll);
                    String pwd = pass.getText().toString();
                    String dataa = db2.encodeData("name", un.getText().toString())
                            + " & " + db2.encodeData("password", pwd);
                    db2.writedb(dataa);
                    String resultt = db2.readdb();

                    Log.e("changepassword", resultt);



            return null;
        }
    }

    class backpassword extends AsyncTask<String,Void,String>{

        Context context;

        backpassword(Context con){
         this.context = con;
        }

        String url = getString(R.string.url)+"forgetPassword.php";

        @Override
        protected String doInBackground(String... strings) {

            Database db = new Database(url);
            Boolean req = false;
            String data = db.encodeData("id",un.getText().toString());
            db.writedb(data);
            String result = db.readdb();
            Log.e("result Login",result);

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(this,R.style.MyDialogTheme)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish();
                        System.exit(1);
                    }
                }).create().show();
    }
}


