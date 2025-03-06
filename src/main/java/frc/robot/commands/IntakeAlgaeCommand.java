package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class IntakeAlgaeCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public IntakeAlgaeCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.intakeAlgae();


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.stopIntakeAlgae();
    }
}