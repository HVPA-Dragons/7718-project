package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class LiftUpCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public LiftUpCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.intakeAlgae();


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.StopLift();

    }
}