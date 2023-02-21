package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
public class rollerEject extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;

    public rollerEject(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Roller Eject Start");
    }

    @Override
    public void execute() {
        intakeSubsystem.rollerEject();
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopRollers();
        System.out.println("Roller Eject ended!");
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}