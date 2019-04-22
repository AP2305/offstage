package com.example.dell5559.offstage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class event_list_adapter extends ArrayAdapter<eventsListModel>{

    List<eventsListModel> eventList;
    Context context;
    int resource;

    public event_list_adapter(@NonNull Context context, int resource, List<eventsListModel> events) {
        super(context, resource,events);

        this.eventList = events;
        this.resource = resource;
        this.context = context;

    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView evid = (TextView) view.findViewById(R.id.eventsListName);
        final TextView evname = (TextView) view.findViewById(R.id.eventsListName);

        final eventsListModel event = eventList.get(position);

        evid.setText(event.getEvent_id());
        evname.setText(event.getEventName());

//        evname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Events...");
//                builder.setMessage(evname.getText());
//                builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        builder.setMessage(event.getEventName());
//                    }
//                });
//                AlertDialog al = builder.create();
//                al.show();

//            }
//        });

        return view;

    }

}
