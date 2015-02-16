package com.TeamHEC.LocomotionCommotion.UI_Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * WarningMessage is a class you can use for whatever you need. Just call fireWarningWindow(warning title, warning body) and it will appear when you need it
 * the user can just click it to make it disappear.
 * 
 *
 */
public class WarningMessage {
	
	static LabelStyle titleStyle, bodyStyle;
	static Label titleLabel, bodyLabel;
	static SpriteButton window;
	
	static Stage stage;
	static Array<Actor> actors = new Array<Actor>();
	
	public void create(Stage stage)
	{	
		WarningMessage.stage = stage;
		
		window = new SpriteButton(300,400,Game_TextureManager.getInstance().game_warningwindow){
			@Override
			protected void onClicked(){
				killWarningWindow();
			}
		};
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gillsans.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;

		BitmapFont titlefont = generator.generateFont(parameter); 
		titlefont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		parameter.size = 32;
		BitmapFont bodyfont = generator.generateFont(parameter); 
		bodyfont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generator.dispose();
		
		titleStyle = new LabelStyle();
		bodyStyle = new LabelStyle();
		
		titleStyle.font = titlefont;
		bodyStyle.font = bodyfont;
		
		titleLabel = new Label(null, titleStyle);
		titleLabel.setColor(0,0,0,1);
		titleLabel.setAlignment(Align.center);
		titleLabel.setX(790);
		titleLabel.setY(630);
		titleLabel.setText("");
		
		bodyLabel = new Label(null, bodyStyle);
		bodyLabel.setColor(0,0,0,1);
		bodyLabel.setAlignment(Align.center);
		bodyLabel.setX(790);
		bodyLabel.setY(575);
		bodyLabel.setText("");
		
		window.setVisible(false);
		titleLabel.setVisible(false);
		bodyLabel.setVisible(false);
		
		actors.add(window);
		actors.add(bodyLabel);
		actors.add(titleLabel);
	}
	/**
	 * Makes the WarningWindow visible to the user 
	 * It adds the actors to the end of actors (so it is on top of everything).
	 * @param title - The title for your window e.g. WARNING
	 * @param body	- The body for your window e.g. You dont have enough gold for this.
	 */
	public static void fireWarningWindow(String title, String body)
	{
		if(window != null && !window.isVisible())
		{
			stage.getActors().addAll(actors);
			
			window.setVisible(true);
			titleLabel.setVisible(true);
			titleLabel.setText(title);
			bodyLabel.setVisible(true);
			bodyLabel.setText(body);
		}
	}
	/**
	 * Removes the WarningWindow by removing its actors from the stage.
	 */
	public static void killWarningWindow()
	{	
		window.setVisible(false);
		titleLabel.setVisible(false);
		titleLabel.setText("");
		bodyLabel.setVisible(false);
		titleLabel.setText("");
		
		for(Actor a: actors)
		{
			stage.getActors().removeValue(a, true);
		}
	}
}
