package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class SetScoringAngleCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public SetScoringAngleCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.setScoringAngle(90);


    }

    @Override
    public void end(boolean interrupted) {
    }
}