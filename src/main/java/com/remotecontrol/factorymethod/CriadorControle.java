package com.remotecontrol.factorymethod;

import com.remotecontrol.bridge.ControleRemoto;


public abstract class CriadorControle {


    public abstract ControleRemoto criarControle(String nomeDispositivo);


    public ControleRemoto inicializarControle(String nomeDispositivo) {
        ControleRemoto controle = criarControle(nomeDispositivo);
        controle.ligar();
        return controle;
    }

    public abstract String getTipo();
}
