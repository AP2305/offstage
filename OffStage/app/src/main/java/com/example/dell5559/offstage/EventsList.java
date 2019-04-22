package com.example.dell5559.offstage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsList extends AppCompatActivity {

    List<eventsListModel> eventsList;
    ListView lvevent;
    EditText evname;
    Button addevent;
    Database db;
    ArrayList<Integer> itemsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

//        final String[] grps = {"SOLO", "DUET", "GROUP"};
        eventsList = new ArrayList<>();
//         itemsSelected = new ArrayList();

        lvevent = (ListView) findViewById(R.id.eventlv);
        addevent = (Button) findViewById(R.id.AddEvent);

        if(new SessionManager(EventsList.this).getKeyRole().equalsIgnoreCase("coordinator"))
        {
            addevent.setVisibility(View.GONE);
        }
        final Backk bk = new Backk(EventsList.this);
        bk.execute();


        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventsList.this,addEvents.class));
            }
        });

//        eventsList.add(new eventsListModel("DJWars"));
//        eventsList.add(new eventsListModel("Battledrome"));
//        eventsList.add(new eventsListModel("Naach"));
//        eventsList.add(new eventsListModel("Footloose"));
//        eventsList.add(new eventsListModel("RagnaRock"));
//        eventsList.add(new eventsListModel("EdmNight"));
//        eventsList.add(new eventsListModel("Raaga"));
//        eventsList.add(new eventsListModel("Raaga"));
//        eventsList.add(new eventsListModel("Raaga"));
    }

    class Backk extends AsyncTask<String,Void,String> {

        Context context;
        ProgressDialog progressDialog;

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
        protected String doInBackground(String... strings) {

            getEvents();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            event_list_adapter eva = new event_list_adapter(context,R.layout.eventslv,eventsList);
            lvevent.setAdapter(eva);
            progressDialog.dismiss();

            lvevent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.e("item NO",eventsList.get(i).getEvent_id());

                Intent intent = new Intent(EventsList.this,EventDetails.class);
                Log.e("event_id",eventsList.get(i).getEvent_id());
                intent.putExtra("event_id",eventsList.get(i).getEvent_id());
                startActivity(intent);
            }
        });


        }
    }

    public void getEvents(){

        String url = getString(R.string.url)+"event_list.php";

        db = new Database(url);

        String result = db.readdb();
        Log.e("getEvent Result",result);

        try {
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                eventsList.add(new eventsListModel(jsonObject.getString("event_id"),jsonObject.getString("event_name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.disconnect();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EventsList.this,HomePage.class);
        startActivity(intent);
    }
}
