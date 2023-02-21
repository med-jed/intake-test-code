package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
public class IntakeToggleCmd extends CommandBase {
    private final double initialPos;
    private final IntakeSubsystem intakeSubsystem;
    public IntakeToggleCmd(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        initialPos = intakeSubsystem.getMeasurement();
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("IntakeSetCmd started!");
    }

    @Override
    public void execute() {
        if (intakeSubsystem.getIntakePosition()) {
            intakeSubsystem.setGoal(initialPos+10);
            intakeSubsystem.enable();
        } else {
            intakeSubsystem.setGoal(initialPos+10);
            intakeSubsystem.enable();
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Stopping...");
        intakeSubsystem.disable();
        intakeSubsystem.stop();
        intakeSubsystem.togglePositionVar();
        System.out.println("IntakeSetCmd ended!");
    }

    @Override
    public boolean isFinished() {
        if (intakeSubsystem.getMeasurement()>=initialPos+25*Math.PI && intakeSubsystem.getIntakePosition()){
            return true;
        } else if (intakeSubsystem.getMeasurement()<=initialPos-25*Math.PI && !intakeSubsystem.getIntakePosition()) {
            return true;
        }
        return false;
    }
}