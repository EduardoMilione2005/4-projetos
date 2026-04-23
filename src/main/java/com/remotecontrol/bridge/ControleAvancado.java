package com.remotecontrol.bridge;

import com.remotecontrol.singleton.GerenciadorSistema;

public class ControleAvancado extends ControleRemoto {

    private boolean mudo = false;
    private int volumeAntesMudo = 0;

    public ControleAvancado(Dispositivo dispositivo) {
        super(dispositivo);
    }

    public void alternarMudo() {
        if (mudo) {
            dispositivo.setVolume(volumeAntesMudo);
            mudo = false;
            GerenciadorSistema.getInstancia().registrarEvento(dispositivo.getNome() + ": mudo desativado.");
        } else {
            volumeAntesMudo = dispositivo.getVolume();
            dispositivo.setVolume(0);
            mudo = true;
            GerenciadorSistema.getInstancia().registrarEvento(dispositivo.getNome() + ": mudo ativado.");
        }
    }

    public void irParaCanal(int canal) {
        dispositivo.setCanal(canal);
        GerenciadorSistema.getInstancia()
                .registrarEvento(dispositivo.getNome() + ": ir direto para canal " + canal + ".");
    }

    public boolean isMudo() {
        return mudo;
    }

    @Override
    public String getTipoControle() {
        return "Controle Avançado";
    }
}
