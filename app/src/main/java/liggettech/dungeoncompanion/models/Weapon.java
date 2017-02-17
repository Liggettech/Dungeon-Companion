package liggettech.dungeoncompanion.models;

public class Weapon extends Item {

    private String proficiency, damage, damageType;
    private String properties;

    public Weapon() {

    }

    public Weapon(String inName, int inCost, int inWeight, String inDescription,
                 String inProficiency, String inDamage,
                 String inDamageType, String inProperties) {

        super(inName, inCost, inWeight, inDescription);

        this.proficiency = inProficiency;
        this.damage = inDamage;
        this.damageType = inDamageType;
        this.properties = inProperties;
    }

    public Weapon (Item inItem) {
        super(inItem.getName(), inItem.getCost(), inItem.getWeight(), inItem.getDescription());
    }

    public void setProficiency (String inProficiency) {
        this.proficiency = inProficiency;
    }

    public void setDamage (String inDamage) {
        this.damage = inDamage;
    }

    public void setDamageType (String inDamageType) {
        this.damageType = inDamageType;
    }

    public void setProperties (String inProperties) {
        this.properties = inProperties;
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
}
