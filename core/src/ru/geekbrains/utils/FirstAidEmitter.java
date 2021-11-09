package ru.geekbrains.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.FirstAidPool;
import ru.geekbrains.sprite.FirstAid;

public class FirstAidEmitter {

    private static final float GENERATE_INTERVAL = 10f;

    private static final float FIRSTAID_SMALL_HEIGHT = 0.07f;
    private static final int FIRSTAID_SMALL_HP = 1;

    private static final float FIRSTAID_MEDIUM_HEIGHT = 0.1f;
    private static final int FIRSTAID_MEDIUM_HP = 5;

    private static final float FIRSTAID_BIG_HEIGHT = 0.13f;
    private static final int FIRSTAID_BIG_HP = 10;

    private final Rect worldBounds;
    private final Sound helpSound;
    private final TextureRegion firstAidRegion;

    private final Vector2 firstAidSmallV = new Vector2(0, -0.2f);

    private final Vector2 firstAidMediumV = new Vector2(0, -0.25f);

    private final Vector2 firstAidBigV = new Vector2(0, -0.3f);

    private Vector2 v;
    private int level;

    private float generateTimer;
    private FirstAidPool firstAidPool;

    public FirstAidEmitter(Rect worldBounds, Sound helpSound, TextureAtlas atlas,
                           FirstAidPool firstAidPool) {
        this.worldBounds = worldBounds;
        this.helpSound = helpSound;
        firstAidRegion = atlas.findRegion("first_aid_kit");
        this.firstAidPool = firstAidPool;
        v = new Vector2();
    }

    public void generate(float delta) {
        generateTimer += delta;

        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            FirstAid firstAid = firstAidPool.obtain();
            float type = (float) Math.random();
            float posX = Rnd.nextFloat(
                    worldBounds.getLeft() + firstAid.getHalfWidth(),
                    worldBounds.getRight() - firstAid.getHalfWidth()
            );
            v.set(posX, worldBounds.getTop());
            if (type < 0.5f) {
                firstAid.set(
                        firstAidRegion,
                        FIRSTAID_SMALL_HEIGHT,
                        FIRSTAID_SMALL_HP,
                        v,
                        firstAidSmallV,
                        worldBounds,
                        helpSound
                );
            } else if (type < 0.8f) {
                firstAid.set(
                        firstAidRegion,
                        FIRSTAID_MEDIUM_HEIGHT,
                        FIRSTAID_MEDIUM_HP,
                        v,
                        firstAidMediumV,
                        worldBounds,
                        helpSound
                );
            } else {
                firstAid.set(
                        firstAidRegion,
                        FIRSTAID_BIG_HEIGHT,
                        FIRSTAID_BIG_HP,
                        v,
                        firstAidBigV,
                        worldBounds,
                        helpSound
                );
            }

        }
    }
}
