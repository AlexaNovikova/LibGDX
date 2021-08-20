package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final Rect worldBounds;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Sound damageSound;

    public EnemyPool(Rect worldBounds, BulletPool bulletPool, ExplosionPool explosionPool,
                     Sound damageSound) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.damageSound = damageSound;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldBounds, bulletPool, explosionPool, damageSound);
    }

}
