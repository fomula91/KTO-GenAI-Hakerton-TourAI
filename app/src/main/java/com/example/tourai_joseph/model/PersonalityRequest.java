package com.example.tourai_joseph.model;

public class PersonalityRequest {
    private int[] openness = new int[5];
    private int[] conscientiousness = new int[5];
    private int[] extraversion = new int[5];
    private int[] agreeableness = new int[5];
    private int[] neuroticism = new int[5];

    public int[] getOpenness() {
        return this.openness;
    }
    public void setOpenness(int[] openness) {
        this.openness = openness;
    }

    public int[] getConscientiousness() {
        return this.conscientiousness;
    }

    public void setConscientiousness(int[] conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public int[] getExtraversion() {
        return this.extraversion;
    }

    public void setExtraversion(int[] extraversion) {
        this.extraversion = extraversion;
    }

    public int[] getAgreeableness() {
        return this.agreeableness;
    }

    public void setAgreeableness(int[] agreeableness) {
        this.agreeableness = agreeableness;
    }

    public int[] getNeuroticism() {
        return this.neuroticism;
    }

    public void setNeuroticism(int[] neuroticism) {
        this.neuroticism = neuroticism;
    }
}
