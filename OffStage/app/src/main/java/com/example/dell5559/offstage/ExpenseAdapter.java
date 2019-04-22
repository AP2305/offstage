package com.example.dell5559.offstage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<ExpenseModel>{

    List<ExpenseModel> expList;
    Context context;
    int resource;
    String url;

    public ExpenseAdapter(@NonNull Context context, int resource, List<ExpenseModel> expense) {
        super(context, resource,expense);

        this.expList = expense;
        this.resource = resource;
        this.context = context;

    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView event = (TextView)view.findViewById(R.id.event);
        TextView name = (TextView)view.findViewById(R.id.Name);
        TextView payeer = (TextView)view.findViewById(R.id.payeer);
        TextView amt = (TextView)view.findViewById(R.id.amount);
        TextView ver = (TextView)view.findViewById(R.id.verified);

        ExpenseModel expense = expList.get(position);

        event.setText(expense.getEvent_name());
        name.setText(expense.getSid());
        payeer.setText(expense.getPayeer());
        amt.setText("Rs. " + expense.getAmount());

        if(expense.getIsverified().equalsIgnoreCase("0")){
            ver.setVisibility(View.INVISIBLE);
        }

        String datetime= expense.getDatetime();
        String desc = expense.getDesc();
        String image= expense.getImage();
        String mode = expense.getMode();
        String type = expense.getType();
        String tid = expense.getTid();

        return view;
    }
}
