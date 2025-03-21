package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class SetAlgaeAngleCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public SetAlgaeAngleCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.setAlgaeAngle(180);


    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
