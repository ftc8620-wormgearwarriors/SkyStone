
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Test_Auto")
public class Test_Auto extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        Init();     //  init robot hardware

        init_vuforia_2(); //init camera


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(false,true);  // look for skystone

        switch (StoneRember) { // distance 11 and 10 to far forward
            case LEFT: //SkyStone is left
                frontgap(15,1,55,sensorSide.LEFT ); //needs to be fixed
                telemetry.addLine("LEFT");
                break;
            case CENTER: //SkyStone is on center
                telemetry.addLine(":CENTER");
                frontgap(15,1,73,sensorSide.LEFT); //fixed
                break;
            case RIGHT:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                frontgap(15,1,90,sensorSide.LEFT);  //fixed 11 to far forward
                //strafe(60,-1);
                break;
        }
        grabBlock();
        armExt(2000,1);

        rotate(85,1);

        switch(StoneRember) {
            //KEY:  1: drive under bridge with gap
            case LEFT:
                gap(80, 1, 53, sensorSide.RIGHT); //1
                break;
            case CENTER:
                gap(100, 1, 53, sensorSide.RIGHT); //1
                break;
            case RIGHT:
                gap(120, 1, 53, sensorSide.RIGHT); //1
                break;
        }

        frontgap(53,1,69, sensorSide.RIGHT); //drive to waffle
        strafe(60,1); // lines up on block
        drive(15,1); // drive to place block decrease this drive was 30
        robot.OpenServo.setPosition(0);//set power zero and wait half a second.
        armTilt(.98,0.8); // tilt to clear skystone
        drive(20,-1); // backs off waffle
        rotate(170,1);
        robot.RightWaffle.setPosition(0.5);
        robot.LeftWaffle.setPosition(0.5);
        strafe(25,1);
        robot.RightWaffle.setPosition(0);
        robot.LeftWaffle.setPosition(1);


        telemetry.update();

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



