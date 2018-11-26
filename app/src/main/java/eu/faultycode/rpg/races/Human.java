package eu.faultycode.rpg.races;

public class Human extends Player {
    private static final int BASIC_DMG = 10;
    private static final int BASIC_AGILITY = 10;

    public Human() {
        super(BASIC_DMG, BASIC_AGILITY);
    }
}
