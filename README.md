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

**Example:** Press **A** on controller 2 → TeleOp detects `gamepad2.a` → calls `intake.on()` → Intake sends power to the motor and positions its servos. TeleOp does not need to know the intake's motor configuration.

## 🎮 Controller mapping

The controls below describe the **current code**, not proposed controls.

| Controller | Input | Result |
| --- | --- | --- |
| Driver 1 | Left stick | Drive forward/backward/sideways |
| Driver 1 | Right stick X | Rotate the robot |
| Driver 1 (field-centric only) | Left bumper (new press) | Set current direction as heading zero |
| Driver 1 (field-centric only) | Right bumper (hold) | Temporarily bypass field-centric correction |
| Driver 1 (field-centric only) | D-pad | Fixed 50% straight-direction commands |
| Driver 2 | A (hold) | Intake forward |
| Driver 2 | B (hold) | Intake reverse |
| Driver 2 | Neither A nor B | Intake stops |
| Driver 2 | X | Set launcher motor power to 0.45 |
| Driver 2 | Y | Set launcher motor power to zero |
| Driver 2 | Right bumper (hold) | Activate the launch feed servo; release to return it |

**Important:** The launcher motor's X/Y buttons are **latched**. Releasing X does **not** stop the launcher; press Y to turn it off. A takes priority over B if both are held. The robot-centric mode does not have the field-centric heading/D-pad features.

## 🧑‍💻 Java concepts you'll see

| Concept | What it means | Example |
| --- | --- | --- |
| **Class** | A blueprint containing related data and actions | `class Intake` |
| **Object** | An instance of a class | `new Intake()` |
| **Method** | A named action that code can call | `intake.on()` |
| **Variable** | A named place to store a value | `double forward` |
| **`if / else`** | Choose actions based on a condition | `if (gamepad2.a)` |
| **`while` loop** | Repeat while a condition is true | `while (opModeIsActive())` |
| **`private`** | Only the class itself can access this field or method | `private DcMotor intakeMotor` |
| **`public`** | Other classes can call this method | `public void on()` |
| **`hardwareMap`** | Finds devices by their configured names | `hardwareMap.get(...)` |
| **`telemetry`** | Sends debugging information to the Driver Station | `telemetry.addData(...)` |

A comment beginning with `//` explains a line; it does **not** run. A block beginning with `/**` documents a class or method and can show up in the editor's tooltips.

## 📁 Where should my new code go?

- **New gamepad button?** Put the button decision in the appropriate TeleOp file, then call an existing or new subsystem method.
- **New intake feature?** Put the motor/servo behavior in `Intake.java`.
- **New launcher power or feeding behavior?** Put it in `Launcher.java`.
- **Different drive movement?** Put reusable wheel/motor operations in `Drivetrain.java`.
- **New sensor reading?** Put the sensor access in `RobotSensors.java` (or make a dedicated sensor subsystem if it grows).
- **New autonomous routine?** Put the **sequence** in `Auto.java` and reuse the subsystem methods.
- **New hardware?** Give it to the subsystem that owns that mechanism, and update the matching robot configuration name.

Keep methods **small and clearly named**. The goal is readable code, not the fewest lines.

## 🛠️ Robot configuration and safe testing

Hardware names must match your Robot Controller configuration **exactly**:

```text
Drive:    leftFront, rightFront, leftBack, rightBack
Intake:   intakeMotor, leftIntakeServo, rightIntakeServo
Launcher: launchMotor, launchServo
Sensor:   imu
```

1. Open the project in Android Studio and **build** before testing on the robot.
2. With wheels safely raised, test every motor at **low power** and confirm directions.
3. Verify all servo positions are mechanically safe and do not push against hard stops.
4. For field-centric driving, confirm the REV hub orientation matches the code: **logo LEFT, USB UP**.
5. Test autonomous movements **one command at a time** in clear space. The drivetrain uses `537.7` encoder ticks per **motor rotation** as an assumption that must match the installed hardware.
6. Encoder commands have an activity check and approximately **five-second timeout**. They are basic learning helpers, not a complete path planner.
7. Keep a Driver Station **STOP** control available. Test startup, ending TeleOp, and stopping autonomous mid-move.

**Current autonomous routine:** six educational moves (forward, backward, turn left, turn right, strafe left, strafe right). It is **not yet a competition strategy**. Launcher instructions in `Auto.java` are commented-out examples, not running actions.

## 💬 Questions to discuss as a team

1. Why do we use `-gamepad1.left_stick_y` to move forward?
2. Why does strafing need different power commands for all four wheels?
3. What is the difference between robot-centric and field-centric driving?
4. What does the IMU measure, and why do we reset heading?
5. Why do `RUN_TO_POSITION` movements need encoders and a timeout?
6. What is the difference between `BRAKE` and `FLOAT` motor behavior?
7. Why should the controller button stay in TeleOp instead of the Intake subsystem?
8. How would we add a second launcher speed without duplicating code?

## ✅ Student contribution checklist

- [ ] I understand which file owns the behavior I am changing.
- [ ] I kept the hardware device names correct.
- [ ] I added a **why** comment for non-obvious math, settings, or decisions.
- [ ] I did not silently change existing controller mappings.
- [ ] I built the project in Android Studio.
- [ ] I tested safely on the real robot with a teammate.
- [ ] I updated this README if controls or behavior changed.

**FTC 35873 BIOBUZZ:** Code should be understandable to the **next student** who opens the project, not just the person who wrote it.
