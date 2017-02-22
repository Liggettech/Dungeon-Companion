package liggettech.dungeoncompanion.models;

public class TradeGood extends Item {

    public TradeGood() {

    }

    public TradeGood(String inName, int inCost, int inWeight, String inDescription) {

        super(inName, inCost, inWeight, inDescription);
    }

    public TradeGood (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }
}
