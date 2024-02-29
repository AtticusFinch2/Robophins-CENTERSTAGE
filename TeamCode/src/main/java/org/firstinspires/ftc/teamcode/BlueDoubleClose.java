package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.MainRobot;

@Autonomous(name = "BlueDoubleClose 2 PIXELS")
public class BlueDoubleClose extends LinearOpMode {
    MainRobot robot;
    int spike = 2;
    public Pose2d startPose = new Pose2d(16, 64, Math.toRadians(270));
    @Override
    public void runOpMode() {

        robot = new MainRobot(hardwareMap, true);
        waitForStart();
        robot.servos.Flick.setPosition(0.4);
        doTheCvThing();
        robot.pause(1200);
        robot.setPoseEstimate(startPose);
        switch (spike){
            case 1:
                robot.lighting.blinkMagenta();
                doSpike1();
                break; //FIXME REMOVE BREAKS AFTER PIXEL1 GETS COMPLETE
            case 2:
                robot.lighting.blinkCyan();
                doSpike2();
                break;
            case 3:
                robot.lighting.blinkGreen();
                doSpike3();
                break;
        }
        //pixel1(); //FIXME uncomment when confirm pixel1 works

    }
    public void doSpike1(){
        Trajectory forward_1  = robot.trajectoryBuilder(startPose)
                .forward(30)
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
        Trajectory spline_1 = robot.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(38, 44), Math.toRadians(0))
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
//temp change
    public void doSpike2(){
        Trajectory spline_1  = robot.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(14, 30), Math.toRadians(180))
                .build();
        startPose = spline_1.end();
        Trajectory left_1  = robot.trajectoryBuilder(startPose)
                .strafeLeft(6)
                .build();
        startPose = left_1.end();
        Trajectory right_1  = robot.trajectoryBuilder(startPose)
                .strafeRight(6)
                .build();
        startPose = right_1.end();
        Trajectory backup  = robot.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(38, 40, Math.toRadians(180)))
                .build();
        startPose = backup.end();

        robot.followTrajectory(spline_1);
        robot.followTrajectory(left_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectory(backup);
        robot.pause(500);
    }
    public void doSpike3(){
        Trajectory spline_1  = robot.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(10, 28), Math.toRadians(90))
                .build();
        startPose = spline_1.end();
        Trajectory left_1 = robot.trajectoryBuilder(startPose)
                .strafeLeft(4)
                .build();
        startPose = left_1.end();
        Trajectory right_1 = robot.trajectoryBuilder(startPose)
                .strafeRight(4)
                .build();
        startPose = right_1.end();
        Trajectory back_1  = robot.trajectoryBuilder(startPose)
                .lineToLinearHeading(new Pose2d(38, 40, Math.toRadians(180)))
                .build();
        startPose = back_1.end();
        robot.followTrajectory(spline_1);
        robot.followTrajectory(left_1);
        robot.servos.Purps.setPosition(0);
        robot.pause(500);
        robot.followTrajectory(right_1);
        robot.followTrajectory(back_1);
        robot.pause(500);
    }

    public void pixel1(){
        /*Trajectory creepbackward = robot.trajectoryBuilder(startPose)
                .back(10)
                .build();
        startPose = creepbackward.end();*/
        Trajectory creepbackward2 = robot.trajectoryBuilder(startPose)
                .back(3)
                .build();
        startPose = creepbackward2.end();

        //robot.followTrajectory(creepbackward);
        robot.pause(200);
        robot.servos.Flick.setPosition(0.5); //FIXME input position from teleop code (bring up)
        robot.pause(200);
        robot.slides.setSlidesPower(1.0);
        robot.pause(1500);
        robot.servos.Flick.setPosition(0); //FIXME input position from teleop code (scoring)
        robot.pause(200);
        robot.followTrajectory(creepbackward2);
        robot.servos.openClaw();
        robot.pause(200);
        robot.servos.closeClaw();
        robot.pause(200);
        robot.servos.Flick.setPosition(0.5); //FIXME input position from teleop code (bring back up)
        robot.slides.setSlidesPower(-1.0);
        robot.pause(1000);
        //FIXME based on testing (should end up in back scoreboard section)

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
