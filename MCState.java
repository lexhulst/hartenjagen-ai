// Used to store the state of the Monte Carlo simulation

public class MCState{
    private double pointObtained;
    private int nrOfRuns;

    public MCState(int pointObtained){
        this.pointObtained = pointObtained / 32;
        this.nrOfRuns = 1;
    };

    public void update(int pointObtained){
        this.pointObtained += pointObtained / 32;
        this.nrOfRuns++;
    }

    public double value(int nrOfRunsParent){
        return pointObtained / nrOfRuns + Math.sqrt(2 * Math.log(nrOfRunsParent) / nrOfRuns);
    }
}