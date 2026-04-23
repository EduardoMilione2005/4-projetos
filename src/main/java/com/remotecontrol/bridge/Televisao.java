package com.remotecontrol.bridge;

import com.remotecontrol.singleton.GerenciadorSistema;

public class Televisao implements Dispositivo {

    private boolean ligada = false;
    private int volume = 30;
    private int canal = 1;
    private final String nome;

    public Televisao(String nome) {
        this.nome = nome;
    }

    @Override
    public void ligar() {
        ligada = true;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": televisão ligada.");
    }

    @Override
    public void desligar() {
        ligada = false;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": televisão desligada.");
    }

    @Override
    public boolean isLigado() {
        return ligada;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        if (volume < 0) volume = 0;
        if (volume > 100) volume = 100;
        this.volume = volume;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": volume ajustado para " + volume + ".");
    }

    @Override
    public int getCanal() {
        return canal;
    }

    @Override
    public void setCanal(int canal) {
        if (canal < 1) canal = 1;
        this.canal = canal;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": canal mudado para " + canal + ".");
    }

    @Override
    public String getNome() {
        return nome;
    }
}
