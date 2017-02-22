package liggettech.dungeoncompanion.models;

public class Tool extends Item {

    public Tool() {

    }

    public Tool(String inName, int inCost, int inWeight, String inDescription) {

        super(inName, inCost, inWeight, inDescription);
    }

    public Tool (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }
}
