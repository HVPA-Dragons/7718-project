package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class IntakeLevelCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public IntakeLevelCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
        liftShooterSubsystem.intakeLevel(10, 120, 90 );;


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.StopLift();

    }
}
