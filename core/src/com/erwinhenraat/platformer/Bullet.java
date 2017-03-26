package com.erwinhenraat.platformer;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by e.henraat on 26-3-2017.
 */
public class Bullet {
    private float xpos = 0;
    private float ypos = 0;
    private float speed = 200;
    private Rectangle collisionBox;
    private Animation<TextureRegion> animation;
    private TextureRegion frame;
    private float time = 0;
    public Bullet(Animation<TextureRegion> anim){
        animation = anim;
        frame = animation.getKeyFrame(0f);
        collisionBox = new Rectangle(0f,0f,5f,5f);
    }
    public void setPosition(float x, float y){
        xpos = x;
        ypos = y;
    }
    public void render(SpriteBatch batch, float delta){
        time += delta;
        frame = animation.getKeyFrame(time);
        xpos += speed * delta; //move bullet
        collisionBox.setPosition(xpos,ypos);
        batch.draw(frame, xpos, ypos);


    }
    public float getXpos(){
        return xpos;

    }

}
