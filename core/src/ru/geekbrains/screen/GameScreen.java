package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Font;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.AsteroidPool;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.pool.FirstAidPool;
import ru.geekbrains.sprite.Asteroid;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.sprite.FirstAid;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.NewGameButton;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.AsteroidEmitter;
import ru.geekbrains.utils.EnemyEmitter;
import ru.geekbrains.utils.FirstAidEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float PADDING = 0.01f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture bg;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas addAtlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private MainShip mainShip;
    private FirstAidPool firstAidPool;
    private AsteroidPool asteroidPool;
    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Sound bulletSound;
    private Sound laserSound;
    private Sound explosionSound;
    private Sound helpSound;
    private Sound damageSound;
    private Sound asteroidSound;
    private Music music;

    private EnemyEmitter enemyEmitter;
    private FirstAidEmitter firstAidEmitter;
    private AsteroidEmitter asteroidEmitter;

    private Font font;
    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        addAtlas = new TextureAtlas("textures/addAtlas.pack");
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);

        damageSound = Gdx.audio.newSound(Gdx.files.internal("sounds/damage1.mp3"));
        asteroidSound =Gdx.audio.newSound(Gdx.files.internal("sounds/asteroidSound.mp3"));

        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool, damageSound);
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound, damageSound);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);
        gameOver = new GameOver(atlas, worldBounds);
        newGameButton = new NewGameButton(atlas, worldBounds, this);

        helpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/help.mp3"));
        firstAidPool = new FirstAidPool();
        firstAidEmitter = new FirstAidEmitter(worldBounds, helpSound, addAtlas, firstAidPool);

        asteroidPool = new AsteroidPool(worldBounds, explosionPool);
        asteroidEmitter = new AsteroidEmitter(worldBounds, addAtlas, asteroidPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        frags = 0;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        addAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        firstAidPool.dispose();
        asteroidPool.dispose();
        explosionSound.dispose();
        laserSound.dispose();
        damageSound.dispose();
        asteroidSound.dispose();
        bulletSound.dispose();
        helpSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {

        if (mainShip.isDestroyed()) {
            newGameButton.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            newGameButton.touchUp(touch, pointer, button);

        } else {
            mainShip.touchUp(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            firstAidPool.updateActiveSprites(delta);
            asteroidPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
            firstAidEmitter.generate(delta);
            if (enemyEmitter.getLevel() >= 2) {
                asteroidEmitter.generate(delta, enemyEmitter.getLevel());
            }
        }
    }

    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveSprites();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDist) {
                mainShip.damage(enemyShip.getBulletDamage() * 2, true);
                enemyShip.destroy();
                enemyShip.boom();
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveSprites();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed() || bullet.getOwner() != mainShip) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage(), true);
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                    bullet.destroy();
                }
            }
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage(), true);
                bullet.destroy();
            }
        }

        List<FirstAid> firstAids = firstAidPool.getActiveSprites();
        for (FirstAid firstAid : firstAids) {
            if (firstAid.isDestroyed()) {
                continue;
            }
            float minDist = firstAid.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(firstAid.pos) < minDist) {
                mainShip.addHp(firstAid.getHelp());
                firstAid.destroy();
                firstAid.sound();
            }
        }

        List<Asteroid> asteroids = asteroidPool.getActiveSprites();
        for (Asteroid asteroid : asteroids) {
            float minDist = asteroid.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(asteroid.pos) < minDist) {
                mainShip.damage(asteroid.getDamage(), false);
                asteroidSound.play();
                asteroid.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        firstAidPool.freeAllDestroyedActiveSprites();
        asteroidPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            firstAidPool.drawActiveSprites(batch);
            asteroidPool.drawActiveSprites(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (mainShip.isDestroyed()) {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        printInfo();
        batch.end();
    }

    public void newGame() {
        explosionPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        bulletPool.freeAllActiveSprites();
        firstAidPool.freeAllActiveSprites();
        asteroidPool.freeAllActiveSprites();
        freeAllDestroyed();
        frags = 0;
        mainShip.reset();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }
}

