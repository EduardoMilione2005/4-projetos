package com.remotecontrol.factorymethod;

import com.remotecontrol.abstractfactory.FabricaTelevisao;
import com.remotecontrol.bridge.ControleRemoto;
import com.remotecontrol.bridge.Dispositivo;

public class CriadorControleTelevisao extends CriadorControle {

    private final FabricaTelevisao fabrica = new FabricaTelevisao();

    @Override
    public ControleRemoto criarControle(String nomeDispositivo) {
        Dispositivo tv = fabrica.criarDispositivo(nomeDispositivo);
        return fabrica.criarControle(tv);
    }

    @Override
    public String getTipo() {
        return "Criador de Controle de Televisão";
    }
}
