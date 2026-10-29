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

# FTC 35873 — BIOBUZZ 🤖

Welcome to the FTC 35873 robot code! This project is written in **Java** using the **FIRST Tech Challenge (FTC) SDK**. New programmers: you do **not** need to understand every line immediately. Start with the file guide and follow a single controller button through the code.

> **The main idea:** An **OpMode** decides **WHEN** a robot action happens. A **subsystem** decides **HOW** that specific part of the robot works.

## 🚀 Start here: file-by-file guide

The team's code lives in `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`. The FTC SDK's `FtcRobotController/` directory contains framework code, so students generally should not edit it.

| File | Beginner-friendly explanation | When should I edit it? |
| --- | --- | --- |
| `TeleOperated.java` | **Robot-centric driver mode.** Reads controllers and tells the robot what to do. Forward follows the robot's front. | Changing gamepad buttons or basic driver behavior |
| `TeleOperatedFieldCentric.java` | **Field-centric driver mode.** Uses the IMU's heading plus math to help the driver move relative to the field. | Adjusting field-centric driving, heading reset, or gamepad mapping |
| `Auto.java` | **Autonomous mode.** Runs a sequence of robot actions after Start without a driver. | Programming a match autonomous route |
| `subsystems/Drivetrain.java` | Controls all **four mecanum wheels**, including encoder-based moves. | Changing wheel behavior, motor configuration, turning, strafing |
| `subsystems/Intake.java` | Controls the **intake motor** and its **two servos**. | Changing how game pieces are collected or reversed |
| `subsystems/Launcher.java` | Controls the **launcher motor** and **feed servo**. | Tuning launcher power or servo behavior |
| `subsystems/RobotSensors.java` | Sets up and reads the **IMU**, which reports the robot's direction (yaw). | Changing sensor setup, mounting orientation, or adding readings |
| `README.md` | This instruction guide. | Documenting new controls and team decisions |

The old `Actuators.java` and `Sensors.java` were replaced by clearer, specialized subsystems. **Do not recreate a giant hardware class** when a mechanism can live in its own file.

### How the files work together

```text
Driver presses a button
         |
         v
TeleOperated.java or TeleOperatedFieldCentric.java
         |       decides WHEN
         v
Intake.java / Launcher.java / Drivetrain.java
         |       decides HOW
         v
Motor or servo on the physical robot

RobotSensors.java -- reads IMU heading for field-centric control
Auto.java         -- calls the same subsystems without gamepads
```

**Example:** Press **A** on controller 2 → TeleOp detects `gamepad2.a` → calls `intake.on()` → Intake sends power to the motor and positions its se
