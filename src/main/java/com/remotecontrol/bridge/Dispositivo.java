package com.remotecontrol.bridge;

public interface Dispositivo {

    void ligar();
    void desligar();
    boolean isLigado();

    int getVolume();
    void setVolume(int volume);

    int getCanal();
    void setCanal(int canal);

    String getNome();
}
