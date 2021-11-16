/** Math2670 Homework06
 * @author Subodh Khanal
 * File: key.java
 * This is the class to check if the key is pressed
 */

package tetris;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Key extends KeyAdapter {
    private Tetris Key;

    public boolean[] buttons = new boolean[400];

    public Key(Tetris key) {
        this.Key = key;
    }

    // checks if key is pressed
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        this.buttons[keyCode] = true;
    }

    // checks if key is released
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        this.buttons[keyCode] = false;
    }

}
