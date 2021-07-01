package it.algos.albergo.statistiche;

import it.algos.albergo.conto.ContoDialogoRiepilogo;
import it.algos.albergo.ristorante.menu.DialogoStatistiche;
import it.algos.albergo.sviluppopresenze.SviluppoDialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialogo di selezione del tipo di statistica
 * <p>
 *
 */
public class StatDialogo extends DialogoBase {

    /**
     * Costruttore completo senza parametri.
     */
    public StatDialogo() {
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
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        String testo;
        String spiega;
        StatElemento elem;

        try { // prova ad eseguire il codice

            this.setTitolo("Menu Statistiche");
            this.getDialogo().setModal(false);

            testo = "Analisi ristorante";
            spiega = "Statistiche sui piatti proposti e ordinati, i coperti serviti, il gradimento dei vari piatti";
            elem = new StatElemento(this,testo,spiega,new AzRistorante());
            this.addComponente(elem);

            testo = "Situazione economica";
            spiega = "Riepilogo dei ricavi suddivisi per conto economico, incassi da clienti, credito clienti e sospesi";
            elem = new StatElemento(this,testo,spiega,new AzConti());
            this.addComponente(elem);

            testo = "Analisi prenotazioni";
            spiega = "Sviluppo delle presenze in base alle prenotazioni, preventivo incassi";
            elem = new StatElemento(this,testo,spiega,new AzPrenotazioni());
            this.addComponente(elem);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Azione del bottone</p>
     */
    public final class AzRistorante implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            new DialogoStatistiche().avvia();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone</p>
     */
    public final class AzConti implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            new ContoDialogoRiepilogo().avvia();
        }
    } // fine della classe 'interna'


    /**
     * Azione del bottone</p>
     */
    public final class AzPrenotazioni implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            new SviluppoDialogo().avvia();
        }
    } // fine della classe 'interna'


}
