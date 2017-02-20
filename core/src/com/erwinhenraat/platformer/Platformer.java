package com.erwinhenraat.platformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Platformer extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Label label;
	Stage stage;
	float posx;
	OrthographicCamera cam;
	Image img2;
	TextureAtlas atlas;
	Animation<TextureRegion> animation;

	float frameTime;
	@Override
	public void create () {

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		frameTime = 0f;
		atlas = new TextureAtlas(Gdx.files.internal("animations/animations.atlas"));
		System.out.println(atlas);

		animation = new Animation<TextureRegion>(0.033f, atlas.findRegions("flash_walks"),Animation.PlayMode.LOOP);


		//TextureRegion frame = animation.getKeyFrame(5f);
		//System.out.println(frame);

		posx = 0f;


        label = new Label("HELLO GDX", new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json")));
        label.setPosition(400f,400f);
        label.setSize(100f,50f);
        label.setFontScale(label.getFontScaleX()*2,label.getFontScaleY()*2);

        img2 = new Image(img);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // input is now sent to the stage so added actor can be reached by input
        stage.addActor(label);
        stage.addActor(img2);

		cam = (OrthographicCamera)stage.getViewport().getCamera();
		cam.update();


	}
	@Override
	public void render () {
        float d = Gdx.graphics.getDeltaTime();
        posx += d * 50;

        //get animation frame and update time
	  	TextureRegion frame = animation.getKeyFrame(frameTime,true);
		frameTime += d;

		//teleport image when out of screen
	    if(posx > Gdx.graphics.getWidth()){
	        posx = 0-img.getWidth();
        }
        //rotate camera
		cam.rotate(3f);
        cam.update();

        //clear screen
	    Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//draw non stage images
		batch.begin();
		batch.draw(img, posx, 0);
		batch.draw(frame,0f,0f);
		batch.end();

		//draw stage images
        stage.act(d);
        stage.draw();

    }
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		stage.dispose();
		atlas.dispose();
	}
}
