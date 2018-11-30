package eu.faultycode.rpg.races;

public abstract class Player {
    private String name;
    private int exp;
    private int level;
    private int dmg;
    private int agility;
    private int HP;
    private int maxWeight;
    private boolean hasCamp;

    Player(String name, int basicDmg, int basicAgility, int basicHP, int maxWeight) {
        this.name = name;
        this.exp = 0;
        this.level = 1;
        this.hasCamp = false;
        setStats(basicDmg, basicAgility, basicHP, maxWeight);
    }

    Player(String name, int basicDmg, int basicAgility, int basicHP, int maxWeight, int level, int exp, boolean hasCamp) {
        this.name = name;
        this.exp = exp;
        this.level = level;
        this.hasCamp = hasCamp;
        setStats(basicDmg, basicAgility, basicHP, maxWeight);
        for(int i=0; i<level; ++i) {
            statsOnLevelUp();
        }
    }

    private void setStats(int basicDmg, int basicAgility, int basicHP, int maxWeight) {
        setDmg(basicDmg);
        setAgility(basicAgility);
        setHP(basicHP);
        setMaxWeight(maxWeight);
    }

    public void addExp(int exp) {
        setExp(this.exp + exp);
        while(isNextLevel()) {
            levelUp();
        }
    }

    private boolean isNextLevel(){
        return this.getExp() >= levelFunction();
    }

    private void levelUp() {
        setLevel(level + 1);
        statsOnLevelUp();
    }

    abstract void statsOnLevelUp();

    private int levelFunction() {
        return (int) Math.floor(Math.pow(50, (Math.pow(this.level, 1.0/4))));
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public int getAgility() {
        return agility;
    }

    public int getDmg() {
        return dmg;
    }

    public int getHP() {
        return HP;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public boolean hasCamp() {
        return hasCamp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setAgility(int agility) {
        if(agility > 0)
            this.agility = agility;
    }

    public void setDmg(int dmg) {
        if(dmg > 0)
            this.dmg = dmg;
    }

    public void setHP(int HP) {
        if(HP > 0)
            this.HP = HP;
    }

    public void setHasCamp(boolean hasCamp) {
        this.hasCamp = hasCamp;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}
