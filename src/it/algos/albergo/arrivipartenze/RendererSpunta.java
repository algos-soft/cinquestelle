package it.algos.albergo.arrivipartenze;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JTable;

/**
 * Classe 'interna'
 * <p/>
 * Superclasse astratta di RendererDoc e RendererBambino
 * Implementa il metodo che consente di vedere se il check Arrivo è spuntato
 */
abstract class RendererSpunta extends RendererBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererSpunta(Campo campo) {
        super(campo);
    }// fine del metodo costruttore completo


    /**
     * Ritorna true se una riga è spuntata col check di arrivo.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return true se spuntata
     */
    protected boolean isSpuntata(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        boolean spuntata = false;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                spuntata = dati.getBoolAt(riga, ConfermaArrivoDialogo.Nomi.check.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return spuntata;
    }


}