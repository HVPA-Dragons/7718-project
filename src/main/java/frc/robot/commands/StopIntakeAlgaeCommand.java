package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class StopIntakeAlgaeCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public StopIntakeAlgaeCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.stopIntakeAlgae();


    }

    @Override
    public void end(boolean interrupted) {
    }
}