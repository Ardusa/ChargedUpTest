package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain mDrivetrain;

    public static final TalonFX talon1 = new TalonFX(OperatorConstants.talon1_ID);
    public static final TalonFX talon2 = new TalonFX(OperatorConstants.talon2_ID);
    public static final TalonFX talon3 = new TalonFX(OperatorConstants.talon3_ID);
    public static final TalonFX talon4 = new TalonFX(OperatorConstants.talon4_ID);

    
    public static Drivetrain getInstance() {
        if (mDrivetrain == null) {
            mDrivetrain = new Drivetrain();
        }
        return mDrivetrain;
    }

    public static TalonFX[] getTalonFXs() {
        final TalonFX[] talons = { talon1, talon2,  talon3, talon4 };
        return talons;
    }

    public void setSpeed(double speed) {
        talon1.set(ControlMode.PercentOutput, speed);
        talon2.set(ControlMode.PercentOutput, speed);
        talon3.set(ControlMode.PercentOutput, speed);
        talon4.set(ControlMode.PercentOutput, speed);
    }
}
