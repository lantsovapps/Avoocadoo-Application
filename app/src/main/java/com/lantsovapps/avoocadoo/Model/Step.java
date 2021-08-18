package com.lantsovapps.avoocadoo.Model;

public class Step {

    private int number;
    private String step;

    public Step() {
    }

    public Step(int number, String step) {
        this.number = number;
        this.step = step;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
