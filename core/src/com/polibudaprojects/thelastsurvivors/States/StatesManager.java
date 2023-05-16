package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.Player.Player;

public class StatesManager {

    private State start;
    private State select;
    private State play;
    private State pause;
    private State end;
    private State state;
    private State previousState;

    public StatesManager() {
        state = new LoadingState(this);
    }

    public void setState(State state) {
        previousState = this.state;
        state.reset();
        this.state = state;
        Gdx.input.setInputProcessor(state.getStage());
    }

    public void returnToPreviousState() {
        state = previousState;
        Gdx.input.setInputProcessor(state.getStage());
    }

    public void update(float dt) {
        state.update(dt);
        state.getStage().act(dt);
    }

    public void render(SpriteBatch sb) {
        state.render(sb);
        state.getStage().draw();
    }

    public State getStart() {
        if (start == null) {
            start = new StartState(this);
        }
        return start;
    }

    public State getPlay(Player player) {
        if (play == null || ((PlayState) play).getPlayer() != player) {
            play = new PlayState(this, player);
        }
        return play;
    }

    public State getPause() {
        if (pause == null) {
            pause = new PauseState(this);
        }
        return pause;
    }

    public State getEnd() {
        if (end == null) {
            end = new EndState(this);
        }
        return end;
    }

    public State getSelect() {
        if (select == null) {
            select = new ChampionSelectState(this);
        }
        return select;
    }
}