package pong;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import pong.cena.Cena;

public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 600;
    public static int screenHeight = 600;

    // Cria a janela de rendeziração do JOGL
    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setFullscreen(true);

//        window.setSize(screenWidth, screenHeight);
//		 window.setResizable(false);

        Cena cena = new Cena();
        window.addGLEventListener(cena); // adiciona a Cena a Janela
        window.addKeyListener(cena); // registra o teclado na janela

        final FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start(); // inicia o loop de animação

        // encerrar a aplicacao adequadamente
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
        window.setVisible(true);
    }

    public static void main(String[] args) {
        init();
    }
}
