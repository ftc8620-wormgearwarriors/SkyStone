import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Force_Odometry_Red_Skystones")
public class Force_Odometry_Red_Skystones extends force_SkyStoneAutonomousMethods {


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
        Init();
        initOdometryHardware(135, 111, 90);
        init_vuforia_2(); //init camera


        telemetry.addData("Status", "Init Complete");
        telemetry.update();
        waitForStart();

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(false, true);  // look for skystone

        switch (StoneRember) {
            case LEFT: //SkyStone is on the left
                telemetry.addLine("LEFT");
                telemetry.update();
//                goToPostion(115 * robot.COUNTS_PER_INCH, 35 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case CENTER: //SkyStone is on the center
                telemetry.addLine("CENTER");
                telemetry.update();
//                goToPostion(115 * robot.COUNTS_PER_INCH, 44 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
            case RIGHT:  //SkyStone is on the right
                telemetry.addLine("RIGHT");
                telemetry.update();
//                goToPostion(115* robot.COUNTS_PER_INCH, 49 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
                autoGrabStone();
                break;
        }




//                //Skystone is on the left
//                goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                sleep(1000);
//                goToPostion(125 * robot.COUNTS_PER_INCH, 60 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                autoDropStone();
//
//                //Skystone is on the center
//                goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                sleep(1000);
//                goToPostion(125 * robot.COUNTS_PER_INCH, 60 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                autoDropStone();
//
//                //Skystone is on the right
//                goToPostion(125 * robot.COUNTS_PER_INCH, 100 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                sleep(1000);
//                goToPostion(125 * robot.COUNTS_PER_INCH, 60 * robot.COUNTS_PER_INCH,.8, 180, 1 * robot.COUNTS_PER_INCH, false);
//                autoDropStone();

    }
}


