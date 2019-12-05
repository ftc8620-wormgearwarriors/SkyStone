
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Test_Auto blue")
public class Test_Auto_Blue extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        init_vuforia_2(); //init camera


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(true,false);  // look for skystone

        switch (StoneRember) { // distance 11 and 10 to far forward
            case LEFT: //SkyStone is left
                frontgap(15,1,79,sensorSide.RIGHT ); //needs to be fixed
                telemetry.addLine("LEFT");
                break;
            case CENTER: //SkyStone is on center
                telemetry.addLine(":CENTER");
                frontgap(15,1,59,sensorSide.RIGHT); //fixed
                break;
            case RIGHT:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                frontgap(15,1,41,sensorSide.RIGHT);  //fixed 11 to far forward
                //strafe(60,-1);
                break;
        }
        telemetry.update();
        grabBlock();
        armExt(2000,1);



        //drive(16,  -1);     //Drive Back
/*
        switch (StoneRember) {
            case 2: //SkyStone is on the right
                strafe(100, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(150);     //way to big was 7 00     // Sleeping to drop block
                strafe(169, 1);  // Going Back to grab another block
                drive(23, 1);   // Going forward to grab block //was 16

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(181, -1); //was 171
                robot.OpenServo.setPosition(0.36);
                break;
            case 1: //SkyStone is on the left
                strafe(120, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(190, 1);  // Going Back to grab another block
                drive(18, 1);   // Going forward to grab block

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(200, -1);
            break;
            case 0: //SkyStone is on the offscreen
                strafe(145, -1);  //Sideways Under Bridge

                robot.OpenServo.setPosition(0.36);  // Open Claw
                sleep(400);     //way to big was 7 00     // Sleeping to drop block
                strafe(40,1);
                /*
                strafe(240, 1);  // Going Back to grab another block
                drive(16,    -1);   // Going forward to grab block

                grabBlock();

                drive(18, -1);
                armExt(2000, 1);
                strafe(220, -1); */
           // break;
        }
        }



