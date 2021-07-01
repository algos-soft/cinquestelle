/**
 * Title:     OggettoNodoPC
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-lug-2006
 */
package it.algos.albergo.pianodeicontialbergo;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;

/**
 * User Object per un Nodo dell'albero del piano dei conti.
 */
public class OggettoNodoPC extends AlberoNodo {

    /* tipo di nodo */
    private AlberoPC.TipoNodo tipo;

    /* codice del record */
    private int codice;

    /* descrizione del record */
    private String descrizione;


    /**
     * Costruttore completo con parametri. <br>
     */
    public OggettoNodoPC() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public AlberoPC.TipoNodo getTipo() {
        return tipo;
    }


    public void setTipo(AlberoPC.TipoNodo tipo) {
        this.tipo = tipo;
    }


    public int getCodice() {
        return codice;
    }


    public void setCodice(int codice) {
        this.codice = codice;
    }


    public String getDescrizione() {
        return descrizione;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}  // fine della classe interna
