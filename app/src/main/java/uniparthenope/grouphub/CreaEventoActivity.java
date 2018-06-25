package uniparthenope.grouphub;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreaEventoActivity extends AppCompatActivity {

    public static DatabaseReference mDatabase;
    public EditText NomeEventoEdit, LuogoEventoEdit, InfoEventoEdit, DataEventoEdit;
    public Button localita, avanti, annulla;
    private String USER_ID, EVENTS_ID;
    public static boolean enable_buttons = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crea_evento_activity);

        USER_ID = SignUpActivity.Email_Logged.replace("@","").replace(".","");


        NomeEventoEdit = (EditText) findViewById(R.id.nome_evento_crea);
        LuogoEventoEdit = (EditText) findViewById(R.id.luogo_evento_crea);
        InfoEventoEdit = (EditText) findViewById(R.id.info_evento_crea);
        DataEventoEdit = (EditText) findViewById(R.id.data_evento_crea);

        localita = (Button) findViewById(R.id.localita_evento_crea);
        avanti = (Button) findViewById(R.id.avanti);
        annulla = (Button) findViewById(R.id.annulla);

        if(enable_buttons == false){
            NomeEventoEdit.setEnabled(false);
            LuogoEventoEdit.setEnabled(false);
            InfoEventoEdit.setEnabled(false);
            DataEventoEdit.setEnabled(false);
            avanti.setEnabled(false);
        }else{
            NomeEventoEdit.setEnabled(true);
            LuogoEventoEdit.setEnabled(true);
            InfoEventoEdit.setEnabled(true);
            DataEventoEdit.setEnabled(true);
            avanti.setEnabled(true);
        }
        localita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomeEventoEdit.setEnabled(true);
                LuogoEventoEdit.setEnabled(true);
                InfoEventoEdit.setEnabled(true);
                DataEventoEdit.setEnabled(true);
                avanti.setEnabled(true);
                enable_buttons = true;
                Intent MappaEvento = new Intent(CreaEventoActivity.this, CreaEventoMapActivity.class);
                startActivity(MappaEvento);
                finish();


            }
        });

        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getNomeEvento = NomeEventoEdit.getText().toString().trim();
                String getLuogoEvento = LuogoEventoEdit.getText().toString().trim();
                String getInfoEvento = InfoEventoEdit.getText().toString().trim();
                String getDataEvento = DataEventoEdit.getText().toString();
                callEventsRegister(getNomeEvento, getLuogoEvento, getInfoEvento, getDataEvento);
                Intent ReturnAfterJob = new Intent(CreaEventoActivity.this, MainActivity.class);
                startActivity(ReturnAfterJob);
                finish();
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent annulla = new Intent(CreaEventoActivity.this, MainActivity.class);
                startActivity(annulla);
                finish();
            }
        });

    }



    private void callEventsRegister(String NomeEvento, String LuogoEvento, String InfoEvento, String DataEvento){

        LatLng cordinate=getIntent().getExtras().getParcelable("coordinate");




        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        EVENTS_ID = db.getReference(USER_ID).push().getKey();

        DatabaseReference ref1= mRootRef.child("EVENTS").child(USER_ID).child(EVENTS_ID);

        ref1.child("NameEvento").setValue(NomeEvento);
        ref1.child("LuogoEvento").setValue(LuogoEvento);
        ref1.child("InfoEvento").setValue(InfoEvento);
        ref1.child("DataEvento").setValue(DataEvento);
        ref1.child("Like").setValue(0);
        ref1.child("Coordinate").setValue(cordinate);

        Toast.makeText(CreaEventoActivity.this, "Evento Creato con Successo", Toast.LENGTH_SHORT).show();
        Log.d("TESTING", "Evento Creato");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
