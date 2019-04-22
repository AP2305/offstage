package com.example.dell5559.offstage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerActivity extends AppCompatActivity {

    ListView listView;
    List<ExpenseModel> expenseModels;
    TextView inc,exp;
    String pos;
    String url;
//    int income=0,expense=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker);

        listView = (ListView) findViewById(R.id.listvieww);
//        inc = (TextView)findViewById(R.id.inc) ;
//        exp = (TextView)findViewById(R.id.exp) ;

        expenseModels = new ArrayList<>();

        Button btn = (Button)findViewById(R.id.expense);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseTrackerActivity.this,ExpenseActivity.class));
            }
        });

        backExpense backExpense = new backExpense(ExpenseTrackerActivity.this);
        backExpense.execute();

    }

    class backExpense extends AsyncTask<String,Void,String>{
        Context context;
        backExpense(Context con){
            this.context = con;
        }
        ProgressDialog progressDialog;
        Database db;
        String url = getString(R.string.url)+ "getTransactionList.php";


        @Override
        protected String doInBackground(String... strings) {

            Database db = new Database(url);
            String data = db.encodeData("event_id",new SessionManager(context).getKeyEventId());
            db.writedb(data);

            String result = db.readdb();

//tid,payeer,datetime,type,mode,amount,desc,image,sid,event_name
            expenseModels.clear();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String tid = jsonObject.getString("transaction_id");
                    String payeer = jsonObject.getString("payee_payer");
                    String dt = jsonObject.getString("transaction_datetime");
                    String type = jsonObject.getString("transaction_type");
                    String mode = jsonObject.getString("transaction_mode");
                    String amount = jsonObject.getString("transaction_amount");
                    String desc = jsonObject.getString("transaction_description");
                    String img = jsonObject.getString("transaction_proof");
                    String sid = jsonObject.getString("student_id");
                    String event_name = jsonObject.getString("event_name");
                    String isVerified = jsonObject.getString("isVerified");

                    expenseModels.add(new ExpenseModel(tid,payeer,dt,type,mode,amount,desc,img,sid,event_name,isVerified));

//                    if(type.equalsIgnoreCase("expense")){
//                        expense+= Integer.parseInt(amount);
//                    }else if (type.equalsIgnoreCase("income")){
//                        income+= Integer.parseInt(amount);
//                    }

//                    Log.e("income", String.valueOf(inc));
//                    Log.e("expense", String.valueOf(exp));
//                    inc.setText(income);
//                    exp.setText(expense);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
            return null;
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

            ExpenseAdapter exa = new ExpenseAdapter(context,R.layout.layout_expense_list,expenseModels);
            listView.setAdapter(exa);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    Log.e("item NO",expenseModels.get(i).getSid());

//payeer,datetime,type,mode,amount,desc,image,sid,event_name
                    AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                    builder.setTitle(expenseModels.get(i).getEvent_name());

                    LayoutInflater inflater = getLayoutInflater();
                    View alertview = inflater.inflate(R.layout.layout_expense_details,null);

                    TextView payeer = alertview.findViewById(R.id.payee);
                    TextView datetime = alertview.findViewById(R.id.datetime);
                    TextView type = alertview.findViewById(R.id.type);
                    TextView mode = alertview.findViewById(R.id.mode);
                    TextView amount = alertview.findViewById(R.id.amount);
                    TextView desc = alertview.findViewById(R.id.desc);
                    ImageView image = view.findViewById(R.id.billimage);
                    TextView sid = alertview.findViewById(R.id.sid);

                    final backverdel bvd = new backverdel(context);
                    Log.e("position", String.valueOf(i));
                    pos = expenseModels.get(i).getTid();
                    Log.e("tid",expenseModels.get(i).getTid());
                    Log.e("Tid", pos);

                    SessionManager sm = new SessionManager(context);

                  if(expenseModels.get(i).getIsverified().equalsIgnoreCase("0")&&(!sm.getKeyRole().equalsIgnoreCase("coordinator"))&&(!sm.getKeySid().equalsIgnoreCase(expenseModels.get(i).getSid()))) {


                      builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              String url = getString(R.string.url) + "expense_verify.php";
                              bvd.execute(url);
                          }
                      });
                  }
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String url = getString(R.string.url)+"expense_delete.php";
                            bvd.execute(url);
                        }
                    });
//

//                    Button verify = (Button)view.findViewById(R.id.verify);
//                    Button delete = (Button)view.findViewById(R.id.delete);
//
//                    verify.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            backverdel bvd = new backverdel(context);
//                            String url = getString(R.string.url)+"expense_verify.php";
//                            pos = expenseModels.get(i).getTid();
//                            bvd.execute(String.valueOf(pos));
//                        }
//                    });
//                    delete.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            backverdel bvd = new backverdel(context);
//                            String url = getString(R.string.url)+"expense_delete.php";
//                            pos = expenseModels.get(i).getTid();
//                            bvd.execute(String.valueOf(i));
//                        }
//                    });
//
//
//                    String img = getString(R.string.url)+expenseModels.get(i).getImage();
//                    Log.e("img",img);
//                    if (!img.equalsIgnoreCase("")) {
//                        try {
//                            URL newurl = new URL(img);
//                            Bitmap bmf = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//                            image.setImageBitmap(bmf);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }

                    payeer.setText(expenseModels.get(i).getPayeer());
                    datetime.setText(expenseModels.get(i).getDatetime());
                    type.setText(expenseModels.get(i).getType());
                    mode.setText(expenseModels.get(i).getMode());
                    amount.setText(expenseModels.get(i).getAmount());
                    desc.setText(expenseModels.get(i).getDesc());
                    sid.setText(expenseModels.get(i).getSid());
//
//                    builder.setView(datetime);
//                    builder.setView(payeer);
//                    builder.setView(sid);
//                    builder.setView(type);
//                    builder.setView(mode);
//                    builder.setView(amount);
//                    builder.setView(image);
//                    builder.setView(desc);
//
                    builder.setView(alertview);
                    builder.create();
                    builder.show();

//                    Toast.makeText(context,i,Toast.LENGTH_LONG).show();
//                    progressDialog.dismiss();

//                    Intent intent = new Intent(ExpenseTrackerActivity.this,);

                }
            });
        }
    }
//
    class backverdel extends AsyncTask<String,Void,String>{

        Context context;
        backverdel(Context con){
            this.context = con;
        }

        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0];
            Log.e("uuurrrlll",url);
            Database db = new Database(url);
            String data = db.encodeData("transaction_id",pos);
            Log.e("data",data);
            db.writedb(data);

            String result = db.readdb();

            Log.e("resultExpnseVerDel",result);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            backExpense backExpense = new backExpense(ExpenseTrackerActivity.this);
            backExpense.execute();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExpenseTrackerActivity.this,HomePage.class));
    }
}
