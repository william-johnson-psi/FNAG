/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
/**
 *
 * @author wjohn
 */
public class GullyAnimatronic {
    // Class References
    private CameraMonitor monitorReference;
    private Main mainClassReference;
    // Assets
    private Sound banging;
    private Music gullyJumpscare;
    // Jumpscaring 
    private boolean startJumpscare;
    private float jumpscareTimer;
    private float jumpscareStartTime;
    // Monitor Settings 
    private int roomLocation;
    private int aiLevel;
    private float moveTimer;
    private float timeToMove;
    private float randomPicker;
    
    private Sound stepSound;
//    private int randomChoice;
    
    public GullyAnimatronic(Main mainClass, CameraMonitor monitor, int aiLevel) {
        // Class References
        this.mainClassReference = mainClass;
        this.monitorReference = monitor;
        // Asset
        this.banging = Gdx.audio.newSound(Gdx.files.internal("audio/banging.wav"));
        this.gullyJumpscare = Gdx.audio.newMusic(Gdx.files.internal("audio/gullyJumpscare.mp3"));
        // Technical
        this.aiLevel = aiLevel;
        this.roomLocation = 0; 
        this.timeToMove = 5.0f;
        this.moveTimer = 0.0f;
        this.randomPicker = 0.0f;
        
        this.jumpscareTimer = 0.0f;
        this.jumpscareStartTime = 10.0f;
        this.startJumpscare = false; 
        
        stepSound = Gdx.audio.newSound(Gdx.files.internal("audio/steps.wav"));
//        this.randomChoice = 1;
        // Start Gully at Stage.
        monitor.moveGully(0, 0); 
    }
    
    public void updateMoveTimer(float dt) {
        if ((this.moveTimer > this.timeToMove) & (!startJumpscare)) {
            this.moveTimer = 0.0f;
            // 5 Seconds up, roll with AI Level.
            if (MathUtils.random(1, 20) <= this.aiLevel) {
                System.out.println("Gully is making a move."); // DEBUG 
                makeMove();
                if (MathUtils.random(1, 10) <= 5) {
                    this.stepSound.play();
                    System.out.println("Steps Sound");
                }
            }
            else {
                System.out.println("Gully decided not to move");
            }
        }
        else if (this.startJumpscare) {
            this.jumpscareTimer += dt;
            System.out.println("GULLY IS ABOUT TO JUMPSCARE");
            System.out.println("TIMER: " + this.jumpscareTimer + " / " + this.jumpscareStartTime);
            if (this.jumpscareTimer > this.jumpscareStartTime) {
                this.attemptingJumpscare();
            }
        }
        else {
            this.moveTimer += dt;
        }
        
    }
    
    public void moveGully(int destinationRoom) {
        System.out.println("Gully moved from Room " + this.roomLocation + " " + this.monitorReference.getRoomNameByIndex(this.roomLocation) + " to Room " + destinationRoom + " " + this.monitorReference.getRoomNameByIndex(destinationRoom));
        this.monitorReference.moveGully(this.roomLocation, destinationRoom);
        this.roomLocation = destinationRoom;
    }
    
    public void makeMove() {
        this.randomPicker = MathUtils.random(0.0f, 10.0f);
        // Main Stage Movement Possibilities://  65% Room 0 Stage Audience -- 35% Room 2 Corridor Door 
        if (this.roomLocation == 0) {
            if (this.randomPicker > 6.5f) {
                this.moveGully(2);
            }
            else {
                this.moveGully(1);
            }
        }
        // Stage Audience Possibilities:// 10% Room 3 Hallway -- 25% Room 0 Main Stage -- 65% Room 2 Corridor Door
        else if (this.roomLocation == 1 ) { 
            if (this.randomPicker <= 1.0f) {
                this.moveGully(3);
            }
            else if (this.randomPicker <= 3.5f) {
                this.moveGully(0);
            }
            else {
                this.moveGully(2);
            }
        }
        // Corridor Door:// 10% Room 0 Main Stage, 10% Room 4 Einsteins Backdoor, 25% Room 1 Stage Audience, Room 3 Hallway 
        else if (this.roomLocation == 2) {
            if (this.randomPicker <= 1.0f) {
                this.moveGully(0);
            }
            else if (this.randomPicker <= 2.0f) {
                this.moveGully(4);
            }
            else if (this.randomPicker <= 4.5f) {
                this.moveGully(1);
            }
            else {
                this.moveGully(3);
            }
        }
        // Hallway:// 10% Room 1 Stage Audience, 10% Room 5 Before CS Lounge, 20% Room 2 Corridor Door , 60% Room 4behind Einsteins 
        else if (this.roomLocation == 3 ) {
            if (this.randomPicker <= 1.0f) {
                this.moveGully(1);
            }
            else if (this.randomPicker <= 2.0f) {
                this.moveGully(5);
            }
            else if (this.randomPicker <= 4.0f) {
                this.moveGully(2);
            }
            else {
                this.moveGully(4);
            }
        }
        // Einsteins Backdoor:// 15% Room 3 Hallway -- 15% Room 2 Corridor Door, 70% Before CS Lounge 
        else if (this.roomLocation == 4 ) {
            if (this.randomPicker <= 1.5f) {
                this.moveGully(3);
            }
            else if (this.randomPicker <= 3.0f) {
                this.moveGully(2);
            }
            else {
                this.moveGully(5);
            }
        }
        else if (this.roomLocation == 5) {
            this.moveGully(6); // Temporary, working Jumpscare
            this.startJumpscare = true; 
            this.jumpscareTimer = 0.0f; 
            this.jumpscareStartTime = MathUtils.random(10.0f, 18.0f);
        }
    }
    
    public void attemptingJumpscare() {
        if (this.mainClassReference.isDoorClosed()) {
            this.banging.play();
            this.moveGully(MathUtils.random(1, 5));
            this.startJumpscare = false;
        }
        else {
            this.mainClassReference.startJumpscare();
            this.gullyJumpscare.play();
            this.startJumpscare = false;
        }
    }
    
    public Music getJumpscareSound() {
        return this.gullyJumpscare;
    }
}
