
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Swartz_MeetOne_Blue -")
public class force_Meet_One_Blue extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        VuforiaInit(); //init camera


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        drive(50, 1);            // Drive to be in range to see skystone

        int StoneRember = SkyStoneDetection();  // look for skystone

        switch (StoneRember) {
            case 2: //SkyStone is off Screen
                strafe(26, -1, 0, sensorFront.NOSENSOR);
                telemetry.addLine("OffScreen");
                break;
            case 1: //SkyStone is on the right
                telemetry.addLine(":Left");
                strafe(5, -1, 0, sensorFront.NOSENSOR);
                break;
            case 0:  //SkyStone is on the left
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                strafe(20, 1, 0, sensorFront.NOSENSOR);
                //strafe(60,-1);
                break;
        }
        grabBlock();
        armExt(2000, 1);

        telemetry.update();

        drive(16, -1);     //Drive Back

        switch (StoneRember) {
            case 2: //SkyStone is offscreen working
                strafe(180, 1, 0, sensorFront.NOSENSOR);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(120, -1, 0, sensorFront.NOSENSOR);  // Going Back to grab another block
                drive(25, 1);   // Going forward to grab block //was 16

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(120, 1, 0, sensorFront.NOSENSOR); //was 171
                robot.OpenServo.setPosition(0.36);
                strafe(40, -1, 0, sensorFront.NOSENSOR);
                drive(25, -1);
                break;
            case 1: //SkyStone is on the left
                strafe(150, 1, 0, sensorFront.NOSENSOR);  //Sideways Under Bridge Fixing

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(40, -1, 0, sensorFront.NOSENSOR);
                //sleep(1000000);
                break;
            case 0: //SkyStone is on the right working
                strafe(110, 1, 0, sensorFront.NOSENSOR);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);   // Open Claw

                strafe(15, -1, 0, sensorFront.NOSENSOR);
                drive(25, -1);
                /*sleep(150);              //way to big was 7 00     // Sleeping to drop block
                strafe(171, -1);    // Going Back to grab another block
                drive(20,    -1);   // Going forward to grab block

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(181, 1); */
                break;
        }
    }
}



