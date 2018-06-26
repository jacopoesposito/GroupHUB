package uniparthenope.grouphub;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView name_person, luogo_evento, data_evento, numero_like;
    private String nameEvento, luogoEvento, infoEvento, dataEvento;
    private LatLng coordinateEvento;
    private Long likeEvento;
    ImageView map_luogo, like;
    Button Link;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<Events, Events_View_Holder> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;
    private List<Events> mEventsList = new ArrayList<>();

    public HomeFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("EVENTS");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    for(DataSnapshot dSnapshot : ds.getChildren()) { //
                        Events eventsClass = dSnapshot.getValue(Events.class);
                        mEventsList.add(eventsClass);
                        nameEvento = eventsClass.getNameEvento();
                        luogoEvento = eventsClass.getLuogoEvento();
                        infoEvento = eventsClass.getInfoEvento();
                        dataEvento = eventsClass.getDataEvento();
                        likeEvento = eventsClass.getLike();

                        Log.d("TAG", "Nome Evento:" + mEventsList);
                    }
                    mFirebaseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(eventListener);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.home_fragment_barprogress);


        recyclerView = (RecyclerView)view.findViewById(R.id.home_fragment_recyclerview);
        recyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);


        return view;
    }

    public void onStart(){
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Events, Events_View_Holder>
                (Events.class, R.layout.card_view_evento, Events_View_Holder.class, myRef) {

            /*@Override
            public int getItemCount() {
                return mEventsList.size();
            }*/


            @Override
            public void populateViewHolder(final Events_View_Holder viewHolder, Events model, int position) {


                for(position = 0; position <= 9; position++){
                progressBar.setVisibility(View.INVISIBLE);
                //Events eventsClass = mEventsList.get(position);
                viewHolder.Person_Name(mEventsList.get(position).getNameEvento());
                viewHolder.Luogo_Evento(mEventsList.get(position).getLuogoEvento());
                viewHolder.Data_Eventp(mEventsList.get(position).getDataEvento());
                viewHolder.Numero_Like(mEventsList.get(position).getLike());
                Log.d("TAG", "Populate" + mEventsList.get(position).getNameEvento());
                Log.d("TAG", "Position 1:  "+ mEventsList.get(position));
                }


            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }


    public static class Events_View_Holder extends RecyclerView.ViewHolder {
        private final TextView name_person, luogo_evento, data_evento, numero_like;
        private final ConstraintLayout layout;
        private final ImageView like, map;
        private final Button info;
        final LinearLayout.LayoutParams params;

        public Events_View_Holder (final View itemView){
            super(itemView);
            name_person =(TextView)itemView.findViewById(R.id.name_person);
            luogo_evento = (TextView)itemView.findViewById(R.id.luogo_evento_cardview);
            data_evento = (TextView)itemView.findViewById(R.id.data_evento_cardview);
            numero_like = (TextView)itemView.findViewById(R.id.numero_like);
            like = (ImageView)itemView.findViewById(R.id.like_cardview);
            map = (ImageView)itemView.findViewById(R.id.map_luogo_evento);
            info = (Button) itemView.findViewById(R.id.link_evento_cardview);
            layout = (ConstraintLayout) itemView.findViewById(R.id.card_view_evento);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        private void Person_Name(String title) {
            name_person.setText(title);
        }

        private void Luogo_Evento(String title){
            luogo_evento.setText(title);
        }

        private void Data_Eventp(String title){
            data_evento.setText(title);
        }

        private void Numero_Like(Long title){
            numero_like.setText(String.valueOf(title));
        }

        private void Layout_hide() {
            params.height = 0;
            layout.setLayoutParams(params);
        }

    }

}
