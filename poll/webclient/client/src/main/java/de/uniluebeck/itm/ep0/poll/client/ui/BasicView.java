package de.uniluebeck.itm.ep0.poll.client.ui;

public interface BasicView<T> {

    T getPresenter();

    void setPresenter(T presenter);
}
