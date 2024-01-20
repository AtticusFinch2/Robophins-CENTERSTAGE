package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.Main;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Components.MainRobot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;
@Autonomous(name = "RedNOARM")
public class RedNOARM extends LinearOpMode {
    MainRobot robot;
    int spike = 2;
    public Pose2d startPose = new Pose2d(24, 70, Math.toRadians(270));
    @Override
    public void runOpMode() {

        robot = new MainRobot(hardwareMap, false);
        waitForStart();
        //robot.servos.Rotator.setPosition(0.17);
        doTheCvThing();
        robot.pause(1200);
        robot.setPoseEstimate(startPose);
        spike = 2; // TODO: REMOVE WHEN DONE MAKING TRAJECTORIES FOR EACH SPIKE
        switch (spike){
            case 1:
                doSpike1();
            case 2:
                doSpike2();
            case 3:
                doSpike3();
        }
        parkr();

    }
    public void doSpike1(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(15)
                .build();
        startPose = forward_1.end();
        Trajectory left_1 = robot.trajectoryBuilder(startPose)
                .strafeLeft(4)
                .build();
        startPose = left_1.end();
        Trajectory right_1 = robot.trajectoryBuilder(startPose)
                .strafeRight(4)
                .build();
        startPose = right_1.end();
        Trajectory spline_1  = robot.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(startPose.getX()-20, startPose.getY()+5), Math.toRadians(0))
                .build();
        startPose = spline_1.end();

        robot.followTrajectory(forward_1);
        robot.followTrajectory(left_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectory(spline_1);
        robot.pause(500);
    }
    public void doSpike2(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(5)
                .build();
        startPose = forward_1.end();
        Trajectory spline_1  = robot.trajectoryBuilder(forward_1.end())
                .splineTo(new Vector2d(startPose.getX()+5, startPose.getY()-21), startPose.getHeading()-Math.toRadians(90))
                .build();
        startPose = spline_1.end();
        Trajectory right_1 = robot.trajectoryBuilder(startPose)
                .strafeRight(4)
                .build();
        startPose = right_1.end();
        TrajectorySequence turn_1 = robot.trajectorySequenceBuilder(startPose)
                .turn(Math.toRadians(-180))
                .build();
        startPose = turn_1.end();
        Trajectory backup  = robot.trajectoryBuilder(startPose)
                .back(38)
                .build();
        startPose = backup.end();
        robot.followTrajectory(forward_1);
        robot.followTrajectory(spline_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectorySequence(turn_1);
        robot.followTrajectory(backup);
        robot.pause(500);
    }
    public void doSpike3(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(20)
                .build();
        startPose = forward_1.end();
        Trajectory right_1 = robot.trajectoryBuilder(startPose)
                .strafeRight(4)
                .build();
        startPose = right_1.end();
        Trajectory left_1 = robot.trajectoryBuilder(startPose)
                .strafeLeft(4)
                .build();
        startPose = left_1.end();
        Trajectory spline_1 = robot.trajectoryBuilder(right_1.end())
                .splineTo(new Vector2d(startPose.getX() -10,startPose.getY()+15), Math.toRadians(0))
                .build();
        startPose = spline_1.end();
        Trajectory backcreep = robot.trajectoryBuilder(startPose)
                .back(20)
                .build();
        startPose = backcreep.end();

        robot.followTrajectory(forward_1);
        robot.followTrajectory(right_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(left_1);
        robot.followTrajectory(spline_1);
        robot.followTrajectory(backcreep);
        robot.pause(500);
    }

    public void parkr(){
        Trajectory rightpark = robot.trajectoryBuilder(startPose)
                .strafeRight(32)
                .build();
        startPose = rightpark.end();

        Trajectory creepbackward2 = robot.trajectoryBuilder(startPose)
                .back(6)
                .build();
        startPose = creepbackward2.end();
        robot.followTrajectory(rightpark);
        robot.pause(300);
        robot.followTrajectory(creepbackward2);
    }
    private ElapsedTime runtime = new ElapsedTime();
    public void doTheCvThing(){
        robot.vision.open();
        //robot.pause(2000);// hoping this is enough to get the camera booted up
        runtime.reset();
        while (runtime.seconds() < 4){
            spike = robot.vision.getSpike();
        }
        robot.pause(50);
        robot.vision.close();
    }




}