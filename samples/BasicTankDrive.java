/*  Arizona FIRST Tech Challenge Sample Robot Code - https://azftc.org
    If you have questions, please contact Zach Smith (zachs@asu.edu) or Lexxi Smith (aas492@nau.edu)

    The following code is an example of a basic tank drive control, where the left and right sides
    of the drive train are controlled independently. A typical configuration can be seen in the
    diagram below. Tank treads are not required for this.

      Front
    o ----- o : Omni wheels (am-2948)
    | ----- |
    | ----- |
    x-a - a-x : Smooth wheels (am-2648) with a motor attached

    Additional motors can be attached to the left and right sides with this configuration;
    however, each side's motors must be combined into a single output shaft attached to the wheel.
    For independent control of four wheels, with one in each corner, please see the mecanum guide.
*/

// Location of code, akin to a folder structure on a computer.
package org.firstinspires.ftc.teamcode;

// Imported code from the FIRST Tech Challenge software development kit.
// If you notice a red squiggly line under a variable type, click the red light bulb that appears
// and click "import" to add the import declaration.
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// Declares the operation mode (OpMode) as TeleOp, which is where drivers operate the robot.
// OpModes are grouped together and distinguished by name. This appears in the Driver Station app.
@TeleOp(name = "Driver Controlled,", group = "TeleOp")

// Extending OpMode means that the "BasicTankDrive" class is implementing code from another class
// called "OpMode," which provides a set of methods (tools) for interacting with the robot.
public class BasicTankDrive extends OpMode {

    // Declare the number of motors located on the robot. The "DcMotor" refers to the variable type,
    // and "left" refers to the variable name. The variable name is only referenced in code and is
    // not associated with the configuration file.
    DcMotor left;
    DcMotor right;

    // If your robot has two motors for each side of the robot, uncomment the following lines by
    // removing the preceding "//".
    //DcMotor leftSecondary;
    //DcMotor rightSecondary;

    // Methods are pieces of reusable code that perform a specific task. The init() method will
    // run once when the robot is turning on, which is useful for initializing robot hardware.
    // If the method never returns, or completes, then the driver station will report an error that
    // the robot is stuck in init(). All code in here needs to be called only once, which is why
    // it is placed in init(). The @Override tag means that the method is originally from
    // the "OpMode" class, which we extended above.
    @Override
    public void init() {
        // Here, the variable "left" is being assigned to a specific device on the robot.
        // The get() method in hardwareMap requires the type of device to get, in this case
        // DcMotor.class, and the name in the configuration file. The variable name in code
        // and the configuration file's device name can be different.
        left  = hardwareMap.get(DcMotor.class, "leftMotor");
        right = hardwareMap.get(DcMotor.class, "rightMotor");

        // Because the left and right motors are mirrored, applying a positive power to both motors
        // will cause the robot to spin in circles. This can be corrected in two ways: by reversing
        // the right motor such that positive powers are automatically reversed, or by negating
        // the right motor each time its power is applied in the setPower() method.
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        // Uncomment the following lines to add the secondary motors.
        // leftSecondary  = hardwareMap.get(DcMotor.class, "leftMotor2");
        // rightSecondary = hardwareMap.get(DcMotor.class, "rightMotor2");
    }

    // The loop() method runs code repeatedly until stop is pressed on the driver station.
    // Each time this method is run, it will retrieve the current values of gamepad1's joysticks
    // and set the left and right motor power accordingly.
    @Override
    public void loop() {
        // Read the input from the joysticks. Because pushing up on the joystick returns a negative
        // value, the value is negated.
        // Gamepad1 refers to the first gamepad (start-A) attached to the driver station phone.
        double leftSpeed  = -gamepad1.left_stick_y;
        double rightSpeed = -gamepad1.right_stick_y;

        // Set the motors to move at a speed proportional to the joystick's value.
        // setPower() accepts values from [-1..1], and a power of zero will stop the motor.
        left.setPower(leftSpeed);
        right.setPower(rightSpeed);

        // Uncomment the following lines to add the secondary motors.
        // leftSecondary.setPower(leftSpeed);
        // rightSecondary.setPower(rightSpeed);
    }
}
