package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.scheda.SchedaBase;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;

/**
 * Scheda specifiche del pacchetto - @todo Manca la descrizione della classe.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 27-nov-2007 ore  15:28
 */
public final class IndirizzoViaScheda extends SchedaBase implements Indirizzo {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public IndirizzoViaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    @Override public void avvia(int codice) {
        /* variabili e costanti locali di lavoro */
        Campo campoNote;

        try { // prova ad eseguire il codice
            super.avvia(codice);

            campoNote = this.getCampo(Modello.NOME_CAMPO_NOTE);

            if (campoNote != null) {
                campoNote.setLarScheda(360);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.via.get());
            pan.add(Cam.indirizzo.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.citta.get());
            pan.add(Cam.cap.get());
            pagina.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.note.get());
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Metodo eseguito quando un campo perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    @Override
    protected void eventoUscitaCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String campoCitta;
        String campoCap;
        Modulo modCitta = null;
        int codCitta;
        String capValore;
        String capCitta;

        super.eventoUscitaCampo(campo);

        try { // prova ad eseguire il codice
            campoCitta = Indirizzo.Cam.citta.get();
            campoCap = Indirizzo.Cam.cap.get();

            continua = (campo.getNomeInterno().equals(campoCitta));

            if (continua) {
                modCitta = Progetto.getModulo(Citta.NOME_MODULO);
                continua = (modCitta != null);
            }// fine del blocco if

            if (continua) {
                capValore = (String)this.getCampo(campoCap).getValore();
                continua = Lib.Testo.isVuota(capValore);
            }// fine del blocco if

            if (continua) {
                codCitta = (Integer)this.getCampo(campoCitta).getValore();
                capCitta = modCitta.getEstratto(Citta.Est.cap, codCitta).getStringa();
                this.getCampo(campoCap).setValore(capCitta);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe