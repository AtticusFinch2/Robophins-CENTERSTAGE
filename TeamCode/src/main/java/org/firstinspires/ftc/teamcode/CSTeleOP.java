/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="CSTeleOP1", group="Linear OpMode")
//@Disabled THIS DISABLES THE OP MODE, LEAVE IT COMMENTED
public class CSTeleOP extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FL = null;
    private DcMotor FR = null;
    private DcMotor BL = null;
    private DcMotor BR = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        BL  = hardwareMap.get(DcMotor.class, "bl");
        BR = hardwareMap.get(DcMotor.class, "br");
        FL  = hardwareMap.get(DcMotor.class, "fl");
        FR = hardwareMap.get(DcMotor.class, "fr");

        BL.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        double x;
        double y;
        double flCurrentPower = 0;
        double frCurrentPower = 0;
        double blCurrentPower = 0;
        double brCurrentPower = 0;
        int stillModifier = 2;
        double flPWR, frPWR, blPWR, brPWR;
        double speed =0.5;//this affects how touchy the stick is to input;
        //             still goes to full power regardless
        float rx;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            x=0;
            y=0;
            flPWR=0;
            frPWR=0;
            blPWR=0;
            brPWR = 0;

            //This uses basic math to combine motions and is easier to drive straight.
            y = -gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            telemetry.addData("dir", "xin (%.2f), yin (%.2f)", x, y);
            x = Range.clip(x, -1.0, 1.0) ; // gives values between -1 and 1, useful later
            y = Range.clip(y, -1.0, 1.0) ;


            //convert circular plane to rectangular plane,
            // such that (x,y) : (1/sqrt(2),1/sqrt(2)) => (x,y) : (0.5,0.5)
            //           (x,y) : (1/sqrt(2),-1/sqrt(2)) => (x,y) : (0.5,-0.5)
            //           (x,y) : (-1/sqrt(2),-1/sqrt(2)) => (x,y) : (-0.5,-0.5)
            //           (x,y) : (-1/sqrt(2),1/sqrt(2)) => (x,y) : (-0.5,0.5)

            x = Math.asin(x)/(Math.PI/2);
            y = Math.asin(y)/(Math.PI/2);//convert to angle then divide by 90degrees(pi/2)
            telemetry.addData("dir", "x1 (%.2f), y1 (%.2f)", x, y);


            //creep (i'm a weirdoooooo what the hell am i doin' here)
            if (gamepad1.dpad_up) {
                y=0.3;
            } else if (gamepad1.dpad_down) {
                y=-0.3;
            } else if (gamepad1.dpad_left) {
                x=-0.3;
            } else if (gamepad1.dpad_right) {
                x=0.3;
            }


            //do smth to drive with x and y
            telemetry.addData("dir", "x2 (%.2f), y2 (%.2f)", x, y);
            if (Math.abs(x)> 0.03 || Math.abs(y)>0.03) { // if input is not due to stick drift
                if (Math.abs(x)<0.03) {x=0;} //if x is too low don't use it
                if (Math.abs(y)<0.03) {y=0;} //if y is too low don't use it

                // front left and back right give same power
               /*
               what to do with x and y: (with our drivebase)
                    Y
                a   b   c          a: no movement  b:forward      c:fast forward
                    |
              X_d___e___f_         d: backward     e:no movement  f:forward
                    |
                g   h   i          g:fast backward h:backward     i:no movement

                  use y+x to approximate this (from y+x=0)
                */
                flPWR = Range.clip(speed * (y+x), -1,1);
                brPWR = Range.clip(speed * (y+x), -1,1);


                // front right and back left give same power,
               /*
               what to do with x and y: (with our drivebase)
                a   b   c          a: fast forward b:forward       c:no movement
                    |
               _d___e___f_         d: forward      e:no movement   f:backward
                    |
                g   h   i          g:no movement   h:backward      i:fast backward
                  use y-x to approximate this (from y-x=0)
                */
                frPWR = Range.clip(speed * (y-x), -1,1);
                blPWR = Range.clip(speed * (y-x), -1,1);

            }


            rx = gamepad1.right_stick_x/2;//this is very touchy so it is divided by 4
            if (Math.abs(rx) > 0.03) { // we do a little spinning
                if (Math.abs(x) > 0.03 || Math.abs(y) > 0.03) {
                    flPWR += rx;
                    blPWR += rx;
                    frPWR -= rx;
                    brPWR -= rx;
                } else {
                    flPWR += rx * stillModifier;
                    blPWR += rx * stillModifier;
                    frPWR -= rx * stillModifier;
                    brPWR -= rx * stillModifier;
                }
            }

            flPWR = Range.clip(flPWR, -1.0, 1.0) ;
            frPWR = Range.clip(frPWR, -1.0, 1.0) ;
            blPWR = Range.clip(blPWR, -1.0, 1.0) ;
            brPWR = Range.clip(brPWR, -1.0, 1.0) ;
            // Send calculated power to wheels


            flCurrentPower -= Range.clip(flCurrentPower - flPWR,-0.02,0.02);
            frCurrentPower -= Range.clip(frCurrentPower - frPWR,-0.02,0.02);
            blCurrentPower -= Range.clip(blCurrentPower - blPWR,-0.02,0.02);
            brCurrentPower -= Range.clip(brCurrentPower - brPWR,-0.02,0.02);
            flCurrentPower = Range.clip(flCurrentPower,-1,1);
            frCurrentPower = Range.clip(frCurrentPower,-1,1);
            blCurrentPower = Range.clip(blCurrentPower,-1,1);
            brCurrentPower = Range.clip(brCurrentPower,-1,1);

            if (gamepad1.a) {
                flCurrentPower = 0.0;
                frCurrentPower = 0.0;
                blCurrentPower = 0.0;
                brCurrentPower = 0.0;
            }

            FL.setPower(flCurrentPower);
            FR.setPower(frCurrentPower);
            BL.setPower(blCurrentPower);
            BR.setPower(brCurrentPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            telemetry.update();
        }
    }
}

