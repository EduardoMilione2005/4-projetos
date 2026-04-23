package com.remotecontrol.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GerenciadorSistema {

    private static volatile GerenciadorSistema instancia;

    private final List<String> historicoEventos;
    private boolean modoNoturno;
    private int volumeGlobal;

    private GerenciadorSistema() {
        this.historicoEventos = new ArrayList<>();
        this.modoNoturno = false;
        this.volumeGlobal = 50;
        registrarEvento("Sistema iniciado.");
    }


    public static GerenciadorSistema getInstancia() {
        if (instancia == null) {
            synchronized (GerenciadorSistema.class) {
                if (instancia == null) {
                    instancia = new GerenciadorSistema();
                }
            }
        }
        return instancia;
    }

    public void registrarEvento(String evento) {
        historicoEventos.add("[SISTEMA] " + evento);
    }

    public List<String> getHistoricoEventos() {
        return Collections.unmodifiableList(historicoEventos);
    }

    public boolean isModoNoturno() {
        return modoNoturno;
    }

    public void setModoNoturno(boolean modoNoturno) {
        this.modoNoturno = modoNoturno;
        registrarEvento("Modo noturno " + (modoNoturno ? "ativado" : "desativado") + ".");
    }

    public int getVolumeGlobal() {
        return volumeGlobal;
    }

    public void setVolumeGlobal(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume deve estar entre 0 e 100.");
        }
        this.volumeGlobal = volume;
        registrarEvento("Volume global ajustado para " + volume + ".");
    }

    static synchronized void resetarInstancia() {
        instancia = null;
    }
}
