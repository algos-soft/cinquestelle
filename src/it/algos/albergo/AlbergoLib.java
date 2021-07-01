package it.algos.albergo;

import it.algos.albergo.arrivipartenze.ArriviPartenze;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.promemoria.Promemoria;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.rubrica.RubricaAlbergo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.scheda.Scheda;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.SetValori;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe statica di libreria - @todo Manca la descrizione della classe..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 27-feb-2008 ore  11:13
 */
public final class AlbergoLib {


    /**
     * Ordine di presentazione dei mebri di un gruppo.
     * <p/>
     * Prima il capogruppo, poi gli altri in ordine di parentela <br>
     *
     * @return ordine
     */
    public static Ordine getOrdineGruppo() {
        /* variabili e costanti locali di lavoro */
        Ordine ordine = null;
        boolean continua;
        Modulo modCliente;
        Modulo modParente = null;
        Campo campoPref = null;
        Campo campoOrd = null;

        try { // prova ad eseguire il codice
            modCliente = ClienteAlbergoModulo.get();
            continua = (modCliente != null);

            if (continua) {
                modParente = ParentelaModulo.get();
                continua = (modParente != null);
            }// fine del blocco if

            if (continua) {
                campoPref = modCliente.getCampoPreferito();
                continua = (campoPref != null);
            }// fine del blocco if

            if (continua) {
                campoOrd = modParente.getCampoOrdine();
                continua = (campoOrd != null);
            }// fine del blocco if

            if (continua) {
                ordine = new Ordine();
                ordine.add(campoPref, Operatore.DISCENDENTE);
                ordine.add(campoOrd);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordine;
    }


    /**
     * Aggancia due periodi con cambio.
     * <p/>
     * Aggancia la fine del primo periodo all'inizio del secondo periodo.
     * Da chiamare sempre sotto transazione.
     * <p/>
     * - La data di fine del primo periodo deve essere uguale alla
     * data di inizio del secondo periodo
     * - La causale di fine del primo periodo non deve essere cambio
     * - La causale di inizio del secondo periodo non deve essere cambio
     * - Il riferimento alla prenotazione deve essere lo stesso per entrambi i periodi
     *
     * @param periodo1 codice del primo periodo
     * @param periodo2 codice del secondo periodo
     * @param conn     la connessione da utilizzare
     *
     * @return stringa vuota se riuscito, motivazione se fallito
     */
    public static String agganciaPeriodi(int periodo1, int periodo2, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua = true;
        boolean riuscito;
        Modulo modPeriodo;
        Dati dati;
        Filtro filtro;
        Query query;
        int codCausaleCambio = Periodo.CausaleAP.cambio.getCodice();

        Date dataP1, dataP2;
        int codCausale1, codCausale2;
        int codPren1, codPren2;

        SetValori sv;
        ArrayList<CampoValore> listaCV;

        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();

            /* recupera i dati dal primo periodo */
            query = new QuerySelezione(modPeriodo);
            query.addCampo(Periodo.Cam.partenzaPrevista.get());
            query.addCampo(Periodo.Cam.causalePartenza.get());
            query.addCampo(Periodo.Cam.prenotazione.get());
            filtro = FiltroFactory.codice(modPeriodo, periodo1);
            query.setFiltro(filtro);
            dati = modPeriodo.query().querySelezione(query, conn);
            dataP1 = dati.getDataAt(Periodo.Cam.partenzaPrevista.get());
            codCausale1 = dati.getIntAt(Periodo.Cam.causalePartenza.get());
            codPren1 = dati.getIntAt(Periodo.Cam.prenotazione.get());
            dati.close();

            /* recupera i dati dal secondo periodo */
            query = new QuerySelezione(modPeriodo);
            query.addCampo(Periodo.Cam.arrivoPrevisto.get());
            query.addCampo(Periodo.Cam.causaleArrivo.get());
            query.addCampo(Periodo.Cam.prenotazione.get());
            filtro = FiltroFactory.codice(modPeriodo, periodo2);
            query.setFiltro(filtro);
            dati = modPeriodo.query().querySelezione(query, conn);
            dataP2 = dati.getDataAt(Periodo.Cam.arrivoPrevisto.get());
            codCausale2 = dati.getIntAt(Periodo.Cam.causaleArrivo.get());
            codPren2 = dati.getIntAt(Periodo.Cam.prenotazione.get());
            dati.close();

            /* controllo congruità date */
            if (!dataP1.equals(dataP2)) {
                continua = false;
                testo += "- La fine del primo periodo non coincide con l'inizio del secondo.\n";
            }// fine del blocco if

            /* controllo causale fine primo periodo */
            if (continua) {
                if (codCausale1 == codCausaleCambio) {
                    continua = false;
                    testo += "- Il primo periodo termina già con un cambio.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* controllo causale inizio secondo periodo */
            if (continua) {
                if (codCausale2 == codCausaleCambio) {
                    continua = false;
                    testo += "- Il secondo periodo inizia già con un cambio.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* controllo pari prenotazione */
            if (continua) {
                if (codPren1 != codPren2) {
                    continua = false;
                    testo += "- I due periodi appartengono a prenotazioni diverse.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* esecuzione modifica primo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.causalePartenza, codCausaleCambio);
                sv.add(Periodo.Cam.linkDestinazione, periodo2);
                listaCV = sv.getListaValori();
                riuscito = modPeriodo.query().registraRecordValori(periodo1, listaCV, conn);
                if (!riuscito) {
                    continua = false;
                    testo += "- Errore nella modifica del primo periodo.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* esecuzione modifica secondo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.causaleArrivo, codCausaleCambio);
                sv.add(Periodo.Cam.linkProvenienza, periodo1);
                listaCV = sv.getListaValori();
                riuscito = modPeriodo.query().registraRecordValori(periodo2, listaCV, conn);
                if (!riuscito) {
                    continua = false;
                    testo += "- Errore nella modifica del secondo periodo.\n";
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Sgancia due periodi agganciati da un cambio.
     * <p/>
     * Sgancia la fine del primo periodo dall'inizio del secondo periodo.
     * Da chiamare sempre sotto transazione.
     * <p/>
     * - Il riferimento alla prenotazione deve essere lo stesso per entrambi i periodi
     *
     * @param periodo1 codice del primo periodo
     * @param periodo2 codice del secondo periodo
     * @param conn     la connessione da utilizzare
     *
     * @return stringa vuota se riuscito, motivazione se fallito
     */
    public static String sganciaPeriodi(int periodo1, int periodo2, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua = true;
        Modulo modPeriodo;
        Dati dati;
        Filtro filtro;
        Query query;

        int codPren1, codPren2;

        int codCausaleNormale = Periodo.CausaleAP.normale.getCodice();
        boolean riuscito;
        SetValori sv;
        ArrayList<CampoValore> listaCV;


        try { // prova ad eseguire il codice

            modPeriodo = PeriodoModulo.get();

            /* recupera i dati dal primo periodo */
            query = new QuerySelezione(modPeriodo);
            query.addCampo(Periodo.Cam.prenotazione.get());
            filtro = FiltroFactory.codice(modPeriodo, periodo1);
            query.setFiltro(filtro);
            dati = modPeriodo.query().querySelezione(query, conn);
            codPren1 = dati.getIntAt(Periodo.Cam.prenotazione.get());
            dati.close();

            /* recupera i dati dal secondo periodo */
            query = new QuerySelezione(modPeriodo);
            query.addCampo(Periodo.Cam.prenotazione.get());
            filtro = FiltroFactory.codice(modPeriodo, periodo2);
            query.setFiltro(filtro);
            dati = modPeriodo.query().querySelezione(query, conn);
            codPren2 = dati.getIntAt(Periodo.Cam.prenotazione.get());
            dati.close();

            /* controllo pari prenotazione */
            if (continua) {
                if (codPren1 != codPren2) {
                    continua = false;
                    testo += "- I due periodi appartengono a prenotazioni diverse.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* esecuzione modifica primo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.causalePartenza, codCausaleNormale);
                sv.add(Periodo.Cam.linkDestinazione, 0);
                listaCV = sv.getListaValori();
                riuscito = modPeriodo.query().registraRecordValori(periodo1, listaCV, conn);
                if (!riuscito) {
                    continua = false;
                    testo += "- Errore nella modifica del primo periodo.\n";
                }// fine del blocco if
            }// fine del blocco if

            /* esecuzione modifica secondo periodo */
            if (continua) {
                sv = new SetValori(modPeriodo);
                sv.add(Periodo.Cam.causaleArrivo, codCausaleNormale);
                sv.add(Periodo.Cam.linkProvenienza, 0);
                listaCV = sv.getListaValori();
                riuscito = modPeriodo.query().registraRecordValori(periodo2, listaCV, conn);
                if (!riuscito) {
                    continua = false;
                    testo += "- Errore nella modifica del secondo periodo.\n";
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;


    }


    /**
     * Aggiunge i moduli standard di albergo al menu.
     * <br>
     * Aggiunge alla collezione moduli (del modulo selezionato),
     * i moduli, che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     */
    public static void addModuliVisibili(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String nomeModulo = "";
        String nomeMod;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (modulo != null);

            if (continua) {
                nomeModulo = modulo.getNomeModulo();
                continua = Lib.Testo.isValida(nomeModulo);
            }// fine del blocco if

            if (continua) {
                for (ModuliVisibiliAlbergo mod : ModuliVisibiliAlbergo.values()) {
                    nomeMod = mod.getNomeInterno();
                    if (!nomeMod.equals(nomeModulo)) {
                        /* aggiunge alla collezione specifica del modulo */
                        modulo.getModuli().add(nomeMod);
                    }// fine del blocco if
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo addModuliVisibili



    /**
     * Aggiunge il bottone Informazioni Storiche a una scheda.
     * <br>
     * @param scheda la scheda alla quale aggiungere il bottone
     * @return il bottone aggiunto
     */
    public static JButton addBotInfoScheda(Scheda scheda) {
        /* variabili e costanti locali di lavoro */
        JButton bot=null;
        ToolBar tb;

        try { // prova ad eseguire il codice

            tb = scheda.getPortale().getToolBar();
            bot = AlbergoLib.creaBotStorico();
            tb.getToolBar().add(Box.createHorizontalGlue());
            tb.getToolBar().add(bot);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


    /**
     * Ritorna un bottone per l'accesso allo Storico Cliente.
     * <br>
     * @return il bottone
     */
    public static JButton creaBotStorico() {
        /* variabili e costanti locali di lavoro */
        JButton bot=null;
        Icon icona;

        try { // prova ad eseguire il codice

            icona = Lib.Risorse.getIconaBase("clock24");

            bot = new JButton(icona);
            bot.setOpaque(false);
            bot.setBorderPainted(false);
            bot.setContentAreaFilled(false); // serve per bug su XP
            bot.setToolTipText("Apre lo storico del cliente");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


    /**
     * Ritorna la data corrente del programma.
     * <p/>
     * Può differire dalla data corrente del sistema.
     *
     * @return la data corrente del programma
     */
    public static Date getDataProgramma () {
        /* variabili e costanti locali di lavoro */
        Date data=null;

        AlbergoModulo mod = AlbergoModulo.get();
        if (mod!=null) {
            data=mod.getDataProgramma();
        }// fine del blocco if

        /* valore di ritorno */
        return data;
    }




    /**
     * Classe interna Enumerazione.
     */
    public enum ModuliVisibiliAlbergo {

        clienti(ClienteAlbergo.NOME_MODULO),
        prenotazioni(Prenotazione.NOME_MODULO),
        conti(Conto.NOME_MODULO),
        ristorante(Menu.NOME_MODULO),
        arrivi(ArriviPartenze.NOME_MODULO),
        presenze(Presenza.NOME_MODULO),
//        booking(Booking.NOME_MODULO), @todo booking o planning, manca il modulo
        promemoria(Promemoria.NOME_MODULO),
        rubrica(RubricaAlbergo.NOME_MODULO)
        ;


        /**
         * nome interno del modulo.
         */
        private String nomeInterno;


        /**
         * Costruttore completo con parametri.
         *
         * @param nomeInterno del modulo
         */
        ModuliVisibiliAlbergo(String nomeInterno) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNomeInterno(nomeInterno);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        public String getNomeInterno() {
            return nomeInterno;
        }


        public void setNomeInterno(String nomeInterno) {
            this.nomeInterno = nomeInterno;
        }
    }// fine della Enumeration Pop


}// fine della classe