/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 26-07-2005
 */

package it.algos.base.layout;

import it.algos.base.errore.Errore;

import java.awt.*;

/**
 * Layout manager per la gestione del componente video di un Campo.
 * <p/>
 * Questo layout e' basato su GridBagLayout.<br>
 * E' possibile aggiungere un componente in una delle cinque posizioni
 * previste (vedi schema).<br>
 * Le posizioni sono definite dalle costanti CENTRO, SOPRA, SOTTO, SINISTRA, DESTRA
 * nell'interfaccia Layout.
 * <p/>
 * +----------+----------+----------+
 * |(0,0)     |(1,0)     |(2,0)     |
 * |          |          |          |
 * |          | SOPRA    |          |
 * +----------+----------+----------+
 * |(0,1)     |(1,1)     |(2,1)     |
 * |          |          |          |
 * | SINISTRA | CENTRO   | DESTRA   |
 * +----------+----------+----------+
 * |(0,2)     |(1,2)     |(2,2)     |
 * |          |          |          |
 * |          | SOTTO    |          |
 * +----------+----------+----------+
 * <p/>
 * Il Pannello Camponenti viene aggiunto al centro.
 * Normalmente l'etichetta viene aggiunta sopra o a sinistra.
 * Normalmente la legenda viene aggiunta sotto o a destra.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 27-06-05
 */
public class LayoutCampo extends LayoutBase {

    /**
     * Costruttore completo senza parametri.
     */
    public LayoutCampo() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setLayoutRef(new GridBagLayout());
    }


} // fine della classe
