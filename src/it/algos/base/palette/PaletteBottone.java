package it.algos.base.palette;

import it.algos.base.azione.Azione;
import it.algos.base.libreria.Lib;

import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Dimension;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 25-giu-2009
 * Time: 18.37.02
 * Bottone della Palette
 */
public class PaletteBottone extends JButton {

    /**
     * Costruttore completo.
     * <p>
     * @param palette di riferimento
     * @param azione per il bottone
     * */
    public PaletteBottone(Palette palette, Azione azione) {

        super(azione.getAzione());

        Icon icona = azione.getIconaMedia();
        this.setIcon(icona);

        /* toglie il testo se percaso c'era nella azione...*/
        this.setText("");

        /* regola le caratteristiche del bottone */
        this.setDisplayedMnemonicIndex(-1);
        this.setFocusable(false);
        this.setRolloverEnabled(true);
        int lar = palette.getLarBottoni();
        int alt = palette.getAltBottoni();
        this.setPreferredSize(new Dimension(lar, alt));
        Lib.Comp.bloccaDim(this);

    }


}
