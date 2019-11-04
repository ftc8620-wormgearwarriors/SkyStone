
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


public abstract class SkyStoneAutonomousMethods extends LinearOpMode {
    Swartz_HardwareMap robot = new Swartz_HardwareMap();

    double SkyStoneAlignDetector;

    // private GoldAlignDetector detector;

    public enum SkyStoneLocation{
                UNKNOWN,
                LEFT,
                CENTER,
                RIGHT,
    }


    public void Init() {
        robot.init(hardwareMap);

    detector = new SkyStoneAlignDetector();
    detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
    detector.useDefaults();

    detector.alignSize = 100;
    detector.alignPosOffset = 0;
    detector.downscale = 0.4;

    detector.perfectAreaScorer.perfectArea = 10000;
    detector.ratioScorer.weight = 0.005;

    detector.ratioScorer.weight = 5;
    detector.ratioScorer.perfectRatio = 1.0;

    detector.enable ();
    }

    /**0
     * Function to drive straight forward or backward
     * Pass:
     * distance = distance to travel (cm)
     * velocity = motor power (+ value moves forward, - value moves backward)
     * Constants:
     * diameter = wheel diameter
     * circumference = wheel circumference
     * gearRatio = gear ratio from motor to wheel
     * ticksPerRotation = ticks returned from encoder per rotation of motor
     * Variables:
     * ticks = number of ticks to move given distance
     * Return average motor ticks at end of movement
     */
    public double drive(double distance, double maxVel) {
        double diameter = 10.16;
        double circumference = diameter * Math.PI;
        double gearRatio = 20.36;
        int ticksPerRotation = 28;
        double targetTicks = distance * (1 / circumference) * gearRatio * ticksPerRotation;
        double targetHeading = robot.imu.getHeading();
        double kpTurn = 0.01;
        double kpDistance = 0.0003;
        double minVel = 0.05;
        double accel = 0.03;    //0.01;
        double vel = minVel;
        double oldVel = minVel;

        //Restart tick count from encoders
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Loop until average motor ticks reaches specified number of ticks
        while (opModeIsActive() && targetTicks > currentPositionAverage()) {

            double distanceError = targetTicks - currentPositionAverage();
            vel = distanceError * kpDistance;


            if (vel > (oldVel + accel))

                vel = oldVel + accel;

            if (vel > (Math.abs(maxVel))) {
                vel = (Math.abs(maxVel));
            }

            if (vel < minVel) {
                vel = minVel;

            }
            oldVel = vel;

            if (maxVel < 0)
                vel = -vel;

            double error = angleErrorDrive(targetHeading, robot.imu.getHeading());
            // Run motors at specified power
            robot.frontLeftDrive.setPower(vel - (error * kpTurn));
            robot.frontRightDrive.setPower(vel + (error * kpTurn));
            robot.backLeftDrive.setPower(vel - (error * kpTurn));
            robot.backRightDrive.setPower(vel + (error * kpTurn));

            telemetry.addData("front right: ", robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("front left:", robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("back right:", robot.backRightDrive.getCurrentPosition());
            telemetry.addData("back left:", robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("FR Speed:", robot.frontRightDrive.getPower());
            telemetry.addData("FL Speed:", robot.frontLeftDrive.getPower());
            telemetry.addData("BR Speed", robot.backRightDrive.getPower());
            telemetry.addData("BL Speed:", robot.backLeftDrive.getPower());
            telemetry.update();
        }

        // Turn off motors
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);

        // Return average total ticks traveled
        return currentPositionAverage();
    }


    /**
     * Function to drive straight forward or backward
     * Pass:
     * distance = distance to travel (cm)
     * velocity = motor power (+ value moves forward, - value moves backward)
     * Constants:
     * diameter = wheel diameter
     * circumference = wheel circumference
     * gearRatio = gear ratio from motor to wheel
     * ticksPerRotation = ticks returned from encoder per rotation of motor
     * Variables:
     * ticks = number of ticks to move given distance
     * Return average motor ticks at end of movement
     */
    public double strafe(double distance, double maxVel) {
        double diameter = 10.16;
        double circumference = diameter * Math.PI;
        double gearRatio = 20.36;
        int ticksPerRotation = 28;
        double targetTicks = distance * (1 / circumference) * gearRatio * ticksPerRotation;
        double targetHeading = robot.imu.getHeading();
        double kpTurn = 0.01;
        double kpDistance = 0.0003;
        double minVel = 0.05;
        double accel = 0.03;      //0.01;
        double vel = minVel;
        double oldVel = minVel;

        //Restart tick count from encoders
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Loop until average motor ticks reaches specified number of ticks
        while (opModeIsActive() && targetTicks > currentPositionAverage()) {

            double distanceError = targetTicks - currentPositionAverage();
            vel = distanceError * kpDistance;


            if (vel > (oldVel + accel))

                vel = oldVel + accel;

            if (vel > (Math.abs(maxVel))) {
                vel = (Math.abs(maxVel));
            }

            if (vel < minVel) {
                vel = minVel;

            }
            oldVel = vel;

            if (maxVel < 0)
                vel = -vel;

            double error = angleErrorDrive(targetHeading, robot.imu.getHeading());
            // Run motors at specified power
            robot.frontLeftDrive.setPower(-vel - (error * kpTurn));
            robot.frontRightDrive.setPower(vel + (error * kpTurn));
            robot.backLeftDrive.setPower(vel - (error * kpTurn));
            robot.backRightDrive.setPower(-vel + (error * kpTurn));

            telemetry.addData("front right:", robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("front left:", robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("back right:", robot.backRightDrive.getCurrentPosition());
            telemetry.addData("back left:", robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("FR Speed:", robot.frontRightDrive.getPower());
            telemetry.addData("FL Speed:", robot.frontLeftDrive.getPower());
            telemetry.addData("BR Speed", robot.backRightDrive.getPower());
            telemetry.addData("BL Speed:", robot.backLeftDrive.getPower());
            telemetry.update();
        }

        // Turn off motors
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);

        // Return average total ticks traveled
        return currentPositionAverage();
    }

    /**
     * Function to drive straight forward or backward
     * Pass:
     * distance = distance to travel (cm)
     * velocity = motor power (+ value moves forward, - value moves backward)
     * Constants:
     * diameter = wheel diameter
     * circumference = wheel circumference
     * gearRatio = gear ratio from motor to wheel
     * ticksPerRotation = ticks returned from encoder per rotation of motor
     * Variables:
     * ticks = number of ticks to move given distance
     * Return average motor ticks at end of movement
     */
    public double rotate(double targetHeading, double maxVel) {

        double kpRotate = 0.01;
        double minVel = 0.05;
        double accel = 0.03;       //0.01;
        double vel = minVel;
        double oldVel = minVel;
        double headingError = 500;  // force into while loop first time through
        double lastHeadingError = 500;
        boolean done = false;

        //Restart tick count from encoders
        robot.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Loop until average motor ticks reaches specified number of ticks
        while (opModeIsActive() && !done) {

            // find how far we are off our desired heading
            // if MaxVel is neagitve we are turning counter-clockwise
            if (maxVel >= 0)
                headingError = angleErrorRotate(robot.imu.getHeading(), targetHeading);
            else
                headingError = angleErrorRotate(targetHeading, robot.imu.getHeading());
            if (headingError > lastHeadingError + 10)
                done = true;
            lastHeadingError = headingError;

            telemetry.addData("Rotateing", headingError);
            telemetry.update();

            vel = headingError * kpRotate;


            if (vel > (oldVel + accel))

                vel = oldVel + accel;

            if (vel > (Math.abs(maxVel))) {
                vel = (Math.abs(maxVel));
            }

            if (vel < minVel) {
                vel = minVel;

            }
            oldVel = vel;

            if (maxVel < 0)
                vel = -vel;

            double error = angleErrorDrive(targetHeading, robot.imu.getHeading());
            // Run motors at specified power
            robot.frontLeftDrive.setPower(vel);
            robot.frontRightDrive.setPower(-vel);
            robot.backLeftDrive.setPower(vel);
            robot.backRightDrive.setPower(-vel);

            telemetry.addData("front right:", robot.frontRightDrive.getCurrentPosition());
            telemetry.addData("front left:", robot.frontLeftDrive.getCurrentPosition());
            telemetry.addData("back right:", robot.backRightDrive.getCurrentPosition());
            telemetry.addData("back left:", robot.backLeftDrive.getCurrentPosition());
            telemetry.addData("FR Speed:", robot.frontRightDrive.getPower());
            telemetry.addData("FL Speed:", robot.frontLeftDrive.getPower());
            telemetry.addData("BR Speed", robot.backRightDrive.getPower());
            telemetry.addData("BL Speed:", robot.backLeftDrive.getPower());
            telemetry.update();
        }

        // Turn off motors
        robot.frontLeftDrive.setPower(0);
        robot.frontRightDrive.setPower(0);
        robot.backLeftDrive.setPower(0);
        robot.backRightDrive.setPower(0);

        // Return average total ticks traveled
        return currentPositionAverage();
    }

    public double currentPositionAverage() {
        return (Math.abs(robot.frontLeftDrive.getCurrentPosition()) +
                Math.abs(robot.frontRightDrive.getCurrentPosition()) +
                Math.abs(robot.backLeftDrive.getCurrentPosition()) +
                Math.abs(robot.backRightDrive.getCurrentPosition()) / 4);

    }

    /**
     * Created by Worm Gear Warriors on 10/28/2018.
     * returns an angle between -180 and +180
     */
    public double angleErrorDrive(double angleTarget, double angleInitial) {
        double error = angleInitial - angleTarget;

        while (error <= -180 || error > 180) {
            if (error > 180) {
                error = error - 360;
            }
            if (error <= -180) {
                error = error + 360;
            }
        }
        return error;
    }

    /**
     * Created by Worm Gear Warriors on 10/28/2018.
     * returns an angle between 0 and 360
     */
    public double angleErrorRotate(double angleTarget, double angleInitial) {
        double error = angleInitial - angleTarget;

        while (error < 0 || error >= 360) {
            if (error >= 360) {
                error = error - 360;
            }
            if (error < 0) {
                error = error + 360;
            }
        }
        return error;
    }

    public double SkyStoneDetection() {
        final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
        final boolean PHONE_IS_PORTRAIT = false;

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */
        final String VUFORIA_KEY =
                " Ac/bw0P/////AAABmdRCZF/Kqk2MjbJIs87MKVlJg32ktQ2Tgl6871UmjRacrtxKJCUzDAeC2aA4tbiTjejLjl1W6e7VgBcQfpYx2WhqclKIEkguBRoL1udCrz4OWonoLn/GCA+GntFUZN0Az+dGGYtBqcuW3XkmVNSzgOgJbPDXOf+73P5qb4/mHry0xjx3hysyAzmM/snKvGv8ImhVOVpm00d6ozC8GzvOMRF/S5Z1NBsoFls2/ul+PcZ+veKwgyPFLEFP4DXSqTeOW1nJGH9yYXSH0kfNHgGutLM5om1hAlxdP8D4XMRD2bgWXj1Md2bz+uJmr1E2ZuI7p26ZRxOIKZE9Hwpai+MW6yaJD0otF6aL9QXYaULPpWKo ";

        // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
        // We will define some constants and conversions here
        final float mmPerInch = 25.4f;
        final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

        // Constant for Stone Target
        final float stoneZ = 2.00f * mmPerInch;

        // Constants for the center support targets
        final float bridgeZ = 6.42f * mmPerInch;
        final float bridgeY = 23 * mmPerInch;
        final float bridgeX = 5.18f * mmPerInch;
        final float bridgeRotY = 59;                                 // Units are degrees
        final float bridgeRotZ = 180;

        // Constants for perimeter targets
        final float halfField = 72 * mmPerInch;
        final float quadField = 36 * mmPerInch;

        // Class Members
        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia = null;
        boolean targetVisible = false;
        float phoneXRotate = 0;
        float phoneYRotate = 0;
        float phoneZRotate = 0;

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        // waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.

        targetsSkyStone.activate();
        while (!isStopRequested()) {

            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            } else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();
        }

        // Disable Tracking when we are done;
        targetsSkyStone.deactivate();
        return 0;


    }
    public SkyStoneLocation checkSkyStoneLocation() {
        SkyStoneLocation SkyStonePosition = SkyStoneLocation.UNKNOWN;
        if (detector.isFound()) {
            double StoneX = detector,getXPosition(;)
            telemetry.addData ("X Pos", StoneX);
            if (StoneX > 0.0 && StoneX <= 255) {
                SkyStonePosition = SkyStoneLocation.LEFT;
                telemetry.addData("Left", StoneX);
            }
        else if (StoneX > 255 && StoneX <= 500) {
            SkyStonePosition = SkyStoneLocation.CENTER;
            telemetry.addData("CENTER", StoneX);
            }
        }
    else {
        SkyStonePosition = SkyStoneLocation.RIGHT;
        telemetry.addData("RIGHT", 0);
        }
     telemetry.update();
    return SkyStonePosition;
    }

}

