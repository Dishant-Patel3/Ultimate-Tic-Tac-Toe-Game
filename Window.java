import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener {
    private int rows, row;
    private int cols, col;
    private int cellsize;
    private int count, count1;
    private JButton[][] buttons;
    private ArrayList<JPanel> panels;
    private ArrayList<Integer> xlist, olist;
    private JLabel label;
    
    public static void main(String[] args) {
        Window win = new Window();
    }

    public Window() {
        setLayout(new GridLayout(3,3,0,0));
        setBackground(Color.black);
        buttons = new JButton[9][9];
        panels = new ArrayList<JPanel>();
        xlist = new ArrayList<Integer>();
        olist = new ArrayList<Integer>();
        rows = 9;
        cols = 9;
        row = 0;
        col = 0;
        cellsize = 60;
        count = 0;
        count1 = 0;

        JPanel main = new JPanel(new GridLayout(3, 3, 5, 5));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        for (int i = 0; i < 9; i++) {
            JPanel indi = new JPanel(new GridLayout(3, 3, 2, 2));
            indi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            for (int j = 0; j < 9; j++) {
                JButton cell = new JButton();
                cell.setPreferredSize(new Dimension(cellsize, cellsize)); 
                cell.setBackground(Color.white);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
                buttons[i][j] = cell;
                buttons[i][j].addActionListener(this);
                indi.add(cell);
            }  
            panels.add(indi);
            main.add(indi);
        }
        label = new JLabel();
        setTitle("Ultimate Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = new JButton();
        button = (JButton) e.getSource();
        for(int x = 0; x < 9; x++) {
            for(int y = 0; y < 9; y++) {
                if(buttons[x][y].equals(button)) {
                    row = x;
                    col = y;
                }
            }
        }
        if (button.getText().equals("")) {
            if(count % 2 == 0) {
                button.setText("X");
                button.setFont(new Font("Koulen", Font.BOLD, 30));
                button.setForeground(Color.RED);
                count++;
            } else if(count % 2 == 1) {
                button.setText("O");
                button.setFont(new Font("Koulen", Font.BOLD, 30));
                button.setForeground(Color.BLUE);
                count++;
            } 
            try {
                for(int i = 0; i < 9; i++) {
                    checkMIni(buttons[i][0], buttons[i][1], buttons[i][2], i);
                    checkMIni(buttons[i][0], buttons[i][3], buttons[i][6], i);
                    checkMIni(buttons[i][0], buttons[i][4], buttons[i][8], i);
                    checkMIni(buttons[i][1], buttons[i][4], buttons[i][7], i);
                    checkMIni(buttons[i][2], buttons[i][5], buttons[i][8], i);
                    checkMIni(buttons[i][2], buttons[i][4], buttons[i][6], i);
                    checkMIni(buttons[i][3], buttons[i][4], buttons[i][5], i);
                    checkMIni(buttons[i][0], buttons[i][3], buttons[i][6], i);
                    checkMIni(buttons[i][6], buttons[i][7], buttons[i][8], i);
                }
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void checkMIni(JButton one, JButton two, JButton three, int i) throws IOException {
        String oneT = one.getText();
        String twoT = two.getText();
        String threeT = three.getText();
        Color color = null;
        if(oneT.equals("X")) {
            color = Color.RED;
        } else if(oneT.equals("O")) {
            color = Color.BLUE;
        }
        if (!oneT.equals("") && oneT.equals(twoT) && twoT.equals(threeT)) {
            System.out.println(oneT + " wins");
            panels.get(i).setLayout(null);
            panels.get(i).removeAll();
            panels.get(i).setBackground(color);
            panels.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panels.get(i).setName(oneT);
            label = new JLabel();
            label.setText(oneT);
            label.setFont(new Font("Koulen", Font.BOLD, 70));
            label.setForeground(Color.WHITE);
            label.setBounds(70, 0, 200, 200);
            panels.get(i).add(label);
            if(oneT.equals("X")) {
                xlist.add(i);
                oneT = "";
            } else if(oneT.equals("O")) {
                olist.add(i);
                oneT = "";
            }
            if((xlist.contains(0) && xlist.contains(1) && xlist.contains(2)) || (xlist.contains(3) && xlist.contains(4) && xlist.contains(5)) || (xlist.contains(6) && xlist.contains(7) && xlist.contains(8)) ||
                (xlist.contains(0) && xlist.contains(3) && xlist.contains(6)) || (xlist.contains(1) && xlist.contains(4) && xlist.contains(7)) || (xlist.contains(2) && xlist.contains(5) && xlist.contains(8)) ||
                (xlist.contains(0) && xlist.contains(4)) && xlist.contains(8) || (xlist.contains(2) && xlist.contains(4) && xlist.contains(6))) {
                    JOptionPane.showMessageDialog(this, "X Wins!");
                    System.exit(0);
            } 
            if((olist.contains(0) && olist.contains(1) && olist.contains(2)) || (olist.contains(3) && olist.contains(4) && olist.contains(5)) || (olist.contains(6) && olist.contains(7) && olist.contains(8)) ||
                (olist.contains(0) && olist.contains(3) && olist.contains(6)) || (olist.contains(1) && olist.contains(4) && olist.contains(7)) || (olist.contains(2) && olist.contains(5) && olist.contains(8)) ||
                (olist.contains(0) && olist.contains(4)) && olist.contains(8) || (olist.contains(2) && olist.contains(4) && olist.contains(6))) {
                    JOptionPane.showMessageDialog(this, "O Wins!");
                    System.exit(0);
            } 
        } 
    }
}