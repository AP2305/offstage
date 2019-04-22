package com.example.dell5559.offstage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExpenseActivity extends AppCompatActivity {

    EditText tname,tamount,tdescription;
    Button upload,save;
    Spinner dropdown,dropdown2;
    ImageView photo;
    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray ;
    String ConvertImage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        tname = (EditText)findViewById(R.id.tname);
        tamount = (EditText)findViewById(R.id.tamount);
        tdescription = (EditText)findViewById(R.id.tdesc);
        upload = (Button)findViewById(R.id.tphotobtn);
        save = (Button)findViewById(R.id.savee);
        photo = (ImageView)findViewById(R.id.photo);

        dropdown = findViewById(R.id.tmode);
        String[] tmodes = {"Cash","Google Pay","Patym","Cheque"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tmodes);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(adapter.getPosition("Cash"));

        dropdown2 = findViewById(R.id.ttype);
        String[] ttype = {"Expense","Income"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ttype);
        dropdown2.setAdapter(adapter2);
        dropdown2.setSelection(adapter.getPosition("Expense"));

        byteArrayOutputStream = new ByteArrayOutputStream();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("dd2",dropdown2.getSelectedItem().toString());
                Log.e("dd",dropdown.getSelectedItem().toString());

                if(TextUtils.isEmpty(tname.getText())){
                    tname.setError("Data Required");
                }else if(TextUtils.isEmpty(tamount.getText())){
                    tamount.setError("Data Required");
                }else if(TextUtils.isEmpty(tdescription.getText())){
                    tdescription.setError("Data Required");
                }else {

                    backexp be = new backexp(ExpenseActivity.this);
                    be.execute();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                photo.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    class backexp extends AsyncTask<String,Void,String> {

        Context context;
        String url = getString(R.string.url)+"AddMoney.php";

        backexp(Context con){
            this.context = con;
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
        protected void onPostExecute(String result) {

            startActivity(new Intent(context,ExpenseTrackerActivity.class));

        }

        @Override
        protected String doInBackground(String... strings) {

            Database db = new Database(url);

            SessionManager sm = new SessionManager(context);

            String data = db.encodeData("payer",tname.getText().toString())
                    +" & "+db.encodeData("type",dropdown2.getSelectedItem().toString())
                    +" & "+db.encodeData("mode",dropdown.getSelectedItem().toString())
                    +" & "+db.encodeData("amount",tamount.getText().toString())
                    +" & "+db.encodeData("description",tdescription.getText().toString())
                    +" & "+db.encodeData("event_id",sm.getKeyEventId())
                    +" & "+db.encodeData("student_id",sm.getKeySid())
                    +" & "+db.encodeData("image",db.convertImage(FixBitmap))
                    +" & "+db.encodeData("ipaddress",getString(R.string.url));

            db.writedb(data);

            Log.e("data Expense",data);

            String result = db.readdb();

            Log.e("result Expense",result);

            progressDialog.dismiss();
            return null;
        }
    }

}
