package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightHelpersSubsystem extends SubsystemBase {
    private final NetworkTable limelightTable;

    public LimelightHelpersSubsystem() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    }

    // Gets the horizontal offset from crosshair to target (-27 to 27 degrees). 
    public double getTx() {
        return limelightTable.getEntry("tx").getDouble(0.0);
    }

    // Gets the vertical offset from crosshair to target (-20.5 to 20.5 degrees). 
    public double getTy() {
        return limelightTable.getEntry("ty").getDouble(0.0);
    }

    // Gets the target area (0% to 100% of image). 
    public double getTa() {
        return limelightTable.getEntry("ta").getDouble(0.0);
    }

    // Checks if the Limelight sees a valid target. 
    public boolean hasValidTarget() {
        return limelightTable.getEntry("tv").getDouble(0) == 1;
    }

    // Gets the robot's pose estimation from Limelight (based on AprilTags). 
    public double[] getBotPose() {
        return limelightTable.getEntry("botpose_wpiblue").getDoubleArray(new double[6]);
    }

    // Determines turn adjustment needed to align with the AprilTag. 
    public double getTurnAdjustment() {
        double kP = 0.02; // Proportional control constant
        double tx = getTx();
        return hasValidTarget() ? -tx * kP : 0.0; 
    }

    public void alignLimelight() {
        getTx();
        getTy();
        getTa();
        hasValidTarget();
        getBotPose();
        getTurnAdjustment();
    }
}


