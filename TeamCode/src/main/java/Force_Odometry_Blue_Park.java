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
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(0,111,270);
        init_vuforia_2(); //init camera

        telemetry.addLine("Ready to jump to hyperspace");
        // Wait for the game to start (driver presses PLAY)

        telemetry.update();
        waitForStart();

        VuforiaStuff.skystonePos StoneRemember = vuforiaStuff.vuforiascan(true,false);  // look for skystone
        int Stone_Position = -1;
        if (StoneRemember == VuforiaStuff.skystonePos.LEFT) {
            Stone_Position = 0;
        }
        else if (StoneRemember == VuforiaStuff.skystonePos.CENTER) {
            Stone_Position = 1;
        }
        else if (StoneRemember == VuforiaStuff.skystonePos.RIGHT){
            Stone_Position = 2;
        }

        telemetry.addData("stone position", String.format ("%d", Stone_Position));
//        telemetry.addData("Init Arm Tilt Encoder",robot.LiftMotor.getCurrentPosition());
//        telemetry.addData("Init Extension ticks",robot.ExtendMotor.getCurrentPosition());
        telemetry.update();


//        telemetry.addData("Status", "Init Complete");
//        telemetry.update();
//        waitForStart();
//
//        goToPostion(135 * robot.COUNTS_PER_INCH, 72 * robot.COUNTS_PER_INCH,.8, 90, 1 * robot.COUNTS_PER_INCH, false);
//        //goToPostion(24 * COUNTS_PER_INCH, 0 * COUNTS_PER_INCH,.8, 0, 1 * COUNTS_PER_INCH, false);
//        telemetry.addData("x Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
//        telemetry.update();
//
//
//        while(opModeIsActive()){
//            //Display Global (x, y, theta) coordinates
//            telemetry.addData("X Position", robot.globalPositionUpdate.returnXCoordinate() / robot.COUNTS_PER_INCH);
//            telemetry.addData("Y Position", robot.globalPositionUpdate.returnYCoordinate() / robot.COUNTS_PER_INCH);
//            telemetry.addData("Orientation (Degrees)", robot.globalPositionUpdate.returnOrientation());
//
//            telemetry.addData("Vertical left encoder position", robot.verticalLeft.getCurrentPosition());
//            telemetry.addData("Vertical right encoder position", robot.verticalRight.getCurrentPosition());
//            telemetry.addData("horizontal encoder position", robot.horizontal.getCurrentPosition());
//
//            telemetry.addData("Thread Active", robot.positionThread.isAlive());
//            telemetry.update();
//        }

//        //Stop the thread
//        robot.globalPositionUpdate.stop();

    }

}




