package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatesManager {

    private State start;
    private State play;
    private State end;
    private State state;

    public StatesManager() {
        state = new LoadingState(this);
    }

    public void set(State state) {
        state.reset();
        this.state = state;
    }

    public void update(float dt) {
        state.update(dt);
    }

    public void render(SpriteBatch sb) {
        state.render(sb);
    }

    public State getStart() {
        if (start == null) {
            start = new StartState(this);
        }
        return start;
    }

    public State getPlay() {
        if (play == null) {
            play = new PlayState(this);
        }
        return play;
    }

    public State getEnd() {
        if (end == null) {
            end = new EndState(this);
        }
        return end;
    }
}