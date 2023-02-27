package frc.robot;

import java.util.ArrayList;
import java.util.function.IntSupplier;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Hand;
import frc.robot.commands.AestheticsCMD.LightCMD;
import frc.robot.commands.AestheticsCMD.MusicCMD;
import frc.robot.commands.Arm.PercentOutput;
import frc.robot.commands.Arm.SetArmPosition;
import frc.robot.commands.Hand.ToggleGrip;
import frc.robot.commands.Swerve.TeleopSwerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Aesthetics.Lighting;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Swerve;

public class PITTest extends SequentialCommandGroup {
    // private frc.robot.subsystems.Swerve mSwerve = new frc.robot.subsystems.Swerve();
    // private Arm mArm = new Arm();

    final PowerDistribution pdp = new PowerDistribution();
    final XboxController xDrive = new XboxController(0);
    final WaitCommand wait = new WaitCommand(2);

    double testSpeed = frc.robot.Constants.Swerve.testSpeed;

    /* Swerve testing */
    final Command translationTest = new TeleopSwerve(() -> testSpeed, () -> 0, () -> 0, () -> false);
    final Command strafeTest = new TeleopSwerve(() -> 0, () -> testSpeed, () -> 0, () -> false);
    final Command rotationTest = new TeleopSwerve(() -> 0, () -> 0, () -> testSpeed, () -> false);

    /* Arm Testing */
    final Command toggleGrip = new ToggleGrip();

    /* Hand Testing */
    final Command setIntakePOS = new SetArmPosition(ArmPosition.kIntakePosition, false);
    final Command setStowPOS = new SetArmPosition(ArmPosition.kStowPosition, true);
    final Command rawPower = new PercentOutput(() -> xDrive.getRightY(), () -> xDrive.getLeftY());
    final Command setLoadingZonePOS = new SetArmPosition(ArmPosition.kLoadingZonePosition, false);
    final Command setLowPOS = new SetArmPosition(ArmPosition.kLowPosition, false);
    final Command setMediumPOS = new SetArmPosition(ArmPosition.kMediumPosition, false);
    final Command setHighPOS = new SetArmPosition(ArmPosition.kHighPosition, false);

    public ArrayList<Command> testedCommands = new ArrayList<Command>();

    private Command[] commandlist = {
        /* Swerve testing */
        translationTest,
        strafeTest,
        rotationTest,

        /* Arm Testing */
        setIntakePOS,
        setStowPOS,
        rawPower,
 
        /* Hand Testing */
        toggleGrip,
        toggleGrip
    };

    private IntSupplier input;
    private Command currCommand;
    private int commandNum = 0;

    public PITTest() {
        this.addRequirements(Swerve.getInstance(), Hand.getInstance(), Arm.getInstance(), Lighting.getInstance());
        
        setName("PIT Test");
        SmartDashboard.putString("PIT Test", "Test Starting");

        Shuffleboard.getTab("Pit Test");
        SmartDashboard.putString("PIT Test Status", "Initizalizing");

        if (input.getAsInt() == 90) {
            scheduleCommand(commandNum++);
        } else if (input.getAsInt() == 180) {
            scheduleCommand(commandNum--);
        } else {
            if (currCommand.isFinished()) {
                scheduleCommand(commandNum);
            }
        }

        new LightCMD(0.73 /* Green */ ).schedule();
    }

    private void scheduleCommand(int commandNum) {
        this.commandNum = commandNum;
        Command x = commandlist[commandNum];
        currCommand = x;
        SmartDashboard.putString("PIT Test Status", currCommand.getName());

        addCommands(x.beforeStarting(wait));
    }

    public Command musicCommand() {
        final Command musicCMD = new MusicCMD();
        new LightCMD(-0.81 /* Forrest Green Pallette */).schedule();
        return musicCMD;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Battery Voltage", () -> RobotController.getBatteryVoltage(), null);
        
        /* Front Left Metrics */
        builder.addDoubleProperty("Drivetrain Front Left Drive Current", () -> pdp.getCurrent(Constants.Swerve.Mod0.driveMotorID), null);
        builder.addDoubleProperty("Drivetrain Front Left Drive Temperature", () -> Swerve.getInstance().mSwerveMods[1].TalonDriveTemperature(), null);
        builder.addDoubleProperty("Drivetrain Front Left Angle Current", () -> pdp.getCurrent(Constants.Swerve.Mod0.angleMotorID), null);
        builder.addDoubleProperty("Drivetrain Front Left Angle Temperature", () -> Swerve.getInstance().mSwerveMods[1].TalonAngleTemperature(), null);
        builder.addDoubleProperty("Drivetrain Front Left Module Can Coder", () -> Swerve.getInstance().mSwerveMods[1].getCanCoder().getRotations(), null);

        /* Front Right Metrics */
        builder.addDoubleProperty("Drivetrain Front Right Drive Current", () -> pdp.getCurrent(Constants.Swerve.Mod1.driveMotorID), null);
        builder.addDoubleProperty("Drivetrain Front Right Drive Temperature", () -> Swerve.getInstance().mSwerveMods[0].TalonDriveTemperature(), null);
        builder.addDoubleProperty("Drivetrain Front Right Angle Current", () -> pdp.getCurrent(Constants.Swerve.Mod1.angleMotorID), null);
        builder.addDoubleProperty("Drivetrain Front Right Angle Temperature", () -> Swerve.getInstance().mSwerveMods[0].TalonAngleTemperature(), null);
        builder.addDoubleProperty("Drivetrain Front Right Module Can Coder", () -> Swerve.getInstance().mSwerveMods[0].getCanCoder().getRotations(), null);

        /* Back Left Metrics */
        builder.addDoubleProperty("Drivetrain Back Left Drive Current", () -> pdp.getCurrent(Constants.Swerve.Mod2.driveMotorID), null);
        builder.addDoubleProperty("Drivetrain Back Left Drive Temperature", () -> Swerve.getInstance().mSwerveMods[2].TalonDriveTemperature(), null);
        builder.addDoubleProperty("Drivetrain Back Left Angle Current", () -> pdp.getCurrent(Constants.Swerve.Mod2.angleMotorID), null);
        builder.addDoubleProperty("Drivetrain Back Left Angle Temperature", () -> Swerve.getInstance().mSwerveMods[2].TalonAngleTemperature(), null);
        builder.addDoubleProperty("Drivetrain Back Left Module Can Coder", () -> Swerve.getInstance().mSwerveMods[2].getCanCoder().getRotations(), null);

        /* Back Right Metrics */
        builder.addDoubleProperty("Drivetrain Back Right Drive Current", () -> pdp.getCurrent(Constants.Swerve.Mod3.driveMotorID), null);
        builder.addDoubleProperty("Drivetrain Back Right Drive Temperature", () -> Swerve.getInstance().mSwerveMods[3].TalonDriveTemperature(), null);
        builder.addDoubleProperty("Drivetrain Back Right Angle Current", () -> pdp.getCurrent(Constants.Swerve.Mod3.angleMotorID), null);
        builder.addDoubleProperty("Drivetrain Back Right Angle Temperature", () -> Swerve.getInstance().mSwerveMods[3].TalonAngleTemperature(), null);
        builder.addDoubleProperty("Drivetrain Back Right Module Can Coder", () -> Swerve.getInstance().mSwerveMods[3].getCanCoder().getRotations(), null);

        /* Shoulder Metrics */
        builder.addDoubleProperty("Arm Shoulder Motor Current", () -> pdp.getCurrent(Constants.Arm.shoulderMotorID), null);
        builder.addDoubleProperty("Arm Shoulder Motor Can Coder", () -> Arm.getInstance().getCanCoder("shoulder"), null);
        builder.addDoubleProperty("Arm Shoulder Motor Temperature", () -> Arm.getInstance().getTemperature("shoulder"), null);
        
        /* Elbow Metrics */
        builder.addDoubleProperty("Arm Elbow Motor Current", () -> pdp.getCurrent(Constants.Arm.elbowMotorID), null);
        builder.addDoubleProperty("Arm Elbow Motor Can Coder", () -> Arm.getInstance().getCanCoder("elbow"), null);
        builder.addDoubleProperty("Arm Elbow Motor Temperature", () -> Arm.getInstance().getTemperature("elbow"), null);
        
        
        //Numbering system for drivetrain: 0 - front right, 1 - front left, 2 - back left, 3 - back right

        
        super.initSendable(builder);
    }
}
