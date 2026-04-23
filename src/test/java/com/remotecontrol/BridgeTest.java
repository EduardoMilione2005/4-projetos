package com.remotecontrol;

import com.remotecontrol.bridge.*;
import com.remotecontrol.singleton.GerenciadorSistema;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bridge — ControleRemoto + Dispositivo")
class BridgeTest {

    @BeforeEach
    void resetarSingleton() throws Exception {
        Method m = GerenciadorSistema.class.getDeclaredMethod("resetarInstancia");
        m.setAccessible(true);
        m.invoke(null);
    }

    @Test
    @DisplayName("TV: deve iniciar desligada")
    void tvIniciaDesligada() {
        Televisao tv = new Televisao("TV Teste");
        assertFalse(tv.isLigado());
    }

    @Test
    @DisplayName("TV: ligar e desligar")
    void tvLigarDesligar() {
        Televisao tv = new Televisao("TV Teste");
        tv.ligar();
        assertTrue(tv.isLigado());
        tv.desligar();
        assertFalse(tv.isLigado());
    }

    @Test
    @DisplayName("TV: volume não ultrapassa limites")
    void tvVolumeLimites() {
        Televisao tv = new Televisao("TV Teste");
        tv.setVolume(120);
        assertEquals(100, tv.getVolume());
        tv.setVolume(-5);
        assertEquals(0, tv.getVolume());
    }

    @Test
    @DisplayName("TV: canal não vai abaixo de 1")
    void tvCanalMinimo() {
        Televisao tv = new Televisao("TV Teste");
        tv.setCanal(0);
        assertEquals(1, tv.getCanal());
    }


    @Test
    @DisplayName("Rádio: deve iniciar desligado")
    void radioIniciaDesligado() {
        Radio radio = new Radio("Rádio Teste");
        assertFalse(radio.isLigado());
    }

    @Test
    @DisplayName("Rádio: trocar estação")
    void radioEstacao() {
        Radio radio = new Radio("Rádio Teste");
        radio.setCanal(5);
        assertEquals(5, radio.getCanal());
    }


    @Test
    @DisplayName("ControleBasico: delega ligar/desligar ao dispositivo")
    void controlebBasicoDelegaLigar() {
        Televisao tv = new Televisao("TV Bridge");
        ControleBasico controle = new ControleBasico(tv);

        controle.ligar();
        assertTrue(controle.isLigado());
        controle.desligar();
        assertFalse(controle.isLigado());
    }

    @Test
    @DisplayName("ControleBasico: aumentar e diminuir volume em steps de 10")
    void controlebBasicoVolume() {
        Televisao tv = new Televisao("TV Bridge");
        ControleBasico controle = new ControleBasico(tv);

        int volumeInicial = tv.getVolume();
        controle.aumentarVolume();
        assertEquals(volumeInicial + 10, tv.getVolume());
        controle.diminuirVolume();
        assertEquals(volumeInicial, tv.getVolume());
    }

    @Test
    @DisplayName("ControleBasico: navegar canais")
    void controleBasicoCanais() {
        Televisao tv = new Televisao("TV Bridge");
        ControleBasico controle = new ControleBasico(tv);

        controle.proximoCanal();
        assertEquals(2, tv.getCanal());
        controle.canalAnterior();
        assertEquals(1, tv.getCanal());

        controle.canalAnterior();
        assertEquals(1, tv.getCanal());
    }

    @Test
    @DisplayName("ControleBasico: getTipoControle correto")
    void controleBasicoTipo() {
        ControleBasico controle = new ControleBasico(new Radio("R"));
        assertEquals("Controle Básico", controle.getTipoControle());
    }


    @Test
    @DisplayName("ControleAvancado: alternar mudo salva e restaura volume")
    void controleAvancadoMudo() {
        Televisao tv = new Televisao("TV Avancada");
        ControleAvancado controle = new ControleAvancado(tv);
        tv.setVolume(60);

        controle.alternarMudo();
        assertTrue(controle.isMudo());
        assertEquals(0, tv.getVolume());

        controle.alternarMudo();
        assertFalse(controle.isMudo());
        assertEquals(60, tv.getVolume());
    }

    @Test
    @DisplayName("ControleAvancado: ir diretamente para canal")
    void controleAvancadoIrParaCanal() {
        Televisao tv = new Televisao("TV Avancada");
        ControleAvancado controle = new ControleAvancado(tv);
        controle.irParaCanal(50);
        assertEquals(50, tv.getCanal());
    }

    @Test
    @DisplayName("ControleAvancado: getTipoControle correto")
    void controleAvancadoTipo() {
        ControleAvancado controle = new ControleAvancado(new Televisao("TV"));
        assertEquals("Controle Avançado", controle.getTipoControle());
    }

    @Test
    @DisplayName("Bridge: mesmo controle funciona com dispositivos diferentes")
    void bridgeIntercambio() {
        Dispositivo tv    = new Televisao("TV X");
        Dispositivo radio = new Radio("Rádio X");

        ControleBasico controle = new ControleBasico(tv);
        controle.ligar();
        assertTrue(tv.isLigado());

        controle = new ControleBasico(radio);
        controle.ligar();
        assertTrue(radio.isLigado());
        assertFalse(tv.isLigado());
    }
}
