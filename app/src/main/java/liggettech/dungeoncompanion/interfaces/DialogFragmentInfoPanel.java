package liggettech.dungeoncompanion.interfaces;

import liggettech.dungeoncompanion.adapters.ExpandableListAdapter;
import liggettech.dungeoncompanion.models.Armor;
import liggettech.dungeoncompanion.R;
import liggettech.dungeoncompanion.data.ItemInfoDatabase;
import liggettech.dungeoncompanion.models.Gear;
import liggettech.dungeoncompanion.models.Item;
import liggettech.dungeoncompanion.models.MiscItem;
import liggettech.dungeoncompanion.models.Tool;
import liggettech.dungeoncompanion.models.TradeGood;
import liggettech.dungeoncompanion.models.Weapon;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class DialogFragmentInfoPanel extends DialogFragment {

    String uid;
    char category;
    ItemInfoDatabase db;

    public static DialogFragmentInfoPanel newInstance (String uid) {
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
            db = new ItemInfoDatabase(getActivity());
        }

        catch (Exception ex){
            category = 'X';
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = findAndShowItemInfo(inflater, container);

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

    private View findAndShowItemInfo(LayoutInflater inflater, ViewGroup container) {
        View view = null;
        Item baseInfo = new Item("null", 0, 0, "null");

        switch (category) {
            case 'A':
                view = inflater.inflate(R.layout.dialog_info_armor,container, false);

                Armor foundArmor = db.getArmorInfo(uid);

                baseInfo.setName(foundArmor.getName());
                baseInfo.setCost(foundArmor.getCost());
                baseInfo.setWeight(foundArmor.getWeight());
                baseInfo.setDescription(foundArmor.getDescription());

                //display Armor info
                View armorType = view.findViewById(R.id.textInfoArmorType);
                View armorClass = view.findViewById(R.id.textInfoArmorAC);

                ((TextView)armorType).setText(foundArmor.getType());
                ((TextView)armorClass).setText(foundArmor.getAC());

                if (foundArmor.getStrengthRequirement() > 0) {
                    View armorRowStrReq = view.findViewById(R.id.rowInfoArmorRequirement);
                    armorRowStrReq.setVisibility(View.VISIBLE);

                    View armorStrReq = view.findViewById(R.id.textInfoArmorRequirement);
                    ((TextView)armorStrReq).setText(foundArmor.getStrengthRequirementString());
                }


                if (foundArmor.getStealthDisadvantage()) {
                    View armorStealthDis = view.findViewById(R.id.rowInfoArmorStealth);

                    armorStealthDis.setVisibility(View.VISIBLE);
                }

                break;

            /*case 'E':
                view = inflater.inflate(R.layout.dialog_info_trade_good,container, false);

                TradeGood foundTradeGood = db.getTradeGoodInfo(uid);

                baseInfo.setName(foundTradeGood.getName());
                baseInfo.setCost(foundTradeGood.getCost());
                baseInfo.setWeight(foundTradeGood.getWeight());
                baseInfo.setDescription(foundTradeGood.getDescription());

                break;

            case 'G':
                view = inflater.inflate(R.layout.dialog_info_gear,container, false);

                Gear foundGear = db.getGearInfo(uid);

                baseInfo.setName(foundGear.getName());
                baseInfo.setCost(foundGear.getCost());
                baseInfo.setWeight(foundGear.getWeight());
                baseInfo.setDescription(foundGear.getDescription());

                break;

            case 'M':
                view = inflater.inflate(R.layout.dialog_info_misc_item,container, false);

                MiscItem foundMiscItem = db.getMiscItem(uid);

                baseInfo.setName(foundMiscItem.getName());
                baseInfo.setCost(foundMiscItem.getCost());
                baseInfo.setWeight(foundMiscItem.getWeight());
                baseInfo.setDescription(foundMiscItem.getDescription());

                break;

            case 'T':
                view = inflater.inflate(R.layout.dialog_info_tool,container, false);

                Tool foundTool = db.getToolInfo(uid);

                baseInfo.setName(foundTool.getName());
                baseInfo.setCost(foundTool.getCost());
                baseInfo.setWeight(foundTool.getWeight());
                baseInfo.setDescription(foundTool.getDescription());

                break;*/

            case 'W':
                view = inflater.inflate(R.layout.dialog_info_weapon,container, false);

                Weapon foundWeapon = db.getWeaponInfo(uid);

                baseInfo.setName(foundWeapon.getName());
                baseInfo.setCost(foundWeapon.getCost());
                baseInfo.setWeight(foundWeapon.getWeight());

                //Weapon proficiency text view
                View weaponProficiency = view.findViewById(R.id.textInfoWeaponProficiency);
                ((TextView)weaponProficiency).setText(foundWeapon.getProficiency());

                //Weapon damage text view
                View weaponDamage = view.findViewById(R.id.textInfoWeaponDamage);
                ((TextView)weaponDamage).setText(String.valueOf(foundWeapon.getDamage()));

                //Weapon damage type text view
                View weaponDamageType = view.findViewById(R.id.textInfoWeaponDamageType);
                ((TextView)weaponDamageType).setText(foundWeapon.getDamageType());

                // Weapon properties
                ExpandableListView expListView = (ExpandableListView) view.findViewById(R.id.listInfoWeaponProperties);
                expListView.setAdapter(foundWeapon.preparePropertiesList(getContext()));

                break;

            default:
                //view = null;
                view = inflater.inflate(R.layout.dialog_info_armor,container, false);

                break;
        }

        if (view != null) {
            View subjectTitle = view.findViewById(R.id.textInfoTitle);
            View subjectCost = view.findViewById(R.id.textInfoCost);
            View subjectWeight = view.findViewById(R.id.textInfoWeight);

            ((TextView) subjectTitle).setText(baseInfo.getName());
            ((TextView) subjectCost).setText(baseInfo.getFormatCost());
            ((TextView) subjectWeight).setText(baseInfo.getFormatWeight());

            //Description text view
            if (category != 'W') {
                View subjectDescription = view.findViewById(R.id.textInfoDescription);
                ((TextView) subjectDescription).setText(baseInfo.getDescription());
            }
        }

        //TODO: need to handle null exception
        return view;
    }
}