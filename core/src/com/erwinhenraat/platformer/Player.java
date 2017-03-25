package com.erwinhenraat.platformer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by e.henraat on 24-3-2017.
 */
public class Player extends AnimatedObject {

    public Player(){
        atlas = new TextureAtlas(Gdx.files.internal("animations/animations.atlas"));
        addAnimation(atlas,Animations.IDLES.animationName());
        addAnimation(atlas,Animations.WALKS.animationName());
        addAnimation(atlas,Animations.JUMPS.animationName());
        addAnimation(atlas,Animations.DIES.animationName());
        addAnimation(atlas,Animations.ATTACKS.animationName());

        Animation<TextureRegion> current = getAnimation(Animations.IDLES.animationName());
    }


}
enum Animations{
    IDLES(0, "flash_idles"),
    WALKS(1, "flash_walks"),
    JUMPS(2, "flash_jumps"),
    DIES(3, "flash_dies"),
    ATTACKS(4, "flash_attacks");
    private final int index;
    private final String name;
    Animations(int index, String name){
        this.index = index;
        this.name = name;
    }
    public int animationIndex(){
        return this.index;
    }
    public String animationName(){
        return this.name;
    }

}
