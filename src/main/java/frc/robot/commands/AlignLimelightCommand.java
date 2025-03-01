package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;
import frc.robot.subsystems.LimelightHelpersSubsystem;

public class AlignLimelightCommand extends Command {
    private LimelightHelpersSubsystem limelightHelpersSubsystem;

    public AlignLimelightCommand(LimelightHelpersSubsystem limelightHelpers) {
        this.limelightHelpersSubsystem = limelightHelpersSubsystem;

        addRequirements(limelightHelpers);
    }
    
    @Override
    public void execute() {
            limelightHelpersSubsystem.alignLimelight();


    }

    @Override
    public void end(boolean interrupted) {

    }
}