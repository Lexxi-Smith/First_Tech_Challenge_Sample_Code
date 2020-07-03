/*  Arizona FIRST Tech Challenge Sample Robot Code - https://azftc.org
    If you have questions, please contact Zach Smith (zachs@asu.edu) or Lexxi Smith (aas492@nau.edu)

    The following code is an example of a basic mecanum drive configuration, where four wheels
    are controlled independently in the corner of each robot to allow three degrees of freedom when
    moving. This implementation uses three axes for defining the motion: forward/reverse, horizontal
    strafe, and rotation. As a result, friction and torque is sacrificed for greater maneuverability
    on a field.

      Front
    L-a - a-R
    | ----- |   The left pair of wheels are (am-3919L); likewise, the right pair of wheels are
    | ----- |   (am-3919R). [L] is left wheels, [R] is right wheels, [a] is motors.
    R-a - a-L

    NOTE: the orientation of the wheels is _incredibly_ important! Please refer to Andymark's
    reference to ensure correct installation.

    Additional motion tracking and telemetry can be implemented using a gyroscope for field-centric
    driving, which is covered in the FieldOrientedMecanum class.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "BasicMecanumDrive", group = "TeleOp")
public class BasicMecanumDrive extends OpMode {

    // Declares the four motors in each corner of the robot.
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void init() {
        // Instantiate the motors.
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft   = hardwareMap.get(DcMotor.class, "backLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");

        // Invert the wheels physically located on the left side of the robot; otherwise, forward
        // movement will be reversed.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double x; // x-axis motion, strafes right (+) and left (-)
        double y; // y-axis motion, drives forward (+) and backwards (-)
        double z; // z-axis motion, turns in place clockwise (+) and anticlockwise (-)

        // The left stick handles translational movement, and the right stick controls rotational.
        x =  gamepad1.left_stick_x;
        y = -gamepad1.left_stick_y;
        z =  gamepad1.right_stick_x;

        // Here are the power levels for each wheel. If the x, y, and z values would exceed [-1..1],
        // then they will be constrained. Whether values are added or subtracted depends on the
        // robot's frame of reference, and may need to be adjusted accordingly. constrain() is a
        // custom method below that limits the range of the input.
        double flSpeed = constrain(x - y - z, -1, 1);  // front left
        double blSpeed = constrain(-x - y - z, -1, 1); // back left
        double frSpeed = constrain(x - y - z, -1, 1);  // front right
        double brSpeed = constrain(-x - y - z, -1, 1); // back right

        // Assign the calculated speeds to the motors.
        frontLeft.setPower(flSpeed);
        backLeft.setPower(blSpeed);
        frontRight.setPower(frSpeed);
        backRight.setPower(brSpeed);
    }

    // Below is a method that allows repetitive code to easily be referenced. This is similar to a
    // function in math: it takes a set number of inputs and returns a single output.

    /**
     * Constrains a number such that it falls between [lowerBound..upperBound]. Useful for ensuring
     * that a motor's power does not exceed [-1..1].
     * @param input Number to constrain.
     * @param lowerBound Lower limit of constraint.
     * @param upperBound Upper limit of constraint.
     * @return Input if not constrained, otherwise the appropriate boundary value.
     */
    public double constrain(double input, double lowerBound, double upperBound) {
        return Math.min(Math.max(input, lowerBound), upperBound);
    }
}
