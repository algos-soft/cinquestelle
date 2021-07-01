/**
 * Title:     VerticalLabel
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-giu-2009
 */
package it.algos.base.componente;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * JLabel disposta in verticale
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-giu-2009 ore 14.26.46
 */
public class VerticalLabel extends JLabel {


    /**
     * Costruttore completo.
     * <p>
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(boolean clockwise) {
        super();
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     * <p>
     * @param image icona per la label
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(Icon image, boolean clockwise) {
        super(image);
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     * <p>
     * @param icon icona per la label
     * @param horizontalAlignment allineamento orizzontale (v.JLabel)
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(Icon icon, int horizontalAlignment, boolean clockwise) {
        super(icon, horizontalAlignment);
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     * <p>
     * @param text testo per la label
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(String text, boolean clockwise) {
        super(text);
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     * <p>
     * @param text testo per la label
     * @param icon icona per la label
     * @param horizontalAlignment allineamento orizzontale (v.JLabel)
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(String text, Icon icon, int horizontalAlignment, boolean clockwise) {
        super(text, icon, horizontalAlignment);
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo.
     * <p>
     * @param text testo per la label
     * @param horizontalAlignment allineamento orizzontale (v.JLabel)
     * @param clockwise true ruota in senso orario, false antiorario
     */
    public VerticalLabel(String text, int horizontalAlignment, boolean clockwise) {
        super(text, horizontalAlignment);
        this.setUI( new VerticalLabelUI(clockwise));
    }// fine del metodo costruttore completo

    
}// fine della classe
