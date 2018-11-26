package eu.faultycode.rpg.races;

public abstract class Player {
    private int exp;
    private int level;
    private int dmg;
    private int agility;

    public Player(int basicDmg, int basicAgility) {
        this.exp = 0;
        this.level = 1;
        setStats(basicDmg, basicAgility);
    }

    private void setStats(int basicDmg, int basicAgility) {
        this.dmg = basicDmg;
        this.agility = basicAgility;
    }

    public void addExp(int exp) {
        this.exp += exp;
        while(isNextLevel()) {
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        statsUp();
    }

    private void statsUp() {
    }

    private boolean isNextLevel(){
        return this.exp >= levelFunction();
    }

    private int levelFunction() {
        return (int) Math.floor(Math.pow(50, (Math.pow(this.level, 1.0/4))));
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }
}
