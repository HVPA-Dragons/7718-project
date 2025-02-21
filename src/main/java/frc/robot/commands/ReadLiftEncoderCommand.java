package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LiftShooterSubsystem;

public class ReadLiftEncoderCommand extends Command {
    private LiftShooterSubsystem liftShooterSubsystem;

    public ReadLiftEncoderCommand(LiftShooterSubsystem liftShooterSubsystem) {
        this.liftShooterSubsystem = liftShooterSubsystem;

        addRequirements(liftShooterSubsystem);
    }

    @Override
    public void execute() {
        liftShooterSubsystem.readNormalizedLiftEncoder();
    }

    @Override
    public void end(boolean interrupted) {
        
        

    }
}