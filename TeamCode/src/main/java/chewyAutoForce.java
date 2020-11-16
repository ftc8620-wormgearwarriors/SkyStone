import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


    /**
     * Created by Sarthak on 10/4/2019.
     */
    @Autonomous(name = "chewyAutoForce")
    public class chewyAutoForce extends force_SkyStoneAutonomousMethods {


        @Override
        public void runOpMode() throws InterruptedException {
            //Initialize hardware map values. PLEASE UPDATE THESE VALUES TO MATCH YOUR CONFIGURATION
            Init();
            initOdometryHardware(9 * robot.COUNTS_PER_INCH,24 * robot.COUNTS_PER_INCH,0);
            robot.rightStoneGrabberUpDown.setPosition(0);
            robot.rightStoneGrabberOpenClose.setPosition(0);
            robot.leftStoneGrabberOpenClose.setPosition(0);

            telemetry.addData("Status", "Init Complete");
            telemetry.update();
            waitForStart();

            //drive to launch line
            goToPostion(70 * robot.COUNTS_PER_INCH,24 * robot.COUNTS_PER_INCH,0.5,0,3 * robot.COUNTS_PER_INCH,false);

            //shoot powershot targets


            //park on launch line
            goToPostion(84 * robot.COUNTS_PER_INCH,24 * robot.COUNTS_PER_INCH,0.5,0,3 * robot.COUNTS_PER_INCH,false);

            //Stop the thread
            robot.globalPositionUpdate.stop();

        }

    }



