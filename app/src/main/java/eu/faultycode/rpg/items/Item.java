package eu.faultycode.rpg.items;

public abstract class Item {
    private int id;
    private String name;
    private int weight;
    private int value;

    public Item(int itemId, String itemName, int weight, int value) {
        setId(itemId);
        setName(itemName);
        setWeight(weight);
        setValue(value);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(!name.equals(""))
            this.name = name;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    private void setWeight(int weight) {
        this.weight = weight;
    }

    private void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
