package liggettech.dungeoncompanion.models;

public class Gear extends Item {

    public Gear() {

    }

    public Gear(String inName, int inCost, int inWeight, String inDescription) {

        super(inName, inCost, inWeight, inDescription);
    }

    public Gear (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }
}
