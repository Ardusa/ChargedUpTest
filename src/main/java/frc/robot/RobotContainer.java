package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.MusicCMD;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final XboxController xDrive = new XboxController(OperatorConstants.kDriverControllerPort);


    // private final Hand mHand = Hand.getInstance();
    // private final Arm mArm = Arm.getInstance();

  //private final Drivetrain mDrivetrain = Drivetrain.getInstance();
//   private final Vision mVision = Vision.getInstance();

    /* Driver Buttons
    private final JoystickButton zeroGyro = new JoystickButton(mDriverController, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(mDriverController, XboxController.Button.kLeftBumper.value);
    */

    /* Subsystems */

    private final Drivetrain mDrivetrain = new Drivetrain();
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        
        // if(mDriverController.getAButton()) {
        //     new Flatten(0.3); 
        // } else if(mDriverController.getBButton()) {
        //     new balance();
        // } else {
        //     s_Swerve.setDefaultCommand(
        //         new TeleopSwerve(
        //             () -> -mDriverController.getRawAxis(translationAxis), 
        //             () -> -mDriverController.getRawAxis(strafeAxis), 
        //             () -> -mDriverController.getRawAxis(rotationAxis), 
        //             () -> robotCentric.getAsBoolean()
        //         )
        //     );
        // }
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        // s_Swerve.setDefaultCommand(
        //     new TeleopSwerve(
        //         () -> -mDriverController.getRawAxis(translationAxis), 
        //         () -> -mDriverController.getRawAxis(strafeAxis), 
        //         () -> -mDriverController.getRawAxis(rotationAxis), 
        //         () -> true
        //     )
        // );
        // // Configure the button bindings

            //Music Selector  
        Music mMusic = Music.getInstance();
        mMusic.setDefaultCommand(new MusicCMD(
            Music.getInstance()
//            Music.talon1,
//            Music.talon2,
//            Music.talon3,
//            Music.talon4
        )
        );        

        // s_Arm.setShoulderMotorPower(mManipController.getLeftY());
        // s_Arm.setElbowMotorPower(mManipController.getRightY());


        // zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
