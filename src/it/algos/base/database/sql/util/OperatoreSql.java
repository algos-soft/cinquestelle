/**
 * Title:     OperatoreSql
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-ott-2004
 */
package it.algos.base.database.sql.util;

import it.algos.base.database.util.OperatoreBase;
import it.algos.base.errore.Errore;

/**
 * Operatore Sql generico
 * </p>
 * Descrive il tipo di dati per un operatore di filtro Sql.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-ott-2004 ore 8.48.05
 */
public final class OperatoreSql extends OperatoreBase {

    /**
     * codifica posizione solo sinistra carattere speciale
     */
    public static final int SINISTRA = 1;

    /**
     * codifica posizione solo destra carattere speciale
     */
    public static final int DESTRA = 2;

    /**
     * codifica posizione sia sinistra sia destra carattere speciale
     */
    public static final int ENTRAMBE = 3;


    /**
     * Wildcard da utilizzare nelle ricerche di testo
     * da usare congiuntamente a posizioneWildcard
     * Solo per operatori che operano solo sul testo
     */
    private String wildcard = null;

    /**
     * Posizione della wildcard nel testo (PRIMA, DOPO, ENTRAMBI)
     * da usare congiuntamente a wildcard<br>
     * Solo per operatori che operano solo sul testo<br>
     * Costanti in OperatoreSql.
     */
    private int posizioneWildcard = 0;


    /**
     * Costruttore completo con parametri. <br>
     */
    public OperatoreSql() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public String getWildcard() {
        return wildcard;
    }


    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }


    public int getPosizioneWildcard() {
        return posizioneWildcard;
    }


    public void setPosizioneWildcard(int posizioneWildcard) {
        this.posizioneWildcard = posizioneWildcard;
    }

}// fine della classe
