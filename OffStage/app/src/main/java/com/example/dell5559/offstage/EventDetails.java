package com.example.dell5559.offstage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventDetails extends AppCompatActivity {

    String event_id;
    Database db;
    TextView evName,date,tform,tto,location,details,prize,spfee,dpfee,gpfee,editDet,paytm,googlepay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        evName = (TextView)findViewById(R.id.event_name);
        date =(TextView)findViewById(R.id.date);
        tform = (TextView)findViewById(R.id.time_from);
        tto = (TextView)findViewById(R.id.time_to);
        location = (TextView)findViewById(R.id.location);
        details = (TextView)findViewById(R.id.event_details);
        prize = (TextView)findViewById(R.id.prize);
        spfee = (TextView)findViewById(R.id.solo_participation_fee);
        dpfee = (TextView)findViewById(R.id.duet_participation_fee);
        gpfee = (TextView)findViewById(R.id.group_participation_fee);
        paytm = (TextView)findViewById(R.id.paytmm);
        googlepay= (TextView)findViewById(R.id.gpay);
        editDet = (TextView)findViewById(R.id.editDetails);

        Intent intent = getIntent();
        event_id = intent.getStringExtra("event_id");

        Log.e("eventDetails ID",event_id);

        SessionManager sm = new SessionManager(EventDetails.this);

        if(event_id.equalsIgnoreCase(sm.getKeyEventId())){
            editDet.setVisibility(View.VISIBLE);
        }

        Backk bk = new Backk(EventDetails.this);
        bk.execute();

        editDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDetails.this,addEvents.class);
                intent.putExtra("event_id",event_id);

                startActivity(intent);
            }
        });

    }

    class Backk extends AsyncTask<String, Void, String> {

        Context context;

        ProgressDialog progressDialog;
        String name,detailss,datee,to ,from,prizee,locationn,paytmm,googlepayy,sfee="",dfee="",gfee="";

        Backk(Context context) {
            this.context = context;
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

            progressDialog.dismiss();

            evName.setText(name);
            details.setText(detailss);
            tform.setText(from);
            tto.setText(to);
            date.setText(datee);
            location.setText(locationn);
            prize.setText(prizee);
            paytm.setText(paytmm);
            googlepay.setText(googlepayy);

            if(!sfee.equals("")){
               spfee.setText(sfee);
               spfee.setVisibility(View.VISIBLE);
               Log.e("pfee","solo");
            }if(!dfee.equals("")){
                dpfee.setText(dfee);
                dpfee.setVisibility(View.VISIBLE);
                Log.e("pfee","duet");
            }if(!gfee.equals("")){
                gpfee.setText(gfee);
                gpfee.setVisibility(View.VISIBLE);
                Log.e("pfee","group");
            }

//            pfee.setText(fee);

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
            Log.e("data Event_details",data);
            db.writedb(data);
            String result = db.readdb();
            Log.e("res",result);

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                name = jsonObject.getString("event_name");
                detailss = jsonObject.getString("event_description");
                datee = jsonObject.getString("start_date");
                from = jsonObject.getString("start_time");
                to = jsonObject.getString("end_time");
                prizee = jsonObject.getString("prize_money");
                locationn = jsonObject.getString("event_venue");
                paytmm = jsonObject.getString("paytm_number");
                googlepayy = jsonObject.getString("google_pay");

                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject json = jsonArray.getJSONObject(i);
                    Log.e("jsonarray",jsonObject.getString("participation_category"));

                    if(json.getString("participation_category").equals("solo")){
                        sfee = json.getString("participation_fee");
                    }if(json.getString("participation_category").equals("duet")){
                        dfee = json.getString("participation_fee");
                    }if(json.getString("participation_category").equals("group")){
                        gfee = json.getString("participation_fee");
                    }
                }

//                fee = jsonObject.getString("participation_fee");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}