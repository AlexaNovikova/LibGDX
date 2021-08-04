package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Logo;


public class MenuScreen extends BaseScreen {


    private Texture bg;
    private Texture lg;
    private Background background;
    private Logo logo;

    private Vector2 pos;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        lg = new Texture("badlogic.jpg");
        background = new Background(bg);
        logo= new Logo(lg);
        pos = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch,pointer, button);
        return super.touchDown(touch, pointer, button);
    }
//    private final int SPEED = 20;
//    private Texture backgroung;
//    private Texture img;
//    private Vector2 position = new Vector2();
//    private Vector2 movement = new Vector2();
//    private Vector2 destination = new Vector2();
//    private Vector2 direction = new Vector2();
//
//
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        destination.set(screenX, Gdx.graphics.getHeight()-screenY);
//        return super.touchDown(screenX, screenY, pointer, button);
//    }
//
//    @Override
//    public void show() {
//        super.show();
//         backgroung = new Texture("zvezdy_galaktika.jpg");
//         img= new Texture("badlogic.jpg");
//         position = new Vector2();
//         movement = new Vector2();
//         destination = new Vector2();
//         direction = new Vector2();
//    }
//
//    @Override
//    public void render(float delta) {
//        super.render(delta);
//
//        //вектор направления
//        direction.set(destination).sub(position).nor();
//        movement.set(direction).scl(SPEED);
//       if (position.dst(destination) > movement.len()) {
//            position.add(movement);
//        } else {
//            position.set(destination);
//        }
//        batch.begin();
//        batch.draw(backgroung, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.draw(img, position.x, position.y);
//        batch.end();
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        backgroung.dispose();
//        img.dispose();
//    }

}
