package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightHelperSubsystem;

public class AlignToAprilTagCommand extends Command {
    
    private final CommandSwerveDrivetrain drivetrain;
    private final LimelightHelperSubsystem limelight;
    
    // Swerve request for robot-centric control
    private final SwerveRequest.RobotCentric driveRequest = new SwerveRequest.RobotCentric();
    
    // Tuning constants - adjust these for your robot
    private static final double KP_STEERING = 0.12;  // Proportional gain for turning
    private static final double KP_DISTANCE = 0.15;   // Proportional gain for forward/back
    private static final double MIN_COMMAND = 0.4;   // Minimum motor output to overcome friction
    private static final double MAX_SPEED = 3.0;      // Maximum speed (safety limit)
    
    // Tolerance values
    private static final double ANGLE_TOLERANCE = 2.0;    // Degrees
    private static final double DISTANCE_TOLERANCE = 3.0; // Inches (adjust based on your needs)
    
    // Target distance (how far you want to be from the tag)
    private static final double TARGET_DISTANCE = 24.0; // 24 inches from tag
    
    private int alignedCount = 0;
    private static final int ALIGNED_THRESHOLD = 10; // Must be aligned for 10 loops (0.2 sec)
    
    public AlignToAprilTagCommand(CommandSwerveDrivetrain drivetrain, LimelightHelperSubsystem limelight) {
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        
        addRequirements(drivetrain);
    }
    
    @Override
    public void initialize() {
        System.out.println("AlignToAprilTag command started");
        alignedCount = 0;
        limelight.setLEDMode(3); // Turn on LEDs
    }
    
    @Override
    public void execute() {
        // Check if we have a valid target
        if (!limelight.hasTarget()) {
            drivetrain.setControl(driveRequest.withVelocityX(0).withVelocityY(0).withRotationalRate(0));
            System.out.println("No AprilTag detected");
            alignedCount = 0;
            return;
        }
        
        // Get angle offset (horizontal)
        double xOffset = limelight.getX();
        
        // Get distance to target (you'll need to calculate this from ty)
        double distance = calculateDistance(limelight.getY());
        double distanceError = distance - TARGET_DISTANCE;
        
        // Calculate rotation rate (proportional control) - negative because positive xOffset = turn right
        double rotationRate = -xOffset * KP_STEERING;
        
        // Calculate forward speed based on distance (in m/s for swerve)
        double forwardSpeed = distanceError * KP_DISTANCE;
        
        // Apply minimum command to overcome friction
        if (Math.abs(rotationRate) > 0 && Math.abs(rotationRate) < MIN_COMMAND) {
            rotationRate = Math.copySign(MIN_COMMAND, rotationRate);
        }
        if (Math.abs(forwardSpeed) > 0 && Math.abs(forwardSpeed) < MIN_COMMAND) {
            forwardSpeed = Math.copySign(MIN_COMMAND, forwardSpeed);
        }
        
        // Clamp speeds to maximum
        rotationRate = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, rotationRate));
        forwardSpeed = Math.max(-MAX_SPEED, Math.min(MAX_SPEED, forwardSpeed));
        
        // Check if we're aligned
        boolean angleAligned = Math.abs(xOffset) < ANGLE_TOLERANCE;
        boolean distanceAligned = Math.abs(distanceError) < DISTANCE_TOLERANCE;
        
        if (angleAligned && distanceAligned) {
            alignedCount++;
            drivetrain.setControl(driveRequest.withVelocityX(0).withVelocityY(0).withRotationalRate(0));
        } else {
            alignedCount = 0;
            // Robot-centric: X = forward/back, Y = strafe, RotationalRate = spin
            drivetrain.setControl(driveRequest
                .withVelocityX(forwardSpeed)
                .withVelocityY(0)
                .withRotationalRate(rotationRate));
        }
        
        // Debug output
        System.out.printf("Tag ID: %d | X: %.2f° | Dist: %.2f in | Rot: %.2f | Fwd: %.2f%n",
                limelight.getAprilTagID(), xOffset, distance, rotationRate, forwardSpeed);
    }
    
    @Override
    public void end(boolean interrupted) {
        drivetrain.setControl(driveRequest.withVelocityX(0).withVelocityY(0).withRotationalRate(0));
        System.out.println("AlignToAprilTag ended. Interrupted: " + interrupted);
    }
    
    @Override
    public boolean isFinished() {
        // Command finishes when aligned for enough consecutive loops
        return alignedCount >= ALIGNED_THRESHOLD;
    }
    
    /**
     * Calculate distance to AprilTag based on vertical angle
     * This uses basic trigonometry. You'll need to measure:
     * - Height of AprilTag from ground
     * - Height of Limelight from ground
     * - Limelight mount angle
     */
    private double calculateDistance(double ty) {
        // These values need to be measured for YOUR robot
        final double LIMELIGHT_HEIGHT = 20.0;      // inches from ground
        final double APRILTAG_HEIGHT = 18.5;       // inches from ground (FRC standard)
        final double LIMELIGHT_ANGLE = 25.0;       // degrees (mount angle)
        
        double angleToTarget = LIMELIGHT_ANGLE + ty;
        double heightDifference = APRILTAG_HEIGHT - LIMELIGHT_HEIGHT;
        
        // tan(angle) = opposite / adjacent
        // adjacent = opposite / tan(angle)
        double distance = heightDifference / Math.tan(Math.toRadians(angleToTarget));
        
        return Math.abs(distance);
    }
}