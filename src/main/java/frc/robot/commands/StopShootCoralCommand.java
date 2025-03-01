package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class StopShootCoralCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public StopShootCoralCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.stopShootCoral();


    }

    @Override
    public void end(boolean interrupted) {

    }
}

