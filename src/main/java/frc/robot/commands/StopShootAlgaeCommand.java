package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class StopShootAlgaeCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public StopShootAlgaeCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.stopShootAlgae();


    }

    @Override
    public void end(boolean interrupted) {

    }
}
