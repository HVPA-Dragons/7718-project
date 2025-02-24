package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SensorSuiteSubsystem extends SubsystemBase {

    private final DigitalInput shooterProximitySensor1;


    public SensorSuiteSubsystem() {
        // Port for the sensors
        shooterProximitySensor1 = new DigitalInput(11);
    }

    public void ReadIRSensor() {
        // TODO use the sensor for stuff
        System.out.println(shooterProximitySensor1.get());

    }

    public void StopRead() {
    }

    public Command ColorReadCommand() {
        return this.runEnd(this::ReadIRSensor, this::StopRead);
    }
}
/* Runs intake motors at half speed and runs back if a note is inserted */