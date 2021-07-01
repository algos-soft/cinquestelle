package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.base.azione.AzSpecifica;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.awt.event.ActionEvent;

public class PeriodoNavDialogo extends NavigatoreL implements Periodo {

    private Dialogo dialogoRif;

    private Azione azConferma;

    private Azione azAnnulla;

    private Azione azStorico;

    /* nome del campo contenente il flag "movimentato" (arrivato o partito) */
    private String campoFlagMovimentazione;

    /* testo con il termine appropriato per "movimentare"
    ("partire", "arrivare", "cambiare")*/
    private String testoMovimentare = "";


    /**
     * Costruttore completo con parametri.
     *
     * @param dialogo di riferimento
     */
    public PeriodoNavDialogo(Dialogo dialogo) {
        this(PeriodoModulo.get(), dialogo);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param dialogo di riferimento
     */
    public PeriodoNavDialogo(Modulo modulo, Dialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setDialogoRif(dialogo);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try {    // prova ad eseguire il codice

            this.setUsaFinestra(false);
            this.setUsaNuovo(false);
            this.setUsaElimina(false);
            this.setUsaSelezione(false);
            this.setUsaRicerca(false);
            this.setUsaStampaLista(true);
            this.setRigheLista(10);
            this.getLista().setUsaFiltriPop(false);

            azione = this.setAzConferma(new AzConferma());
            this.addAzione(azione);
            azione = this.setAzAnnulla(new AzAnnulla());
            this.addAzione(azione);
            azione = this.setAzStorico(new AzStorico());
            this.addAzione(azione);



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    public void inizializza() {

        try { // prova ad eseguire il codice

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Solo se il dialogo è visibile
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Azione azione;
        Lista lista;
        int quante;

        try { // prova ad eseguire il codice

            continua = this.getDialogoRif().getDialogo().isVisible();

            if (continua) {

                /* sincronizza nella superclasse */
                super.sincronizza();

                azione = this.getAzConferma();
                if (azione != null) {
                    azione.setEnabled(this.isConfermabile());
                }// fine del blocco if

                azione = this.getAzAnnulla();
                if (azione != null) {
                    azione.setEnabled(this.isAnnullabile());
                }// fine del blocco if

                azione = this.getAzStorico();
                if (azione != null) {
                    lista = this.getLista();                    
                    quante = lista.getQuanteRigheSelezionate();
                    azione.setEnabled(quante == 1);
                }// fine del blocco if

                /* calcola e assegna il totale persone effettivo */
                this.updateTotalePersone();

                /* sincronizza la label non movimentati */
                this.syncLabelNonMovimentati();

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna true se l'azione Vai alle Presenze è abilitabile.
     * <p/>
     * @return true se abilitabile
     */
    protected boolean isVaiPresenzeAbilitabile () {
        /* variabili e costanti locali di lavoro */
        boolean abilitabile  = false;
        int chiave;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            chiave = this.getLista().getChiaveSelezionata();
            if (chiave>0) {
                modPeriodo = PeriodoModulo.get();
                abilitabile = modPeriodo.query().valoreBool(Periodo.Cam.arrivato.get(), chiave);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return abilitabile;
    }





    /**
     * Calcola e assegna il totale persone effettivo.
     * <p/>
     */
    private void updateTotalePersone() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        Filtro filtro;
        int[] periodi;
        int quanti = 0;
        Campo campo;

        try {    // prova ad eseguire il codice
            lista = this.getLista();
            filtro = lista.getFiltro();
            periodi = this.query().valoriChiave(filtro);
            for (int cod : periodi) {
                quanti += this.getPersonePeriodo(cod);
            }
            campo = PeriodoModulo.get().getCampo(Periodo.Cam.persone);

            /**
             * todo disabilitato alex assieme ai renderers 21-06-08
             * */
//            lista.setTotale(campo, quanti);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il numero di persone relative a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     *
     * @return il numero di persone relative al periodo
     */
    protected int getPersonePeriodo(int codPeriodo) {
        return 0;
    }


    /**
     * sincronizza la label non movimentati.
     * <p/>
     */
    private void syncLabelNonMovimentati() {
        /* variabili e costanti locali di lavoro */
        int quantePersone;
        int quanteCamere;
        String testo = "";

        try {    // prova ad eseguire il codice
            quantePersone = this.getQuantePersoneNonMovimentate();
            quanteCamere = this.getQuanteCamereNonMovimentate();
            if (quantePersone > 0) {
                testo = "Devono ancora ";
                testo += this.getTestoMovimentare();
                testo += ": ";
                testo += quantePersone + " persone in ";
                testo += quanteCamere + " camere";
            }// fine del blocco if

            this.getLista().setCustom(testo);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il numero di persone non ancora movimentate.
     * <p/>
     *
     * @return il numero di persone non ancora movimentate
     */
    protected int getQuantePersoneNonMovimentate() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Filtro filtro;
        int[] periodi;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = PeriodoModulo.get();
            filtro = this.getFiltroPeriodiNonMovimentati();
            periodi = mod.query().valoriChiave(filtro);
            for (int codPeriodo : periodi) {
                quanti += this.getPersonePeriodo(codPeriodo);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna il numero di camere non ancora movimentate.
     * <p/>
     *
     * @return il numero di camere non ancora movimentate
     */
    protected int getQuanteCamereNonMovimentate() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Number numero;
        Modulo modulo;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modulo = PeriodoModulo.get();
            filtro = this.getFiltroPeriodiNonMovimentati();
            numero = modulo.query().contaRecords(filtro);
            quanti = Libreria.getInt(numero);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Ritorna un filtro che isola i periodi visualizzati e non ancora arrivati.
     * <p/>
     *
     * @return il filtro
     */
    protected Filtro getFiltroPeriodiNonMovimentati() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Lista lista;
        Filtro filtro = null;
        Filtro filtroLista;
        Filtro filtroFlag;
        String nomeCampoFlag = "";

        try {    // prova ad eseguire il codice

            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                nomeCampoFlag = this.getCampoFlagMovimentazione();
                continua = (Lib.Testo.isValida(nomeCampoFlag));
            }// fine del blocco if

            if (continua) {
                filtroLista = lista.getFiltro();
                filtroFlag = FiltroFactory.crea(nomeCampoFlag, false);
                filtro = new Filtro();
                filtro.add(filtroLista);
                filtro.add(filtroFlag);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Controlla l'abilitazione del pulsante conferma.
     * <p/>
     *
     * @return confermabile
     */
    protected boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;
        boolean continua;
        Lista lista;
        int quante;

        try { // prova ad eseguire il codice

            /* abilitazione del bottone conferma */
            lista = this.getLista();
            continua = lista != null;

            if (continua) {
                quante = lista.getQuanteRigheSelezionate();
                confermabile = (quante == 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Controlla l'abilitazione del pulsante annulla.
     * <p/>
     *
     * @return annullabile
     */
    protected boolean isAnnullabile() {
        /* variabili e costanti locali di lavoro */
        boolean annullabile = false;
        boolean continua;
        Lista lista;
        int quante;

        try { // prova ad eseguire il codice

            /* abilitazione del bottone conferma */
            lista = this.getLista();
            continua = lista != null;

            if (continua) {
                quante = lista.getQuanteRigheSelezionate();
                annullabile = (quante == 1);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return annullabile;
    }


    protected Dialogo getDialogoRif() {
        return dialogoRif;
    }


    private void setDialogoRif(Dialogo dialogoRif) {
        this.dialogoRif = dialogoRif;
    }


    /**
     * Conferma il movimento selezionato.
     * <p/>
     */
    private void conferma() {
        /* variabili e costanti locali di lavoro */
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getLista().getChiaveSelezionata();
            this.conferma(codice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Annulla il movimento selezionato.
     * <p/>
     */
    private void annulla() {
        /* variabili e costanti locali di lavoro */
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getLista().getChiaveSelezionata();
            this.annulla(codice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Doppio clic sulla riga.
     * <p/>
     */
    protected boolean modificaRecord() {
        
        this.vaiPrenotazione();

//        //doppio clic equivale a Conferma - alex 19-07-2010
//        this.conferma();

        /* valore di ritorno */
        return true;
    }


    /**
     * Conferma il movimento selezionato.
     * <p/>
     *
     * @param codPeriodo codice del periodo selezionato da confermare
     */
    protected void conferma(int codPeriodo) {
    }


    /**
     * Annulla il movimento selezionato.
     * <p/>
     *
     * @param codPeriodo codice del periodo selezionato da annullare
     */
    protected void annulla(int codPeriodo) {
    }


    /**
     * Apre la scheda della prenotazione relativa alla
     * riga correntemente selezionata nella lista.
     * <p/>
     */
    private void vaiPrenotazione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codPrenotazione = 0;
        Modulo modPeriodo;
        Modulo modPrenotazione = null;

        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();
            continua = (modPeriodo != null);

            if (continua) {
                codPrenotazione = this.getCodPrenSelezionata();
                continua = (codPrenotazione > 0);
            }// fine del blocco if

            if (continua) {
                modPrenotazione = PrenotazioneModulo.get();
                continua = (modPrenotazione != null);
            }// fine del blocco if

            if (continua) {
                modPrenotazione.presentaRecord(codPrenotazione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Apre lo storico del cliente relativo alla
     * riga correntemente selezionata nella lista.
     * <p/>
     */
    private void vaiStorico() {
        ClienteAlbergoModulo.showStorico(this.getCodClienteSelezionato());
    }



    /**
     * Ritorna il codice della prenotazione corrispondente
     * alla riga correntemente selezionata nella lista.
     * <p/>
     *
     * @return il codice della prenotazione
     */
    protected int getCodPrenSelezionata() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codPeriodo;
        int codPrenotazione = 0;
        Modulo modPeriodo;

        try {    // prova ad eseguire il codice
            codPeriodo = this.getLista().getChiaveSelezionata();
            continua = (codPeriodo > 0);

            if (continua) {
                modPeriodo = PeriodoModulo.get();
                codPrenotazione = modPeriodo.query().valoreInt(Periodo.Cam.prenotazione.get(),
                        codPeriodo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
    }


    /**
     * Ritorna il codice del cliente corrispondente
     * alla riga correntemente selezionata nella lista.
     * <p/>
     *
     * @return il codice del cliente selezionato
     */
    protected int getCodClienteSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        int codPrenotazione;
        boolean continua;
        Modulo modPrenotazione = PrenotazioneModulo.get();

        try {    // prova ad eseguire il codice

            codPrenotazione = this.getCodPrenSelezionata();
            continua = (codPrenotazione > 0);

            if (continua) {
                codCliente = modPrenotazione.query().valoreInt(Prenotazione.Cam.cliente.get(),
                        codPrenotazione);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCliente;
    }




    private Azione getAzConferma() {
        return azConferma;
    }


    private Azione setAzConferma(Azione azConferma) {
        this.azConferma = azConferma;
        return this.getAzConferma();
    }


    private Azione getAzAnnulla() {
        return azAnnulla;
    }


    private Azione setAzAnnulla(Azione azAnnulla) {
        this.azAnnulla = azAnnulla;
        return this.getAzAnnulla();
    }


    private Azione getAzStorico() {
        return azStorico;
    }


    private Azione setAzStorico(Azione azStorico) {
        this.azStorico = azStorico;
        return this.getAzStorico();
    }


    private String getCampoFlagMovimentazione() {
        return campoFlagMovimentazione;
    }


    protected void setCampoFlagMovimentazione(String nome) {
        this.campoFlagMovimentazione = nome;
    }


    private String getTestoMovimentare() {
        return testoMovimentare;
    }


    protected void setTestoMovimentare(String testoMovimentare) {
        this.testoMovimentare = testoMovimentare;
    }


    /**
     * Azione conferma il movimento selezionato.
     * </p>
     */
    private final class AzConferma extends AzSpecifica {

        /**
         * Costruttore senza parametri.
         */
        public AzConferma() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setChiave("conferma");
            super.setIconaMedia("Conferma24");
            super.setTooltip("conferma il movimento selezionato");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                conferma();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'


    /**
     * Azione annulla il movimento selezionato.
     * </p>
     */
    private final class AzAnnulla extends AzSpecifica {

        /**
         * Costruttore senza parametri.
         */
        public AzAnnulla() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setChiave("annulla");
            super.setIconaMedia("chiudischeda24");
            super.setTooltip("annulla il movimento selezionato");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                annulla();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'



    /*
    * Azione Apri Storico Cliente.
    * </p>
    */
    private final class AzStorico extends AzSpecifica {

        /**
         * Costruttore
         * <p/>
         */
        public AzStorico() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setChiave("vaistorico");
            super.setIconaMedia("clock24");
            super.setTooltip("apre lo storico del cliente");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                vaiStorico();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'



}
