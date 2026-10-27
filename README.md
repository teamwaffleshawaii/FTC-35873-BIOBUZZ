# FTC 35873 BIOBUZZ

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

## Controls

- **Driver 1:** left stick drives, right stick turns; field-centric left bumper
  re-zeros heading; right bumper temporarily uses robot-relative drive.
- **Driver 2:** A intake in, B reverse, otherwise stop; X sets launcher to
  45% speed, Y turns launcher motor off; hold right bumper to activate feed servo.
- X/Y is a **latched** motor control: releasing X does not switch the motor off.
