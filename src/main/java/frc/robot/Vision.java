package frc.robot;

import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.Arm;

public class Vision {
    
    private static Vision Instance;
    private double value;
   
    private final NetworkTable mLeftLimelight = NetworkTableInstance.getDefault().getTable("limelight-left");
    private DoubleArraySubscriber mLeftPosition = mLeftLimelight.getDoubleArrayTopic("botpose").subscribe(new double[] {});
    // private DoubleArraySubscriber mLeftTarget = mLeftLimelight.getDoubleArrayTopic("").subscribe(new double[] {});

    private final NetworkTable mRightLimelight = NetworkTableInstance.getDefault().getTable("limelight-right");
    private DoubleArraySubscriber mRightPosition = mRightLimelight.getDoubleArrayTopic("botpose").subscribe(new double[] {});
    // private DoubleArraySubscriber mRightTarget = mLeftLimelight.getDoubleArrayTopic("").subscribe(new double[] {});

    private final NetworkTable mDriverLimelight = NetworkTableInstance.getDefault().getTable("limelight-driver");
    private DoubleSubscriber mObjectTY = mDriverLimelight.getDoubleTopic("ty").subscribe(value);
    private DoubleSubscriber mObjectTX = mDriverLimelight.getDoubleTopic("tx").subscribe(value);


    public enum LimelightState {
        leftLimelight,
        rightLimelight,
        unknown
    }

    public static Vision getInstance() {
        if (Instance == null) {
            Instance = new Vision();
        }
        return Instance;
    }

    public boolean targetVisible(LimelightState limelight) {
        if(limelight.compareTo(LimelightState.leftLimelight) == 0) {
            if(mLeftLimelight.getEntry("ta").getDouble(100) != 0) {
                return true;
            } else {
                return false;
            }
        } else if(limelight.compareTo(LimelightState.rightLimelight) == 0) {
            if(mRightLimelight.getEntry("ta").getDouble(100) != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Utils.Vector3D getPosition(LimelightState Limelight) {
        if(Limelight.compareTo(LimelightState.leftLimelight) == 0) {
            return fromAT(mLeftPosition);
        } else if(Limelight.compareTo(LimelightState.rightLimelight) == 0) {
            return fromAT(mRightPosition);
        } else {
            return new Utils.Vector3D(0, 0, 0);
        }
    }

    public void switchPipelinesDrivetrain() {
        if(mDriverLimelight.getEntry("getpipe").getInteger(100) == 1) {
            mDriverLimelight.getEntry("pipeline").setValue("0");
        } else {
            mDriverLimelight.getEntry("pipeline").setValue("1");
        }
    }

    public double getDrivetrainDistance() {
        double theta1 = Arm.getInstance().getShoulderAngle();
        double ty = mObjectTY.get();
        double value = ((Constants.Arm.upperarmLength) * Math.sin((theta1 - 90 + ty))) / (Math.sin(270 - (2*theta1) -ty));
        // System.out.println(value);
        return value;
    }

    public double getDrivetrainAngle() {
        return mObjectTX.get();
    }

    // public Utils.Vector3D getLimelightPosition(LimelightState Limelight) {
    //     if(Limelight.compareTo(LimelightState.leftLimelight) == 1) {
    //         return forTarget(mLeftTarget);
    //     } else if(Limelight.compareTo(LimelightState.rightLimelight) == 1) {
    //         return forTarget(mRightTarget);
    //     } else {
    //         return new Utils.Vector3D(100, 100, 100);
    //     }
    // }

    /**
     * Calculates 3 Dimensional Coordinates from the information of the limelight
     * @param tx
     *            the horizontal angle of the target from the center of the limelight camera
     * @param ty
     *            the vertical angle of the target from the center of the limelight camera
     * @param distanceX
     *            The horizontal distance from the camera to the target
     * @param distanceY
     *             the vertical distance from the camera to the target
     */
    // public Utils.Vector3D fromLL(NetworkTable nt) {
    //     double tx = nt.getEntry("tx").getDouble(0);
    //     double ty = nt.getEntry("ty").getDouble(0);
    //     double distanceX = Constants.Vision.kTargetHeightDelta / (Math.tan(Utils.degToRad(ty + Constants.Vision.kLimelightVerticalAngle)));
    //     double distanceY = distanceY;

    //     return new Utils.Vector3D(distanceX * Math.acos(tx), distanceY, distanceX * Math.asin(tx));
    // }

    public Utils.Vector3D fromAT(DoubleArraySubscriber sub) {
        double[] positions = sub.get();
        return new Utils.Vector3D(positions[0], positions[1], positions[2]);
    }

    // public Utils.Vector3D forTarget(DoubleArraySubscriber sub) {
    //     double[] positions = sub.get();
    //     return new Utils.Vector3D(positions[0], positions[1], positions[2]);
    // }
}
