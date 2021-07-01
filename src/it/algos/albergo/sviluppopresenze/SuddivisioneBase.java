/**
 * Title:     Suddivisione
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

/**
 * Classe astratta che rappresenta una singola tipologia
 * di suddivisione delle presenze sviluppate.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
abstract class SuddivisioneBase implements Suddivisione {

    /* titolo che appare esternamente nel popup (es. "Settimanale" */
    private String titolo;

    /* titolo nella colonna della lista e nei grafici (es. "Settimana") */
    private String label;

    /* true se è una tipologia temporale (giornalera, settimanale, mensile...) */
    private boolean temporale;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param titolo titolo dell'elemento (per popup)
     * @param label titolo dell'elemento (per header lista e grafici)
     * @param temporale true se è una suddivisione di tipo temporale
     */
    SuddivisioneBase(String titolo, String label, boolean temporale) {

        /* regola le variabili di istanza coi parametri */
        this.setTitolo(titolo);
        this.setLabel(label);
        this.setTemporale(temporale);

    }// fine del metodo costruttore completo


    @Override
    public String toString() {
        return this.getTitolo();
    }


    private String getTitolo() {
        return titolo;
    }


    private void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    /**
     * Ritorna il titolo della suddivisione da usare
     * nelle testate di lista e nei grafici
     * <p>
     * @return il titolo della suddivisione
     */
    public String getLabel() {
        return label;
    }


    private void setLabel(String label) {
        this.label = label;
    }


    /**
     * Ritorna true se è una suddivisione di tipo temporale
     *(giornalera, settimanale, mensile...)
     * <p>
     * @return true se temporale
     */
    public boolean isTemporale() {
        return temporale;
    }


    private void setTemporale(boolean temporale) {
        this.temporale = temporale;
    }


}// fine della classe
