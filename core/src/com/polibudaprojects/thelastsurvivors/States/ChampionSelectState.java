package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.Player.MageWarrior;
import com.polibudaprojects.thelastsurvivors.Player.NightWarrior;

public class ChampionSelectState extends State {
    private final Texture background;

    private final Stage stage;

    Drawable drawable;
    ImageButton mageButton;
    ImageButton fireButton;
    ImageButton nightButton;


    public ChampionSelectState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        Texture mage = Assets.get("players/MageWarriorIcon.png", Texture.class);
        Texture fireWarrior = Assets.get("players/FireWarriorIcon.png", Texture.class);
        Texture nightWarrior = Assets.get("players/NightWarriorIcon.png", Texture.class);
        drawable = new TextureRegionDrawable(new TextureRegion(mage));
        mageButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(new TextureRegion(fireWarrior));
        fireButton = new ImageButton(drawable);
        drawable = new TextureRegionDrawable(new TextureRegion(nightWarrior));
        nightButton = new ImageButton(drawable);
        stage = new Stage(new ScreenViewport(cam));
    }

    @Override
    public void handleInput() {
        nightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gsm.player = new NightWarrior();
                gsm.setState(gsm.getPlay());
            }
        });
        fireButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gsm.player = new FireWarrior();
                gsm.setState(gsm.getPlay());
            }
        });
        mageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gsm.player = new MageWarrior();
                gsm.setState(gsm.getPlay());
            }
        });
        mageButton.setPosition(cam.position.x - mageButton.getWidth() / 2f - 300, cam.position.y - mageButton.getHeight() / 2f);
        fireButton.setPosition(cam.position.x - fireButton.getWidth() / 2f, cam.position.y - fireButton.getHeight() / 2f);
        nightButton.setPosition(cam.position.x - nightButton.getWidth() / 2f + 320, cam.position.y - nightButton.getHeight() / 2f);
        stage.addActor(mageButton);
        stage.addActor(fireButton);
        stage.addActor(nightButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void reset() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
