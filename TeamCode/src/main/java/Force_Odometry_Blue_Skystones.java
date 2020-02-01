import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Blue_Skystones")
public class Force_Odometry_Blue_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException
    {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(0, 39, 90);
        init_vuforia_2(); //init camera
        robot.rightStoneGrabberUpDown.setPosition(0);
        robot.rightStoneGrabberOpenClose.setPosition(0);

        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

//        //debugging odometry
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
        //moving right claw out of the way of bridge
        robot.rightStoneGrabberOpenClose.setPosition(0.8);

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(true, false);  // look for skystone

        switch (StoneRember) {
            case LEFT: //SkyStone is on the left
                telemetry.addLine("LEFT");
                telemetry.update();
                goToPostion(27 * robot.COUNTS_PER_INCH, 49 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                leftAutoGrabStone();
                sleep(250);
                break;
            case CENTER: //SkyStone is on the center
                telemetry.addLine("CENTER");
                telemetry.update();
                goToPostion(27 * robot.COUNTS_PER_INCH, 41 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                leftAutoGrabStone();
                sleep(250);
                break;
            case RIGHT:  //SkyStone is on the right
                telemetry.addLine("RIGHT");
                telemetry.update();
                goToPostion(27 * robot.COUNTS_PER_INCH, 33 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                leftAutoGrabStone();
                sleep(250);
                break;
        }

        //temporary sleep
        //going under the bridge to avoid hitting the claw against the bridge
        goToPostion(22 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);

        //raising claw to drop stone on waffle
        robot.leftStoneGrabberUpDown.setPosition(0.4);

        //drops skystone on waffle
        goToPostion(34 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
        leftAutoDropStone();

        //rotates to grab waffle
        goToPostion(42 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, 1.0, 270, 1000 * robot.COUNTS_PER_INCH, true);

        //drives back to waffle
        goToPostion(42 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 270, 2 * robot.COUNTS_PER_INCH, false);

        //grabs the waffle
        robot.LeftWaffle.setPosition(0);
        robot.RightWaffle.setPosition(0);
        sleep(500);

        //drags waffle to build site
        goToPostion(0 * robot.COUNTS_PER_INCH, 124 * robot.COUNTS_PER_INCH, .8, 270, 1 * robot.COUNTS_PER_INCH, false);




    }
}

