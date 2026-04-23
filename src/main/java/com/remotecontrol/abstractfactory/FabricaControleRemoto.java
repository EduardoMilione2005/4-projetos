package com.remotecontrol.abstractfactory;

import com.remotecontrol.bridge.ControleRemoto;
import com.remotecontrol.bridge.Dispositivo;

public interface FabricaControleRemoto {

    Dispositivo criarDispositivo(String nome);

    ControleRemoto criarControle(Dispositivo dispositivo);
}
