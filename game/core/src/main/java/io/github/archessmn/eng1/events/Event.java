package io.github.archessmn.eng1.events;

public interface Event {
    public int eventStart(int elapsedTime);

    public int eventMain(int elapsedTime);

    public int eventEnd();
}
