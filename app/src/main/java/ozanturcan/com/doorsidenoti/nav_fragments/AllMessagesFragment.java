package ozanturcan.com.doorsidenoti.nav_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ozanturcan.com.doorsidenoti.Adapter.ExpandableListAdapter;
import ozanturcan.com.doorsidenoti.Models.DeviceInformations;
import ozanturcan.com.doorsidenoti.Models.MessagesDetails;
import ozanturcan.com.doorsidenoti.Operations.FirebaseDatabaseOperations;
import ozanturcan.com.doorsidenoti.R;

/**
 * Created by DELL on 23.10.2017.
 */

public class AllMessagesFragment extends Fragment {
    MessagesDetails messagesDetails = new MessagesDetails();
    DeviceInformations deviceInformations = new DeviceInformations().getAnInnerClass();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private  FirebaseDatabaseOperations FireDB = new FirebaseDatabaseOperations().getAnInnerClass();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FireDB.getAllMessagesFromUser();
        final  View rootview = inflater.inflate(R.layout.fragment_allmessages, container, false);

        // get the listview
        expListView = (ExpandableListView) rootview.findViewById(R.id.Expand_AllMsgList);
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                 Toast.makeText(rootview.getContext(),
                 "Group Clicked " + listDataHeader.get(groupPosition),
                 Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(rootview.getContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(rootview.getContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(
                        rootview.getContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
        return rootview;
    }


    /*
         * Preparing the list data
         */
    private void prepareListData() {

     Map<String,List<MessagesDetails>> DeviceMessages =  deviceInformations.getDeviceMessages();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data  Must be Message Subjec  like Subject + Token
//        listDataHeader.add("Token : nasd1na21js23dasd ");
//        listDataHeader.add("Token : 13asd123gasd211f1f ");
//        listDataHeader.add("Token : 12f12g13hrj12rhgf ");

//        // Adding child data Must be Message Details like Sender Message Phone vs
//        List<String> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemption");
//        top250.add("The Godfather");
//        top250.add("The Godfather: Part II");
//        top250.add("Pulp Fiction");
//        top250.add("The Good, the Bad and the Ugly");
//        top250.add("The Dark Knight");
//        top250.add("12 Angry Men");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");
//
        for (Map.Entry<String,List<MessagesDetails>> entry :
                DeviceMessages.entrySet()) {
            String key = entry.getKey();
            List<String> MessageList = new ArrayList<String>();
            List<MessagesDetails> MessagesDetailsList =  entry.getValue();
            for ( MessagesDetails messagesDetails : MessagesDetailsList)
            {
              String tempString = messagesDetails.getsTimestamp() + "-" +  messagesDetails.getsSubject().toUpperCase() + "\n" + " To:   " + messagesDetails.getsSender()  + "-" + messagesDetails.getsMessage()+ " " + messagesDetails.getsPhone()+ "\n" + messagesDetails.getsEmail();
              MessageList.add(tempString);
            }
            listDataChild.put(key,MessageList);
            listDataHeader.add(key);
        }


//        for (int a = 0 ; a < listDataHeader.size(); a++){
//        listDataChild.put(listDataHeader.get(a), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(++a), nowShowing);
//        listDataChild.put(listDataHeader.get(++a), comingSoon);
//        }


    }



}

