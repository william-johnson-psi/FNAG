/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.ArrayList;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.*;
/**
 *
 * @author wjohn
 */
public class CameraMonitor {
    private ArrayList<Room> cameras; 
    
    private float delayTimer;
    private float waitDelay;
    private boolean isMovingLeft;
    private boolean isPaused;
    
    private int cameraNumber;
    private Sound blip;
   
    
    public CameraMonitor() {
        this.cameras = new ArrayList<Room>();
        this.cameraNumber = 0;
        this.blip = Gdx.audio.newSound(Gdx.files.internal("audio/blip.wav"));
        this.delayTimer = (float)0.0;
        this.waitDelay = (float)3.0;
        this.isMovingLeft = true; 
        this.isPaused = false;
    }
    
    
    public void moveGully(int prevRoom, int nextRoom) {
        this.cameras.get(prevRoom).removeGully(); 
        this.cameras.get(nextRoom).addGully();
    }
    public void addRoom(Room newRoom) {
        this.cameras.add(newRoom); // IMPORTANT INDEX CORRESPONDS WITH CAM NUMBER / ROOM NUMBER
    }
    
    public void changeCameraNumber(int cameraNumber) {
        this.cameraNumber = cameraNumber;
        this.blip.play();
    }
    
    public String getRoomNameByIndex(int i) {
        return this.cameras.get(i).getRoomName();
    }
    
    public int getCameraNumber() {
        return this.cameraNumber;
    }
    
    public boolean isGullyInside(int roomNumber) {
        return this.cameras.get(roomNumber).isGullyInside();
    }
    public Sprite getCurrentRoomPicture(OrthographicCamera camera) {
        if (this.isPaused) {
            this.delayTimer += Gdx.graphics.getDeltaTime();
            if (this.delayTimer > (float)3.0) {
                this.isPaused = false;
                this.delayTimer = (float)0.0;
            }
        }
        
        if (this.isMovingLeft) {
            if (camera.position.x > 70) {
                CameraHelper.autoTranslate(camera, this.isMovingLeft, this.isPaused);
            }
            else {
                this.isMovingLeft = false; 
                this.isPaused = true; 
            }
        }
        else {
            if (camera.position.x < 112.0) {
                CameraHelper.autoTranslate(camera, this.isMovingLeft, this.isPaused);
            }
            else {
                this.isMovingLeft = true; 
                this.isPaused = true;
            }
        }
        return this.cameras.get(this.cameraNumber).getRoomImage();
    }
    
}
