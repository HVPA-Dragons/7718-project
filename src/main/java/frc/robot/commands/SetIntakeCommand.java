package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class SetIntakeCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public SetIntakeCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.setIntake(0, 0, 40);


    }

    @Override
    public void end(boolean interrupted) {

    }
}
