package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.MainRobot;

@Autonomous(name = "RedFlickFar") // THIS IS JUST BLUEFLICK WITH NO PARKING
public class RedFlickFar extends LinearOpMode {
    MainRobot robot;
    int spike = 2;
    public Pose2d startPose = new Pose2d(24, 70, Math.toRadians(90));
    @Override
    public void runOpMode() {

        robot = new MainRobot(hardwareMap, false);
        waitForStart();
        //robot.servos.Rotator.setPosition(0.17);
        doTheCvThing();
        robot.pause(1200);
        robot.setPoseEstimate(startPose);
        //spike = 3;
        switch (spike){
            case 1:
                robot.lighting.blinkMagenta();
                doSpike1();
                break;
            case 2:
                robot.lighting.blinkCyan();
                doSpike2();
                break;
            case 3:
                robot.lighting.blinkGreen();
                doSpike3();
                break;
        }
        //parkr();

    }
    public void doSpike1(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(29)
                .build();
        startPose = forward_1.end();
        Trajectory left_1 = robot.trajectoryBuilder(startPose)
                .strafeLeft(3)
                .build();
        startPose = left_1.end();
        Trajectory right_1 = robot.trajectoryBuilder(startPose)
                .strafeRight(3)
                .build();
        startPose = right_1.end();
        Trajectory forward_2 = robot.trajectoryBuilder(startPose)
                .forward(19)
                .build();
        startPose = forward_2.end();
        Trajectory right_2 = robot.trajectoryBuilder(startPose)
                .strafeRight(90)
                .build();
        startPose = right_2.end();

        robot.followTrajectory(forward_1);
        robot.followTrajectory(left_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectory(forward_2);
        robot.followTrajectory(right_2);
        robot.pause(500);
    }

    public void doSpike2(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(8)
                .build();
        startPose = forward_1.end();
        Trajectory spline_1  = robot.trajectoryBuilder(forward_1.end())
                .splineTo(new Vector2d(startPose.getX(), startPose.getY()+19), Math.toRadians(0))
                .build();
        startPose = spline_1.end();
        Trajectory right_2  = robot.trajectoryBuilder(startPose)
                .strafeRight(5)
                .build();
        startPose = right_2.end();
        Trajectory back_1  = robot.trajectoryBuilder(startPose)
                .back(10)
                .build();
        startPose = back_1.end();
        Trajectory left_1  = robot.trajectoryBuilder(startPose)
                .strafeLeft(30)
                .build();
        startPose = left_1.end();
        Trajectory forward_2  = robot.trajectoryBuilder(startPose)
                .forward(100)
                .build();
        startPose = forward_2.end();

        robot.followTrajectory(forward_1);
        robot.followTrajectory(spline_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_2);
        robot.followTrajectory(back_1);
        robot.followTrajectory(left_1);
        robot.followTrajectory(forward_2);
        robot.pause(500);
    }
    public void doSpike3(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(6)
                .build();
        startPose = forward_1.end();
        Trajectory left_1 = robot.trajectoryBuilder(startPose)
                .strafeLeft(4)
                .build();
        startPose = left_1.end();
        Trajectory spline_1  = robot.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(startPose.getX()+4, startPose.getY()+18), Math.toRadians(-90))
                .build();
        startPose = spline_1.end();
        Trajectory left_2 = robot.trajectoryBuilder(startPose)
                .strafeLeft(5)
                .build();
        startPose = left_2.end();
        Trajectory right_1  = robot.trajectoryBuilder(startPose)
                .strafeRight(5)
                .build();
        startPose = right_1.end();
        Trajectory back_1  = robot.trajectoryBuilder(startPose)
                .back(23)
                .build();
        startPose = back_1.end();
        Trajectory left_final  = robot.trajectoryBuilder(startPose)
                .strafeLeft(90)
                .build();
        startPose = left_final.end();
        robot.followTrajectory(forward_1);
        robot.followTrajectory(left_1);
        robot.followTrajectory(spline_1);
        robot.followTrajectory(left_2);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectory(back_1);
        robot.followTrajectory(left_final);
        robot.pause(500);
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