package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

/**
 * Example autonomous route. Each move uses the drivetrain's motor encoders.
 *
 * CAUTION: One "rotation" is one MOTOR rotation, not one wheel rotation or inch.
 * Confirm gear ratio, wheel direction, travel distance, and motor power on blocks.
 * Edit this route to match the current FTC season's starting position and goals.
 */
@Autonomous(name = "Auto Left")
public class AutoLeft extends LinearOpMode {
    private final Drivetrain drivetrain = new Drivetrain();
    private final Intake intake = new Intake();
    private final Launcher launcher = new Launcher();

    // FTC calls runOpMode() when the autonomous program is selected and started.
    // Code BEFORE waitForStart() prepares the robot; code AFTER it runs at Start.
    @Override public void runOpMode() {
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);
        launcher.init(hardwareMap);
        telemetry.addLine("Autonomous ready. Clear space before starting.");
        telemetry.update();
        waitForStart();
        if (!opModeIsActive() || isStopRequested()) return;
        try {
            // try/finally is a safety pattern: 'finally' runs even when a movement fails.
            // The third argument prevents a busy-wait after Stop is pressed.

            // Launcher example (UNCOMMENT ONLY after safe testing):
            launcher.setMotorPower(0.45);
            sleep(1500); // Allow flywheel to spin up.
            launcher.feedOn();
            sleep(1000);
            launcher.feedOff();

            // Adjust distances/speeds ONLY after testing physical robot motion.
            drivetrain.goForward(1, 0.20, this::opModeIsActive);
            drivetrain.goBackward(1, 0.20, this::opModeIsActive);
            drivetrain.turnLeft(1, 0.20, this::opModeIsActive);
            drivetrain.turnRight(1, 0.20, this::opModeIsActive);
            drivetrain.strafeLeft(1, 0.20, this::opModeIsActive);
            drivetrain.strafeRight(1, 0.20, this::opModeIsActive);

        } finally {
            drivetrain.stop();
            intake.off();
            launcher.stop();
        }
    }
}
