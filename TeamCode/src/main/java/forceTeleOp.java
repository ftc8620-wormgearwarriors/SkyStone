

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="forceTeleOp", group="Pushbot")

public class forceTeleOp extends OpMode {

    /* Declare OpMode members. */
    force_HardwareMap robot = new force_HardwareMap(); // use the class created to define a Pushbot's hardware

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {


    }


    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    double maxVel = 0.5;
    double dropServoPos = 1.1;
    double openServoPos = 0.4;
    double twistServoPos = 0.75;
    double LeftWafflePos = 0;
    double RightWafflePos = 1;
    double clawPosition = 0.0;
    boolean intakeOn = false;
    double rightGrabberUpDownPos = 0.5;
    double rightStoneGrabberOpenClosePos = 0.5;
    double rightGrabberUpDownMinPos = 0.2;
    double rightGrabberUpDownMaxPos = 1.0;
    double rightGrabberOpenCloseMinPos = 0.0;
    double rightGrabberOpenCloseMaxPos = 1.0;
    double leftGrabberUpDownPos = 0.2;
    double leftStoneGrabberOpenClosePos = 0.8;
    double leftGrabberUpDownMinPos = 0.2;
    double leftGrabberUpDownMaxPos = 1.0;
    double leftGrabberOpenCloseMinPos = 0.0;
    double leftGrabberOpenCloseMaxPos = 1.0;







    @Override
    public void loop() {
        double markerServoPos = .72;
        double frontLeft;
        double backLeft;
        double frontRight;
        double backRight;
        double max;
        double x_axis = -gamepad1.left_stick_x * maxVel;
        double y_axis = -gamepad1.left_stick_y * maxVel;
        double x_prime;
        double y_prime;
        double theta = Math.toRadians(-robot.imu.getHeading());
        double gyroHeading = robot.imu.getHeading();

        /*
        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
*/

//        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //  Find robot's current axes in relation to original axes
        x_prime = x_axis * Math.cos(theta) + y_axis * Math.sin(theta);
        y_prime = -x_axis * Math.sin(theta) + y_axis * Math.cos(theta);

        telemetry.addData("Gamepad2.leftstick_x", gamepad2.left_stick_x);
        telemetry.addData("clawPosition", String.format ("%.01f", clawPosition));
        telemetry.addData("Theta (in radians)", theta);
        telemetry.addData("x_axis", x_axis);
        telemetry.addData("y_axis", y_axis);
        telemetry.addData("x_prime", x_prime);
        telemetry.addData("y_prime", y_prime);
        telemetry.addData("Gyro Heading", gyroHeading);
        telemetry.addData("Arm Position", robot.armPosInput.getVoltage());
        telemetry.addData("Arm Tilt Encoder",robot.LiftMotorLeft.getCurrentPosition());
        telemetry.addData("Arm Tilt Encoder",robot.LiftMotorRight.getCurrentPosition());
        telemetry.addData("Claw Position",openServoPos);
//        telemetry.addData("wookie range", String.format("%.01f cm", robot.wookie.getDistance(DistanceUnit.CM)));
//        telemetry.addData("DeathStar range", String.format("%.01f cm", robot.deathStar.getDistance(DistanceUnit.CM)));
        telemetry.addData("right Waffle", robot.RightWaffle.getPosition());
        telemetry.addData("left Waffle", robot.LeftWaffle.getPosition());
        telemetry.addData("Claw Twist", robot.TwistServo.getPosition());
        telemetry.addData("rightStoneGrabberUpDown", String.format ("%.01f", rightGrabberUpDownPos));
        telemetry.addData("rightStoneGrabberOpenClose", String.format ("%.01f", rightStoneGrabberOpenClosePos));
        telemetry.addData("leftStoneGrabberUpDown", String.format ("%.01f", leftGrabberUpDownPos));
        telemetry.addData("leftStoneGrabberOpenClose", String.format ("%.01f", leftStoneGrabberOpenClosePos));




        telemetry.update();

        // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
        frontRight  = y_prime - (gamepad1.right_stick_x / 2 * maxVel) + x_prime;
        backRight   = y_prime - (gamepad1.right_stick_x / 2 * maxVel) - x_prime;
        frontLeft   = y_prime + (gamepad1.right_stick_x / 2 * maxVel) - x_prime;
        backLeft    = y_prime + (gamepad1.right_stick_x / 2 * maxVel) + x_prime;

        if (gamepad1.left_trigger > .05)
            maxVel = 0.5;
        else if (gamepad1.right_trigger > .05)
            maxVel = 1.0;
        else if (gamepad1.left_bumper)
            maxVel = 0.25;
        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.max(Math.abs(frontLeft), Math.abs(backLeft)), Math.max(Math.abs(frontRight), Math.abs(backRight)));
        if (max > 1) {
            frontLeft /= max;
            backLeft /= max;
            frontRight /= max;
            backRight /= max;
        }

        robot.frontLeftDrive.setPower(frontLeft);
        robot.frontRightDrive.setPower(frontRight);
        robot.backLeftDrive.setPower(backLeft);
        robot.backRightDrive.setPower(backRight);

        if (gamepad1.y)  {
            robot.imu.resetHeading();
        }
        if (gamepad1.a)   {
            robot.LeftWaffle.setPosition(0.5);
            robot.RightWaffle.setPosition(0.5);
        }

        if (gamepad1.b) {
            robot.LeftWaffle.setPosition(0);
            robot.RightWaffle.setPosition(0);

        }


        if (gamepad1.dpad_up && !gamepad1.x) {
            if (rightGrabberUpDownPos > rightGrabberUpDownMinPos) {
                rightGrabberUpDownPos -= 0.05;

            }
        }
        if (gamepad1.dpad_down && !gamepad1.x) {
            if (rightGrabberUpDownPos < rightGrabberUpDownMaxPos) {
                rightGrabberUpDownPos += 0.05;
            }
        }
        robot.rightStoneGrabberUpDown.setPosition(rightGrabberUpDownPos);

        if (gamepad1.dpad_left && !gamepad1.x){
            if (rightStoneGrabberOpenClosePos < rightGrabberOpenCloseMaxPos) {
                rightStoneGrabberOpenClosePos += 0.01;
            }
        }
        if (gamepad1.dpad_right && !gamepad1.x){
            if (rightStoneGrabberOpenClosePos > rightGrabberOpenCloseMinPos) {
                rightStoneGrabberOpenClosePos -= 0.01;
            }

        }
        robot.rightStoneGrabberOpenClose.setPosition(rightStoneGrabberOpenClosePos);

        if (gamepad1.dpad_up && gamepad1.x) {
            if (leftGrabberUpDownPos > leftGrabberUpDownMinPos) {
                leftGrabberUpDownPos -= 0.05;

            }
        }
        if (gamepad1.dpad_down && gamepad1.x) {
            if (leftGrabberUpDownPos < leftGrabberUpDownMaxPos) {
                leftGrabberUpDownPos += 0.05;
            }
        }
        robot.leftStoneGrabberUpDown.setPosition(leftGrabberUpDownPos);

        if (gamepad1.dpad_left && gamepad1.x){
            if (leftStoneGrabberOpenClosePos < leftGrabberOpenCloseMaxPos) {
                leftStoneGrabberOpenClosePos += 0.01;
            }
        }
        if (gamepad1.dpad_right && gamepad1.x){
            if (leftStoneGrabberOpenClosePos > leftGrabberOpenCloseMinPos) {
                leftStoneGrabberOpenClosePos -= 0.01;
            }

        }
        robot.leftStoneGrabberOpenClose.setPosition(leftStoneGrabberOpenClosePos);

        /************* Read game pad 2 *************/
        if (gamepad2.a) {           //setting claw and intake into position to run
           robot.IntakeLeft.setPower(-1);
           robot.IntakeRight.setPower(-1);
           robot.RevwhlLeft.setPosition(-1);
           robot.RevwhlRight.setPosition(-1);
           twistServoPos = 0.649;  // 0.75;
           openServoPos = 0.84;
           intakeOn = true;
        }
        if (gamepad2.x) {
            robot.IntakeLeft.setPower(0);
            robot.IntakeRight.setPower(0);
            robot.RevwhlLeft.setPosition(0.5);
            robot.RevwhlRight.setPosition(0.5);
            intakeOn = false;
        }
        if (gamepad2.left_bumper) {
            twistServoPos += 0.01;
        }
        if (gamepad2.right_bumper) {
            twistServoPos -= 0.01;
        }
        robot.TwistServo.setPosition(twistServoPos);

        if (gamepad2.right_trigger > .1) {
            openServoPos -= 0.05;
        }
        if (gamepad2.left_trigger > .1) {
            openServoPos += 0.05;
        }
        robot.OpenServo.setPosition(openServoPos);


        double maxLiftHeight = 7200;
        double leftSpeed = 0, rightSpeed = 0;
        double blockLiftHeight = 660;

        if (gamepad2.dpad_up) {
            if (robot.LiftMotorRight.getCurrentPosition() - robot.LiftMotorLeft.getCurrentPosition() > 30) {
               rightSpeed = 0.5;
               leftSpeed = 0.8;
            }
            else if (robot.LiftMotorLeft.getCurrentPosition() - robot.LiftMotorRight.getCurrentPosition() > 30) {
                leftSpeed = (0.5);
                rightSpeed = (0.8);
            }
            else {
                leftSpeed = (0.8);
                rightSpeed = (0.8);
            }
        }
        else if (gamepad2.dpad_down || intakeOn) {
            if (robot.LiftMotorRight.getCurrentPosition() - robot.LiftMotorLeft.getCurrentPosition() < -30) {
               rightSpeed = (-0.5);
               leftSpeed = (-0.8);
            }
            else if (robot.LiftMotorLeft.getCurrentPosition() - robot.LiftMotorRight.getCurrentPosition() < -30) {
                leftSpeed = (-0.5);
                rightSpeed = (-0.8);
            }
            else {
                leftSpeed = (-0.8);
                rightSpeed = (-0.8);
            }
        }
        if (rightSpeed > 0 && robot.LiftMotorRight.getCurrentPosition() < maxLiftHeight)
            robot.LiftMotorRight.setPower(rightSpeed);
        else if (rightSpeed < 0 && (!intakeOn) && ((robot.LiftMotorRight.getCurrentPosition() > 0) || gamepad2.right_stick_y < -0.75 ))
            robot.LiftMotorRight.setPower(rightSpeed);
        else if (rightSpeed < 0 && intakeOn && robot.LiftMotorRight.getCurrentPosition() > blockLiftHeight)
            robot.LiftMotorRight.setPower(rightSpeed);
        else
            robot.LiftMotorRight.setPower(0);

        if (leftSpeed > 0 && robot.LiftMotorLeft.getCurrentPosition() < maxLiftHeight)
            robot.LiftMotorLeft.setPower(leftSpeed);
        else if (leftSpeed < 0 && (!intakeOn) && ((robot.LiftMotorLeft.getCurrentPosition() > 0) || gamepad2.right_stick_y < -0.75 ))
            robot.LiftMotorLeft.setPower(leftSpeed);
        else if (leftSpeed < 0 && intakeOn && robot.LiftMotorLeft.getCurrentPosition() > blockLiftHeight)
            robot.LiftMotorLeft.setPower(leftSpeed);
        else
            robot.LiftMotorLeft.setPower(0);

        //shrinking the range of the joystick to work with that of the servo
        clawPosition = (gamepad2.left_stick_x / 4.0) + 0.5;
        robot.ExtendClaw.setPosition(clawPosition);
        RobotLog.d("8620WGW: %.4f", clawPosition);

        //Runs intake the other way to spit the block out
        if (gamepad2.b) {
            robot.IntakeLeft.setPower(1);
            robot.IntakeRight.setPower(1);
            robot.RevwhlLeft.setPosition(1);
            robot.RevwhlRight.setPosition(1);
        }

         /*
       double x = gamepad1.left_stick_x;
       double y = gamepad1.left_stick_y;
       double z = gamepad1.right_stick_x;
       double frontLeftPower = -x + y + z;
       double frontRightPower = x + y - z;
       double backLeftPower = x + y + z;
       double backRightPower = -x +y - z;

       double speedScalar = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
               Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

       if (speedScalar > 1) {
           frontLeftPower = frontLeftPower / speedScalar;
           frontRightPower = frontRightPower / speedScalar;
           backLeftPower = backLeftPower / speedScalar;
           backRightPower = backRightPower / speedScalar;
       }

       robot.frontLeftDrive.setPower(frontLeftPower);
       robot.frontRightDrive.setPower(frontRightPower);
       robot.backLeftDrive.setPower(backLeftPower);
       robot.backRightDrive.setPower(backRightPower);
*/
        //} add if the code doent compile


        /*
         * Code to run ONCE after the driver hits STOP
         */
    }
    @Override
    public void stop() {
    }

}




