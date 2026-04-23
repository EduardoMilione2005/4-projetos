package com.remotecontrol;

import com.remotecontrol.abstractfactory.*;
import com.remotecontrol.bridge.*;
import com.remotecontrol.factorymethod.*;
import com.remotecontrol.singleton.GerenciadorSistema;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AbstractFactory + FactoryMethod — Fábricas de Controles")
class FabricasTest {

    @BeforeEach
    void resetarSingleton() throws Exception {
        Method m = GerenciadorSistema.class.getDeclaredMethod("resetarInstancia");
        m.setAccessible(true);
        m.invoke(null);
    }


    @Test
    @DisplayName("FabricaTelevisao: deve criar Televisao")
    void fabricaTvCriaDispositivo() {
        FabricaControleRemoto fabrica = new FabricaTelevisao();
        Dispositivo dispositivo = fabrica.criarDispositivo("TV Fab");
        assertInstanceOf(Televisao.class, dispositivo);
        assertEquals("TV Fab", dispositivo.getNome());
    }

    @Test
    @DisplayName("FabricaTelevisao: deve criar ControleAvancado")
    void fabricaTvCriaControleAvancado() {
        FabricaControleRemoto fabrica = new FabricaTelevisao();
        Dispositivo tv = fabrica.criarDispositivo("TV");
        ControleRemoto controle = fabrica.criarControle(tv);
        assertInstanceOf(ControleAvancado.class, controle);
    }

    @Test
    @DisplayName("FabricaRadio: deve criar Radio")
    void fabricaRadioCriaDispositivo() {
        FabricaControleRemoto fabrica = new FabricaRadio();
        Dispositivo dispositivo = fabrica.criarDispositivo("Rádio Fab");
        assertInstanceOf(Radio.class, dispositivo);
        assertEquals("Rádio Fab", dispositivo.getNome());
    }

    @Test
    @DisplayName("FabricaRadio: deve criar ControleBasico")
    void fabricaRadioCriaControleBasico() {
        FabricaControleRemoto fabrica = new FabricaRadio();
        Dispositivo radio = fabrica.criarDispositivo("Rádio");
        ControleRemoto controle = fabrica.criarControle(radio);
        assertInstanceOf(ControleBasico.class, controle);
    }

    @Test
    @DisplayName("AbstractFactory: dispositivo e controle devem referenciar o mesmo objeto")
    void fabricaReferenciaMesmoDispositivo() {
        FabricaControleRemoto fabrica = new FabricaTelevisao();
        Dispositivo tv = fabrica.criarDispositivo("TV Ref");
        ControleRemoto controle = fabrica.criarControle(tv);
        assertSame(tv, controle.getDispositivo(), "Controle deve apontar para o mesmo dispositivo criado");
    }


    @Test
    @DisplayName("CriadorControleTelevisao: criarControle retorna ControleAvancado")
    void criadorTvRetornaControleAvancado() {
        CriadorControle criador = new CriadorControleTelevisao();
        ControleRemoto controle = criador.criarControle("TV FM");
        assertInstanceOf(ControleAvancado.class, controle);
    }

    @Test
    @DisplayName("CriadorControleTelevisao: dispositivo interno é Televisao")
    void criadorTvDispositivoCorreto() {
        CriadorControle criador = new CriadorControleTelevisao();
        ControleRemoto controle = criador.criarControle("TV FM");
        assertInstanceOf(Televisao.class, controle.getDispositivo());
    }

    @Test
    @DisplayName("CriadorControleRadio: criarControle retorna ControleBasico")
    void criadorRadioRetornaControleBasico() {
        CriadorControle criador = new CriadorControleRadio();
        ControleRemoto controle = criador.criarControle("Rádio FM");
        assertInstanceOf(ControleBasico.class, controle);
    }

    @Test
    @DisplayName("CriadorControleRadio: dispositivo interno é Radio")
    void criadorRadioDispositivoCorreto() {
        CriadorControle criador = new CriadorControleRadio();
        ControleRemoto controle = criador.criarControle("Rádio FM");
        assertInstanceOf(Radio.class, controle.getDispositivo());
    }

    @Test
    @DisplayName("FactoryMethod: inicializarControle deve ligar o dispositivo")
    void factoryMethodInicializaLigado() {
        CriadorControle criador = new CriadorControleTelevisao();
        ControleRemoto controle = criador.inicializarControle("TV Auto");
        assertTrue(controle.isLigado(), "Dispositivo deve estar ligado após inicialização");
    }

    @Test
    @DisplayName("CriadorControleTelevisao: getTipo retorna texto correto")
    void criadorTvTipo() {
        CriadorControle criador = new CriadorControleTelevisao();
        assertTrue(criador.getTipo().toLowerCase().contains("televisão"));
    }

    @Test
    @DisplayName("CriadorControleRadio: getTipo retorna texto correto")
    void criadorRadioTipo() {
        CriadorControle criador = new CriadorControleRadio();
        assertTrue(criador.getTipo().toLowerCase().contains("rádio"));
    }


    @Test
    @DisplayName("Integração: fluxo completo Singleton + AbstractFactory + FactoryMethod + Bridge")
    void integracaoCompleta() {
        CriadorControle criador = new CriadorControleTelevisao();
        ControleRemoto controle = criador.inicializarControle("TV Integração");

        controle.aumentarVolume();
        controle.proximoCanal();

        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        sistema.setModoNoturno(true);

        assertTrue(controle.isLigado());
        assertTrue(sistema.isModoNoturno());
        assertTrue(sistema.getHistoricoEventos().size() > 3,
                "Devem existir múltiplos eventos registrados");
        assertTrue(sistema.getHistoricoEventos().stream()
                .anyMatch(e -> e.contains("noturno")));
    }
}
