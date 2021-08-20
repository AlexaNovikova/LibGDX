package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.FirstAid;

public class FirstAidPool extends SpritesPool<FirstAid> {

    @Override
    protected FirstAid newSprite() {
        return new FirstAid();
    }
}
