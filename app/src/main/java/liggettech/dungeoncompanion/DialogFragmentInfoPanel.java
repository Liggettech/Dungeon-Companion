package liggettech.dungeoncompanion;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DialogFragmentInfoPanel extends DialogFragment {

    String uid, subjectName;
    char category;

    static DialogFragmentInfoPanel newInstance (String uid) {
        DialogFragmentInfoPanel panel = new DialogFragmentInfoPanel();

        Bundle args = new Bundle();
        args.putString("UID", uid);
        panel.setArguments(args);

        return panel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getArguments().getString("UID");

        try {
            category = uid.charAt(0);
        }
        catch (Exception ex){
            category = 'X';
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Object subject = findObjectInfo();

        View view ; //= inflater.inflate(R.layout.dialog_info_armor,container, false);

        switch (category) {
            case 'A':
                view = inflater.inflate(R.layout.dialog_info_armor,container, false);
                break;
            case 'W':
                view = inflater.inflate(R.layout.dialog_info_weapon,container, false);
                break;
            default:
                view = inflater.inflate(R.layout.dialog_info_armor,container, false);
                break;
        }

        subjectName = uid;
        View subjectTitle = view.findViewById(R.id.textInfoTitle);
        ((TextView)subjectTitle).setText(subjectName);

        final Dialog myDialog = getDialog();

        //Close button
        Button close = (Button) view.findViewById(R.id.btnInfoClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        return view;
    }

    private Object findObjectInfo() {
        Object subject = null;

        switch (category) {
            case 'A':
                //findArmor();
                break;
            case 'G':
                //findGear();
                break;
            case 'T':
                //findTool();
                break;
            case 'E':
                //findTradeGood();
                break;
            case 'W':
                //findWeapon();
                break;
            case 'M':
                //findMisc();
                break;
            case 'S':
                //findSpell();
                break;
            case 'X':
                subject =  null;
                break;
            default:
                subject = null;
                break;
        }

        return subject;
    }
}