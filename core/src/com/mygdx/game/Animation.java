/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author wjohn
 */

// Animation Manager for deciding which scenes should be drawn when playing an animation.
public class Animation {
    private Sprite[] animationList;
    private boolean isPlaying;   // Main Program checks this, if it's playing then it will play the animation 
    private boolean isStarted; // Animation not started so animation player will give its frameIndex a 0.
    private float currentTimeToSwitch; // Accumlator DT Values, reset to 0 when bigger than interval
    private float interval; // When the time to switch is bigger than interval, move to next animation frame 
    private int frameIndex; // Determines the frame the animation is on, accumulates or goes backward depending on requested animation
    
    public Animation(Sprite[] animationList, float interval) {
        this.animationList = animationList;
        this.interval = interval;
        this.currentTimeToSwitch = 0;
        this.frameIndex = 0;
        this.isPlaying = false;
        this.isStarted = false;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    public Sprite playAnimation(boolean isReversed, float dt) {
        if (!isReversed) {
            if (!isStarted) {
                frameIndex = 0;
                this.isStarted = true;
                this.isPlaying = true;
            }
            this.currentTimeToSwitch += dt;
            if (this.frameIndex >= this.animationList.length-1) {
                this.isPlaying = false;
                return this.animationList[frameIndex];
            }
            if (this.currentTimeToSwitch > this.interval) {
                frameIndex += 1;
                this.currentTimeToSwitch = 0.0f;
            }
            return this.animationList[frameIndex];
        }
        else {
            if (!isStarted) {
                frameIndex = this.animationList.length-1;
                this.isStarted = true;
                this.isPlaying = true;
            }
            this.currentTimeToSwitch += dt;
            if (this.frameIndex <= 0) {
                this.isPlaying = false; 
                return this.animationList[frameIndex];
            }
            if (this.currentTimeToSwitch > this.interval) {
                frameIndex -= 1;
            }
            return this.animationList[frameIndex];
        }
    }
    
    public Sprite playEndlessAnimation(float dt) {
        if (this.currentTimeToSwitch > this.interval) {
            this.frameIndex += 1; 
            this.currentTimeToSwitch = 0;
            if (this.frameIndex > this.animationList.length-1) {
                this.frameIndex = 0;
            }
        }
        this.currentTimeToSwitch += dt;
        return this.animationList[this.frameIndex];
    }
    public void stopAnimation() {
        this.isStarted = false;
        this.isPlaying = false; 
        this.frameIndex = 0;
        this.currentTimeToSwitch = 0;
    }
    
    ////    

}
