package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class Level2Command extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public Level2Command(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
        liftShooterSubsystem.level2(30, 90);;


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.StopLift();

    }
}
