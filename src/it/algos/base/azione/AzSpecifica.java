package it.algos.base.azione;

import it.algos.base.modulo.Modulo;

/**
 * Azione specifica di un progetto/modulo.
 * </p>
 * Questa classe: <ul>
 * <li> Usa (se la usa) un'icona del set base </li>
 * <li> Non usa icone nella cartella icone del progetto specifico </li>
 * <li> Non necessita quindi del modulo </li>
 * <li> La classe è astratta </li>
 * <li> La classe concreta viene dichiarata, di norma, privata nel Modulo specifico </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-mag-2007 ore 11.32.23
 */
public abstract class AzSpecifica extends AzioneLocale {

    /**
     * Costruttore senza parametri.
     */
    public AzSpecifica() {
        /* rimanda al costruttore della superclasse */
        super(null);
        super.setUsaIconaModulo(false);
    }// fine del metodo costruttore senza parametri


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param posMenu di posizionamento
     */
    public AzSpecifica(Modulo modulo, String posMenu) {
        /* rimanda al costruttore della superclasse */
        super(modulo, posMenu);
        super.setUsaIconaModulo(false);
    }// fine del metodo costruttore completo


}// fine della classe
