import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        // Tamanho da Tela
        int boardWidth = 360;
        int boardHeight = 640;

        // Abrir JFrame
        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
 
        // Criar o FlappyBird
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();  
        frame.setVisible(true);
    }
}
