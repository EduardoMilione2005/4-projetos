package com.remotecontrol.factorymethod;

import com.remotecontrol.abstractfactory.FabricaRadio;
import com.remotecontrol.bridge.ControleRemoto;
import com.remotecontrol.bridge.Dispositivo;

public class CriadorControleRadio extends CriadorControle {

    private final FabricaRadio fabrica = new FabricaRadio();

    @Override
    public ControleRemoto criarControle(String nomeDispositivo) {
        Dispositivo radio = fabrica.criarDispositivo(nomeDispositivo);
        return fabrica.criarControle(radio);
    }

    @Override
    public String getTipo() {
        return "Criador de Controle de Rádio";
    }
}
