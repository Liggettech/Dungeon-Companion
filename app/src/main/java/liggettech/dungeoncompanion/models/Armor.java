package liggettech.dungeoncompanion.models;

public class Armor extends Item {

    private String type, ac;
    private int strengthRequirement;
    private boolean stealthDisadvantage;

    public Armor() {

    }

    public Armor(String inName, int inCost, int inWeight, String inDescription,
                 String inType, String inAC,
                 int inStrengthRequirement, boolean inStealthDisadvantage) {

        super(inName, inCost, inWeight, inDescription);

        ac = inAC;
        type = inType;
        strengthRequirement = inStrengthRequirement;
        stealthDisadvantage = inStealthDisadvantage;
    }

    public Armor (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }

    public void setAC (String inAC) {
        ac = inAC;
    }

    public void setType (String inType) {
        type = inType;
    }

    public void setStrengthRequirement (int inStrengthRequirement) {
        strengthRequirement = inStrengthRequirement;
    }

    public void setStrengthRequirement (String inStrengthRequirement) {
        strengthRequirement = Integer.parseInt(inStrengthRequirement);
    }

    public void setStealthDisadvantage (boolean inStealthDisadvantage) {
        stealthDisadvantage = inStealthDisadvantage;
    }

    public void setStealthDisadvantage (String inStealthDisadvantage) {
        if (inStealthDisadvantage.equalsIgnoreCase("true") ||
                inStealthDisadvantage.equalsIgnoreCase("t")) {
            stealthDisadvantage = true;
        }

        else {
            stealthDisadvantage = false;
        }
    }

    public String getAC() {
        return ac;
    }

    public String getType() {
        return type;
    }

    public int getStrengthRequirement() {
        return strengthRequirement;
    }

    public String getStrengthRequirementString() {
        return String.valueOf(strengthRequirement);
    }

    public boolean getStealthDisadvantage() {
        return stealthDisadvantage;
    }

}
