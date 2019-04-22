package com.example.dell5559.offstage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView name,role,cont,email;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView)findViewById(R.id.user_name);
        role = (TextView)findViewById(R.id.user_role);
        cont = (TextView)findViewById(R.id.user_contact);
        email = (TextView)findViewById(R.id.user_email);
        btn = (Button) findViewById(R.id.logout);

        final SessionManager sm = new SessionManager(ProfileActivity.this);

        name.setText(sm.getKeyUname());
        role.setText(sm.getKeyRole());
        cont.setText(sm.getKeyContactNo());
        email.setText(sm.getKeyEmail());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.logoutUser();

            }
        });

    }
}
