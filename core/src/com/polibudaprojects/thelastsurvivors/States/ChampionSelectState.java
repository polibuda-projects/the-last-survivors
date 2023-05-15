package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.Player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.Player.MageWarrior;
import com.polibudaprojects.thelastsurvivors.Player.NightWarrior;

public class ChampionSelectState extends State {

    private final Texture background;
    private final Stage stage;
    private final ImageButton mageButton;
    private final BitmapFont font = new BitmapFont();
    private final ImageButton fireButton;
    private final ImageButton nightButton;
    private final GlyphLayout textLayout;

    public ChampionSelectState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        mageButton = new ImageButton(getDrawable("players/MageWarriorIcon.png"));
        fireButton = new ImageButton(getDrawable("players/FireWarriorIcon.png"));
        nightButton = new ImageButton(getDrawable("players/NightWarriorIcon.png"));
        mageButton.setPosition(cam.position.x - mageButton.getWidth() / 2f - 300, cam.position.y - mageButton.getHeight() / 2f);
        fireButton.setPosition(cam.position.x - fireButton.getWidth() / 2f, cam.position.y - fireButton.getHeight() / 2f);
        nightButton.setPosition(cam.position.x - nightButton.getWidth() / 2f + 320, cam.position.y - nightButton.getHeight() / 2f);

        stage = new Stage(new ScreenViewport(cam));
        stage.addActor(mageButton);
        stage.addActor(fireButton);
        stage.addActor(nightButton);
        Gdx.input.setInputProcessor(stage);

        font.setColor(Color.WHITE);
        font.getData().setScale(3f);
        textLayout = new GlyphLayout(font, "SELECT HERO");
    }

    private static TextureRegionDrawable getDrawable(String filename) {
        return new TextureRegionDrawable(new TextureRegion(Assets.get(filename, Texture.class)));
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
        font.draw(sb, textLayout, cam.position.x - (textLayout.width / 2), cam.position.y + 200);
        sb.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
