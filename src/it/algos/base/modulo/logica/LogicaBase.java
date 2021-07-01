/**
 * Title:     LogicaModulo
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.base.modulo.logica;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Contenitore delle logiche di un modulo.
 * </p>
 * Ogni modulo può mantenere un'istanza di questa classe.<br>
 * Per implementare delle logiche specifiche in un modulo, creare una
 * sottoclasse (es. LogicaSpecifica) di questa classe e assegnare
 * al modulo il riferimento usando la chiamata modulo.setLogica(new LogicaSpecifica).
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 23.44.46
 */
public class LogicaBase implements LogicaModulo {

    /**
     * Modulo di riferimento
     */
    private Modulo modulo = null;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo proprietario di questa logica.
     */
    public LogicaBase(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModulo(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

}// fine della classe
