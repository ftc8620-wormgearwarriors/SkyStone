
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.RobotLog;

@Autonomous (name = "Test_Auto")
public class Test_Auto extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        telemetry.addLine("Initializing PLEASE WAIT!!!");
        telemetry.update();

        Init();     //  init robot hardware

        init_vuforia_2(); //init camera


        // Wait for the game to start (driver presses PLAY)
        telemetry.addLine("Init Complete, ready to START");
        telemetry.update();
        waitForStart();

        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(false,true);  // look for skystone

        telemetry.addData("Init Arm Tilt Encoder",robot.LiftMotor.getCurrentPosition());
        telemetry.addData("Init Extension ticks",robot.ExtendMotor.getCurrentPosition());
        telemetry.update();
        //sleep(5000);

        armTiltWithEncoder(-1000,0.50);      //  rotate the arm up, but don't wait for it to finish moving
        armExtNonBlockling(2893,0.8);       //  extend the arm, but don't wait for it to finish moving

        //sleep(5000);

        /*
        while(robot.ExtendMotor.isBusy() || robot.LiftMotor.isBusy()){
            telemetry.addData("Arm Tilt Encoder",robot.LiftMotor.getCurrentPosition());
            telemetry.addData("Extension ticks",robot.ExtendMotor.getCurrentPosition());
            telemetry.update();

        }
        */

        // sleep(3000);

        switch (StoneRember) { // distance 11 and 10 to far forward
            case LEFT: //SkyStone is left
                frontgap(15,1,57 ,sensorSide.LEFT, sensorFront.DEATHSTAR ); //needs to be fixed
                telemetry.addLine("LEFT");
                break;
            case CENTER: //SkyStone is on center
                telemetry.addLine(":CENTER");
                frontgap(15,1,75,sensorSide.LEFT, sensorFront.DEATHSTAR); //fixed
                break;
            case RIGHT:  //SkyStone is on the right
                //strafe(25,-0.5); //Strafe over to the skystone
                telemetry.addLine("Right");
                frontgap(15,1,90,sensorSide.LEFT, sensorFront.DEATHSTAR);  //fixed 11 to far forward
                //strafe(60,-1);
                break;
        }
        RobotLog.d("8620WGW Test_Auto Before 1st GrabBlock.  Arm Angle=" + robot.LiftMotor.getCurrentPosition() + "Arm Extension=" + robot.ExtendMotor.getCurrentPosition());

        grabBlock();
        armExt(2000,1);

       drive(15,-1);
        rotate(85,1);

        switch(StoneRember) {
            //KEY:  1: drive under bridge with gap TODO test the distances of the gap drive
            case LEFT:
                gap(140, 1, 53, sensorSide.RIGHT); //1
                break;
            case CENTER:
                gap(130, 1, 53, sensorSide.RIGHT); //1
                break;
            case RIGHT:
                gap(120, 1, 53, sensorSide.RIGHT); //1
                break;
        }

        //frontgap(110,1,69, sensorSide.RIGHT, sensorFront.WOOKIE); //drive to waffle
        armExtNonBlockling(2800,1); //TODO strafed to far to the left
        armTiltWithEncoder(-1000,0.50);      //  rotate the arm up, but don't wait for it to finish moving
        strafe(66,1, 100, sensorFront.WOOKIE); // lines up on block
        //drive(10,1); // drive to place block decrease this drive was 30
        robot.OpenServo.setPosition(0);//set power zero and wait half a second.
        armTilt(.98,0.8); // tilt to clear skystone
        rotate(170,1); //rotates to align the waffle grabers on waffle
        armExtNonBlockling(1500,1);
        armTiltWithEncoder(900,0.25);
        //drive(10,1);//todo get closer to waffle
        robot.RightWaffle.setPosition(0.5); //moves the waffle grabber to push out the skystone if it is in the way
        robot.LeftWaffle.setPosition(0.5);
        strafe(15,1, 0, sensorFront.NOSENSOR); //strafes onto waffle was 20
        robot.RightWaffle.setPosition(0); //grabs waffle
        robot.LeftWaffle.setPosition(1);
        sleep(400); //TODO can this be shortned or taken out
        rotate(255,1); //rotates to align the waffle with building zone needs to be 240 to 250 was 225
        robot.RightWaffle.setPosition(1); //grabs waffle
        robot.LeftWaffle.setPosition(0);
        strafe(80,1, 0, sensorFront.NOSENSOR); //strafe waffle into build site
        drive(65,1);
        telemetry.update();

        }
    }



