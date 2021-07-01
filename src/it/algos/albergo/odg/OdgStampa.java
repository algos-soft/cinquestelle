/**
 * Title:     OdgStampa
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-giu-2009
 */
package it.algos.albergo.odg;

import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.PageEject;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.albergo.odg.odgzona.OdgZonaModulo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

/**
 * Oggetto stampabile (Flowable) per un ODG
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-giu-2009 ore 14.09.24
 */
public final class OdgStampa extends J2FlowPrinter {

    /**
     * codice dell'ODG
     */
    private int codOdg;

    /**
     * codici delle zone da includere nella stampa
     */
    private int[] codiciZona;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param codOdg codice dell'ODG
     * @param codiciZona codici delle zone da stampare
     */
    public OdgStampa(int codOdg, int[] codiciZona) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodOdg(codOdg);
        this.setCodiciZona(codiciZona);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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

        try { // prova ad eseguire il codice

            /**
             * Recupera le Righe di Zona richieste, le spazzola,
             * e per ognuna crea un Pageable e lo aggiunge a
             * questo Flowable separati da un PageEject
             */
            int[] codiciRZ = this.getCodiciRigheZona();
            for (int k = 0; k < codiciRZ.length; k++) {
                int cod = codiciRZ[k];
                if (k>0) {
                    this.addFlowable(new PageEject());
                }// fine del blocco if
                OdgStampaZona printerZona = new OdgStampaZona(cod);
//                this.addFlowable(this.creaPrinterTitolo(cod));
                this.addFlowable(printerZona);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Ritorna i codici delle righe di zona corrispondenti
     * a zone/odg richiesti.
     * <p/>
     * @return l'elenco delle chiavi delle righe di zona
     */
    private int[] getCodiciRigheZona() {
        /* variabili e costanti locali di lavoro */
        int[] chiavi = new int[0];
        Modulo modRigheZona = OdgZonaModulo.get();

        try {    // prova ad eseguire il codice

            /* crea il filtro per l'odg richiesto */
            Filtro filtroOdg = FiltroFactory.crea(OdgZona.Cam.odg.get(), this.getCodOdg());

            /* crea il filtro per le zone richieste */
            Filtro filtroZone = new Filtro();
            for(int codZona : this.getCodiciZona()){
                Filtro filtro = FiltroFactory.crea(OdgZona.Cam.zona.get(), codZona);
                filtroZone.add(Filtro.Op.OR, filtro);
            }

            /* crea il filtro completo */
            Filtro filtroTot = new Filtro();
            filtroTot.add(filtroOdg);
            filtroTot.add(filtroZone);

            /* esegue la query e recupera le chiavi */
            chiavi = modRigheZona.query().valoriChiave(filtroTot);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiavi;
    }



    /**
     * Ritorna il codice della zona dal codice della riga di zona.
     * <p/>
     * @param codRigaZona il codice della riga di zona
     * @return il codice della Zona
     */
    private int getCodZona (int codRigaZona) {
        return OdgZonaModulo.get().query().valoreInt(OdgZona.Cam.zona.get(), codRigaZona);
    }



    private int getCodOdg() {
        return codOdg;
    }


    private void setCodOdg(int codOdg) {
        this.codOdg = codOdg;
    }


    private int[] getCodiciZona() {
        return codiciZona;
    }


    private void setCodiciZona(int[] codiciZona) {
        this.codiciZona = codiciZona;
    }
}// fine della classe
