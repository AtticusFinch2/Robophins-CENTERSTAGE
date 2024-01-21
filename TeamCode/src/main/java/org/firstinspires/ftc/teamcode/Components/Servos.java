package org.firstinspires.ftc.teamcode.Components;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


@Config
public class Servos {

    public CRServo Sweep = null;
    public Servo Airplane;
    public Servo Flick;
    public Servo Claw;
    public Servo Purps;

    public Servos (HardwareMap hardwareMap) {
        Sweep = hardwareMap.get(CRServo.class, "sweeper");
        Airplane = hardwareMap.get(Servo.class, "airplane");
        Claw = hardwareMap.get(Servo.class, "claw");
        Flick = hardwareMap.get(Servo.class, "flick");
        Purps = hardwareMap.get(Servo.class, "purps");
        Flick.setPosition(0.12);
        Sweep.setPower(0.0);
        Airplane.setPosition(0.5);
        Purps.setPosition(0.35);
    }



}
