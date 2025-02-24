package frc.robot.subsystems;

import frc.robot.utils.constants;
import frc.robot.utils.constants.liftShooterConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.servohub.config.ServoHubParameter;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class LiftShooterSubsystem extends SubsystemBase {

    private final TalonFX liftmotor1;
    private final TalonFX liftmotor2;
    private final Spark shooterMotor1;
    private final SparkMax shooterMotor2;
    private final SparkMaxConfig shooterMotor2config;
    private final Encoder liftEncoder;
    private final Encoder shooterEncoder;
    private final DigitalInput liftTopLimitSwitch;
    private final DigitalInput liftBottomLimitSwitch;
    private final double kLiftBottom;
    private final double kLiftTop;
    private final double kShooterTop;
    private final double kShooterBottom;
    private final DigitalInput shooterLimitSwitch;
    private final DigitalInput shooterProximitySensor1;




    public LiftShooterSubsystem() {
        liftmotor1 = new TalonFX(14);
        liftmotor2 = new TalonFX(15);
        shooterMotor1 = new Spark(16);
        shooterMotor2 = new SparkMax(16,MotorType.kBrushless);
        shooterMotor2config=new SparkMaxConfig();
        shooterMotor2config
            .inverted(false)
            .idleMode(IdleMode.kBrake);
        shooterMotor2config.encoder
            .positionConversionFactor(1000)
            .velocityConversionFactor(1000);
        shooterMotor2config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(1.0,0.0,0.0);
        shooterMotor2.configure(shooterMotor2config,ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);

        liftEncoder = new Encoder(0, 1);
        shooterEncoder = new Encoder(2,3);
        liftTopLimitSwitch = new DigitalInput(4);
        liftBottomLimitSwitch = new DigitalInput(5);
        shooterLimitSwitch = new DigitalInput(6);
        shooterProximitySensor1 = new DigitalInput(7);
        kLiftTop = constants.liftShooterConstants.kLiftTop;
        kLiftBottom = constants.liftShooterConstants.kLiftBottom;
        kShooterTop = constants.liftShooterConstants.kShooterTop;
        kShooterBottom = constants.liftShooterConstants.kShooterBottom;
        

        

    

    }
    
        public void StopLift() {
            liftmotor1.set(0);
            liftmotor2.set(0);
        }

        public void ZeroEncoder(){
            while (!liftBottomLimitSwitch.get()) {
                liftmotor1.set(-0.2);
                liftmotor2.set(-0.2);
            }
            liftmotor1.set(0);
            liftmotor2.set(0);
            liftEncoder.reset();
        }

        public void SetShooterAngle(double angle){
            //TODO figure out how to replace this method with a PID controller
            if (!shooterLimitSwitch.get()) {
               while(readNormalizedShooterEncoder() < angle); {
                 shooterMotor1.set(.2);
                }

                while(readNormalizedShooterEncoder() > angle); {
                 shooterMotor1.set( -.2);
                }

                while(readNormalizedShooterEncoder() == angle); {
                 shooterMotor1.set(0);
                }
            }


            else {

                liftmotor1.set(.0);
                liftmotor2.set(.0 );
                System.out.println("Shooter Stopped");

            }
        }

        public void setLift(double height){
            //TODO Replace with PID

            // Safety feature to ensure shooter is at appropriate angle before moving lift
            if(readNormalizedShooterEncoder() < 20); {
                liftmotor1.set(0);
                liftmotor2.set(0);
                SetShooterAngle(20);
            } 

            // If the lift is below set height raise the lift
            while(readNormalizedLiftEncoder() < height) {
                if(!liftTopLimitSwitch.get()) {
                 liftmotor1.set(.2);
                 liftmotor2.set(.2);
                }
                // Safety feature: if the limit switch is hit stop the lift
                else {
                 liftmotor1.set(0);
                 liftmotor2.set(0);
                 System.out.println("Lift Stopped-- Top Limit");
                }
                // Stop the lift at the correct hight
                while(readNormalizedLiftEncoder() == height) {
                    liftmotor1.set(0);
                    liftmotor2.set(0);
                }

            }

            // If the lift is above set height lower the lift
            while(readNormalizedLiftEncoder() > height) {
                if(!liftTopLimitSwitch.get()) {
                 liftmotor1.set(-.2);
                 liftmotor2.set(-.2);
                }
                // Safety feature: if the limit switch is hit stop the lift
                else {
                 liftmotor1.set(0);
                 liftmotor2.set(0);
                 System.out.println("Lift Stopped-- Bottom Limit");
                }

            }


            }
        
            //no manual lift (sals orders)
       


        public double readRawLiftEncoder() {

            return liftEncoder.getDistance();
        }

        public double readRawShooterEncoder() {

            return shooterEncoder.getDistance();
        }

        public double readNormalizedLiftEncoder() {
            // Method for normalizing encoder readings, making them easier to interact with
            double LiftPosition = liftEncoder.getDistance();
            double angleConversionFactor = 5.69; //TODO find conversion factor for lift

            double normalized = (LiftPosition / angleConversionFactor);
            System.out.println(normalized);
            return normalized;
        }


        public double readNormalizedShooterEncoder() {
            // Method for normalizing encoder readings, making them easier to interact with
            double ShooterPosition = shooterEncoder.getDistance();
            double angleConversionFactor = 5.69;

            double normalized = (ShooterPosition / angleConversionFactor);
            System.out.println(normalized);
            return normalized;
        }

        public void shootAlgae() {
            shooterMotor2.set(1.0);
        }
        
        public void shootCoral() {
            if(!shooterProximitySensor1.get()) {
            shooterMotor2.set(-1.0);
            }
        }

        public void intakeAlgae() {
            if(!shooterProximitySensor1.get()) {
                shooterMotor2.set(-1.0);

            }
        }

        public void intakeCoral() {
            System.out.println("About To Check Sensor");
            System.out.println(shooterProximitySensor1.getChannel());
            System.out.println(shooterProximitySensor1.get());
            System.out.println("About To Check Motor Speed");
            System.out.println(shooterMotor2.get());
            if(shooterProximitySensor1.get()){
                shooterMotor2.set(1.0);
                System.out.println("Intake");
                }
            else {
                System.out.println("NOT INTAKE");
                shooterMotor2.set(0);
            }
        }


        public void scoreCoral(double height, double angle) {
         setLift(height);

         SetShooterAngle(angle);
         shootCoral();
            
        }

        public void scoreAlgae(double height, double angle) {
            setLift(height);
   
            SetShooterAngle(angle);
            shootAlgae();
               
           }

                      
        
    

        


        

        

    }