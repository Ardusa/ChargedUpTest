package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.subsystems.Hand;
import frc.robot.commands.AestheticsCMD.LightCMD;
import frc.robot.commands.AestheticsCMD.MusicCMD;
import frc.robot.commands.AestheticsCMD.WarningBeep;
import frc.robot.commands.Arm.PercentOutput;
import frc.robot.commands.Arm.SetArmPosition;
import frc.robot.commands.Hand.ToggleGrip;
import frc.robot.commands.Swerve.TeleopSwerve;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Aesthetics.Lighting;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.subsystems.Swerve;

public class PITTest extends SequentialCommandGroup {
    final PowerDistribution pdp = new PowerDistribution();
    final XboxController xDrive = new XboxController(0);

    double testSpeed = frc.robot.Constants.Swerve.testSpeed;

    final Command 
        translationTest, strafeTest, rotationTest,
        toggleGrip,
        setIntakePOS, setStowPOS, rawPower;
        // setLoadingZonePOS, setLowPOS, setMediumPOS, setHighPOS;

    Command[] commandList = {
        /* Swerve testing */
        translationTest = new TeleopSwerve
            (() -> testSpeed, () -> 0, () -> 0, () -> false),
        strafeTest = new TeleopSwerve
            (() -> 0, () -> testSpeed, () -> 0, () -> false),
        rotationTest = new TeleopSwerve
            (() -> 0, () -> 0, () -> testSpeed, () -> false),

        /* Arm Testing */
        toggleGrip = new ToggleGrip(),

        /* Hand Testing */
        setIntakePOS = new SetArmPosition(ArmPosition.kIntakePosition, false),
        setStowPOS = new SetArmPosition(ArmPosition.kStowPosition, true),
        rawPower = new PercentOutput(() -> xDrive.getRightY(), () -> xDrive.getLeftY()),
        // setLoadingZonePOS = new SetArmPosition(ArmPosition.kLoadingZonePosition, false),
        // setLowPOS = new SetArmPosition(ArmPosition.kLowPosition, false),
        // setMediumPOS = new SetArmPosition(ArmPosition.kMediumPosition, false),
        // setHighPOS = new SetArmPosition(ArmPosition.kHighPosition, false)
    };

    public ArrayList<Command> testedCommands;
    private Command currCommand;
    private int commandNum = 0;

    final WaitCommand wait;
    final Command beep;

    public PITTest() {
        this.addRequirements(Swerve.getInstance(), Hand.getInstance(), Arm.getInstance(), Lighting.getInstance());
        
        Shuffleboard.getTab("Pit Test");
        setName("PIT Test");
        SmartDashboard.putString("PIT Test Status", "Press A to begin test");

        testedCommands = new ArrayList<Command>();
        
        new LightCMD(0.73).schedule(); //Green
        beep = new WarningBeep();
        wait = new WaitCommand(1);
        new JoystickButton(xDrive, XboxController.Button.kA.value).onTrue(scheduleCommand(0).beforeStarting(beep).beforeStarting(wait));
        new POVButton(xDrive, 90).debounce(1).onTrue(scheduleCommand(commandNum++).beforeStarting(beep).beforeStarting(wait));
        new POVButton(xDrive, 270).debounce(1).onTrue(scheduleCommand(commandNum--).beforeStarting(beep).beforeStarting(wait));

        // if (input.getAsInt() == 90) {
        //     beep.schedule();
        //     scheduleCommand(commandNum++);
        // } else if (input.getAsInt() == 180) {
        //     beep.schedule();
        //     scheduleCommand(commandNum--);
        // } else {
        //     if (currCommand.isFinished()) {
        //         scheduleCommand(commandNum);
        //     }
        // }
    }

    private Command scheduleCommand(int commandNum) {
        this.commandNum = commandNum;

        if (commandList[commandNum] == null) {
            SmartDashboard.putString("PIT Test Status", "Completed");
        } else {
            Command x = commandList[commandNum];
            currCommand = x;
            SmartDashboard.putString("PIT Test Status", currCommand.getName());
            addCommands(x);
        }
        return null;
    }

    public Command musicCommand() {
        final Command musicCMD = new MusicCMD();
        new LightCMD(-0.81).schedule(); //Forrest Green Pallette
        return musicCMD;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Battery Voltage", () -> RobotController.getBatteryVoltage(), null);

        //Numbering system for drivetrain: 0 - front right, 1 - front left, 2 - back left, 3 - back right

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

        super.initSendable(builder);
    }
}
