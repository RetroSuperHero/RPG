package eu.faultycode.rpg.items;

public abstract class Item {
    private int id;
    private String name;
    private int weight;
    private int stat;

    public Item(int itemId, String itemName, int weight, int value) {
        setId(itemId);
        setName(itemName);
        setWeight(weight);
        setStat(value);
    }

    public String getName() {
        return name;
    }

    public int getStat() {
        return stat;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    private void setName(String name) {
        if(!name.equals(""))
            this.name = name;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setWeight(int weight) {
        this.weight = weight;
    }

    private void setStat(int stat) {
        this.stat = stat;
    }
}
