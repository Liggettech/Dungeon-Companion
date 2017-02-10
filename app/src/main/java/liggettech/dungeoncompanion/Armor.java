package liggettech.dungeoncompanion;

public class Armor {

    private String uid, name, description;


    public Armor() {

    }

    public Armor(String name, String description) {
        //this.uid = uid;
        this.name = name;
        this.description = description;
    }

    public void setUid (String uid) {
        this.uid = uid;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
