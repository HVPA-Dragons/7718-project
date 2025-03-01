package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class Level3Command extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public Level3Command(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
        liftShooterSubsystem.level3(30, 90);;


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.StopLift();

    }
}
