package eu.faultycode.rpg.items;

public abstract class WearableItem extends Item {
    private int minLevel;

    public WearableItem(int itemId, String itemName, int weight, int value, int minLevel) {
        super(itemId, itemName, weight, value);
        setMinLevel(minLevel);
    }

    public int getMinLevel() {
        return minLevel;
    }

    private void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }
}
