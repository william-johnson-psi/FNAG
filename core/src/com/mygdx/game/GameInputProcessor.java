/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.*;
/**
 *
 * @author wjohn
 */
public class GameInputProcessor implements InputProcessor {
    Main mainClass;
    CameraMonitor monitor;
    
    public GameInputProcessor(Main mainClass, CameraMonitor gameCameraMonitor) {
        this.mainClass = mainClass;
        this.monitor = gameCameraMonitor;
    }
    public boolean keyDown(int i) {
        if (this.mainClass.isCameraMode()) { // I'm aware of this messy if block, but I'd rather use this until I'm comfortable with switches.
            if (com.badlogic.gdx.Input.Keys.NUM_1 == i) {
                monitor.changeCameraNumber(0);
            }
            else if (com.badlogic.gdx.Input.Keys.NUM_2 == i ) {
                monitor.changeCameraNumber(1);
            }
            else if (com.badlogic.gdx.Input.Keys.NUM_3 == i ) {
                monitor.changeCameraNumber(2);
            }
            else if (com.badlogic.gdx.Input.Keys.NUM_4 == i ) {
                monitor.changeCameraNumber(3);
            }
            else if (com.badlogic.gdx.Input.Keys.NUM_5 == i ) {
                monitor.changeCameraNumber(4);
            }
            else if (com.badlogic.gdx.Input.Keys.NUM_6 == i ) {
                monitor.changeCameraNumber(5);
            }
        }
        else if (this.mainClass.isInLeftOffice() && !this.mainClass.isCameraMode()) {
            if (com.badlogic.gdx.Input.Keys.F == i) {
                if (!this.mainClass.isLightOn()) {
                    this.mainClass.enableLight();
                }
                else {
                    this.mainClass.disableLight();
                }
            }
            else if (com.badlogic.gdx.Input.Keys.SPACE == i ) {
                if (this.mainClass.isDoorClosed()) {
                    this.mainClass.openDoor();
                }
                else {
                    this.mainClass.closeDoor();
                }
            }
        }
        if (com.badlogic.gdx.Input.Keys.U == i ) {
            this.mainClass.startJumpscare();
            System.out.println("STARTED JUMPSCARE");
        }
        return false;
    }

   public boolean keyUp (int keycode) {
      return false;
   }

   public boolean keyTyped (char character) {
      return false;
   }

   public boolean touchDown (int x, int y, int pointer, int button) {
      return false;
   }

   public boolean touchUp (int x, int y, int pointer, int button) {
      return false;
   }

   public boolean touchDragged (int x, int y, int pointer) {
      return false;
   }
   
   public boolean touchCancelled(int v, int y, int z, int x) {
       return false;
   }
   
   public boolean mouseMoved (int x, int y) {
      return false;
   }

   public boolean scrolled (float amountX, float amountY) {
      return false;
   }
}
