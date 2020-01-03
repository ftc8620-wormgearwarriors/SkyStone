
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.RobotLog;

@Autonomous (name = "Swartz_Auto_Blue")
public class Swartz_Auto_Blue extends SkyStoneAutonomousMethods {

    public void runOpMode() {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        //  init robot hardware
        Init();

        //init camera
        init_vuforia_2();
        armTilt(0.997,0.8);         //tilt up to level so the claw clears the base

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Identifies placement of skystone with vision code
        double frontDistance = 15.0;
        double frontGap = 0.0;
        double frontVelocity = 1.0;


        VuforiaStuff.skystonePos StoneRember = vuforiaStuff.vuforiascan(false, false);  // look for skystone
//        telemetry.addData("Init Arm Tilt Encoder",robot.LiftMotor.getCurrentPosition());
//        telemetry.addData("Init Extension ticks",robot.ExtendMotor.getCurrentPosition());
        telemetry.update();
        //sleep(5000);

        armTiltWithEncoder(-1000,0.50);      //  rotate the arm up, but don't wait for it to finish moving
        armExtNonBlockling(2893,0.8);       //  extend the arm, but don't wait for it to finish moving
        switch (StoneRember) {
            case LEFT: //SkyStone is left
                frontGap = 79.0;
                telemetry.addLine("LEFT");
                break;
            case CENTER: //SkyStone is on center
                frontGap = 59.0;
                telemetry.addLine(":CENTER");
                break;
            case RIGHT:  //SkyStone is on the right
                frontGap = 41.0;
                telemetry.addLine("Right");
                break;
        }
        telemetry.update();


        //Drives forward to skystone
        frontgap(frontDistance, frontVelocity, frontGap, sensorSide.RIGHT, sensorFront.DEATHSTAR);


        //Extend arm and grab stone and put arm in position to go under bridge
        grabBlock();
        armExt(2000, 1);

        // Turning to drive under bridge
        rotate(275, -1);

        // Driving under bridge depending on skystone position
        switch (StoneRember) {
            //KEY:  1: drive under bridge with gap
            case LEFT:// Left position
                gap(130, 1, 53, sensorSide.LEFT); //1
                break;
            case CENTER:// center position
                gap(140, 1, 53, sensorSide.LEFT); //1
                 break;
            case RIGHT:// right position
                gap(155, 1, 53, sensorSide.LEFT); //1
                break;
        }

        //drive to waffle
        //  frontgap(53, 1, 69, sensorSide.LEFT, sensorFront.WOOKIE);
        // lines up on block
        armExtNonBlockling(2800,1); //TODO strafed to far to the left
        armTiltWithEncoder(-1000,0.50);      //  rotate the arm up, but don't wait for it to finish moving

        strafe(90, -1, 98, sensorFront.WOOKIE); // lines up on block was 66
        // drive to place block

        // extending arm and dropping block
        armTilt(1.12, 1);
        armExt(2800, 1);
        robot.OpenServo.setPosition(0);

        // tilt to clear skystone
        armTilt(.98, 0.8);

        //rotates to align to the waffle
        rotate(0, 1);

        //grabbers on waffle
        robot.RightWaffle.setPosition(0.5);
        robot.LeftWaffle.setPosition(0.5);

        //strafes onto waffle
        strafe(15, 1, 0, sensorFront.NOSENSOR);

        //grabs waffle
        robot.RightWaffle.setPosition(0);
        robot.LeftWaffle.setPosition(1);
        sleep(400);

        //strafe waffle into build site
        rotate(300,-1);

        robot.RightWaffle.setPosition(1);
        robot.LeftWaffle.setPosition(0);
        strafe(80,1, 0, sensorFront.NOSENSOR); //strafe waffle into build site
        drive(65,-1);
        telemetry.update();
    }
}



