package com.erwinhenraat.platformer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


public class Platformer extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private Animation<TextureRegion>[] animations;
	private TextureRegion currentFrame;

	private float posx;
	private float posy;
	private float yFloor = 0f;

	private float frameTime;

	private float iv = 280f; //Initial Velocity
	private float v = 0f; //Velocity
	private float a  = -300f; //Accelleration
	private float t = 0f; //Time

	private boolean jumping = false;
	private boolean wPressed = false;

	private boolean spacePressed = false;
	private boolean	shooting = false;
	private float cooldown = 0;
	private ArrayList<Bullet> bullets;

	@Override
	public void create () {
		batch = new SpriteBatch();
		frameTime = 0f;

		posx = Gdx.graphics.getWidth()*0.5f;
		posy = 0f;

		atlas = new TextureAtlas(Gdx.files.internal("animations/animations.atlas"));

		animations = new Animation[4];
		animations[0] = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_idles"), Animation.PlayMode.LOOP);
		animations[1] = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_jumps"), Animation.PlayMode.LOOP);
		animations[2] = new Animation<TextureRegion>(0.033f,atlas.findRegions("bullet"), Animation.PlayMode.LOOP);
		animations[3] = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_attacks"), Animation.PlayMode.LOOP);
		//0 idle, 1 jump, 2 bullet

		currentFrame = animations[0].getKeyFrame(0);

		bullets = new ArrayList<Bullet>();

	}
	private void jump(float delta){
		//check if W is pressed
		wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
		//jump if not jumping yet
		if(wPressed && !jumping)jumping = true;

		if(jumping) {
			t += delta; 												//Increase Time
			v = iv + a * t;											//velocity = initial velocity + acceleration * time
			posy += v * delta;											//update y position
			currentFrame = animations[1].getKeyFrame(frameTime); 	//get frame from animation
		}
		if(posy <= yFloor){											//touch the floor
			jumping = false;										//change state to not jumping
			t = 0;													//reset jumptime
			currentFrame = animations[0].getKeyFrame(frameTime);	//get frame from animation
		}

	}
	private void shoot(float delta){
		//check if space is pressed
		spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);

		if(spacePressed && cooldown <= 0){
			cooldown = 0.2f;
			Bullet b = new Bullet(animations[2]);
			bullets.add(b);
			b.setPosition(posx, posy + currentFrame.getRegionHeight()*0.25f);
			frameTime = 0;

		}
		if(cooldown>0)
		{
			cooldown-=delta;
			currentFrame = animations[3].getKeyFrame(frameTime);
		}



	}
	@Override
	public void render () {

		//get time between frames
		float d = Gdx.graphics.getDeltaTime();
		frameTime += d;

		jump(d);
		shoot(d);


		for (Bullet b : bullets) { //for each loop door alle bullets
			if(b.getXpos() > Gdx.graphics.getWidth()){
				bullets.remove(b);
 				break;
			}
		}

		//clear screen
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw images
		batch.begin();
		batch.draw(currentFrame ,posx - currentFrame.getRegionWidth()*0.5f, posy);
		for (Bullet b : bullets) { //for each loop door alle bullets
			b.render(batch, d);
		}
		batch.end();
    }
	@Override
	public void dispose () {
		batch.dispose();
		atlas.dispose();


	}
}
