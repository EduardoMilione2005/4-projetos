package com.remotecontrol.bridge;

public class ControleBasico extends ControleRemoto {

    public ControleBasico(Dispositivo dispositivo) {
        super(dispositivo);
    }

    @Override
    public String getTipoControle() {
        return "Controle Básico";
    }
}
