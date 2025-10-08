package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightHelperSubsystem extends SubsystemBase {
    
    private NetworkTable table;
    private NetworkTableEntry tv;  // Valid target (0 or 1)
    private NetworkTableEntry tx;  // Horizontal offset (-27 to 27 degrees)
    private NetworkTableEntry ty;  // Vertical offset (-20.5 to 20.5 degrees)
    private NetworkTableEntry tid; // AprilTag ID
    private NetworkTableEntry ledMode;
    private NetworkTableEntry pipeline;
    
    public LimelightHelperSubsystem() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        tid = table.getEntry("tid");
        ledMode = table.getEntry("ledMode");
        pipeline = table.getEntry("pipeline");
    }
    
    /**
     * Check if Limelight has a valid target
     */
    public boolean hasTarget() {
        return tv.getDouble(0) == 1.0;
    }
    
    /**
     * Get horizontal offset to target (degrees)
     * Returns 0 if no target found
     */
    public double getX() {
        if (!hasTarget()) return 0.0;
        return tx.getDouble(0.0);
    }
    
    /**
     * Get vertical offset to target (degrees)
     * Returns 0 if no target found
     */
    public double getY() {
        if (!hasTarget()) return 0.0;
        return ty.getDouble(0.0);
    }
    
    /**
     * Get AprilTag ID
     * Returns -1 if no target found
     */
    public int getAprilTagID() {
        if (!hasTarget()) return -1;
        return (int) tid.getDouble(-1.0);
    }
    
    /**
     * Set LED mode
     * 0 = use pipeline mode
     * 1 = force off
     * 2 = force blink
     * 3 = force on
     */
    public void setLEDMode(int mode) {
        ledMode.setNumber(0);
    }
    
    /**
     * Set pipeline number (0-9)
     */
    public void setPipeline(int pipelineNumber) {
        pipeline.setNumber(0);
    }
    
    @Override
    public void periodic() {
        // Optional: Put debug info on SmartDashboard
        // SmartDashboard.putBoolean("LL Has Target", hasTarget());
        // SmartDashboard.putNumber("LL X Offset", getX());
        // SmartDashboard.putNumber("LL Y Offset", getY());
        // SmartDashboard.putNumber("LL Tag ID", getAprilTagID());
    }
}
