package com.example.dell5559.offstage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ParticipantsAdapter extends ArrayAdapter<ParticipantModel>{

    List<ParticipantModel> participantModels;
    Context context;
    int resource;

    public ParticipantsAdapter(Context context,int resource ,List<ParticipantModel> participants) {
        super(context, resource, participants);
        this.participantModels = participants;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView tvname = view.findViewById(R.id.pname);
        TextView tvevent = view.findViewById(R.id.pevent);
        TextView partpid = view.findViewById(R.id.pid);

        ParticipantModel pm = participantModels.get(position);

        String pid = pm.getPid();
        String altcontact = pm.getAlt_contact();
        String contact = pm.getPcontact();
        String email = pm.getPemail();

        tvname.setText(pm.getPname());
        tvevent.setText(pm.getEvent_name());
        partpid.setText(pm.getCategory());

        return view;
    }
}
