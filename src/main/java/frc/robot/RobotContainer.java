// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
//import frc.robot.commands.LiftUpCommand;
//import frc.robot.commands.ReadLiftEncoderCommand;
//import frc.robot.commands.ZeroLiftEncoderCommand;
import frc.robot.commands.IntakeCoralCommand;
import frc.robot.commands.ShootCoralCommand;
import frc.robot.commands.IntakeAlgaeCommand;
import frc.robot.commands.ShootAlgaeCommand;
import frc.robot.commands.SetIntakeCommand;
import frc.robot.commands.IntakeLevelCommand;
import frc.robot.commands.SetScoringAngleCommand;
import frc.robot.commands.AlignLimelightCommand;
import frc.robot.commands.Level1Command;
import frc.robot.commands.Level2Command;
import frc.robot.commands.Level3Command;
import frc.robot.commands.SetAlgaeAngleCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LiftShooterSubsystem;
import frc.robot.subsystems.LimelightHelpersSubsystem;



public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.5) // Add a 50% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);
    //private final CommandXboxController joystick2 = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
        var liftShooter = new LiftShooterSubsystem();
        var limelightHelpers = new LimelightHelpersSubsystem();
        //Command liftUpCommand = new LiftUpCommand(liftShooter);
        //Command readLiftEncoderCommand = new ReadLiftEncoderCommand(liftShooter);
        //Command zeroLiftEncoderCommand = new ZeroLiftEncoderCommand(liftShooter);
        Command intakeCoralCommand = new IntakeCoralCommand(liftShooter);
        Command shootCoralCommand = new ShootCoralCommand(liftShooter);
        Command setIntakeCommand = new SetIntakeCommand(liftShooter);
        Command setScoringAngleCommand = new SetScoringAngleCommand(liftShooter);
        Command intakeAlgaeCommand = new IntakeAlgaeCommand(liftShooter);
        Command shootAlgaeCommand = new ShootAlgaeCommand(liftShooter);
        Command intakeLevelCommand = new IntakeLevelCommand(liftShooter);
        Command level1Command = new Level1Command(liftShooter);
        Command level2Command = new Level2Command(liftShooter);
        Command level3Command = new Level3Command(liftShooter);
        Command alignLimelightCommand = new AlignLimelightCommand(limelightHelpers);
        Command setAlgaeAngleCommand = new SetAlgaeAngleCommand(liftShooter);
    
       
        

        //joystick2.a().onTrue(alignLimelightCommand); // Press A to align with AprilTag
        joystick.a().onTrue(intakeLevelCommand); // trough level
        joystick.b().onTrue(level1Command); // reef level 1 and algae processor
        joystick.y().onTrue(level2Command); // reef level 2 
        joystick.x().onTrue(level3Command); // reef level 3
        joystick.leftTrigger().onTrue(intakeCoralCommand); // intakes coral
        joystick.rightTrigger().onTrue(shootCoralCommand); // shoots coral
        joystick.leftBumper().onTrue(setIntakeCommand); // sets intake height and angle
        joystick.rightBumper().onTrue(setScoringAngleCommand); // sets shooter to scoring angle
        joystick.povRight().onTrue(intakeAlgaeCommand); // intakes algae
        joystick.povLeft().onTrue(shootAlgaeCommand); // shoots algae
        joystick.povDown().onTrue(setAlgaeAngleCommand); // sets the algae angle for intake and shooting
        
    
       
        
        
        

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        joystick.povUp().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
    }
    

    public Command getAutonomousCommand() {
        try{
        // Load the path you want to follow using its name in the GUI
        // PathPlannerPath path = PathPlannerPath.fromPathFile("testpath");

        // // Create a path following command using AutoBuilder. This will also trigger event markers.
        // return AutoBuilder.followPath(path);
        return new PathPlannerAuto("testauto");
    } catch (Exception e) {
        DriverStation.reportError("Big oops: " + e.getMessage(), e.getStackTrace());
        return Commands.none();
    }
    }
}
