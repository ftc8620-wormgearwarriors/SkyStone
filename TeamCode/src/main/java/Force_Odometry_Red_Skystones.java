import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Red_Skystones")
public class Force_Odometry_Red_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException
    {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(144, 39, 270);
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

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(true, true);  // look for skystone

        switch (StoneRember) {
            case LEFT: //SkyStone is on the left
                telemetry.addLine("LEFT");
                telemetry.update();
                goToPostion(118 * robot.COUNTS_PER_INCH, 35 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                rightAutoGrabStone();
                sleep(250);
                break;
            case CENTER: //SkyStone is on the center
                telemetry.addLine("CENTER");
                telemetry.update();
                goToPostion(118 * robot.COUNTS_PER_INCH, 40 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                rightAutoGrabStone();
                sleep(250);
                break;
            case RIGHT:  //SkyStone is on the right
                telemetry.addLine("RIGHT");
                telemetry.update();
                goToPostion(118 * robot.COUNTS_PER_INCH, 48 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                rightAutoGrabStone();
                sleep(250);
                break;
        }
        //going under the bridge to aviod hitting the claw against the bridge
        goToPostion(122 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);

        //raising claw to drop stone on waffle
        robot.rightStoneGrabberUpDown.setPosition(0.4);

        //drops skystone on waffle
        goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
        rightAutoDropStone();

        //rotates to grab waffle
        goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, 1.0, 90, 1000 * robot.COUNTS_PER_INCH, true);

       //drives back to waffle
        goToPostion(102 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 90, 2 * robot.COUNTS_PER_INCH, false);

        //grabs the waffle
        robot.LeftWaffle.setPosition(0);
        robot.RightWaffle.setPosition(0);
        sleep(500);

        //drags waffle to build site
        goToPostion(144 * robot.COUNTS_PER_INCH, 124 * robot.COUNTS_PER_INCH, .8, 90, 1 * robot.COUNTS_PER_INCH, false);




    }
}






