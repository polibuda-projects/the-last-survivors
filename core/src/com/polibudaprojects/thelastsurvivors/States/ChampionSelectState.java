package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.polibudaprojects.thelastsurvivors.Assets;
import com.polibudaprojects.thelastsurvivors.FontFactory;
import com.polibudaprojects.thelastsurvivors.Player.FireWarrior;
import com.polibudaprojects.thelastsurvivors.Player.MageWarrior;
import com.polibudaprojects.thelastsurvivors.Player.NightWarrior;
import com.polibudaprojects.thelastsurvivors.Player.Player;

public class ChampionSelectState extends State {

    private final Texture background;
    private final ImageButton mageButton;
    private final BitmapFont font = FontFactory.getFont(60);
    private final ImageButton fireButton;
    private final ImageButton nightButton;
    private final GlyphLayout textLayout;
    private Player selectedPlayer;

    public ChampionSelectState(StatesManager gsm) {
        super(gsm);
        background = Assets.get("background.png", Texture.class);
        mageButton = new ImageButton(getDrawable("players/MageWarriorIcon.png"));
        fireButton = new ImageButton(getDrawable("players/FireWarriorIcon.png"));
        nightButton = new ImageButton(getDrawable("players/NightWarriorIcon.png"));
        mageButton.setPosition(cam.position.x - mageButton.getWidth() / 2f - 300, cam.position.y - mageButton.getHeight() / 2f);
        fireButton.setPosition(cam.position.x - fireButton.getWidth() / 2f, cam.position.y - fireButton.getHeight() / 2f);
        nightButton.setPosition(cam.position.x - nightButton.getWidth() / 2f + 320, cam.position.y - nightButton.getHeight() / 2f);

        stage.addActor(mageButton);
        stage.addActor(fireButton);
        stage.addActor(nightButton);

        textLayout = new GlyphLayout(font, "SELECT HERO");
    }

    private static TextureRegionDrawable getDrawable(String filename) {
        return new TextureRegionDrawable(new TextureRegion(Assets.get(filename, Texture.class)));
    }

    @Override
    public void handleInput() {
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
    }

    @Override
    public void reset() {
        selectedPlayer = null;
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (selectedPlayer != null) {
            gsm.setState(gsm.getPlay(selectedPlayer));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        font.draw(sb, textLayout, cam.position.x - (textLayout.width / 2), cam.position.y + 200);
        sb.end();
    }
}
