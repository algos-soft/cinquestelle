/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 8 mag 2006
 */

package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di AddebitoFisso.
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
 * @version 1.0 / 8 mag 2006
 */
public final class AddebitoFissoScheda extends SchedaBase {

    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public AddebitoFissoScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;
        Pannello panConto;
        Pannello panSott;
        Pannello panQP;
        Pannello panAdd;
        Pannello panDate;
        Pannello panFlag;
        Pannello panPer;

        try { // prova ad eseguire il codice

            panConto = PannelloFactory.orizzontale(this);
            panConto.creaBordo("conto");
            panConto.add(Addebito.Cam.data.get());
            panConto.add(Addebito.Cam.conto);

            panSott = PannelloFactory.orizzontale(this);
            panSott.add(Addebito.Cam.listino.get());

            panQP = PannelloFactory.orizzontale(this);
            panQP.add(Addebito.Cam.quantita.get());
            panQP.add(Addebito.Cam.prezzo.get());
            panQP.add(Addebito.Cam.importo.get());

            panAdd = PannelloFactory.verticale(this);
            panAdd.creaBordo("addebito");
            panAdd.add(panSott);
            panAdd.add(panQP);

            panDate = PannelloFactory.orizzontale(this);
            panDate.add(AddebitoFisso.Cam.dataInizioValidita);
            panDate.add(AddebitoFisso.Cam.dataFineValidita);

            panFlag = PannelloFactory.orizzontale(this);
            panFlag.add(AddebitoFisso.Cam.dataSincro);

            panPer = PannelloFactory.verticale(this);
            panPer.creaBordo("periodo");
            panPer.add(panDate);
            panPer.add(panFlag);

            pag = this.addPagina("generale");
            pag.add(panConto);
            pag.add(panAdd);
            pag.add(panPer);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
