package com.remotecontrol.bridge;

import com.remotecontrol.singleton.GerenciadorSistema;

public class Radio implements Dispositivo {

    private boolean ligado = false;
    private int volume = 20;
    private int estacao = 1;
    private final String nome;

    public Radio(String nome) {
        this.nome = nome;
    }

    @Override
    public void ligar() {
        ligado = true;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": rádio ligado.");
    }

    @Override
    public void desligar() {
        ligado = false;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": rádio desligado.");
    }

    @Override
    public boolean isLigado() {
        return ligado;
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
        return estacao;
    }

    @Override
    public void setCanal(int canal) {
        if (canal < 1) canal = 1;
        this.estacao = canal;
        GerenciadorSistema.getInstancia().registrarEvento(nome + ": estação mudada para " + canal + ".");
    }

    @Override
    public String getNome() {
        return nome;
    }
}
