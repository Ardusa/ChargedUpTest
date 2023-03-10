package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hand extends SubsystemBase {

    private static Hand mInstance;
    private DoubleSolenoid mSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6,7);
    public boolean holdingCone = true;
    public boolean gripping = true;

    public static Hand getInstance() {
        if(mInstance == null) {
            mInstance = new Hand();
        }
        return mInstance;
    }

    public void periodic() {
        if(gripping) {
            mSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            mSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void setGripping(Boolean g) {
        gripping = g;
    }

    public void setHandHolding(boolean s) {
        holdingCone = s;
    }

    public boolean getGripping() {
        return mSolenoid.get() == DoubleSolenoid.Value.kForward;
    }

    public void setHolding(boolean h) {
        holdingCone = h;
    }

    public boolean getHolding() {
        return holdingCone;
    }
}