package com.example.dell5559.offstage;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsActivity extends AppCompatActivity {

    ListView listView;
    List<ParticipantModel> partss;
    String pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        if (ContextCompat.checkSelfPermission(ParticipantsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ParticipantsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        else{
            Log.e("CALL_PHONE","Call permission not Granted");
        }

        listView = (ListView)findViewById(R.id.listview);

        partss = new ArrayList<>();

//        partss.add(new ParticipantModel("2","Ankit Dave","8756463289","9856321478","ankit@gmail.com","Naach"));
//        partss.add(new ParticipantModel("3","Mehul Sharma","9856247130","9856321478","mehul3@gmail.com","Naach"));
//        partss.add(new ParticipantModel("4","keyur Khatri","7785410026","9856321478","keyurkhatri@gmail.com","Naach"));
//        partss.add(new ParticipantModel("5","Yash Jhangid","9985636225","9856321478","yashj@gmail.com","Naach"));
//        partss.add(new ParticipantModel("6","jaini Shah","7789554102","9856321478","jain87@gmail.com","Naach"));
//        partss.add(new ParticipantModel("7","Soniya Shah","8856997420","9856321478","sshah@gmail.com","Naach"));
//        partss.add(new ParticipantModel("8","Vatsal Dave","8874136959","9856321478","vatsal69@gmail.com","Naach"));
        final Backk bk = new Backk(ParticipantsActivity.this);
        bk.execute();
//        ParticipantsAdapter patadapt = new ParticipantsAdapter(ParticipantsActivity.this,R.layout.layout_participation,partss);
//        listView.setAdapter(patadapt);


    }

    class Backk extends AsyncTask<String,Void,String>{

        Context context;
        Backk (Context con){
            this.context = con;
        }
        ProgressDialog progressDialog;
        Database db;
        String url = getString(R.string.url)+ "getParticipants.php";


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


            ParticipantsAdapter patadapt = new ParticipantsAdapter(context,R.layout.layout_participation,partss);
            listView.setAdapter(patadapt);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("item NO",partss.get(i).getPid());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);

                    builder.setTitle(partss.get(i).getPname());

                    LayoutInflater inflater = getLayoutInflater();
                    View alertView = inflater.inflate(R.layout.layout_participants,null);
                    final TextView contact = alertView.findViewById(R.id.contact);
                    TextView altcontact = alertView.findViewById(R.id.contact2);
                    TextView email = alertView.findViewById(R.id.email);
                    contact.setText(partss.get(i).getPcontact());
                    altcontact.setText(partss.get(i).getAlt_contact());
                    email.setText(partss.get(i).getPemail());

                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+contact.getText()));//change the number
                            if (ContextCompat.checkSelfPermission(ParticipantsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ParticipantsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                            }
                            else{
                                Log.e("CALL_PHONE","Call permission not Granted");
                            }
                            if (ContextCompat.checkSelfPermission(ParticipantsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(context,"Grant Call Pemission",Toast.LENGTH_LONG).show();
                            }else{
                                startActivity(callIntent);
                            }
                        }
                    });
                    altcontact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+contact.getText()));//change the number
                            if (ContextCompat.checkSelfPermission(ParticipantsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ParticipantsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                            }
                            else{
                                Log.e("CALL_PHONE","Call permission not Granted");
                            }
                            if (ContextCompat.checkSelfPermission(ParticipantsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(context,"Grant Call Pemission",Toast.LENGTH_LONG).show();
                            }else{
                                startActivity(callIntent);
                            }
                        }
                    });

                    if(partss.get(i).getIsPresent().equalsIgnoreCase("0")) {
                        final backverdel bvd = new backverdel(context);
                        Log.e("position", String.valueOf(i));
                        pos = partss.get(i).getPid();
                        Log.e("tid", partss.get(i).getPid());
                        Log.e("Tid", pos);


                        builder.setPositiveButton("PRESENT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bvd.execute();
                            }
                        });
                    }

                    builder.setView(alertView);
//                    builder.setView(contact);
//                    builder.setView(altcontact);
//                    builder.setView(email);
                    builder.create();
                    builder.show();
                }
            });

            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... strings) {

            db = new Database(url);

            String data = db.encodeData("event_id",new SessionManager(context).getKeyEventId());
            Log.e("Participants Activity ",data);
            db.writedb(data);
            String result = db.readdb();
            partss.clear();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String pid= jsonObject.getString("participation_id");
                    String pname = jsonObject.getString("participant_name");
                    String pcon = jsonObject.getString("participant_contact");
                    String altcontact = jsonObject.getString("participant_alt_contact");
                    String pemail = jsonObject.getString("participant_email");
                    String evname = jsonObject.getString("event_name");
                    String isPresent = jsonObject.getString("is_present");
                    String category = jsonObject.getString("participation_category");

                    partss.add(new ParticipantModel(pid,pname,pcon,altcontact,pemail,evname,isPresent,category));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
    class backverdel extends AsyncTask<String,Void,String>{

        Context context;
        backverdel(Context con){
            this.context = con;
        }

        String url = getString(R.string.url)+"participation_present.php";

        @Override
        protected String doInBackground(String... strings) {

//            String url = strings[0];
            Log.e("uuurrrlll",url);
            Database db = new Database(url);
            String data = db.encodeData("participation_id",pos);
            Log.e("data",data);
            db.writedb(data);

            String result = db.readdb();

            Log.e("resultExpnseVerDel",result);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Backk backExpense = new Backk(ParticipantsActivity.this);
            backExpense.execute();
        }
    }
}
