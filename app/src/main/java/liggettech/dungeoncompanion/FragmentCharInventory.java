package liggettech.dungeoncompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentCharInventory extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentCharInventory newInstance(int sectionNumber) {
        FragmentCharInventory fragment = new FragmentCharInventory();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCharInventory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_char_inventory, container, false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.listBagOfHolding);

        // populate list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return rootView;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Armor");            //UID starts with 'A'
        listDataHeader.add("Adventuring Gear"); //UID starts with 'G'
        listDataHeader.add("Tools");            //UID starts with 'T'
        listDataHeader.add("Trade Goods");      //UID starts with 'E'
        listDataHeader.add("Weapons");          //UID starts with 'W'
        listDataHeader.add("Misc.");            //UID starts with 'M'

        // Adding child data
        // Valid UID = Item@A0000
        List<String> armor = new ArrayList<String>();
        armor.add("Helmet@A0001");

        List<String> gear = new ArrayList<String>();
        gear.add("Torch@G0001");

        List<String> tools = new ArrayList<String>();
        tools.add("Herbalism@T0001");

        List<String> goods = new ArrayList<String>();
        goods.add("Wheat@E0001");

        List<String> weapons = new ArrayList<String>();
        weapons.add("Sword@W0001");

        List<String> misc = new ArrayList<String>();
        misc.add("Extra@M0001");

        listDataChild.put(listDataHeader.get(0), armor); // Header, Child data
        listDataChild.put(listDataHeader.get(1), gear);
        listDataChild.put(listDataHeader.get(2), tools);
        listDataChild.put(listDataHeader.get(3), goods);
        listDataChild.put(listDataHeader.get(4), weapons);
        listDataChild.put(listDataHeader.get(5), misc);
    }
}