package eu.faultycode.rpg.races;

public class Human extends Player {
    private static final int BASIC_DMG = 10,
            BASIC_AGILITY = 10,
            BASIC_HP = 100,
            BASIC_MAX_WEIGHT = 150,
            DELTA_DMG = 2,
            DELTA_AGILITY = 1,
            DELTA_HP = 10;

    public Human() {
        super(BASIC_DMG, BASIC_AGILITY, BASIC_HP, BASIC_MAX_WEIGHT);
    }

    @Override
    void statsOnLevelUp() {
        setAgility(this.getAgility() + DELTA_AGILITY);
        setDmg(this.getDmg() + DELTA_DMG);
        setHP(this.getHP() + DELTA_HP);
    }
}
