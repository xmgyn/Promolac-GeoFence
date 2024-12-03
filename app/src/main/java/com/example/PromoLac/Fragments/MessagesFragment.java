package com.example.PromoLac.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.PromoLac.LocationWork.ServiceForLocation;
import com.example.PromoLac.NotificationsTrying.MessagesAdapter;
import com.example.PromoLac.NotificationsTrying.MessagesNotifications;
import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FileInputStream fi;
    ProgressBar progressBar;

    ObjectInputStream oi;
    RecyclerView recyclerView ;
    ArrayList<MessagesNotifications> messagesNotificationsArrayList;
    LinearLayoutManager layoutManager;
    MessagesAdapter messagesAdapter;
    private DatabaseReference databaseReference;
    ArrayList<MessagesNotifications> arrayList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance(String param1, String param2) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View Rview= inflater.inflate(R.layout.fragment_messages, container, false);
        progressBar =Rview.findViewById(R.id.progressBar_messageFragment);
//        messagesNotificationsArrayList = new ArrayList<MessagesNotifications>();
//        messagesNotificationsArrayList.add(new MessagesNotifications("1","KFC","Delivering in 1 min","2 min ago","Foods"));
//        messagesNotificationsArrayList.add(new MessagesNotifications("2","KFC-2","Delivering in 1 min","2 min ago","Foods"));
//        messagesNotificationsArrayList.add(new MessagesNotifications("3","KFC-3","Delivering in 1 min","2 min ago","Foods"));
//        messagesNotificationsArrayList.add(new MessagesNotifications("4","KFC-4","Delivering in 1 min","2 min ago","Foods"));
//        messagesNotificationsArrayList.add(new MessagesNotifications("5","KFC-5","Delivering in 1 min","2 min ago","Foods"));

        //File handling
        String fileName= "Messages.txt";
//        try {
//            messagesNotificationsArrayList=showData(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        recyclerView = (RecyclerView) Rview.findViewById(R.id.MessagesFragmentRecycleView);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(layoutManager);
        messagesNotificationsArrayList = new ArrayList<>(); // null /// htana hy
//        recyclerView.hasFixedSize(true);
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Messages").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot contact: dataSnapshot.getChildren()) {
                    MessagesNotifications m = contact.getValue(MessagesNotifications.class);
                    messagesNotificationsArrayList.add(m);
                    Log.d("googlegoogle", "onDataChange: "+contact.getValue()+ "\n");
                }
                messagesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        for (MessagesNotifications m:messagesNotificationsArrayList) {
//            databaseReference.child("UsersData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Messages").push().setValue(m);
//        }
//        progressBar.setVisibility(View.GONE);
//        databaseReference.child("UsersData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Messages").setValue(messagesNotificationsArrayList);

        messagesAdapter = new MessagesAdapter(messagesNotificationsArrayList);
        recyclerView.setAdapter(messagesAdapter);






        // Inflate the layout for this fragment
        return Rview;
    }

    private ArrayList<MessagesNotifications> showData(String fileName) throws IOException {
        try {
            arrayList = new ArrayList<MessagesNotifications>();

            String filePath = getContext().getFilesDir().getPath().toString() + fileName;
            File fileRoot = new File(filePath);
            fi = new FileInputStream(fileRoot);
            oi = new ObjectInputStream(fi);



//            arrayList.add((MessagesNotifications)oi.readObject());
//            // Read objects
            while (true){
                arrayList.add((MessagesNotifications)oi.readObject());
            }


        } catch ( EOFException e){
            oi.close();
            fi.close();
        } catch ( Exception e){
            Log.d("pakistan", "showData: "+e.toString());

            Toast.makeText(getContext(), "Failed to Retrieve"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return arrayList;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
