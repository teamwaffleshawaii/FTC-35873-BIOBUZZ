# FTC 35873 BIOBUZZ — Student Code Guide

This repository uses the FTC SDK and Java. **All team-written code** is in
`TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`.

## Where does code belong?

| Folder or file | Responsibility | Examples |
| --- | --- | --- |
| `subsystems/Drivetrain.java` | Four drive motors and encoder movement | drive, strafe, turn |
| `subsystems/Intake.java` | Intake motor and two intake servos | collect, reverse, stop |
| `subsystems/Launcher.java` | Launcher motor and launch servo | spin up, feed, stop |
| `subsystems/RobotSensors.java` | REV hub IMU | read yaw / heading |
| `TeleOperated.java` | Driver buttons, robot-centric driving | gamepad 1 + 2 |
| `TeleOperatedFieldCentric.java` | Field-centric driver mode | IMU correction and controls |
| `Auto.java` | Sequence of autonomous actions | drive/turn/strafe examples |

**Rule of thumb:** A subsystem owns its hardware; an OpMode decides **when**
to call a subsystem method. Do not call `hardwareMap.get()` in TeleOp loops.
Do not duplicate motor configuration in both autonomous and TeleOp.

## Controls (preserved from the original code)

- **Driver 1:** left stick drives, right stick turns; field-centric left bumper
  re-zeros heading; right bumper temporarily uses robot-relative drive.
- **Driver 2:** A intake in, B reverse, otherwise stop; X sets launcher to
  45% speed, Y turns launcher motor off; hold right bumper to activate feed servo.
- X/Y is a **latched** motor control: releasing X does not switch the motor off.

## Before driving

1. Verify the Robot Controller configuration names: `leftFront`, `rightFront`,
   `leftBack`, `rightBack`, `intakeMotor`, `launchMotor`,
   `leftIntakeServo`, `rightIntakeServo`, `launchServo`, and `imu`.
2. Raise the wheels off the floor. Check each motor direction at low speed.
3. Check intake/launcher servo positions for hard stops before full motion.
4. Check IMU hub mounting: code assumes **logo LEFT, USB UP**.
5. Test encoder movements **one at a time** with clear space. `537.7`
   counts per motor revolution may not match the motors/gearing installed.
6. Watch the robot with an accessible STOP control. This code is an
   educational starting point, not a verified competition autonomous.

## Learning questions

- Why is `-gamepad1.left_stick_y` used for forward?
- Why do mecanum wheels need different wheel powers for strafing?
- What changes when driver 1 resets field-centric heading?
- What is the difference between motor `FLOAT` and `BRAKE`?
- Why do encoder moves need an activity check **and** timeout?
- Why is a subsystem easier to troubleshoot than one giant `Actuators` class?

## Notes for reviewers

The earlier `Actuators.java` and `Sensors.java` were removed as code was
moved into subsystem classes. Robot-centric and field-centric drive keep the
original gamepad mappings and basic equations. The field-centric heading
reset is corrected to capture one offset on a button press instead of adding
offsets on every loop. Motor powers are clipped to the FTC legal range.
Encoder helpers now stop and time out instead of looping forever; they need
testing on the real robot. Autonomous is still the original six-move DEMO.
