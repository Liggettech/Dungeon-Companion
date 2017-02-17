package liggettech.dungeoncompanion.models;

public class Item {
    private String uid, name, description;
    private int cost, weight;

    public Item() {
        name = "null";
        cost = 0;
        weight = 0;
        description = "null";
    }

    public Item(String inName, int inCost, int inWeight, String inDescription) {
        //this.uid = uid;
        this.name = inName;
        this.cost = inCost;
        this.weight = inWeight;
        this.description = inDescription;
    }

    public void setUid (String inUID) {
        this.uid = inUID;
    }

    public void setName (String inName) {
        this.name = inName;
    }

    public void setCost (int inCost) {
        this.cost = inCost;
    }

    public void setCost (String inCost) {
        this.cost = Integer.parseInt(inCost);
    }

    public void setWeight (int inWeight) {
        this.weight = inWeight;
    }

    public void setWeight (String inWeight) {
        this.weight = Integer.parseInt(inWeight);
    }

    public void setDescription (String inDescription) {
        this.description = inDescription;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getFormatCost() {
        String formattedCost;

        if (cost / 100 >= 1) {

            formattedCost = String.valueOf(cost/100) + " GP";
        }

        else if (cost / 10 >= 1) {
            formattedCost = String.valueOf(cost/10) + " SP";
        }

        else {
            formattedCost = String.valueOf(cost) + " CP";
        }

        return formattedCost;
    }

    public int getWeight() {
        return weight;
    }

    public String getFormatWeight() {
        return String.valueOf(weight + " lb.");
    }

    public String getDescription() {
        return description;
    }
}
