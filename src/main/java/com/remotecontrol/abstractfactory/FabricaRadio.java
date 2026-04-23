package com.remotecontrol.abstractfactory;

import com.remotecontrol.bridge.*;

public class FabricaRadio implements FabricaControleRemoto {

    @Override
    public Dispositivo criarDispositivo(String nome) {
        return new Radio(nome);
    }

    @Override
    public ControleRemoto criarControle(Dispositivo dispositivo) {
        return new ControleBasico(dispositivo);
    }
}
