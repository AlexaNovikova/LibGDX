package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class GameOver extends Sprite {

    private static final float HEIGHT = 0.07f;
    private static final float BOTTOM_MARGIN = 0.08f;

    public GameOver(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("message_game_over"), 1, 1, 1);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

}
