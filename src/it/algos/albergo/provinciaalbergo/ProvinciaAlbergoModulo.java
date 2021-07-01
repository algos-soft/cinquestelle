package it.algos.albergo.provinciaalbergo;

import it.algos.albergo.nazionealbergo.NazioneAlbergoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

/**
 * Questa classe esiste solo per creare il modulo Nazioni specifico di Albergo
 * vedi creaModuli()
 */
public class ProvinciaAlbergoModulo extends ProvinciaModulo {


    public ProvinciaAlbergoModulo() {
    	super();
    }

    public ProvinciaAlbergoModulo(AlberoNodo unNodo) {
    	super(unNodo);
    }

    public ProvinciaAlbergoModulo(String unNomeModulo, AlberoNodo unNodo) {
        super(unNomeModulo, unNodo);
    }

    protected void creaModuli() {
        super.creaModulo(new NazioneAlbergoModulo());
    }

}
