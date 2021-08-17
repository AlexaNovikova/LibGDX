package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;


public class NewGameButton extends BaseButton {

    private static final float BOTTOM_MARGIN = 0.5f;

    public NewGameButton(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("button_new_game"));
        resize(worldBounds);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.04f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void action() {
        GameScreen.newGame();
    }

}
