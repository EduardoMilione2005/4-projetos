package com.remotecontrol;

import com.remotecontrol.singleton.GerenciadorSistema;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Singleton — GerenciadorSistema")
class GerenciadorSistemaTest {

    @BeforeEach
    void resetar() throws Exception {
        var m = GerenciadorSistema.class.getDeclaredMethod("resetarInstancia");
        m.setAccessible(true);
        m.invoke(null);
    }

    @Test
    @DisplayName("Deve retornar sempre a mesma instância")
    void mesmaInstancia() {
        GerenciadorSistema a = GerenciadorSistema.getInstancia();
        GerenciadorSistema b = GerenciadorSistema.getInstancia();
        assertSame(a, b, "Singleton deve retornar a mesma referência");
    }

    @Test
    @DisplayName("Deve registrar evento ao ser criado")
    void registraEventoInicial() {
        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        assertFalse(sistema.getHistoricoEventos().isEmpty());
        assertTrue(sistema.getHistoricoEventos().get(0).contains("iniciado"));
    }

    @Test
    @DisplayName("Deve alternar modo noturno e registrar evento")
    void modoNoturno() {
        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        assertFalse(sistema.isModoNoturno());

        sistema.setModoNoturno(true);
        assertTrue(sistema.isModoNoturno());

        long eventos = sistema.getHistoricoEventos().stream()
                .filter(e -> e.contains("noturno")).count();
        assertEquals(1, eventos);
    }

    @Test
    @DisplayName("Deve aceitar volume entre 0 e 100")
    void volumeValido() {
        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        sistema.setVolumeGlobal(75);
        assertEquals(75, sistema.getVolumeGlobal());
    }

    @Test
    @DisplayName("Deve lançar exceção para volume fora do intervalo")
    void volumeInvalido() {
        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        assertThrows(IllegalArgumentException.class, () -> sistema.setVolumeGlobal(101));
        assertThrows(IllegalArgumentException.class, () -> sistema.setVolumeGlobal(-1));
    }

    @Test
    @DisplayName("Histórico deve ser imutável")
    void historicoImutavel() {
        GerenciadorSistema sistema = GerenciadorSistema.getInstancia();
        assertThrows(UnsupportedOperationException.class,
                () -> sistema.getHistoricoEventos().add("hack"));
    }

    @Test
    @DisplayName("Deve ser thread-safe: múltiplas threads devem obter a mesma instância")
    void threadSafety() throws InterruptedException {
        GerenciadorSistema[] resultados = new GerenciadorSistema[10];
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> resultados[idx] = GerenciadorSistema.getInstancia());
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        GerenciadorSistema primeiro = resultados[0];
        for (GerenciadorSistema r : resultados) {
            assertSame(primeiro, r, "Todas as threads devem obter a mesma instância");
        }
    }
}
