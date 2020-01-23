import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Red_Skystones")
public class Force_Odometry_Red_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(135,111,90);
        init_vuforia_2(); //init camera


        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(false,true);  // look for skystone

        //goToPostion(48*robot.COUNTS_PER_INCH,0*robot.COUNTS_PER_INCH,1,0,1*robot.COUNTS_PER_INCH,false);
        //goToPostion(0 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
        //sleep(5000);
        //goToPostion( 24 *COUNTS_PER_INCH,24*COUNTS_PER_INCH,.5,0,1*COUNTS_PER_INCH);
        //goToPostion(0 *COUNTS_PER_INCH,0*COUNTS_PER_INCH, 0.5, 0, 1*COUNTS_PER_INCH);


        switch (StoneRember) {
            case LEFT: //SkyStone is on the left
                telemetry.addLine("LEFT");
                goToPostion(115 * robot.COUNTS_PER_INCH, 35 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case CENTER: //SkyStone is on the center
                telemetry.addLine("CENTER");
                goToPostion(115 * robot.COUNTS_PER_INCH, 44 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case RIGHT:  //SkyStone is on the right
                telemetry.addLine("RIGHT");
                goToPostion(115* robot.COUNTS_PER_INCH, 49 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
        }


//        goToPostion(104 * robot.COUNTS_PER_INCH, 125 * robot.COUNTS_PER_INCH,.8, 90, 1 * robot.COUNTS_PER_INCH, false);
//        robot.RightWaffle.setPosition(0);
//        robot.LeftWaffle.setPosition(0);
//        sleep(1000);
//        //goToPostion(104*robot.COUNTS_PER_INCH, 120* robot.COUNTS_PER_INCH,1,180,100* robot.COUNTS_PER_INCH,true);
//        goToPostion(135 * robot.COUNTS_PER_INCH, 130 * robot.COUNTS_PER_INCH,.8, 90, 1 * robot.COUNTS_PER_INCH, false);
//        sleep(1000);
//        robot.RightWaffle.setPosition(1);
//        robot.LeftWaffle.setPosition(1);
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
//
//        //Stop the thread
//        robot.globalPositionUpdate.stop();
//
    }

}

