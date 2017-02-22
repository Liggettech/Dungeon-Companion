package liggettech.dungeoncompanion.models;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import liggettech.dungeoncompanion.adapters.ExpandableListAdapter;

public class Weapon extends Item {

    private String proficiency, damage, damageType, properties;
    private static final HashMap<String, String> propertiesMap;

    public Weapon() {

    }

    public Weapon(String inName, int inCost, int inWeight, String inDescription,
                 String inProficiency, String inDamage,
                 String inDamageType, String inProperties) {

        super(inName, inCost, inWeight, inDescription);

        proficiency = inProficiency;
        damage = inDamage;
        damageType = inDamageType;
        properties = inProperties;
    }

    public Weapon (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }

    static {
        propertiesMap = new HashMap<>();
        propertiesMap.put("Ammunition", "You can use a weapon that has the ammunition properly " +
                                        "to make a ranged attack only if you have ammunition to " +
                                        "fire from the weapon. Each time you attack with the " +
                                        "weapon, you expend one piece of ammunition. Drawing " +
                                        "the ammunition from a quiver, case, or other container " +
                                        "is part of the attack. At the end of the battle, you " +
                                        "can recover half your expended ammunition by taking a " +
                                        "minute to search the battlefield.\n\nIf you use a " +
                                        "weapon that has the ammunition property to make a melee " +
                                        "attack, you treat the weapon as an improvised weapon " +
                                        "(see 'Improvised Weapons' later in the section). A " +
                                        "sling must be loaded to deal any damage when used in " +
                                        "this way.");

        propertiesMap.put("Finesse",    "When making an attack with a finesse weapon, you use " +
                                        "your choice of your Strength or Dexterity modifier " +
                                        "for the attack and damage rolls. You must use the same " +
                                        "modifier for both rolls.");

        propertiesMap.put("Heavy",      "Small creatures have disadvantage on attack rolls with " +
                                        "heavy weapons. A heavy weapon's size and bulk make it " +
                                        "too large for a Small creature to use effectively.");

        propertiesMap.put("Lance",      "You have disadvantage when you use a lance to attack " +
                                        "a target within 5 feet of you. Also, a lance requires " +
                                        "two hands to wield when you aren't mounted.");

        propertiesMap.put("Light",      "A light weapon is small and easy to handle, making it " +
                                        "ideal for use when fighting with two weapons. See the " +
                                        "rules for two-weapon fighting in chapter 9.");

        propertiesMap.put("Loading",    "Because of the time required to load this weapon, you " +
                                        "can fire only one piece of ammunition from it when you " +
                                        "use an action, bonus action, or reaction to fire it, " +
                                        "regardless of the number of attacks you can normally " +
                                        "make.");

        propertiesMap.put("Net",        "A Large or smaller creature hit by a net is restrained " +
                                        "until it is freed. A net has no effect on creatures " +
                                        "that are formless, or creatures that are Huge or " +
                                        "larger. A creature can use its action to make a DC 10 " +
                                        "Strength check, freeing itself or another creature " +
                                        "within its reach on a success. Dealing 5 slashing " +
                                        "damage to the net (AC 10) also frees the creature " +
                                        "without harming it, ending the effect and destroying " +
                                        "the net.\n\nWhen you use an action, bonus action, or " +
                                        "reaction to attack with a net, you can make only one " +
                                        "attack regardless of the number of attacks you can " +
                                        "normally make.");

        propertiesMap.put("Range",      "A weapon that can be used to make a ranged attack has a " +
                                        "range shown in parentheses after the ammunition or " +
                                        "thrown property. The range lists two numbers. The " +
                                        "first is the weapon's normal range in feet, and the " +
                                        "second indicates the weapon's maximum range. When " +
                                        "attacking a target beyond normal range, you have " +
                                        "disadvantage on the attack roll. You can't attack a " +
                                        "target beyond the weapon's long range.");

        propertiesMap.put("Reach",      "This weapon adds 5 feet to your reach when you " +
                                        "attack with it.");

        propertiesMap.put("Silvered",   "Some monsters that have immunity or resistance to " +
                                        "non-magical weapons are susceptible to silver weapons, " +
                                        "so cautious adventurers invest extra coin to plate " +
                                        "their weapons with silver.");

        propertiesMap.put("Thrown",     "If a weapon has the thrown property, you can throw the " +
                                        "weapon to make a ranged attack. If the weapon is a " +
                                        "melee weapon, you use the same ability modifier for " +
                                        "that attack roll and damage roll that you would use for " +
                                        "a melee attack with the weapon.\n\nFor example, if you " +
                                        "throw a handaxe, you use your Strength, but if you " +
                                        "throw a dagger, you can use either your Strength or " +
                                        "your Dexterity, since the dagger has the finesse " +
                                        "property.");

        propertiesMap.put("Two-Handed", "This weapon requires two hands to use.");

        propertiesMap.put("Versatile",  "This weapon can be used with one or two hands. A damage " +
                                        "value in parentheses appears with the property-the " +
                                        "damage when the weapon is used with two hands to make a " +
                                        "melee attack.");
    }

    public void setProficiency (String inProficiency) {
        proficiency = inProficiency;
    }

    public void setDamage (String inDamage) {
        damage = inDamage;
    }

    public void setDamageType (String inDamageType) {
        damageType = inDamageType;
    }

    public void setProperties (String inProperties) {
        properties = inProperties;
    }

    public String getProficiency() {
        return proficiency;
    }

    public String getDamage() {
        return damage;
    }

    public String getDamageType() {
        return damageType;
    }

    public String getProperties() {
        return properties;
    }

    public ExpandableListAdapter preparePropertiesList(Context context) {

        //parse properties string
        String[] propertiesList = TextUtils.split(properties, ",");

        //declare header and child
        List<String> listDataHeader = new ArrayList<String>();
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

        //loop through all properties for weapon
        for (int i = 0; i < propertiesList.length; i++) {
            //make each property title a header
            listDataHeader.add(propertiesList[i].trim());

            //insert pair into list
            listDataChild.put(listDataHeader.get(i), findPropertyDescription(listDataHeader.get(i)));
        }

        return(new ExpandableListAdapter(context, listDataHeader, listDataChild));
    }

    private List<String> findPropertyDescription(String propertyName) {
        List<String> propertyDescription = new ArrayList<String>();

        if (propertyName.contains("Ammunition")) {
            propertyName = "Ammunition";
        }

        if (propertyName.contains("Thrown")) {
            propertyName = "Thrown";
        }

        if (propertyName.contains("Versatile")) {
            propertyName = "Versatile";
        }

        //find and get property description, append properties identifier
        propertyDescription.add(propertiesMap.get(propertyName) + "@P");

        return (propertyDescription);
    }
}
