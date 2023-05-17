package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.Gdx;
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
import com.polibudaprojects.thelastsurvivors.assets.Assets;
import com.polibudaprojects.thelastsurvivors.assets.FontFactory;
import com.polibudaprojects.thelastsurvivors.player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.player.MageWarrior;
import com.polibudaprojects.thelastsurvivors.player.NightWarrior;
import com.polibudaprojects.thelastsurvivors.player.Player;

import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_HEIGHT;
import static com.polibudaprojects.thelastsurvivors.GameMain.SCREEN_WIDTH;

public class ChampionSelectState extends State {

    private final Texture background;
    private final BitmapFont font = FontFactory.getFont(60);
    private final GlyphLayout textLayout;
    private final Stage stage = new Stage(viewport);
    private Player selectedPlayer;

    public ChampionSelectState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        textLayout = new GlyphLayout(font, "SELECT HERO");
        createImageButtons();
        Gdx.input.setInputProcessor(stage);
    }

    private static TextureRegionDrawable getDrawable(String filename) {
        return new TextureRegionDrawable(new TextureRegion(Assets.get(filename, Texture.class)));
    }

    private void createImageButtons() {
        ImageButton mageButton = new ImageButton(getDrawable("players/MageWarriorIcon.png"));
        ImageButton fireButton = new ImageButton(getDrawable("players/FireWarriorIcon.png"));
        ImageButton nightButton = new ImageButton(getDrawable("players/NightWarriorIcon.png"));
        mageButton.setPosition(cam.position.x - mageButton.getWidth() / 2f - 300, cam.position.y - mageButton.getHeight() / 2f);
        fireButton.setPosition(cam.position.x - fireButton.getWidth() / 2f, cam.position.y - fireButton.getHeight() / 2f);
        nightButton.setPosition(cam.position.x - nightButton.getWidth() / 2f + 320, cam.position.y - nightButton.getHeight() / 2f);

        nightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedPlayer = new NightWarrior();
            }
        });
        fireButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedPlayer = new FireWarrior();
            }
        });
        mageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedPlayer = new MageWarrior();
            }
        });

        stage.addActor(mageButton);
        stage.addActor(fireButton);
        stage.addActor(nightButton);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void reset() {
        selectedPlayer = null;
    }

    @Override
    public void update(float dt) {
        handleInput();
        stage.act(dt);
        if (selectedPlayer != null) {
            gsm.setState(gsm.getPlay(selectedPlayer));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        font.draw(sb, textLayout, cam.position.x - (textLayout.width / 2), cam.position.y + 200);
        sb.end();
        stage.draw();
    }
}
