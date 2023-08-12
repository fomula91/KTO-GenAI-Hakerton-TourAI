package com.example.tourai_joseph.model;

public class PersonalityRequest {
    private int[] openness, conscientiousness, extraversion, agreeableness, neuroticism = new int[5];

    public int[] getOpenness() {
        return this.openness;
    }
    public void setOpenness(int[] openness) {
        this.openness = openness;
    }

    public int[] getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(int[] conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public int[] getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(int[] extraversion) {
        this.extraversion = extraversion;
    }

    public int[] getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(int[] agreeableness) {
        this.agreeableness = agreeableness;
    }

    public int[] getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(int[] neuroticism) {
        this.neuroticism = neuroticism;
    }
}
