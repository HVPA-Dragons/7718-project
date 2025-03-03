
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class ShootAlgaeCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public ShootAlgaeCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }
    
    @Override
    public void execute() {
            liftShooterSubsystem.shootAlgae();


    }

    @Override
    public void end(boolean interrupted) {
    }
}