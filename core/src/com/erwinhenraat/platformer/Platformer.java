package com.erwinhenraat.platformer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


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
	private boolean spacePressed = false;


	@Override
	public void create () {
		batch = new SpriteBatch();
		frameTime = 0f;

		posx = Gdx.graphics.getWidth()*0.5f;
		posy = 0f;

		atlas = new TextureAtlas(Gdx.files.internal("animations/animations.atlas"));

		animations = new Animation[2];
		animations[0] = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_idles"), Animation.PlayMode.LOOP);
		animations[1] = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_jumps"), Animation.PlayMode.LOOP);
		//0 idle, 1 jump

		currentFrame = animations[0].getKeyFrame(0);

	}

	@Override
	public void render () {

		//get time between frames
		float d = Gdx.graphics.getDeltaTime();
		frameTime += d;

		//check is space is pressed
		spacePressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		//jump if not jumping yet
		if(spacePressed && !jumping)jumping = true;

		if(jumping) {
			t += d; 												//Increase Time
			v = iv + a * t;											//velocity = initial velocity + acceleration * time
			posy += v * d;											//update y position
			currentFrame = animations[1].getKeyFrame(frameTime); 	//get frame from animation
		}
		if(posy <= yFloor){											//touch the floor
			jumping = false;										//change state to not jumping
			t = 0;													//reset jumptime
			currentFrame = animations[0].getKeyFrame(frameTime);	//get frame from animation
		}

		//clear screen
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw images
		batch.begin();
		batch.draw(currentFrame ,posx - currentFrame.getRegionWidth()*0.5f, posy);
		batch.end();
    }
	@Override
	public void dispose () {
		batch.dispose();
		atlas.dispose();


	}
}
