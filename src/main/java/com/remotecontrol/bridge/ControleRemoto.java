package com.remotecontrol.bridge;


public abstract class ControleRemoto {

    protected Dispositivo dispositivo;

    protected ControleRemoto(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void ligar() {
        dispositivo.ligar();
    }

    public void desligar() {
        dispositivo.desligar();
    }

    public boolean isLigado() {
        return dispositivo.isLigado();
    }

    public void aumentarVolume() {
        dispositivo.setVolume(dispositivo.getVolume() + 10);
    }

    public void diminuirVolume() {
        dispositivo.setVolume(dispositivo.getVolume() - 10);
    }

    public void proximoCanal() {
        dispositivo.setCanal(dispositivo.getCanal() + 1);
    }

    public void canalAnterior() {
        int atual = dispositivo.getCanal();
        if (atual > 1) {
            dispositivo.setCanal(atual - 1);
        }
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public abstract String getTipoControle();

    @Override
    public String toString() {
        return getTipoControle() + " → " + dispositivo.getNome()
                + " | Ligado: " + dispositivo.isLigado()
                + " | Volume: " + dispositivo.getVolume()
                + " | Canal/Estação: " + dispositivo.getCanal();
    }
}
