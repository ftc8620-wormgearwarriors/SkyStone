import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Blue_Skystones")
public class Force_Odometry_Blue_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException
    {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(144, 39, 270);
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

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(true, false);  // look for skystone

        switch (StoneRember) {
            case LEFT: //SkyStone is on the left
                telemetry.addLine("LEFT");
                telemetry.update();
                goToPostion(120 * robot.COUNTS_PER_INCH, 32 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
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
                goToPostion(118 * robot.COUNTS_PER_INCH, 46 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                rightAutoGrabStone();
                sleep(250);
                break;
        }
        //going under the bridge to aviod hitting the claw against the bridge
        goToPostion(122 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
        sleep(5000);


        //raising claw to drop stone on waffle
        robot.rightStoneGrabberUpDown.setPosition(0.4);

        //drops skystone on waffle
        goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
        leftAutoDropStone();

//        goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 90, 100 * robot.COUNTS_PER_INCH, true);
//        goToPostion(105 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 90, 1 * robot.COUNTS_PER_INCH, false);
//        robot.LeftWaffle.setPosition(0);
//        robot.RightWaffle.setPosition(0);
//        sleep(250);
//        goToPostion(105 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 20 * robot.COUNTS_PER_INCH, true);
//        goToPostion(112 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);



        //goes under bridge
        goToPostion(125 * robot.COUNTS_PER_INCH, 60 * robot.COUNTS_PER_INCH, .8, 270, 1 * robot.COUNTS_PER_INCH, false);
        sleep(2000);
        goToPostion(125 * robot.COUNTS_PER_INCH, 60 * robot.COUNTS_PER_INCH, .8, 180, 100 * robot.COUNTS_PER_INCH, true);

        switch (StoneRember) {

            case RIGHT:
                goToPostion(96 * robot.COUNTS_PER_INCH, 26  * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                break;
            case CENTER:
                goToPostion(96 * robot.COUNTS_PER_INCH, 20  * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                telemetry.addData("x current position", robot.globalPositionUpdate.returnXCoordinate());
                telemetry.addData("y current position", robot.globalPositionUpdate.returnYCoordinate());
                telemetry.addData("current heading", robot.globalPositionUpdate.returnOrientation());
                telemetry.update();
                sleep(2000);
                break;
            case LEFT:
                goToPostion(96 * robot.COUNTS_PER_INCH, 14  * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
                break;




        }
        //grabs skystone
        leftAutoGrabStone();
        sleep(250);
        goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);

        //drops skystone on waffle
        goToPostion(110 * robot.COUNTS_PER_INCH, 120 * robot.COUNTS_PER_INCH, .8, 180, 1 * robot.COUNTS_PER_INCH, false);
        leftAutoDropStone();



    }
}
