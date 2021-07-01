package it.algos.albergo.presenza;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaDefault;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Scheda specifica di una Presenza
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class PresenzaScheda extends SchedaDefault implements Presenza {


    /**
     * Bottone per aprire lo storico del cliente
     */
    private JButton botStorico;

    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public PresenzaScheda(Modulo modulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo costoGiornaliero;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    @Override
    public void inizializza() {
        super.inizializza();

        /* aggiunge il bottone Storico alla scheda */
        JButton bot = AlbergoLib.addBotInfoScheda(this);
        bot.addActionListener(new AzInfoStorico());
        this.setBotStorico(bot);

    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello pan;

        try { // prova ad eseguire il codice
            /* crea la pagina Generale */
            pag = super.addPagina("generale");

            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.add(Cam.entrata);
            pan.add(Cam.cambioEntrata);
            pan.add(Cam.uscita);
            pan.add(Cam.cambioUscita);
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.camera);
            pan.add(Cam.cliente);
            pan.add(Cam.conto);            
            if (AlbergoModulo.isRistorante()) {
                pan.add(Cam.pasto);
            }// fine del blocco if
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.pensione);
            pan.add(Cam.arrivoCon);
            pan.add(Cam.tavolo);
            pan.add(Cam.azienda);
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.add(Cam.arrivo);
            pan.add(Cam.chiuso);
            pan.add(Cam.provvisoria);
            pan.add(Cam.bambino);            
            pag.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        int codPens;
        boolean mezzaPens;

        try { // prova ad eseguire il codice

            /* il campo pasto è visibile solo se a mezza pensione */
            codPens = this.getInt(Cam.pensione.get());
            mezzaPens = (codPens == Listino.PensioniPeriodo.mezzaPensione.getCodice());
            campo = this.getCampo(Cam.pasto);
            if (campo != null) {
                campo.setVisibile(mezzaPens);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.sincronizza();    //To change body of overridden methods use File | Settings | File Templates.
    }


    private JButton getBotStorico() {
        return botStorico;
    }


    private void setBotStorico(JButton botStorico) {
        this.botStorico = botStorico;
    }

    /**
     * Azione Informazioni Storiche
     * </p>
     */
    private final class AzInfoStorico implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /* variabili e costanti locali di lavoro */
            int codice;

            try { // prova ad eseguire il codice
                codice = getInt(Presenza.Cam.cliente.get());
                if (codice>0) {
                    ClienteAlbergoModulo.showStorico(codice);
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'



}// fine della classe