package com.example.dell5559.offstage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddMember extends AppCompatActivity {

    Button addMember;
    SessionManager sm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        sm = new SessionManager(AddMember.this);
        addMember = (Button) findViewById(R.id.addmember);

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               backaddmem bak = new backaddmem(AddMember.this);
               bak.execute();
            }
        });

    }

    class backaddmem extends AsyncTask<String,Void,String>{

        Context context;

        backaddmem(Context con){
            this.context = con;
        }

        @Override
        protected String doInBackground(String... strings) {

            String url = getString(R.string.url)+"addMembers.php";

            Database db = new Database(url);

            String data = db.encodeData("student_id",sm.getKeyEventId());
            db.writedb(data);

            Log.e("event_id",data);

            String result = db.readdb();

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context,result,Toast.LENGTH_LONG).show();
        }
    }

}
