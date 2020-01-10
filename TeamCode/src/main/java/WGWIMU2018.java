// Team 8620 IMU Functions (TeleOP), Created By Reece Watson on 10/8/17

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;


//@Autonomous(name="MyIMU", group="InheritedClasses")

public class WGWIMU2018  {

    private double offset = 0;
    private BNO055IMU myIMU2017;

    public WGWIMU2018(BNO055IMU imu) {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.useExternalCrystal = true;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.pitchMode = BNO055IMU.PitchMode.WINDOWS;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu.initialize(parameters);

        myIMU2017 = imu;
    }

    public double getHeading() {
        Orientation angles;

        angles   = myIMU2017.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double heading = angles.firstAngle - offset;
        if (heading  < 0)
            heading = heading + 360;
        if (heading > 360)
            heading = heading - 360;
            return heading;
            //        Quaternion quatAngles = myIMU2017.getQuaternionOrientation();
//
//        double w = quatAngles.w;
//        double x = quatAngles.x;
//        double y = quatAngles.y;
//        double z = quatAngles.z;
//
//        double yaw = (Math.atan2(1 - 2 * (y * y + z * z), 2 * (w * z + x * y)) * 180 / Math.PI) - 90;
//        yaw = 360 - yaw;
//        if (yaw < 0) {
//            yaw = yaw + 360;
//        }
//
//        return yaw - offset;
    }

    public double resetHeading() {
        offset = getHeading() + offset;

        return offset;
    }
}

