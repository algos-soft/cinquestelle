/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaBase;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;

/**
 * Presentazione grafica di un singolo record di Indirizzo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public class IndirizzoScheda extends SchedaBase implements Indirizzo {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public IndirizzoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        this.creaPaginaIndirizzo();
    } /* fine del metodo */


    /**
     * Costruisce la pagina indirizzo.<br>
     * Costruisce la pagina vuota, col voce <br>
     * Aggiunge i varii campi, presi dal set <i>normale</i> di indirizzo:<ul>
     * <li> Campo via</li>
     * <li> Campo indirizzo</li>
     * <li> Campo numero</li>
     * <li> Campo CAP</li>
     * <li> Campo citta</li>
     * <li> Campo tipo</li>
     * <li> Campo note</li>
     * </ul>
     */
    private void creaPaginaIndirizzo() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea una pagina vuota col voce */
            pagina = super.addPagina("Indirizzo");

            pan = PannelloFactory.verticale(this);
            pan.add(Cam.indirizzo.get());
            pan.add(Cam.indirizzo2.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.citta.get());
            pan.add(Cam.cap.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.note.get());
            pan.add(Cam.tipo.get());
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Mostra un dialogo che consente di selezionare una delle città
     * corrispondenti al CAP specificato.
     * <p/>
     *
     * @param cap CAP da cercare
     *
     * @return il codice della città selezionata, 0 se annullato
     */
    private int selCittaDaCAP(String cap) {
        /* variabili e costanti locali di lavoro */
        int codSelezionata = 0;
        Modulo modCitta;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modCitta = CittaModulo.get();
            filtro = FiltroFactory.crea(Citta.Cam.cap.get(), cap);
            codSelezionata = modCitta.selezionaRecord(Citta.Cam.citta.get(),
                    filtro,
                    "Località corrispondenti al CAP " + cap,
                    "",
                    false,
                    null,
                    Citta.Cam.linkProvincia.get(),
                    Citta.Cam.linkNazione.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codSelezionata;
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String cap;
        Modulo modCitta;
        int codCitta;
        int codici[];

        try { // prova ad eseguire il codice

            modCitta = CittaModulo.get();

            /* se ho inserito la città e manca il CAP, inserisce il CAP */
            if (this.isCampo(campo, Cam.citta)) {
                cap = this.getString(Cam.cap.get());
                if (!Lib.Testo.isValida(cap)) {
                    codCitta = this.getInt(Cam.citta.get());
                    cap = modCitta.query().valoreStringa(Citta.Cam.cap.get(), codCitta);
                    this.setValore(Cam.cap.get(), cap);
                }// fine del blocco if
            }// fine del blocco if

            /* se ho inserito il CAP e manca la città, propone la città in base al CAP */
            if (this.isCampo(campo, Cam.cap)) {
                codCitta = this.getInt(Cam.citta.get());
                if (codCitta == 0) {
                    cap = this.getString(Cam.cap.get());
                    if (Lib.Testo.isValida(cap)) {
                        codici = modCitta.query().valoriChiave(Citta.Cam.cap.get(), cap);
                        if (codici.length > 0) {
                            if (codici.length == 1) {   // 1 sola trovata
                                codCitta = codici[0];
                                this.setValore(Cam.citta.get(), codCitta);
                            } else {
                                codCitta = selCittaDaCAP(cap);
                                if (codCitta != 0) {
                                    this.setValore(Cam.citta.get(), codCitta);
                                }// fine del blocco if
                            }// fine del blocco if-else
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
