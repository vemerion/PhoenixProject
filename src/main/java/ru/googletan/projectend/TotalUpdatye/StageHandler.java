package ru.googletan.projectend.TotalUpdatye;

public class StageHandler implements IStager
{
    private int stage = 0;

    @Override
    public int getStage() {return 0;}

    @Override
    public void addStage(){if(stage < 3) stage++;}

    @Override
    public void setStage(int stageIn) {stage = stageIn;}
}
