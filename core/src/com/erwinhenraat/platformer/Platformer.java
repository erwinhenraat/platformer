package com.erwinhenraat.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;


public class Platformer extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture platform;
	private Texture spikes;
	private Label label;
	private float posx;
	private float posy;

	private float yFloor = 0f;


	private TextureAtlas atlas;

	private Sound[] sounds = new Sound[3];
	private Music music;

	private Animation<TextureRegion> animation;
	private Animation<TextureRegion> animation2;
	private Animation<TextureRegion> animation3;
	private Rectangle[] collisionBoxes;
	private float frameTime;

	private boolean diePlays = false;
	private boolean right = false;
	private boolean left = false;
	private boolean space = false;
	private float speed = 0f;
	private int dir = 0;

	private float iv = 280f;
	private float v = 0f;
	private float a  = -300f;
	private float t = 0f;
	private boolean jumping = false;
	//private boolean onFloor = true;

	@Override
	public void create () {
		batch = new SpriteBatch();
		platform = new Texture("platform.png");
		spikes = new Texture("spikes.png");
		frameTime = 0f;
		atlas = new TextureAtlas(Gdx.files.internal("animations/animations.atlas"));


		animation = new Animation<TextureRegion>(0.033f, atlas.findRegions("flash_walks"),Animation.PlayMode.LOOP);
		animation2 = new Animation<TextureRegion>(0.033f,atlas.findRegions("flash_idles"), Animation.PlayMode.LOOP);


		collisionBoxes = new Rectangle[4];
		posx = 0f;
		posy = 0f;

		collisionBoxes[0] = new Rectangle(posx,posy,7,10); //character feet
		collisionBoxes[1] = new Rectangle(posx,posy-5,7,5); //below character
		collisionBoxes[2] = new Rectangle(350,0,79,55); //spikes
		collisionBoxes[3] = new Rectangle(400,100,444,71); //platform


		sounds[0] = Gdx.audio.newSound(Gdx.files.internal("audio/cannon.wav"));
		sounds[1] = Gdx.audio.newSound(Gdx.files.internal("audio/cannon.mp3"));
		sounds[2] = Gdx.audio.newSound(Gdx.files.internal("audio/dies.mp3"));

		music = Gdx.audio.newMusic(Gdx.files.internal("audio/bgMusic.wav"));

		sounds[0].play(1.0f);
		sounds[1].play(1.0f);

		music.setLooping(true);
		music.play();


	}
		@Override
	public void render () {

		float d = Gdx.graphics.getDeltaTime();
		frameTime += d;

		TextureRegion frame;

		right = Gdx.input.isKeyPressed(Input.Keys.D);
		left = Gdx.input.isKeyPressed(Input.Keys.A);
		space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

		if(right && !left)speed = 50f;
		if(left && !right)speed = -50f;
		if(!left&&!right)speed = 0f;

		if(space && !jumping)jumping = true;

		if(jumping) {
			t += d;
			v = iv + a * t;//velocity = initial velocity + acceleration * time
			posy += v * d;
		}
		if(posy <= yFloor){
			jumping = false;
			t = 0;
			//onFloor = true;

		}
		else if (collisionBoxes[0].overlaps(collisionBoxes[3]) ){
			posy = 100f + platform.getHeight();
			jumping = false;
			t = 0;

		}
		else if (!collisionBoxes[1].overlaps(collisionBoxes[3]) && !jumping){


			t = 2;
			jumping = true;
			System.out.println("drop");
		}

		//collide with trap
		if(collisionBoxes[0].overlaps(collisionBoxes[1])){
			frame = animation2.getKeyFrame(frameTime,true);
			if(!diePlays) {
				sounds[2].play();
				diePlays = true;
			}
		}else{
			frame = animation.getKeyFrame(frameTime,true);
		}

		//scale frame
		dir = (int) (speed/Math.abs(speed));
		if(dir == 1 && frame.isFlipX())frame.flip(true, false);
		if(dir == -1 && !frame.isFlipX())frame.flip(true,false);


		//determine player position
		posx += d * speed;
		collisionBoxes[0].setPosition(posx, posy);
		collisionBoxes[1].setPosition(posx, posy-5);

		//teleport image when out of screen
	    if(posx > Gdx.graphics.getWidth()){
	        posx = 0-frame.getRegionWidth();
        }





		//clear screen
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw non stage images
		batch.begin();
		batch.draw(platform, 400f, 100f);
		batch.draw(spikes, 350f, 0f);
		batch.draw(frame,posx - frame.getRegionWidth()*0.5f,posy);
		batch.end();



    }
	
	@Override
	public void dispose () {
		batch.dispose();

		atlas.dispose();
		for (Sound s : sounds) {
			s.dispose();
		}
		music.dispose();
	}
}
