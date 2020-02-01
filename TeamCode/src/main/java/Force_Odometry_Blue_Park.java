import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;

@Autonomous(name = "Force_Odometry_Blue_Park")
public class Force_Odometry_Blue_Park extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values.
        Init();
        initOdometryHardware(0,111,90);
        robot.rightStoneGrabberUpDown.setPosition(0);
        robot.rightStoneGrabberOpenClose.setPosition(0);

        telemetry.addLine("Ready to jump to hyperspace");
        // Wait for the game to start (driver presses PLAY)

        telemetry.update();
        waitForStart();


        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

        robot.rightStoneGrabberOpenClose.setPosition(1.0);
        robot.leftStoneGrabberOpenClose.setPosition(1.0);

        goToPostion(0 * robot.COUNTS_PER_INCH, 72 * robot.COUNTS_PER_INCH,
                    .8, 90, 1 * robot.COUNTS_PER_INCH,
                    false);
         //Drives and parks under the bridge
        telemetry.addData("x Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
        telemetry.update();


        while(opModeIsActive()){
            //Display Global (x, y, theta) coordinates
            telemetry.addData("X Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
            telemetry.addData("Y Position", robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
            telemetry.addData("Orientation (Degrees)", robot.globalPositionUpdate.returnOrientation());

            telemetry.addData("Vertical left encoder position", robot.verticalLeft.getCurrentPosition());
            telemetry.addData("Vertical right encoder position", robot.verticalRight.getCurrentPosition());
            telemetry.addData("horizontal encoder position", robot.horizontal.getCurrentPosition());

            telemetry.addData("Thread Active", robot.positionThread.isAlive());
            telemetry.update();
        }

        //Stop the thread
        robot.globalPositionUpdate.stop();

    }

}




