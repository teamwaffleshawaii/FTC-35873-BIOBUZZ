package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Controls the four mecanum drive motors.
 *
 * A "subsystem" owns the hardware and methods for ONE part of the robot.
 * OpModes decide WHAT the robot should do; this class decides HOW the wheels do it.
 * Hardware names must match the Robot Configuration on the Driver Station.
 */
public class Drivetrain {
    private static final double TICKS_PER_ROTATION = 537.7; // Check gearbox/encoder before tuning!
    // An array groups four similar objects, allowing one loop to configure all motors.
    private final DcMotor[] motors = new DcMotor[4];
    private DcMotor leftFront, rightFront, leftBack, rightBack;

    // The hardware map links Java variables to the named devices on the robot.
    // Call init once BEFORE moving the robot, not on every controller loop.
    public void init(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        motors[0] = leftFront;
        motors[1] = rightFront;
        motors[2] = leftBack;
        motors[3] = rightBack;

        // BRAKE resists coasting when the driver releases the sticks.
        for (DcMotor motor : motors) motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Preserve the team's existing physical wiring conventions.
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        for (DcMotor motor : motors) motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /** Sets wheel powers in LF, RF, LB, RB order; each value is limited to [-1, 1]. */
    public void setWheelPowers(double lf, double rf, double lb, double rb) {
        leftFront.setPower(clip(lf));
        rightFront.setPower(clip(rf));
        leftBack.setPower(clip(lb));
        rightBack.setPower(clip(rb));
    }

    /**
     * Robot-centric mecanum: forward is relative to the front of the ROBOT.
     * forward > 0 drives toward the robot's front; strafe > 0 drives right.
     * The team previously used these same wheel-mixing signs.
     */
    // Adding forward, sideways, and turning commands is called mecanum mixing.
    // Each wheel needs its own combination because its rollers face a different way.
    public void driveRobotCentric(double forward, double strafe, double turn) {
        setWheelPowers(turn + forward + strafe,
                -turn + forward - strafe,
                turn + forward - strafe,
                -turn + forward + strafe);
    }

    public double getLeftFrontPower() { return leftFront.getPower(); }
    public double getRightFrontPower() { return rightFront.getPower(); }
    public double getLeftBackPower() { return leftBack.getPower(); }
    public double getRightBackPower() { return rightBack.getPower(); }

    /** Stops all four wheels. Call from an OpMode's finally block if possible. */
    public void stop() { setWheelPowers(0, 0, 0, 0); }

    /**
     * The field-centric OpMode still calculates wheel power using its existing math.
     * Keeping that math in the OpMode for now makes it easier for students to compare
     * the two driving styles before independently improving either algorithm.
     */

    /**
     * Encoder movement measured in MOTOR rotations (not inches).
     * Positive wheel rotations move each wheel forward; negative moves backward.
     *
     * IMPORTANT: This method blocks. It MUST be given an activity check and timeout
     * so an unplugged encoder or stalled motor cannot hang autonomous indefinitely.
     * This is a simple educational helper, not a path planner.
     */
    // These are signed rotation targets; the motor controller handles moving to them.
    // isActive is a small function supplied by Auto.java that checks for STOP.
    public void moveRotations(double lf, double rf, double lb, double rb, double power,
                              java.util.function.BooleanSupplier isActive) {
        // FTC Android supports System.nanoTime(); this avoids requiring an OpMode timer.
        int[] targets = {
                (int) (lf * TICKS_PER_ROTATION), (int) (rf * TICKS_PER_ROTATION),
                (int) (lb * TICKS_PER_ROTATION), (int) (rb * TICKS_PER_ROTATION)
        };
        for (int i = 0; i < motors.length; i++) {
            motors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors[i].setTargetPosition(targets[i]);
            // RUN_TO_POSITION moves toward the target using encoder feedback.
            motors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors[i].setPower(Math.abs(clip(power)));
        }
        // Record start time so we can stop waiting after five seconds.
        long start = System.nanoTime();
        try {
            while (isActive.getAsBoolean() && !Thread.currentThread().isInterrupted()
                    && (System.nanoTime() - start) < 5_000_000_000L) {
                // Wait until ALL motors arrive (not just the first one).
                boolean anyBusy = false;
                for (DcMotor motor : motors) anyBusy |= motor.isBusy();
                if (!anyBusy) break;
                Thread.yield();
            }
        } finally {
            // Motor power must be stopped even when a timeout occurs.
            stop();
            // Return to manual control so subsequent TeleOp commands work correctly.
            for (DcMotor motor : motors) motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void goForward(double rotations, double power, java.util.function.BooleanSupplier active) {
        moveRotations(rotations, rotations, rotations, rotations, power, active);
    }
    public void goBackward(double rotations, double power, java.util.function.BooleanSupplier active) {
        goForward(-rotations, power, active);
    }
    public void turnLeft(double rotations, double power, java.util.function.BooleanSupplier active) {
        moveRotations(-rotations, rotations, -rotations, rotations, power, active);
    }
    public void turnRight(double rotations, double power, java.util.function.BooleanSupplier active) {
        turnLeft(-rotations, power, active);
    }
    public void strafeLeft(double rotations, double power, java.util.function.BooleanSupplier active) {
        moveRotations(-rotations, rotations, rotations, -rotations, power, active);
    }
    public void strafeRight(double rotations, double power, java.util.function.BooleanSupplier active) {
        strafeLeft(-rotations, power, active);
    }

    private double clip(double power) { return Math.max(-1, Math.min(1, power)); }
}
