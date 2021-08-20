package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class FirstAid extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private int help;
    private Sound sound;


    public FirstAid() {
        regions = new TextureRegion[1];
        v = new Vector2();
    }

    public void set (TextureRegion region, float height, int help,  Vector2 pos0,  Vector2 v0, Rect worldBounds,
                     Sound sound)
    {
        this.worldBounds = worldBounds;
        this.regions[0] = region;
        this.help = help;
        setHeightProportion(height);
        this.v.set(v0);
        this.pos.set(pos0);
        this.sound= sound;
    }

    @Override
    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }


    public int getHelp() {
        return help;
    }

    public void sound() {
        sound.play();
    }
}
