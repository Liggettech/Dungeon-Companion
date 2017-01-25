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

public class FragmentCharSpells extends Fragment {
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
    public static FragmentCharSpells newInstance(int sectionNumber) {
        FragmentCharSpells fragment = new FragmentCharSpells();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCharSpells() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_char_spells, container, false);

        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.listSpells);

        // preparing list data
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
        listDataHeader.add("Cantrips");
        listDataHeader.add("Level 1");

        // Adding child data
        List<String> level0 = new ArrayList<String>();
        level0.add("Null");

        List<String> level1 = new ArrayList<String>();
        level1.add("Null");

        listDataChild.put(listDataHeader.get(0), level0); // Header, Child data
        listDataChild.put(listDataHeader.get(1), level1);
    }
}