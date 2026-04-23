package com.remotecontrol.app;

import com.remotecontrol.bridge.*;
import com.remotecontrol.factorymethod.*;
import com.remotecontrol.singleton.GerenciadorSistema;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Sistema de Controle Remoto ===\n");

        CriadorControle criadorTv    = new CriadorControleTelevisao();
        CriadorControle criadorRadio = new CriadorControleRadio();

        ControleRemoto controleTv    = criadorTv.inicializarControle("TV Sala");
        ControleRemoto controleRadio = criadorRadio.inicializarControle("Rádio Quarto");

        System.out.println("Dispositivos criados:");
        System.out.println("  " + controleTv);
        System.out.println("  " + controleRadio);

        System.out.println("\n--- Operações via Controle Básico (Rádio) ---");
        controleRadio.aumentarVolume();
        controleRadio.aumentarVolume();
        controleRadio.proximoCanal();
        System.out.println("  " + controleRadio);

        System.out.println("\n--- Operações via Controle Avançado (TV) ---");
        ControleAvancado avancado = (ControleAvancado) controleTv;
        avancado.irParaCanal(13);
        avancado.alternarMudo();
        System.out.println("  Mudo ativo: " + avancado.isMudo());
        avancado.alternarMudo();
        System.out.println("  Mudo ativo: " + avancado.isMudo());

        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        sistema.setModoNoturno(true);
        sistema.setVolumeGlobal(30);

        System.out.println("\n--- Log de Eventos (Singleton) ---");
        sistema.getHistoricoEventos().forEach(e -> System.out.println("  " + e));

        System.out.println("\nSistema finalizado.");
    }
}
