package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.AsteroidPool;
import ru.geekbrains.sprite.Asteroid;


public class AsteroidEmitter {

    private static final float GENERATE_INTERVAL = 15f;

    private static final float ASTEROID_SMALL_HEIGHT = 0.07f;
    private static final int ASTEROID_SMALL_DAMAGE = 5;

    private static final float ASTEROID_MEDIUM_HEIGHT = 0.1f;
    private static final int ASTEROID_MEDIUM_DAMAGE = 10;

    private static final float ASTEROID_BIG_HEIGHT = 0.13f;
    private static final int ASTEROID_BIG_DAMAGE = 15;

    private final Rect worldBounds;
    private final TextureRegion asteroidRegion;

    private final Vector2 asteroidSmallV = new Vector2(0, -0.2f);

    private final Vector2 asteroidMediumV = new Vector2(0, -0.3f);

    private final Vector2 asteroidBigV = new Vector2(0, -0.4f);

    private Vector2 v;
    private int level;

    private float generateTimer;
    private AsteroidPool asteroidPool;

    public AsteroidEmitter(Rect worldBounds, TextureAtlas atlas,
                           AsteroidPool asteroidPool) {
        this.worldBounds = worldBounds;
        asteroidRegion = atlas.findRegion("asteroid");
        this.asteroidPool = asteroidPool;
        v = new Vector2();
    }

    public void generate(float delta, int level) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            Asteroid asteroid = asteroidPool.obtain();
            float type = (float) Math.random();
            float posX = Rnd.nextFloat(
                    worldBounds.getLeft() + asteroid.getHalfWidth(),
                    worldBounds.getRight() - asteroid.getHalfWidth()
            );
            v.set(posX, worldBounds.getTop());
            if (type < 0.3f) {
                asteroid.set(
                        asteroidRegion,
                        ASTEROID_SMALL_HEIGHT,
                        ASTEROID_SMALL_DAMAGE*level,
                        v,
                        asteroidSmallV
                );
            } else if (type < 0.7f) {
                asteroid.set(
                        asteroidRegion,
                        ASTEROID_MEDIUM_HEIGHT,
                        ASTEROID_MEDIUM_DAMAGE*level,
                        v,
                        asteroidMediumV
                );
            } else {
                asteroid.set(
                        asteroidRegion,
                        ASTEROID_BIG_HEIGHT,
                        ASTEROID_BIG_DAMAGE*level,
                        v,
                        asteroidBigV
                );
            }

        }
    }
}
