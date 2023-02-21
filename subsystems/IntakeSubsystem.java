package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class IntakeSubsystem extends ProfiledPIDSubsystem {
    private boolean currentlyUp = true;
    private DigitalInput leftLimitSwitch = new DigitalInput(IntakeConstants.leftLimitSwitchChannel);
    private DigitalInput middleLimitSwitch = new DigitalInput(IntakeConstants.middleLimitSwitchChannel);
    private DigitalInput rightLimitSwitch = new DigitalInput(IntakeConstants.rightLimitSwitchChannel);
    private DigitalInput[] limitSwitches = {leftLimitSwitch, middleLimitSwitch, rightLimitSwitch};
    private CANSparkMax armMotor = new CANSparkMax(IntakeConstants.intakeMotorID, MotorType.kBrushless);
    private CANSparkMax intakeRoller1 = new CANSparkMax(IntakeConstants.intakeRoller1ID, MotorType.kBrushless);
    private CANSparkMax intakeRoller2 = new CANSparkMax(IntakeConstants.intakeRoller2ID, MotorType.kBrushless);
    private final RelativeEncoder m_encoder = armMotor.getEncoder();
    private final ArmFeedforward m_feedforward =
        new ArmFeedforward(
            IntakeConstants.kSVolts, IntakeConstants.kGVolts,
            IntakeConstants.kVVoltSecondPerRad, IntakeConstants.kAVoltSecondSquaredPerRad);
    public IntakeSubsystem() {
        super(
            new ProfiledPIDController(
                IntakeConstants.kP,
                IntakeConstants.kI,
                IntakeConstants.kD,
                new TrapezoidProfile.Constraints(
                    IntakeConstants.kMaxVelocityRadPerSecond,
                    IntakeConstants.kMaxAccelerationRadPerSecSquared)),
            0);
        m_encoder.setPositionConversionFactor(IntakeConstants.kEncoderDistancePerPulse);
        // Start arm at rest in neutral position
        setGoal(IntakeConstants.kArmOffsetRads);
      }
    public boolean getCurrentlyUp() {
        return currentlyUp;
    }
    @Override
    public void useOutput(double output, TrapezoidProfile.State setpoint) {
        System.out.println("trying to use output");
        // Calculate the feedforward from the sepoint
        double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
        // Add the feedforward to the PID output to get the motor output
        armMotor.setVoltage(output + feedforward);
      }
      @Override
    public double getMeasurement() {
        return m_encoder.getPosition() + IntakeConstants.kArmOffsetRads;
    }
    @Override
    public void periodic() {
    }
    public boolean isSwitchSet(int limitSwitchChannel) {
        return limitSwitches[limitSwitchChannel].get();
    }
    public void togglePosition() {
        if (currentlyUp) {
            System.out.println("trying to spin");
            //rollerFeed();
            armMotor.set(IntakeConstants.kOpenSpeed);
            System.out.println(2);
        } else {
            System.out.println("trying to spin");
            //stopRollers();
            armMotor.set(IntakeConstants.kCloseSpeed);
            System.out.println(2);
        }
    }
    public void rollerFeed() {
        intakeRoller1.set(-1*IntakeConstants.kRollerSpeed);
        intakeRoller2.set(IntakeConstants.kRollerSpeed);
    }
    public boolean getIntakePosition(){
        return currentlyUp;
    }
    public void togglePositionVar() {
        if (currentlyUp) {
            currentlyUp = false;
        } else {
            currentlyUp = true;
        }
    }
    public void rollerEject() {
        //intakeRoller1.set(IntakeConstants.kRollerSpeed);
        //intakeRoller2.set(-1*IntakeConstants.kRollerSpeed);
    }

    public void stopRollers() {
        //intakeRoller1.set(0.0);
        //intakeRoller1.set(0.0);
    }

    public void stop() {
        System.out.println("Stopping...");
        armMotor.set(0.0);
    }


}