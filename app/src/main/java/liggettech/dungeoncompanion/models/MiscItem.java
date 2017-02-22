package liggettech.dungeoncompanion.models;

public class MiscItem extends Item {

    private int speed;

    public MiscItem() {

    }

    public MiscItem(String inName, int inCost, int inWeight, String inDescription) {

        super(inName, inCost, inWeight, inDescription);
    }

    //for Vehicles
    public MiscItem(String inName, int inCost, int inCarryingCapacity, String inDescription, int inSpeed) {

        super(inName, inCost, inCarryingCapacity, inDescription);

        speed = inSpeed;
    }

    public MiscItem (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }

    public void setSpeed(int inSpeed) {
        speed = inSpeed;
    }

    public int getSpeed() {
        return speed;
    }
}
