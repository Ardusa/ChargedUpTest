package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OperatorConstants;

public class Drivetrain extends SubsystemBase {
/*
 *  public static final TalonFX talon1 = new TalonFX(OperatorConstants.talon1_ID);
    public static final TalonFX talon2 = new TalonFX(OperatorConstants.talon2_ID);
    public static final TalonFX talon3 = new TalonFX(OperatorConstants.talon3_ID);
    public static final TalonFX talon4 = new TalonFX(OperatorConstants.talon4_ID);
 */
    private static Drivetrain mDrivetrain;
    
    public static Drivetrain getInstance() {
        if (mDrivetrain == null) {
            mDrivetrain = new Drivetrain();
        }    
        return mDrivetrain;
    }   
}
