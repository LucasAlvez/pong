package pong.cena;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import pong.menu.Menu;
import pong.textura.Textura;

public class Cena implements GLEventListener, KeyListener {
    private Menu menu;
    private Textura textura = null;

    public static final String IMG_TEXTURA = "src/imagens/metal.gif";

    private int opcao = 0;
    private boolean INICIO = true;
    private boolean PAUSE = false;

    private int PONTUACAO_ATUAL = 0;
    public static int PONTUACAO_FASE_2 = 200;
    public static int PONTOS_POR_BATIDA = 50;
    public static int PONTOS_PARA_VENCER = 400;
    private boolean FASE_2 = true;

    private float MOVE_Y = 0;
    private float MOVE_X = 0;
    private float VELOCIDADE = 0.012f;

    private float LIMITE_SUPERIOR = 0.85f;
    private float LIMITE_DIREITA = 0.87f;
    private float LIMITE_ESQUERDA = -1;

    private float BASTAO_X1 = -0.2f;
    private float BASTAO_X2 = 0.2f;
    private float BASTAO_Y1 = -0.8f;
    private float BASTAO_Y2 = -0.9f;

    private float BASTAO_PONTA_ESQUERDA = BASTAO_X1;
    private float BASTAO_PONTA_DIREITA = BASTAO_X2;
    private float MOVE_BASTAO_X = 0;

    private float BASTAO_CENTRO = 0;
    private float BASTAO_2_CENTRO = 0;

    private float BASTAO_2_X1 = -0.5f;
    private float BASTAO_2_X2 = 0.5f;
    private float BASTAO_2_Y1 = 0.4f;
    private float BASTAO_2_Y2 = 0.3f;

    private float BASTAO_2_PONTA_ESQUERDA = BASTAO_2_X1;
    private float BASTAO_2_PONTA_DIREITA = BASTAO_2_X2;

    private float CENTRO_TELA = 0.0f;
    private float MEIO_CENTRO_TELA_ESQUERDO = -0.5f;
    private float MEIO_CENTRO_TELA_DIREITO = 0.5f;

    private float cX = 0.0f;
    private float cY = 0.0f;
    private float rX = 0.05f;
    private float rY = 0.075f;
    private float POSICAO_BOLA_X = cX + rX;
    private float POSICAO_BOLA_Y = cY + rY;

    private boolean SUBIR_RETO = false;
    private boolean DESCER_RETO = false;
    private boolean SUBIR_DIREITA = false;
    private boolean SUBIR_ESQUERDA = false;
    private boolean DESCER_DIREITA = false;
    private boolean DESCER_ESQUERDA = false;

    public static int VIDAS = 5;

    private float POSICAO_VIDA_ESQ_X = 0.5f;
    private float POSICAO_VIDA_ESQ_Y = 0.9f;

    private float POSICAO_VIDA_Z = 0.3f;

    private float POSICAO_VIDA_DIR_X = 0.525f;
    private float POSICAO_VIDA_DIR_Y = 0.9f;

    private float POSICAO_VIDA_BASE_X = (POSICAO_VIDA_ESQ_X + POSICAO_VIDA_DIR_X) / 2;
    private float POSICAO_VIDA_BASE_Y = 0.3f;
    private float POSICAO_VIDA_BASE_Z = -0.89f;

    private float SPHERE = 0.022f;
    private float CONE = 0.05f;

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);

        //Liga iluminacao
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHTING);

        this.textura = new Textura(1);

        //Habilita as cores do objeto 3D
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
    }

    public void display(GLAutoDrawable drawable) {
        // obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        // define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);

        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        GLUT glut = new GLUT();

        // limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity(); // lê a matriz identidade

        switch (opcao) {
            case 0:
                this.menu = new Menu();
                this.menu.principal();
                break;
            case 1:
                this.menu = new Menu();
                this.menu.instrucoes();
                break;
            case 2:
                this.menu = new Menu();
                double lim = 2 * Math.PI;

                gl.glPushMatrix();

                if (INICIO) {
                    this.gameOver();
                    this.inicio(gl);
                }
                this.gameOver();

                if (PONTUACAO_ATUAL >= PONTUACAO_FASE_2) {
                    this.menu.jogo("Fase 2");
                } else {
                    this.menu.jogo("Fase 1");
                }

                if (PONTUACAO_ATUAL == PONTUACAO_FASE_2 && FASE_2) {
                    opcao = 5;
                    this.reset(false);
                    FASE_2 = false;
                    DESCER_RETO = true;
                }

                POSICAO_BOLA_Y = -(cY + rY) + MOVE_Y;
                POSICAO_BOLA_X = -(cX + rX) + MOVE_X;
                System.out.println("-----------------------------------------------------");
                System.out.println("BOLA X: " + POSICAO_BOLA_X + " BOLA Y: " + POSICAO_BOLA_Y);
                System.out.println("CENTRO DO BASTÃO: " + BASTAO_CENTRO);
                System.out.println("PONTA ESQUERDA: " + BASTAO_PONTA_ESQUERDA + " PONTA DIREITA: " + BASTAO_PONTA_DIREITA);


                if (PONTUACAO_ATUAL >= PONTUACAO_FASE_2) {
                    VELOCIDADE = 0.015f;

                    BASTAO_2_CENTRO = (BASTAO_2_PONTA_ESQUERDA + BASTAO_2_PONTA_DIREITA) / 2;
                    float BOLA_X = (POSICAO_BOLA_X + rX);
                    float BASTAO_Y2 = (BASTAO_2_Y2 - 0.15f);

                    if (SUBIR_DIREITA || SUBIR_ESQUERDA || SUBIR_RETO) {

                        BASTAO_2_X1 = -0.5f;
                        BASTAO_2_X2 = 0.5f;

                        BASTAO_2_PONTA_ESQUERDA = BASTAO_2_X1;
                        BASTAO_2_PONTA_DIREITA = BASTAO_2_X2;

                        if (POSICAO_BOLA_Y >= BASTAO_Y2 && POSICAO_BOLA_Y < BASTAO_2_Y1
                                && BOLA_X >= BASTAO_2_PONTA_ESQUERDA
                                && BOLA_X <= BASTAO_2_PONTA_DIREITA) {

                            System.out.println("BATEU NO BASTÃO SUBINDO");

                            if (POSICAO_BOLA_X == -rX || POSICAO_BOLA_X == BASTAO_2_CENTRO && SUBIR_RETO) {
                                DESCER_RETO = true;
                                SUBIR_ESQUERDA = false;
                                SUBIR_DIREITA = false;
                                SUBIR_RETO = false;
                            }

                            if (SUBIR_ESQUERDA) {
                                DESCER_ESQUERDA = true;
                                SUBIR_ESQUERDA = false;
                                SUBIR_DIREITA = false;
                                SUBIR_RETO = false;
                            }
                            if (SUBIR_DIREITA) {
                                DESCER_DIREITA = true;
                                SUBIR_ESQUERDA = false;
                                SUBIR_DIREITA = false;
                                SUBIR_RETO = false;
                            }
                        }
                    }

                    if (DESCER_DIREITA || DESCER_ESQUERDA) {

                        BASTAO_2_X1 = -0.3f;
                        BASTAO_2_X2 = 0.3f;

                        BASTAO_2_PONTA_ESQUERDA = BASTAO_2_X1;
                        BASTAO_2_PONTA_DIREITA = BASTAO_2_X2;

                        if (POSICAO_BOLA_Y <= BASTAO_2_Y1 && POSICAO_BOLA_Y > BASTAO_2_Y2
                                && BOLA_X >= BASTAO_2_PONTA_ESQUERDA
                                && BOLA_X <= BASTAO_2_PONTA_DIREITA) {

                            System.out.println("BATEU NO BASTÃO DESCENDO");

                            if (DESCER_DIREITA) {
                                SUBIR_DIREITA = true;
                                DESCER_RETO = false;
                                DESCER_ESQUERDA = false;
                                DESCER_DIREITA = false;
                            }
                            if (DESCER_ESQUERDA) {
                                SUBIR_ESQUERDA = true;
                                DESCER_RETO = false;
                                DESCER_ESQUERDA = false;
                                DESCER_DIREITA = false;
                            }
                        }
                    }

                    if (PONTUACAO_ATUAL == PONTOS_PARA_VENCER) {
                        opcao = 6;
                        this.reset(true);
                        PONTUACAO_ATUAL = 0;
                        VIDAS = 5;
                        FASE_2 = true;
                    }
                }


                if (POSICAO_BOLA_Y <= BASTAO_Y1) {
                    DESCER_ESQUERDA = false;
                    DESCER_DIREITA = false;
                    DESCER_RETO = false;

                    PONTUACAO_ATUAL += PONTOS_POR_BATIDA;

                    BASTAO_CENTRO = (BASTAO_PONTA_ESQUERDA + BASTAO_PONTA_DIREITA) / 2;

                    if (POSICAO_BOLA_X == -rX && BASTAO_CENTRO == CENTRO_TELA || POSICAO_BOLA_X == BASTAO_CENTRO) {
                        SUBIR_RETO = true;
                    }

                    if (POSICAO_BOLA_X > BASTAO_CENTRO) {
                        if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO >= MEIO_CENTRO_TELA_ESQUERDO) {
                            SUBIR_DIREITA = true;
                        }

                        if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO < MEIO_CENTRO_TELA_ESQUERDO) {
                            SUBIR_ESQUERDA = true;
                        }

                        if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO <= MEIO_CENTRO_TELA_DIREITO) {
                            SUBIR_DIREITA = true;
                        }

                        if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_DIREITO) {
                            SUBIR_ESQUERDA = true;
                        }
                    }

                    if (POSICAO_BOLA_X < BASTAO_CENTRO) {
                        if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_ESQUERDO) {
                            SUBIR_ESQUERDA = true;
                        }

                        if (BASTAO_CENTRO < CENTRO_TELA && BASTAO_CENTRO <= MEIO_CENTRO_TELA_ESQUERDO) {
                            SUBIR_DIREITA = true;
                        }

                        if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO <= MEIO_CENTRO_TELA_DIREITO) {
                            SUBIR_ESQUERDA = true;
                        }

                        if (BASTAO_CENTRO > CENTRO_TELA && BASTAO_CENTRO > MEIO_CENTRO_TELA_DIREITO) {
                            SUBIR_DIREITA = true;
                        }
                    }
                }

                if (SUBIR_ESQUERDA) {
                    this.subirEsquerda(gl);
                }

                if (SUBIR_DIREITA) {
                    this.subirDireita(gl);
                }

                if (DESCER_ESQUERDA) {
                    this.descerEsquerda(gl);
                }

                if (DESCER_DIREITA) {
                    this.descerDireita(gl);
                }

                if (SUBIR_RETO) {
                    this.subirReto(gl);
                }

                if (DESCER_RETO) {
                    this.descerReto(gl);
                }

                gl.glBegin(GL2.GL_POLYGON);
                gl.glColor3f(1, 1, 0);
                for (float i = 0; i < lim; i += 0.01) {
                    gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));
                }

                gl.glEnd();
                gl.glPopMatrix();

                //BASTAO
                gl.glPushMatrix();

                //transformações geométricas para as texturas
                gl.glMatrixMode(GL2.GL_TEXTURE);
                gl.glLoadIdentity();
                gl.glScalef(textura.getWidth(), textura.getHeight(), 1);
                gl.glMatrixMode(GL2.GL_MODELVIEW);

                //é geração de textura automática
                textura.setAutomatica(true);

                //habilita os filtros
                textura.setFiltro(GL2.GL_LINEAR);
                textura.setModo(GL2.GL_DECAL);
                textura.setWrap(GL2.GL_REPEAT);
                textura.gerarTextura(gl, IMG_TEXTURA, 0);

                gl.glColor3f(0, 0, 1);
                gl.glTranslatef(MOVE_BASTAO_X, 0, 0);
                gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2d(BASTAO_X1, BASTAO_Y1);
                gl.glVertex2d(BASTAO_X2, BASTAO_Y1);
                gl.glVertex2d(BASTAO_X2, BASTAO_Y2);
                gl.glVertex2d(BASTAO_X1, BASTAO_Y2);

                textura.desabilitarTextura(gl, 0);

                gl.glEnd();
                textura.desabilitarTextura(gl, 0);
                gl.glPopMatrix();

                if (PONTUACAO_ATUAL >= PONTUACAO_FASE_2) {
                    // BASTAO 2
                    gl.glPushMatrix();
                    gl.glColor3f(0.5f, 0, 0);
                    gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2d(BASTAO_2_X1, BASTAO_2_Y1);
                    gl.glVertex2d(BASTAO_2_X2, BASTAO_2_Y1);
                    gl.glVertex2d(BASTAO_2_X2, BASTAO_2_Y2);
                    gl.glVertex2d(BASTAO_2_X1, BASTAO_2_Y2);
                    gl.glEnd();
                    gl.glPopMatrix();
                }

                float incr = 0;
                for (int i = 0; i < VIDAS; i += 1) {
                    desenhaVida(gl, glut, incr);
                    incr += 0.1f;
                }
                this.menu.pontos(PONTUACAO_ATUAL);
                break;
            case 3:
                this.menu = new Menu();
                this.menu.pause();
                break;
            case 4:
                this.menu = new Menu();
                this.menu.gameOver();
                break;
            case 5:
                this.menu = new Menu();
                this.menu.fase2();
                break;
            case 6:
                this.menu = new Menu();
                this.menu.venceu();
                break;
        }

        gl.glFlush();
    }

    public void desenhaVida(GL2 gl, GLUT glut, float incr) {
        SPHERE += 0.0001f;
        CONE += 0.0001f;

        if (SPHERE > 0.030f) {
            SPHERE = 0.022f;
            CONE = 0.05f;
        }

        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glColor3f(1, 0, 0);
        gl.glTranslatef(POSICAO_VIDA_ESQ_X + incr, POSICAO_VIDA_ESQ_Y, POSICAO_VIDA_Z);

        glut.glutSolidSphere(SPHERE, 10, 10);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(POSICAO_VIDA_DIR_X + incr, POSICAO_VIDA_DIR_Y, POSICAO_VIDA_Z);
        glut.glutSolidSphere(SPHERE, 10, 10);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(90, 1, 0, 0);
        gl.glTranslatef(POSICAO_VIDA_BASE_X + incr, POSICAO_VIDA_BASE_Y, POSICAO_VIDA_BASE_Z);
        glut.glutSolidCone(CONE-0.02f, CONE, 10, 10);
        gl.glPopMatrix();

        gl.glPopMatrix();

    }

    private void subirDireita(GL2 gl) {
        System.out.println("SUBINDO PARA A DIREITA");
        gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
        if (POSICAO_BOLA_X >= LIMITE_DIREITA) {
            SUBIR_ESQUERDA = true;
            SUBIR_DIREITA = false;
            SUBIR_RETO = false;
            DESCER_ESQUERDA = false;
            DESCER_DIREITA = false;
            DESCER_RETO = false;
        }

        if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
            DESCER_DIREITA = true;
            SUBIR_RETO = false;
            SUBIR_DIREITA = false;
            SUBIR_ESQUERDA = false;
            DESCER_ESQUERDA = false;
            DESCER_RETO = false;
        }
    }

    private void subirEsquerda(GL2 gl) {
        System.out.println("SUBINDO PARA A ESQUERDA");
        gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y += VELOCIDADE, 0);
        if (POSICAO_BOLA_X <= LIMITE_ESQUERDA) {
            SUBIR_DIREITA = true;
            SUBIR_ESQUERDA = false;
            SUBIR_RETO = false;
            DESCER_RETO = false;
            DESCER_DIREITA = false;
            DESCER_ESQUERDA = false;
        }

        if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
            DESCER_ESQUERDA = true;
            SUBIR_ESQUERDA = false;
            SUBIR_DIREITA = false;
            SUBIR_RETO = false;
            DESCER_DIREITA = false;
            DESCER_RETO = false;
        }
    }

    private void descerEsquerda(GL2 gl) {
        System.out.println("DESCENDO PARA ESQUERDA");
        gl.glTranslatef(MOVE_X -= VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
        if (POSICAO_BOLA_X <= LIMITE_ESQUERDA) {
            DESCER_DIREITA = true;
            DESCER_ESQUERDA = false;
            DESCER_RETO = false;
            SUBIR_RETO = false;
            SUBIR_DIREITA = false;
            SUBIR_ESQUERDA = false;
        }
    }

    private void descerDireita(GL2 gl) {
        System.out.println("DESCENDO PARA A DIREITA");
        gl.glTranslatef(MOVE_X += VELOCIDADE, MOVE_Y -= VELOCIDADE, 0);
        if (POSICAO_BOLA_X >= LIMITE_DIREITA) {
            DESCER_ESQUERDA = true;
            DESCER_DIREITA = false;
            DESCER_RETO = false;
            SUBIR_RETO = false;
            SUBIR_DIREITA = false;
            SUBIR_ESQUERDA = false;
        }
    }

    private void subirReto(GL2 gl) {
        System.out.println("SUBINDO RETO");
        gl.glTranslatef(0, MOVE_Y += VELOCIDADE, 0);
        if (POSICAO_BOLA_Y >= LIMITE_SUPERIOR) {
            DESCER_RETO = true;
            SUBIR_RETO = false;
            SUBIR_DIREITA = false;
            SUBIR_ESQUERDA = false;
            DESCER_DIREITA = false;
            DESCER_ESQUERDA = false;
        }
    }

    private void descerReto(GL2 gl) {
        System.out.println("DESCENDO RETO");
        gl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0);
    }

    private void inicio(GL2 gl) {
        gl.glTranslatef(0, MOVE_Y -= VELOCIDADE, 0);
        if (POSICAO_BOLA_Y <= BASTAO_Y1) {
            System.out.println("BATEU NA ALTURA DO BASTÃO");
            PONTUACAO_ATUAL -= PONTOS_POR_BATIDA;
            INICIO = false;
        }
    }

    private void reset(boolean inicio) {

        if (inicio) {
            INICIO = true;
        } else {
            INICIO = false;
        }

        PAUSE = false;

        MOVE_Y = 0;
        MOVE_X = 0;
        VELOCIDADE = 0.01f;

        LIMITE_SUPERIOR = 0.85f;
        LIMITE_DIREITA = 0.87f;
        LIMITE_ESQUERDA = -1;

        BASTAO_X1 = -0.2f;
        BASTAO_X2 = 0.2f;
        BASTAO_Y1 = -0.8f;
        BASTAO_Y2 = -0.9f;

        CENTRO_TELA = 0;
        MEIO_CENTRO_TELA_ESQUERDO = -0.5f;
        MEIO_CENTRO_TELA_DIREITO = 0.5f;

        cX = 0.0f;
        cY = 0.0f;
        rX = 0.05f;
        rY = 0.075f;
        POSICAO_BOLA_X = cX + rX;
        POSICAO_BOLA_Y = cY + rY;

        BASTAO_PONTA_ESQUERDA = BASTAO_X1;
        BASTAO_PONTA_DIREITA = BASTAO_X2;
        MOVE_BASTAO_X = 0;

        BASTAO_CENTRO = 0;

        SUBIR_RETO = false;
        DESCER_RETO = false;
        SUBIR_DIREITA = false;
        SUBIR_ESQUERDA = false;
        DESCER_DIREITA = false;
        DESCER_ESQUERDA = false;
    }

    private void gameOver() {
        float BOLA_X = 0;
        BOLA_X = (POSICAO_BOLA_X + rX);

        if (POSICAO_BOLA_Y <= BASTAO_Y1
                && BOLA_X < BASTAO_PONTA_ESQUERDA) {

            VIDAS -= 1;
            PONTUACAO_ATUAL -= PONTOS_POR_BATIDA;
            this.reset(true);

            if (VIDAS == 0) {
                System.out.println("GAME OVER ESQUERDA :" + BOLA_X + " VS " + BASTAO_PONTA_ESQUERDA);
                opcao = 4;
                VIDAS = 5;
                this.reset(true);
            }
        }

        if (POSICAO_BOLA_Y <= BASTAO_Y1
                && POSICAO_BOLA_X > BASTAO_PONTA_DIREITA) {

            VIDAS -= 1;
            PONTUACAO_ATUAL -= PONTOS_POR_BATIDA;
            this.reset(true);

            if (VIDAS == 0) {
                System.out.println("GAME OVER DIREITA :" + BASTAO_PONTA_DIREITA);
                opcao = 4;
                VIDAS = 5;
                this.reset(true);
            }
        }
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();
        // ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // lê a matriz identidade
        // projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        // ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_RIGHT:
                if (BASTAO_PONTA_DIREITA < 1) {
                    MOVE_BASTAO_X += 0.1f;
                    BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X);
                    BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X);
                }
                break;

            case KeyEvent.VK_LEFT:
                if (BASTAO_PONTA_ESQUERDA > -1) {
                    MOVE_BASTAO_X -= 0.1f;
                    BASTAO_PONTA_ESQUERDA = BASTAO_X1 + (MOVE_BASTAO_X);
                    BASTAO_PONTA_DIREITA = BASTAO_X2 + (MOVE_BASTAO_X);
                }
                break;
            case KeyEvent.VK_TAB:
                opcao = 1;
                break;
            case KeyEvent.VK_ENTER:
                opcao = 2;
                break;
            case KeyEvent.VK_P:
                PAUSE = !PAUSE;
                if (PAUSE) {
                    opcao = 3;
                } else {
                    opcao = 2;
                }
                break;
            case KeyEvent.VK_V:
                opcao = 0;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}