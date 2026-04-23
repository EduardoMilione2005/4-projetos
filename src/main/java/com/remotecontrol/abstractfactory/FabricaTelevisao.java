package com.remotecontrol.abstractfactory;

import com.remotecontrol.bridge.*;

public class FabricaTelevisao implements FabricaControleRemoto {

    @Override
    public Dispositivo criarDispositivo(String nome) {
        return new Televisao(nome);
    }

    @Override
    public ControleRemoto criarControle(Dispositivo dispositivo) {
        return new ControleAvancado(dispositivo);
    }
}
