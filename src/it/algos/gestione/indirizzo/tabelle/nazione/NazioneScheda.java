package it.algos.gestione.indirizzo.tabelle.nazione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pref.Pref;
import it.algos.base.scheda.SchedaBase;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Presentazione grafica di un singolo record di Nazione.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su più pagine, risulterà
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma è attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-apr-2007 ore 20.59.40
 */
public class NazioneScheda extends SchedaBase implements Nazione {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo di riferimento per la scheda
     */
    public NazioneScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#add
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello panSopra;
        Pannello panMedio;
        Pannello panSotto;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col titolo */
            pagina = super.addPagina("generale");

            /* nazione (breve) + europa */
            panSopra = PannelloFactory.orizzontale(this);
            panSopra.add(Cam.nazione);
            panSopra.add(Cam.checkEuropa);
            pagina.add(panSopra);

            /* nazione (esteso) */
            pagina.add(Cam.nazioneCompleto);

            /* capitale */
            pagina.add(Cam.capitale);

            /* iso e internet */
            panMedio = PannelloFactory.orizzontale(this);
            panMedio.add(Cam.sigla2);
            panMedio.add(Cam.sigla3);
            panMedio.add(Cam.tld);
            pagina.add(panMedio);

            /* valuta */
            pagina.add(Cam.linkValuta);

            /* offset GMT e telefono */
            panSotto = PannelloFactory.orizzontale(this);
            panSotto.add(Cam.offsetGMT);
            panSotto.add(Cam.telefono);
            pagina.add(panSotto);

            /* festivo */
            pagina.add(Cam.festivo);

            /* regioni */
            if (Pref.Gen.livello.comboInt() > Pref.Livello.basso.ordinal() + 1) {
                pagina.add(Cam.subRegioni);
            }// fine del blocco if

            /* europa */

            /* note */
            pagina.add(ModelloAlgos.NOME_CAMPO_NOTE);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


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
    @Override
    public void avvia(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campoNav;
        Navigatore nav;
        Lista lista;
        TavolaModello modello;
        TableColumnModel modTavola;
        TableColumn colonna;
        String titolo;

        try { // prova ad eseguire il codice
            super.avvia(codice);

            campoNav = this.getCampo(Nazione.Cam.subRegioni);
            continua = (campoNav != null);

            if (continua) {
                titolo = (String)this.getValore(Nazione.Cam.divisioniUno.get());
                nav = campoNav.getNavigatore();

                lista = nav.getLista();

                modTavola = lista.getTavola().getColumnModel();
                colonna = modTavola.getColumn(0);
                colonna.setHeaderValue(titolo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe
