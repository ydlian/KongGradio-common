/*
 * Copyright (c) 2018-2024.
 *
 *  @author ydlian  whulyd@foxmail.com
 *  @since 2024-9-15
 *  @file: InputForm.java
 *  <p>
 *  Licensed under the Apache License Version 2.0;
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.konggradio.core.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * launch application with password
 */
public class InputForm {

    private static final String tips = " Please input Password: \r\n";

    private static final char passChar = '*';

    private JDialog frame;

    private JTextArea text;

    private int keyIndex = 0;

    private char[] password = new char[100];

    boolean hasNextLine = false;


    public char[] nextPasswordLine() {
        while (!hasNextLine) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int charsSize = 0;
        while (password[charsSize] != 0) {
            charsSize++;
        }
        char[] chars = new char[charsSize];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = password[i];
        }

        keyIndex = 0;
        password = new char[100];
        return chars;

    }

    /**
     * show windows
     *
     * @return if succeed
     */
    public boolean showForm() {
        try {
            frame = new JDialog();
            frame.setTitle("project launch password - kongcgradio");
            frame.setSize(560, 320);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            text = new JTextArea();
            text.setFont(new Font(null, 0, 18));
            text.setBackground(new Color(0, 0, 0));
            text.setForeground(new Color(0, 255, 0));
            text.setText(tips);
            text.addKeyListener(getKeyAdapter());
            text.enableInputMethods(false);
            frame.add(text);
            frame.setVisible(true);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void closeForm() {
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     *
     *
     * @return KeyAdapter
     */
    private KeyAdapter getKeyAdapter() {
        return new KeyAdapter() {
            String fakePass = "";

            @Override
            public void keyReleased(KeyEvent e) {
                if (keyIndex < 100 && e.getKeyChar() > 32 && e.getKeyChar() < 127) {
                    password[keyIndex] = e.getKeyChar();
                    keyIndex++;
                    fakePass += passChar;
                } else if (keyIndex > 0 && e.getKeyCode() == 8) {//退格
                    keyIndex--;
                    password[keyIndex] = 0;
                    fakePass = fakePass.substring(1);
                } else if (e.getKeyCode() == 10) {//ENTER
                    fakePass = "";
                    hasNextLine = true;
                }
                text.setText(tips + fakePass);
            }
        };
    }

    public static void main(String[] args) {
        InputForm input = new InputForm();
        boolean gui = input.showForm();
        if (gui) {
            System.out.println(input.nextPasswordLine());
            input.closeForm();
        }
    }
}
