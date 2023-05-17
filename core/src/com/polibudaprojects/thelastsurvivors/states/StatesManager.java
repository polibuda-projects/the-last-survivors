package com.polibudaprojects.thelastsurvivors.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polibudaprojects.thelastsurvivors.player.Player;

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
        state.resize();
        this.state = state;
    }

    public void returnToPreviousState() {
        state = previousState;
        state.resize();
    }

    public void update(float dt) {
        state.update(dt);
    }

    public void render(SpriteBatch sb) {
        state.render(sb);
    }

    public void resize() {
        state.resize();
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