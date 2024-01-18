package game.Objects;

public interface Movable {
    void doLogic(long delta);
    void move(long delta);
}
