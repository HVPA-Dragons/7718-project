package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LiftShooterSubsystem extends SubsystemBase {

    private final TalonFX liftmotor1;
    private final TalonFX liftmotor2;
    private final SparkMax shooterMotor1;
    private final SparkMax shooterMotor2;
    private final SparkMaxConfig shooterMotor1config;
    private final SparkMaxConfig shooterMotor2config;
    private final Encoder liftEncoder;
    private final Encoder shooterEncoder;
    private final DigitalInput liftTopLimitSwitch;
    private final DigitalInput liftBottomLimitSwitch;
    private final DigitalInput shooterLimitSwitch;
    private final DigitalInput shooterProximitySensor1;

    private final PIDController shooterPID = new PIDController(0.05, 0, 0); //Tune these values
    private final PIDController liftPID = new PIDController(0.05, 0, 0); //Tune these values as well


    public LiftShooterSubsystem() {
        liftmotor1 = new TalonFX(14);
        liftmotor2 = new TalonFX(15);
        shooterMotor1 = new SparkMax(17,MotorType.kBrushless);
        shooterMotor1config = new SparkMaxConfig();
        shooterMotor1config
            .inverted(false)
            .idleMode(IdleMode.kBrake);
        shooterMotor1config.encoder
            .positionConversionFactor(1000)
            .velocityConversionFactor(1000);
        shooterMotor1config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(1.0, 0.0, 0.0);
        shooterMotor1.configure(shooterMotor1config, ResetMode.kResetSafeParameters,PersistMode.kPersistParameters);
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

        public void setScoringAngle(double scoringAngle) {
            double currentAngle = readNormalizedShooterEncoder();
            double output = shooterPID.calculate(currentAngle, scoringAngle);
        
            // Safety: Ensure we don't exceed limits
            if (!shooterLimitSwitch.get()) {
                shooterMotor1.set(0);
            } else {
                shooterMotor1.set(output);
            }
        }
        //public void setLift(double height)
        public void setIntakeHeight(double intakeHeight){
            double currentHeight = readNormalizedLiftEncoder();
            double output = liftPID.calculate(currentHeight, intakeHeight);
            
            // safety: makes sure we don't exceed limits
            if (!liftBottomLimitSwitch.get()) { 
                liftmotor1.set(0);
                liftmotor2.set(0);
            } else {
                liftmotor1.set(output);
                liftmotor2.set(output);
            }
        }

        public void setLevel0(double troughLevel){
            double currentLevel = readNormalizedLiftEncoder();
            double output = liftPID.calculate(currentLevel, troughLevel);

            // safety
            if (!liftBottomLimitSwitch.get()) {
                liftmotor1.set(0);
                liftmotor2.set(0);
            } else {
                liftmotor1.set(output);
                liftmotor2.set(output);
            }
        }

        public void setLevel1(double level1){
            double currentLevel = readNormalizedLiftEncoder();
            double output = liftPID.calculate(currentLevel, level1);

            if (!liftTopLimitSwitch.get()) {
                liftmotor1.set(0);
                liftmotor2.set(0);
            } else {
                liftmotor1.set(output);
                liftmotor2.set(output);
            }
        }
        
        public void setLevel2(double level2){
            double currentLevel = readNormalizedLiftEncoder();
            double output = liftPID.calculate(currentLevel, level2);

            if (!liftTopLimitSwitch.get()) {
                liftmotor1.set(0);
                liftmotor2.set(0);
            } else {
                liftmotor1.set(output);
                liftmotor2.set(output);
            }
        }

        public void setLevel3(double level3){
            double currentLevel = readNormalizedLiftEncoder();
            double output = liftPID.calculate(currentLevel, level3);

            if (!liftTopLimitSwitch.get()) {
                liftmotor1.set(0);
                liftmotor2.set(0);
            } else {
                liftmotor1.set(output);
                liftmotor2.set(output);
            }
        }
        
        
        
        public void setIntakeAngle(double intakeAngle){
            double currentAngle = readNormalizedShooterEncoder();
            double output = shooterPID.calculate(currentAngle, intakeAngle);

            if (!shooterLimitSwitch.get()) {
                shooterEncoder.reset();
                shooterMotor1.set(0);

            } else {
                shooterMotor1.set(output);
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
        

        public void intakeAlgae() {
            System.out.println("Intake Algae Run!");
            if (shooterMotor2.get()==0 && shooterProximitySensor1.get()){
                    shooterMotor2.set(-1.0);
                    }
            else if (!shooterProximitySensor1.get()){
                shooterMotor2.set(0);
                }
            }
        
        public void stopIntakeAlgae() {
            System.out.println("Stop Intake Algae Run!");
            shooterMotor1.set(0);
        }

        public void shootAlgae() {
            System.out.println("Shoot Algae!");
            if(shooterMotor2.get()==0 && shooterProximitySensor1.get()) {
                shooterMotor2.set(1.0);
            }
            else if (shooterProximitySensor1.get()){
                shooterMotor2.set(0.0);
            }
        }

        public void stopShootAlgae() {
            System.out.println("Stop Shoot Algae!");
            shooterMotor2.set(0);
        }

        public void intakeCoral() {
            // System.out.println("About To Check Sensor");
            // System.out.println(shooterProximitySensor1.getChannel());
            // System.out.println(shooterProximitySensor1.get());
            // System.out.println("About To Check Motor Speed");
            // System.out.println(shooterMotor2.get());
            // if(shooterProximitySensor1.get()){
            //     shooterMotor2.set(1.0);
            //     System.out.println("Intake");
            //     }
            // else {
            //     System.out.println("NOT INTAKE");
            //     shooterMotor2.set(0);
            // }
            System.out.println("Intake Coral Run!");
            if (shooterMotor2.get()==0 && shooterProximitySensor1.get()){
                    shooterMotor2.set(1.0);
                    }
            else if (!shooterProximitySensor1.get()){
                shooterMotor2.set(0.0);
                }
            }

            
        public void stopIntakeCoral(){
        System.out.println("Stop Intake Coral Run!");
        shooterMotor2.set(0);

        }

        public void shootCoral() {
            System.out.println("Shoot Coral!");
            if(shooterMotor2.get()==0 && !shooterProximitySensor1.get()) {
            shooterMotor2.set(-1.0);
            }
            else if (shooterProximitySensor1.get()){
                shooterMotor2.set(0);
            }
        }

        public void stopShootCoral() {
            System.out.print("Stop Shoot Coral");
            shooterMotor2.set(0);
        }

        public void scoreCoral(double height, double angle) {
            
        }

        public void scoreAlgae(double height, double angle) {
        
        }
        
        public void intakeLevel(double troughLevel, double intakeAngle, double scoringAngle) {
            setScoringAngle(scoringAngle); //safety
            setLevel0(troughLevel);
            setIntakeAngle(intakeAngle);
        }

        public void level1(double level1, double scoringAngle) {
            setScoringAngle(scoringAngle);
            setLevel1(level1);
        }

        public void level2(double level2, double scoringAngle) {
            setScoringAngle(scoringAngle);
            setLevel2(level2);

        }

        public void level3(double level3, double scoringAngle) {       
            setScoringAngle(scoringAngle);
            setLevel3(level3);

        }

        public void setIntake(double intakeHeight, double intakeAngle, double scoringAngle) {
            setScoringAngle(scoringAngle); //safety
            setIntakeHeight(intakeHeight);
            setIntakeAngle(intakeAngle);

        }

    }