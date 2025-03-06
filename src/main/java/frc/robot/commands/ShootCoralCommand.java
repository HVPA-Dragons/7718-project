package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class ShootCoralCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public ShootCoralCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.shootCoral();


    }

    @Override
    public void end(boolean interrupted) {
        liftShooterSubsystem.stopShootCoral();
    }
}