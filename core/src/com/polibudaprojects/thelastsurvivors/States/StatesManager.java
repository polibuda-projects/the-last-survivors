package com.polibudaprojects.thelastsurvivors.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatesManager {

    private final State start;
    private final State play;
    private final State end;
    private State state;

    public StatesManager() {
        start = new StartState(this);
        play = new PlayState(this);
        end = new EndState(this);
        state = start;
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
        return start;
    }

    public State getPlay() {
        return play;
    }

    public State getEnd() {
        return end;
    }
}