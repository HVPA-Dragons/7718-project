package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class StopIntakeCoralCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public StopIntakeCoralCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.stopIntakeCoral();


    }

    @Override
    public void end(boolean interrupted) {

    }
}
