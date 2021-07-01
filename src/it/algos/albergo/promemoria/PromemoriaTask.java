package it.algos.albergo.promemoria;

import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class PromemoriaTask extends TimerTask {

    /**
     * modulo di riferimento
     */
    private Modulo modulo;

    /**
     * lista di avvisi attiva
     */
    private ArrayList<PromemoriaAvviso> avvisi;


    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento
     */
    public PromemoriaTask(Modulo modulo) {
        /* regolazioni iniziali di riferimenti e variabili */
        this.setModulo(modulo);

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
        /* selezione del modello (obbligatorio) */
        this.setAvvisi(new ArrayList<PromemoriaAvviso>());
    }


    public void run() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modulo;
        Filtro filtro;
        int[] codici;

        try { // prova ad eseguire il codice

            // per evitare che esegua prima che il programma sia completamente avviato
            if (Progetto.isPronto()) {

                modulo = this.getModulo();
                continua = (modulo != null);

                if (continua) {
                    filtro = this.getFiltro();
                    codici = modulo.query().valoriChiave(filtro);

                    if (codici != null && codici.length > 0) {
//                    System.out.println("Ok, sono le " + System.currentTimeMillis());
                        this.mostraAvvisi(codici);
                    } else {
//                    System.out.println("Non ce ne sono");
                    }// fine del blocco if-else
                }// fine del blocco if
                
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private void mostraAvvisi(int[] codici) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<PromemoriaAvviso> avvisi = null;
        PromemoriaAvviso dialogo;
        int posX = 40;
        int posY = 0;
        int deltaX = 10;
        int deltaY = 40;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod.isInizializzato());

            if (continua) {
                this.restAvvisi();
                avvisi = this.getAvvisi();
            }// fine del blocco if

            if (continua) {
                for (Integer cod : codici) {
                    posX += deltaX;
                    posY += deltaY;
                    dialogo = new PromemoriaAvviso(this.getModulo(), cod, posX, posY);
                    avvisi.add(dialogo);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void restAvvisi() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<PromemoriaAvviso> avvisi;

        try { // prova ad eseguire il codice
            avvisi = this.getAvvisi();
            continua = (avvisi != null && avvisi.size() > 0);

            if (continua) {
                for (PromemoriaAvviso dialogo : avvisi) {
                    dialogo.getDialogo().dispose();
                    dialogo = null;
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Filtro filtro = null;
        Filtro filtroTemp = null;
        Filtro filtroGiorno = null;
        Filtro filtroValide = null;
        Filtro filtroAttive = null;
        Filtro filtroPrecedenti = null;
        Filtro filtroOggi = null;
        Filtro filtroOre = null;
        Date oggi = AlbergoLib.getDataProgramma();
        Date vuota = Lib.Data.getVuota();

        int adesso = Lib.Data.getSecondiCorrenti();

        try { // prova ad eseguire il codice
            filtro = new Filtro();

            filtroValide = FiltroFactory.crea(
                    Promemoria.Cam.dataVisione.get(), Filtro.Op.DIVERSO, vuota);
            filtroAttive = FiltroFactory.creaFalso(Promemoria.Cam.eseguito);

            filtroPrecedenti = FiltroFactory.crea(
                    Promemoria.Cam.dataVisione.get(), Filtro.Op.MINORE, oggi);

            filtroOggi = new Filtro();
            filtroGiorno = FiltroFactory.crea(Promemoria.Cam.dataVisione.get(), oggi);
            filtroOre = FiltroFactory.crea(
                    Promemoria.Cam.oraVisione.get(), Filtro.Op.MINORE_UGUALE, adesso);
            filtroOggi.add(filtroGiorno);
            filtroOggi.add(filtroOre);

            filtroTemp = new Filtro();
            filtroTemp.add(filtroPrecedenti);
            filtroTemp.add(Filtro.Op.OR, filtroOggi);

            filtro.add(filtroValide);
            filtro.add(filtroAttive);
            filtro.add(filtroTemp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    private Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    private ArrayList<PromemoriaAvviso> getAvvisi() {
        return avvisi;
    }


    private void setAvvisi(ArrayList<PromemoriaAvviso> avvisi) {
        this.avvisi = avvisi;
    }
}
