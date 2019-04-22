package com.example.dell5559.offstage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EventListener;

public class addEvents extends AppCompatActivity {

    String url,event_id="";

    EditText evName,date,tform,tto,location,details,prize;
    TextView save;
    Boolean b =false;
    CheckBox s,d,g;
    EditText solo,duet,grp,paytm,googlepay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        evName = (EditText) findViewById(R.id.edit_event_name);
        date =(EditText) findViewById(R.id.edit_date);
        tform = (EditText) findViewById(R.id.edit_time_from);
        tto = (EditText) findViewById(R.id.edit_time_to);
        location = (EditText) findViewById(R.id.edit_location);
        details = (EditText) findViewById(R.id.edit_event_details);
        prize = (EditText) findViewById(R.id.edit_prize);
//        pfee = (TextView)findViewById(R.id.edit_participation_fee);
        save = (TextView) findViewById(R.id.save);
        s = (CheckBox) findViewById(R.id.solobtn);
        d = (CheckBox) findViewById(R.id.duetbtn);
        g = (CheckBox) findViewById(R.id.grpbtn);
        solo = (EditText)findViewById(R.id.solofees);
        duet = (EditText)findViewById(R.id.duetfees);
        grp = (EditText)findViewById(R.id.grpfees);
        paytm = (EditText)findViewById(R.id.paytm);
        googlepay = (EditText)findViewById(R.id.GooglePay);

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    solo.setEnabled(s.isChecked());
                }
            });
            d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    duet.setEnabled(d.isChecked());
                }
            });
            g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    grp.setEnabled(g.isChecked());
                }
            });

            if (bundle != null) {
                b = true;
                event_id = intent.getStringExtra("event_id");

                Back back = new Back(addEvents.this);
                back.execute();
            }

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if( TextUtils.isEmpty(evName.getText())){

                        evName.setError( "Name Required" );

                    }
                    else if( TextUtils.isEmpty(date.getText())){

                        date.setError( "Date required" );

                    }
                    else if( TextUtils.isEmpty(tform.getText())){

                        tform.setError( "From time is required!" );

                    }
                    else if( TextUtils.isEmpty(tto.getText())){

                        tto.setError( "To time is required!" );

                    }
                    else if( TextUtils.isEmpty(location.getText())){

                        location.setError( "location is required!" );

                    }
                    else if( TextUtils.isEmpty(details.getText())){

                        details.setError( "Description is required!" );

                    }
                    else if( TextUtils.isEmpty(prize.getText())){

                        prize.setError( "Prize is required!" );

                    }
                    else if( TextUtils.isEmpty(paytm.getText())){

                        paytm.setError( "Paytm number is required!" );

                    }
                    else if( TextUtils.isEmpty(googlepay.getText())){

                        googlepay.setError( "GooglePay number is required!" );

                    }

                    else {


                        Backk bk = new Backk(addEvents.this);
                        bk.execute();
                    }
                }

            });


    }

    class Backk extends AsyncTask<String,Void,String>{

        Context context;
        ProgressDialog progressDialog1;
        Intent intent;

        Backk(Context con){
            this.context = con;
        }

        @Override
        protected void onPreExecute() {
            progressDialog1 = new ProgressDialog(context);
            progressDialog1.setMessage("Loading");
            progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog1.setIndeterminate(true);
            progressDialog1.setCancelable(false);
            progressDialog1.show();

            if(b){
                            url = getString(R.string.url) + "event_update.php";
                            evName.setEnabled(false);
                intent.putExtra("event_id",event_id);
                intent = new Intent(context,EventDetails.class);
            }else{
                            url = getString(R.string.url) + "event_add.php";
                intent = new Intent(context,EventsList.class);
            }

            progressDialog1.dismiss();
        }

        @Override
        protected void onPostExecute(String result) {

            startActivity(intent);

        }


            @Override
        protected String doInBackground(String... strings) {

            Database db = new Database(url);

            //name, des, startdate, stime,etime,prize,poster,venue,paytm,googlepay,solo,duo,grp;

                String issolo="0",isduet="0",isgrp="0";
                String fs,fd,fg;

            String data = db.encodeData("EventName",evName.getText().toString())
                    +" & "+db.encodeData("evdesc",details.getText().toString())
                    +" & "+db.encodeData("startDate",date.getText().toString())
                    +" & "+db.encodeData("startTime",tform.getText().toString())
                    +" & "+db.encodeData("endTime",tto.getText().toString())
                    +" & "+db.encodeData("evPrize",prize.getText().toString())
                    +" & "+db.encodeData("evloc",location.getText().toString())
                    +" & "+db.encodeData("paytm",paytm.getText().toString())
                    +" & "+db.encodeData("googlepay",googlepay.getText().toString());

                if(s.isChecked()) {
                    issolo = "1";
                    fs = solo.getText().toString();
                    data+= " & "+db.encodeData("solofees",fs);
                }
                if(d.isChecked()) {
                    isduet = "1";
                    fd = duet.getText().toString();
                    data += " & "+ db.encodeData("duetfees",fd);
                }
                if (g.isChecked()) {
                    isgrp = "1";
                    fg = grp.getText().toString();
                    data+=" & "+db.encodeData("grpfees",fg);
                }
            data+=   " & "+ db.encodeData("is_solo",issolo)
                    +" & "+db.encodeData("is_duet",isduet)
                    +" & "+db.encodeData("is_grp",isgrp);



            if(!event_id.equalsIgnoreCase("")){
                data += " & " +db.encodeData("Event_id",event_id);
            }

            Log.e("data==>",data);

            db.writedb(data);

            String result = db.readdb();
            Log.e("result",result);

//            Toast.makeText(context,result,Toast.LENGTH_LONG).show();

            return null;
        }

    }

    class Back extends AsyncTask<String, Void, String> {

        Context context;
        Database db;
        ProgressDialog progressDialog2;
        String name,detailss,datee,to ,from,prizee,locationn,fee;

        Back(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog2 = new ProgressDialog(context);
            progressDialog2.setMessage("Loading");
            progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog2.setIndeterminate(true);
            progressDialog2.setCancelable(false);
            progressDialog2.show();
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog2.dismiss();

            evName.setText(name);
            details.setText(detailss);
            tform.setText(from);
            tto.setText(to);
            date.setText(datee);
            location.setText(locationn);
            prize.setText(prizee);
//            pfee.setText(fee);

//            solo.setText();
//            duet.setText();
//            grp.setText();


        }

        @Override
        protected String doInBackground(String... strings) {

            getDetails();

            return null;
        }
        public void getDetails(){
            String url = getString(R.string.url) + "eventDetails.php";

            db = new Database(url);
            String data = db.encodeData("event_id",event_id);
            db.writedb(data);
            String result = db.readdb();
            Log.e("res",result);

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                name = jsonObject.getString("event_name");
                detailss = jsonObject.getString("event_description");
                datee = jsonObject.getString("event_date");
                from = jsonObject.getString("start_time");
                to = jsonObject.getString("end_time");
                prizee = jsonObject.getString("prize_money");
                locationn = jsonObject.getString("Venue");
                fee = jsonObject.getString("participation_fee");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
