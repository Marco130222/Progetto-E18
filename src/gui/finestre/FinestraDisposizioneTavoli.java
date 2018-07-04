package gui.finestre;

import gui.panels.PannelloDisposizioneTavoli;
import locale.GestoreEvento;
import locale.GestoreLocale;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lecciovich
 */

public class FinestraDisposizioneTavoli extends JFrame {
    public FinestraDisposizioneTavoli(GestoreLocale gestoreLocale, GestoreEvento gestoreEvento){
        setSize(200,200);
        PannelloDisposizioneTavoli pd=new PannelloDisposizioneTavoli(gestoreLocale, gestoreEvento);
        Container c=getContentPane();
        c.add(pd);

    }
}
