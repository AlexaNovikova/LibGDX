package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {
	Texture img;
	
	@Override
	public void create () {
		setScreen(new MenuScreen());
	}

}
