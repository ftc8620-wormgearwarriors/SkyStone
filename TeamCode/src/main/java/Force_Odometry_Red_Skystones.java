import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Red_Skystones")
public class Force_Odometry_Red_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(144,39,270);
        init_vuforia_2(); //init camera


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
                goToPostion(118 * robot.COUNTS_PER_INCH, 32 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case CENTER: //SkyStone is on the center
                telemetry.addLine("CENTER");
                telemetry.update();
                goToPostion(118 * robot.COUNTS_PER_INCH, 40 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case RIGHT:  //SkyStone is on the right
                telemetry.addLine("RIGHT");
                telemetry.update();
                goToPostion(118 * robot.COUNTS_PER_INCH, 48 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
        }
                //Turning under the bridge to aviod hitting the claw against the bridge
                goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 90, 1 * robot.COUNTS_PER_INCH, false);

                //raising claw to drop stone on waffle
                robot.stoneGrabberUpDown.setPosition (0.6);
                goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);

                //dropping stone on waffle
                autoDropStone();
                sleep(1000);

    }
}


