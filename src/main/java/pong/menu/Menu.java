package pong.menu;

import com.jogamp.opengl.util.awt.TextRenderer;
import pong.Renderer;
import pong.cena.Cena;

import java.awt.*;

public class Menu {
    private TextRenderer textRenderer;

    private void voltar() {
        this.desenhaTexto(500, 10, Color.BLUE, "<- Voltar (v)", 15);
    }

    private void desenhaTexto(int xPosicao, int yPosicao, Color cor, String frase, int size) {
        textRenderer = new TextRenderer(new Font("Arial Negrito", Font.PLAIN, size));
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }

    public void principal() {
        this.desenhaTexto(227, 300, Color.BLUE, "PONG", 40);
        this.desenhaTexto(20, 20, Color.BLUE, "Instruções (Tab)", 15);
        this.desenhaTexto(20, 40, Color.BLUE, "Jogo (Enter)", 15);
        this.desenhaTexto(520, 10, Color.BLUE, "Sair (Esc)", 15);
    }

    public void instrucoes() {
        int Y = 500;
        this.desenhaTexto(200, Y, Color.BLUE, "Instruções:", 40);

        this.desenhaTexto(5, Y-=70, Color.BLUE, "Teclas:", 20);

        this.desenhaTexto(5, Y-=20, Color.BLUE,
                " - Utilize as teclas de (<-) esquerda e direita (->) para movimentar o bastão.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - (Enter) Começa o jogo.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - (P) Pausa o jogo.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - (Tab) Vai para as instruções do jogo.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - (Esc) Sai do jogo.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - (V) Volta para o menu principal.", 15);

        this.desenhaTexto(5, Y-=50, Color.BLUE, "Geral:", 20);

        this.desenhaTexto(5, Y-=20, Color.BLUE,
                " - A cada toque da bola no bastão, você ganha " + Cena.PONTOS_POR_BATIDA + " pontos.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - Faça " + Cena.PONTOS_PARA_VENCER + " pontos para vencer o jogo.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - Cada vez que a bola passar do bastão, você perde 1 vida, ao perder "
                        + Cena.VIDAS + " vidas", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                "   você perde o jogo", 15);

        this.desenhaTexto(5, Y-=50, Color.BLUE,
                "Fase 1:", 20);

        this.desenhaTexto(5, Y-=20, Color.BLUE,
                " - Faça " + Cena.PONTUACAO_FASE_2 + " pontos para chegar na fase 2.", 15);

        this.desenhaTexto(5, Y-=50, Color.BLUE,
                "Fase 2:", 20);

        this.desenhaTexto(5, Y-=20, Color.BLUE,
                " - A velocidade de delocamento da bola é maior.", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                " - Aparece outro bastão na tela, que fará com que a bola mude sua trajetoria,", 15);

        this.desenhaTexto(5, Y-=15, Color.BLUE,
                "   trasendo maior dificuldade ao jogo.", 15);

        this.voltar();
    }

    public void jogo(String frase) {
        this.desenhaTexto(15, 560, Color.BLUE, frase, 15);
    }

    public void fase2() {
        this.desenhaTexto(230, 300, Color.PINK, "Fase 2", 40);
    }

    public void pontos(int pontos) {
        this.desenhaTexto(280, 555, Color.BLUE, pontos+"", 20);
    }

    public void pause() {
        this.desenhaTexto(225, 300, Color.PINK, "PAUSE", 40);
    }

    public void gameOver() {
        this.desenhaTexto(175, 300, Color.PINK, "GAME OVER", 40);
    }

    public void venceu() {
        this.desenhaTexto(175, 300, Color.PINK, "Você venceu!", 40);
    }
}
