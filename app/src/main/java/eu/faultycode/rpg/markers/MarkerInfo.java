package eu.faultycode.rpg.markers;

public abstract class MarkerInfo {
    private String title;
    private String content;
    private int rewardExp;

    public MarkerInfo(int id, int plotID, String title, String content, int rewardExp) {
        this.content = content;
        this.title = title;
        this.rewardExp = rewardExp;
    }

    public int getRewardExp() {
        return rewardExp;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
