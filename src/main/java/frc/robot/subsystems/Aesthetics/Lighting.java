package frc.robot.subsystems.Aesthetics;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Arm;

public class Lighting extends SubsystemBase {
    private static Lighting mLighting;
    
    private Spark blinken;

    public static Lighting getInstance() {
        if (mLighting == null) {
            mLighting = new Lighting();
        }
        return mLighting;
    }
    
    public Lighting() {
        blinken = new Spark(Arm.blinken_ID);
    }


    public void setLights(double PWMVal) {
        blinken.set(PWMVal);
    }

    public void killLights() {
        blinken.set(0.99);
    }
}