package com.erwinhenraat.platformer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by e.henraat on 24-3-2017.
 */
public class AnimatedObject extends TextureRegion {
    private ArrayList<Animation> animations;
    private ArrayList<String> names;
    protected TextureAtlas atlas;
    protected Animation<TextureRegion> currentAnimation;
    public AnimatedObject(){
        animations = new ArrayList<Animation>();
        names = new ArrayList<String>();
    }
    protected void addAnimation(TextureAtlas atlas , String animation){
        animations.add(new Animation<TextureRegion>(1f/60f, atlas.findRegions(animation), Animation.PlayMode.NORMAL));
        names.add(animation);
    }
    protected Animation<TextureRegion> getAnimation(int index){
        return animations.get(index);
    }
    protected Animation<TextureRegion> getAnimation(String name){
        return animations.get(names.indexOf(name));
    }
    public void gotoAnimation(int index){
        currentAnimation = getAnimation(index);
    }

}