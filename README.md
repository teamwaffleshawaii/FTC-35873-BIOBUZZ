## NOTICE
FTC 35873, Team Waffles, FTC SDK for the BIOBUZZ (2026-2027) competition season.
Running on FTC SDK Version 12.0 (20260907-090034)

## Variable Configurations
**Control Hub:**

Motor 0: intakeMotor

Motor 1: rightFrontMotor

Motor 2: leftFrontMotor

Motor 3: launchMotor

Servo 0: intakeServoRight

Servo 1: intakeServoLeft

Servo 2: launchServo

**Expansion Hub:**

Motor 0: rightBackMotor

Motor 1: leftBackMotor

# FTC 35873: Finding your way around the robot code

A **subsystem** is a Java class that groups the actions for one mechanism. An
**OpMode** is the program selected on the Driver Station. The OpMode decides when
to act, and the subsystem sends the hardware commands.

## Where to look

All Java files below live in `src/main/java/org/firstinspires/ftc/teamcode/`.

| File | Responsibility |
| --- | --- |
| `subsystems/Drivetrain.java` | Four wheel-power methods and the six original encoder movement methods. |
| `subsystems/Intake.java` | Coordinates the intake motor and two intake servos for in, out, and off. |
| `subsystems/Launcher.java` | Applies launcher motor power. |
| `subsystems/Sensors.java` | Sets up the IMU and reads yaw (heading). |
| `TeleOp.java` | Uses controller input and IMU heading to calculate wheel powers (field centric). |
| `Auto.java` | Runs the team's existing movement and launcher sequence. |

