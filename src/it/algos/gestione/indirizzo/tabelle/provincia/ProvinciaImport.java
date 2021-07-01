package it.algos.gestione.indirizzo.tabelle.provincia;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.CinqueStringhe;
import it.algos.base.wrapper.DueStringhe;
import it.algos.base.wrapper.FtpWrap;
import it.algos.base.wrapper.QuattroStringhe;
import it.algos.base.wrapper.SeiStringhe;
import it.algos.base.wrapper.SetteStringhe;
import it.algos.base.wrapper.TreStringhe;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import javax.swing.JSeparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Dialogo di importazione delle suddivisioni amministrative di alcune nazioni.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-apr-2007 ore 19.59.27
 */
public final class ProvinciaImport extends DialogoImport {

    private ArrayList<TreStringhe> nazioni;


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param mostraDialogo flag per usare o meno il dialogo
     */
    public ProvinciaImport(Modulo modulo, boolean mostraDialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setMostraDialogo(mostraDialogo);

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
        try { // prova ad eseguire il codice
            if (this.isMostraDialogo()) {
                this.creaDialogo();
            } else {
                super.creaCondizionato();
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {
        try { // prova ad eseguire il codice
            this.creaDialogo("Suddivisioni nazionali");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico:
     * nella toolbar della lista o nel menu Archivio <br>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mess specifico
     */
    protected void creaDialogo(String mess) {
        /* variabili e costanti locali di lavoro */
        Campo campoScelta;
        int scelta;
        FtpWrap ftp;

        try { // prova ad eseguire il codice
            this.setGapMassimo(5);

            /* costruisce e memorizza la connessione da usare diverse volte */
            this.setFtp(Lib.Web.getFtp());

            /* crea la lista base delle province */
            this.addProvince();

            /* aggiunge il nome della pagina delle regioni (se disponibile) */
            this.addRegioni();

            ftp = this.getFtp();
            if (ftp != null) {
                Lib.Web.chiudeFtp(ftp);
            }// fine del blocco if

            /* aggiunge una mappa di checkbox per selezionare le province disponibili */
            this.addCheckProvince();

            /* Regolazione iniziale del dialogo, senza avviarlo */
            campoScelta = super.regolaDialogo(mess);

            super.avvia();

            /* esegue l'operazione */
            if (super.isConfermato()) {
                scelta = (Integer)campoScelta.getValore();
                super.setScelta(Scelta.getScelta(scelta));

                this.leggeSelezioneProvince();

                super.crea();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * crea la lista base delle province.
     * <p/>
     * Le province sono quelle disponibili alla directory geo/secondo livello <br>
     */
    private void addProvince() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        FtpWrap ftp;
        String directory = "/algosData/geo/secondo livello/";
        String provincia;
        String nome;
        String tagIni = "(";
        String tagEnd = ")";
        ArrayList<TreStringhe> nazioni = null;
        ArrayList<String> listaFiles;
        TreStringhe naz;

        try { // prova ad eseguire il codice
            ftp = this.getFtp();

            /* elenco dei files nella directory */
            listaFiles = Lib.Web.getNomiFiles(directory, ftp);
            continua = (listaFiles != null && listaFiles.size() > 0);

            /* costruisce la lista col nome della nazione ed il titolo specifico della suddivisione */
            if (continua) {
                nazioni = new ArrayList<TreStringhe>();
                for (String titolo : listaFiles) {
                    nome = Lib.Testo.prima(titolo, tagIni);
                    nome = Lib.Testo.primaMaiuscola(nome);

                    provincia = Lib.Testo.dopo(titolo, tagIni);
                    provincia = Lib.Testo.levaCoda(provincia, tagEnd);

                    naz = new TreStringhe(nome, "", provincia);
                    nazioni.add(naz);
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                this.setNazioni(nazioni);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * aggiunge il nome della pagina delle regioni (se disponibile).
     * <p/>
     * Le regioni sono quelle disponibili alla directory geo/primo livello <br>
     * Se la nazione esiste già, aggiungo il nome delle regioni <br>
     * Se non esisteva, la creo <br>
     * <p/>
     * 1-nome nazione
     * 2-suddivisione di primo livello
     * 3-suddivisione di secondo livello
     */
    private void addRegioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        FtpWrap ftp;
        String directory = "/algosData/geo/primo livello/";
        String nome;
        ArrayList<TreStringhe> nazioniReg = null;
        ArrayList<TreStringhe> nazioni = null;
        String tagIni = "(";
        String tagEnd = ")";
        TreStringhe naz;
        String regione;
        String nomePro;
        String nomeReg;
        String nomeFin;
        ArrayList<String> listaFiles;

        try { // prova ad eseguire il codice
            ftp = this.getFtp();

            /* elenco dei files nella directory */
            listaFiles = Lib.Web.getNomiFiles(directory, ftp);
            continua = (listaFiles != null && listaFiles.size() > 0);

            /* costruisce la lista col nome della nazione ed il titolo specifico della suddivisione */
            if (continua) {
                nazioniReg = new ArrayList<TreStringhe>();
                for (String titolo : listaFiles) {
                    nome = Lib.Testo.prima(titolo, tagIni);
                    nome = Lib.Testo.primaMaiuscola(nome);

                    regione = Lib.Testo.dopo(titolo, tagIni);
                    regione = Lib.Testo.levaCoda(regione, tagEnd);

                    naz = new TreStringhe(nome, regione, "");
                    nazioniReg.add(naz);
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                /* Se la nazione esiste già, aggiungo il nome della regione */
                nazioni = this.getNazioni();
                for (TreStringhe nazione : nazioni) {
                    nomePro = nazione.getPrima();
                    for (TreStringhe nazReg : nazioniReg) {
                        nomeReg = nazReg.getPrima();
                        if (nomeReg.equals(nomePro)) {
                            nazione.setSeconda(nazReg.getSeconda());
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                } // fine del ciclo for-each

                /* Se non esisteva, la creo */
                for (TreStringhe nazReg : nazioniReg) {
                    nomeReg = nazReg.getPrima();
                    boolean esiste = false;
                    for (TreStringhe nazFinale : nazioni) {
                        nomeFin = nazFinale.getPrima();
                        if (nomeFin.equals(nomeReg)) {
                            esiste = true;
                            break;
                        }// fine del blocco if
                    } // fine del ciclo for-each
                    if (!esiste) {
                        nazioni.add(nazReg);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if


            if (continua) {
                this.setNazioni(nazioni);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione di un popup con le province disponibili per l'importazione.
     * <p/>
     */
    private void addCheckProvince() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String tagIta = "Italia";
        String nome;
        Campo campo;
        ArrayList<TreStringhe> nazioni;
        int lar = 120;

        try { // prova ad eseguire il codice
            nazioni = this.getNazioni();
            continua = (nazioni != null && nazioni.size() > 0);

            if (continua) {
                for (TreStringhe naz : nazioni) {
                    nome = naz.getPrima();
                    nome = Lib.Testo.primaMaiuscola(nome);

                    campo = CampoFactory.checkBox(nome);
                    campo.setLarScheda(lar);
                    campo.decora().eliminaEtichetta();

                    if (nome.equals(tagIta)) {
                        campo.setValore(true);
                    } else {
                        campo.setValore(false);
                    }// fine del blocco if-else

                    campo.setTestoComponente(nome);
                    campo.decora().legendaDestra(this.getLegenda(naz));
                    this.addCampo(campo);
                } // fine del ciclo for-each

                super.getPrimaPagina().aggiungeComponenti(new JSeparator());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Legenda informativa sui nomi delle suddivisioni amministrative per la nazione.
     * <p/>
     */
    private String getLegenda(TreStringhe naz) {
        /* variabili e costanti locali di lavoro */
        String legenda = "";
        boolean continua;
        String nomePaese;
        String nomeRegione = "";
        String nomeProvincia = "";
        String and = " e ";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (naz != null);

            if (continua) {
                nomePaese = naz.getPrima();
                continua = (Lib.Testo.isValida(nomePaese));
            }// fine del blocco if

            if (continua) {
                nomeRegione = naz.getSeconda();
                nomeProvincia = naz.getTerza();
                continua = (Lib.Testo.isValida(nomeRegione) || Lib.Testo.isValida(nomeProvincia));
            }// fine del blocco if

            if (continua) {
                if (Lib.Testo.isValida(nomeRegione) && Lib.Testo.isValida(nomeProvincia)) {
                    legenda = nomeRegione + and + nomeProvincia;
                } else {
                    if (Lib.Testo.isValida(nomeRegione)) {
                        legenda = nomeRegione;
                    }// fine del blocco if
                    if (Lib.Testo.isValida(nomeProvincia)) {
                        legenda = nomeProvincia;
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return legenda;
    }


    /**
     * Controlla quali nazioni sono state selezionate.
     * <p/>
     */
    private void leggeSelezioneProvince() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String nome;
        ArrayList<TreStringhe> nazioniAll;
        ArrayList<TreStringhe> nazioniTrue = null;
        boolean acceso;

        try { // prova ad eseguire il codice
            nazioniAll = this.getNazioni();
            continua = (nazioniAll != null && nazioniAll.size() > 0);

            if (continua) {
                nazioniTrue = new ArrayList<TreStringhe>();
                for (TreStringhe naz : nazioniAll) {
                    nome = naz.getPrima();
                    acceso = this.getBool(nome);
                    if (acceso) {
                        nazioniTrue.add(naz);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                this.setNazioni(nazioniTrue);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati dalla pagina Wikipedia:Data/Divisioni nazionali di 2° livello <br>
     */
    @Override
    protected void creaRecords() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.creaProvince();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati da una pagina di wikipedia <br>
     * I dati vengono passati con un wrapper <br>
     */
    private void creaProvince() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<TreStringhe> nazioni;
        ArrayList<SeiStringhe> listaGrezza;
        ArrayList<SetteStringhe> listaElaborata;

        try { // prova ad eseguire il codice
            nazioni = this.getNazioni();
            continua = (nazioni != null && nazioni.size() > 0);

            /* recupera la lista delle province in ordine alfabetico di codice */
            if (continua) {
                for (TreStringhe naz : nazioni) {
                    listaGrezza = getProvince(naz);
                    listaElaborata = this.elabora(naz, listaGrezza);

                    if (listaElaborata != null) {
                        for (SetteStringhe pro : listaElaborata) {
                            switch (super.getScelta()) {
                                case cancellaCompleto:
                                    this.creaRecord(pro);
                                    break;
                                case aggiunta:
                                    this.aggiungeRecord(pro);
                                    break;
                                case modifica:
                                    this.modificaRecord(pro);
                                    break;
                                default: // caso non definito
                                    break;
                            } // fine del blocco switch
                        } // fine del ciclo for-each
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista delle province.
     * <p/>
     * Recupera i dati dalle pagine specificate per ogni stato <br>
     * 1-provinciaBreve
     * 2-provinciaCompleto
     * 3-siglaProvincia
     * 4-regioneBreve
     * 5-regioneCompleto
     * 6-note
     */
    private ArrayList<SeiStringhe> getProvince(TreStringhe naz) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SeiStringhe> lista = null;
        ArrayList<QuattroStringhe> listaReg = null;
        ArrayList<CinqueStringhe> listaPro;
        boolean continua;
        String titolo;
        String paese = "";
        String regione = "";
        String provincia = "";
        String nome;
        String nomePro;
        String nomeReg;
        String proCompleto;
        String regCompleto = "";
        String sigla;
        String nomeCompleto;
        String note;
        SeiStringhe sei;
        LinkedHashMap<String, DueStringhe> mappa;
        DueStringhe due;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (naz != null);

            if (continua) {
                paese = naz.getPrima();
                regione = naz.getSeconda();
                provincia = naz.getTerza();
                continua = (Lib.Testo.isValida(paese) && Lib.Testo.isValida(regione));
            }// fine del blocco if

            if (continua) {
                titolo = "geo/primo livello/" + paese + " (" + regione + ")";
                listaReg = this.getListaRegioni(titolo);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<SeiStringhe>();

                if (Lib.Testo.isValida(provincia)) {
                    mappa = this.getMappaRegioni(listaReg);
                    titolo = "geo/secondo livello/" + paese + " (" + provincia + ")";
                    listaPro = this.getListaProvince(titolo);

                    for (CinqueStringhe cinque : listaPro) {
                        nomePro = cinque.getPrima();
                        nomeReg = cinque.getSeconda();
                        sigla = cinque.getTerza();
                        proCompleto = cinque.getQuarta();
                        due = mappa.get(nomeReg);
                        if (due != null) {
                            regCompleto = due.getPrima();
                        }// fine del blocco if

                        note = cinque.getQuinta();
                        if (Lib.Testo.isValida(note)) {
                            note += "\n" + mappa.get(nomeReg).getSeconda();
                        } else {
                            if (due != null) {
                                note = due.getSeconda();
                            }// fine del blocco if
                        }// fine del blocco if-else

                        sei = new SeiStringhe(nomePro,
                                proCompleto,
                                sigla,
                                nomeReg,
                                regCompleto,
                                note);
                        lista.add(sei);
                    } // fine del ciclo for-each

                } else {
                    for (QuattroStringhe quattro : listaReg) {
                        nome = quattro.getPrima();
                        sigla = quattro.getSeconda();
                        nomeCompleto = quattro.getTerza();
                        note = quattro.getQuarta();
                        sei = new SeiStringhe(nome, nomeCompleto, sigla, "", "", note);
                        lista.add(sei);
                    } // fine del ciclo for-each
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera la lista delle province.
     * <p/>
     * 1-provinciaBreve
     * 2-regioneBreve
     * 3-siglaProvincia
     * 4-provinciaCompleto
     * 5-note
     */
    private ArrayList<CinqueStringhe> getListaProvince(String titolo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> lista = null;
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice
            testo = Lib.Web.downLoadData(titolo);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = Lib.Web.getCsvCinque(testo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera la lista delle regioni.
     * <p/>
     * 1-regioneBreve
     * 2-siglaRegione
     * 3-regioneCompleto
     * 4-note
     */
    private ArrayList<QuattroStringhe> getListaRegioni(String titolo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> lista = null;
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice
            testo = Lib.Web.downLoadData(titolo);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = Lib.Web.getCsvQuattro(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Costruisce una mappa delle regioni.
     * <p/>
     * A-regioneBreve
     * B1-regioneCompleto
     * B2-note
     */
    private LinkedHashMap<String, DueStringhe> getMappaRegioni(ArrayList<QuattroStringhe> lista) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, DueStringhe> mappa = null;
        String breve;
        String completo;
        String note;
        DueStringhe due;

        try { // prova ad eseguire il codice
            mappa = new LinkedHashMap<String, DueStringhe>();

            for (QuattroStringhe quattro : lista) {
                breve = quattro.getPrima();
                completo = quattro.getTerza();
                note = quattro.getQuarta();

                due = new DueStringhe(completo, note);
                mappa.put(breve, due);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }


    /**
     * Elabora le informazioni per ogni provincia.
     * <p/>
     * 1-provinciaBreve
     * 2-provinciaCompleto
     * 3-siglaProvincia
     * 4-regioneBreve
     * 5-regioneCompleto
     * 6-note
     * 7-link nazione
     * <p/>
     * Recupera le info sulle regioni (se esistono) <br>
     */
    private ArrayList<SetteStringhe> elabora(TreStringhe naz, ArrayList<SeiStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SetteStringhe> listaOut = null;
        boolean continua;
        Modulo mod = null;
        String paese = "";
        SetteStringhe sette;
        int codLinkNazione = 0;
        String siglaPostale;
        String siglaNaz = "";
        String tag = "-";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (naz != null && listaIn != null);

            if (continua) {
                paese = naz.getPrima();
                continua = (Lib.Testo.isValida(paese));
            }// fine del blocco if

            if (continua) {
                mod = NazioneModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                codLinkNazione = mod.query().valoreChiave(Nazione.Cam.nazione.get(), paese);
                if (codLinkNazione > 0) {
                    siglaNaz = mod.query().valoreStringa(Nazione.Cam.sigla2.get(), codLinkNazione);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                listaOut = new ArrayList<SetteStringhe>();
                for (SeiStringhe sei : listaIn) {
                    sette = new SetteStringhe(sei);

                    siglaPostale = sei.getTerza();
                    if (Lib.Testo.isValida(siglaNaz)) {
                        if (siglaPostale.startsWith(siglaNaz)) {
                            siglaPostale = Lib.Testo.dopoPrima(siglaPostale, siglaNaz);
                            if (siglaPostale.startsWith(tag)) {
                                siglaPostale = Lib.Testo.dopoPrima(siglaPostale, tag);
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                    sette.setTerza(siglaPostale);
                    sette.setSettima("" + codLinkNazione);

                    listaOut.add(sette);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Costruisce una mappa nome-nomeCompleto delle regioni dello stato (se esistono).
     * <p/>
     */
    private LinkedHashMap<String, String> getRegioni(TreStringhe treNaz) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, String> mappa = null;
        boolean continua;
        String completo;
        String paese;
        String nome;
        String titolo;
        String nomeRegione = "";
        String testo = "";
        ArrayList<TreStringhe> lista = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (treNaz != null);

            if (continua) {
                nomeRegione = treNaz.getSeconda();
                continua = (Lib.Testo.isValida(nomeRegione));
            }// fine del blocco if

            if (continua) {
                paese = treNaz.getPrima();
                titolo = "geo/primo livello/" + paese + " (" + nomeRegione + ")";
                testo = Lib.Web.downLoadData(titolo);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                lista = Lib.Web.getCsvTre(testo);
                continua = (lista != null && lista.size() > 0);
            }// fine del blocco if

            if (continua) {
                mappa = new LinkedHashMap<String, String>();
                for (TreStringhe tre : lista) {
                    completo = tre.getTerza();
                    nome = tre.getPrima();
                    mappa.put(nome, completo);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }


    /**
     * Crea un singolo record.
     * <p/>
     * 1-provinciaBreve
     * 2-provinciaCompleto
     * 3-siglaProvincia
     * 4-regioneBreve
     * 5-regioneCompleto
     * 6-note
     * 7-link nazione
     */
    private void creaRecord(SetteStringhe pro) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modPro = null;
        ArrayList<CampoValore> campi;
        CampoValore campoNomeCorrente;
        CampoValore campoSiglaProvincia;
        CampoValore campoNomeCompleto;
        CampoValore campoNomeRegione;
        CampoValore campoRegioneCompleto;
        CampoValore campoLinkStato;
        CampoValore campoNote;
        String nomeCorrente = "";
        String nomeRegione = "";
        String siglaPostale = "";
        String nomeCompleto = "";
        String regioneCompleto = "";
        String nomeCodiceNaz = "";
        String note = "";
        int linkNazione = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (pro != null);

            if (continua) {
                nomeCorrente = pro.getPrima();
                nomeCompleto = pro.getSeconda();
                siglaPostale = pro.getTerza();
                nomeRegione = pro.getQuarta();
                regioneCompleto = pro.getQuinta();
                note = pro.getSesta();
                nomeCodiceNaz = pro.getSettima();
                continua = (Lib.Testo.isValida(nomeCorrente));
            }// fine del blocco if

            /* link allo stato */
            if (continua) {
                try { // prova ad eseguire il codice
                    linkNazione = Libreria.getInt(nomeCodiceNaz);
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch
            }// fine del blocco if

            if (continua) {
                modPro = this.getModulo();
                continua = (modPro != null);
            }// fine del blocco if

            if (continua) {
                campoNomeCorrente = new CampoValore(Provincia.Cam.nomeCorrente.get(), nomeCorrente);
                campoNomeCompleto = new CampoValore(Provincia.Cam.nomeCompleto.get(), nomeCompleto);
                campoSiglaProvincia = new CampoValore(Provincia.Cam.sigla.get(), siglaPostale);
                campoNomeRegione = new CampoValore(Provincia.Cam.regioneBreve.get(), nomeRegione);
                campoRegioneCompleto = new CampoValore(Provincia.Cam.regioneCompleto.get(),
                        regioneCompleto);
                campoLinkStato = new CampoValore(Provincia.Cam.linkNazione.get(), linkNazione);
                campoNote = new CampoValore(ModelloAlgos.NOME_CAMPO_NOTE, note);

                campi = new ArrayList<CampoValore>();
                campi.add(campoNomeCorrente);
                campi.add(campoNomeCompleto);
                campi.add(campoSiglaProvincia);
                campi.add(campoNomeRegione);
                campi.add(campoRegioneCompleto);
                campi.add(campoLinkStato);
                if (Lib.Testo.isValida(note)) {
                    campi.add(campoNote);
                }// fine del blocco if

                super.creaRecord(modPro, campi, Provincia.Cam.nomeCorrente.get(), nomeCorrente);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un record se non esistente.
     * <p/>
     * 1-provinciaBreve
     * 2-provinciaCompleto
     * 3-siglaProvincia
     * 4-regioneBreve
     * 5-regioneCompleto
     * 6-link nazione
     * 7-note
     */
    private void aggiungeRecord(SetteStringhe pro) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod = null;
        String nome = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (pro != null);

            if (continua) {
                mod = this.getModulo();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                nome = pro.getPrima();
                continua = (Lib.Testo.isValida(nome));
            }// fine del blocco if

            /* controlla l'esistenza del record dal nome */
            if (continua) {
                continua = !mod.query().isEsisteRecord(Provincia.Cam.nomeCorrente.get(), nome);
            }// fine del blocco if

            if (continua) {
                this.creaRecord(pro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica un singolo record.
     * <p/>
     * 1-provinciaBreve
     * 2-provinciaCompleto
     * 3-siglaProvincia
     * 4-regioneBreve
     * 5-regioneCompleto
     * 6-link nazione
     * 7-note
     */
    private void modificaRecord(SetteStringhe pro) {
        /* variabili e costanti locali di lavoro */
        String nomeCorrente;
        String nomeCompleto;
        int cod;

        try { // prova ad eseguire il codice

            nomeCorrente = pro.getPrima();
            nomeCompleto = pro.getQuarta();

            cod = this.getModulo().query().valoreChiave(Provincia.Cam.nomeCorrente.get(),
                    nomeCorrente);
            this.getModulo().query().registraRecordValore(cod,
                    Provincia.Cam.nomeCompleto.get(),
                    nomeCompleto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il codice del record dello stato.
     * <p/>
     */
    private void regolaCodiceStato(Stato stato) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod = null;
        String sigla2 = "";
        int codice = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (stato != null);

            if (continua) {
                mod = NazioneModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                sigla2 = stato.getSigla();
                continua = (Lib.Testo.isValida(sigla2));
            }// fine del blocco if

            if (continua) {
                codice = mod.query().valoreChiave(Nazione.Cam.sigla2.get(), sigla2);
                continua = (codice > 0);
            }// fine del blocco if

            if (continua) {
                stato.setCodice(codice);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private ArrayList<TreStringhe> getNazioni() {
        return nazioni;
    }


    private void setNazioni(ArrayList<TreStringhe> nazioni) {
        this.nazioni = nazioni;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Stato {

        germania("Germania", "DE", "", "Lander", true, false, true, true),
        italia("Italia", "IT", "Regioni", "Province", true, true, true, true),
        francia("Francia", "FR", "Regioni", "Dipartimenti", true, true, true, true),
        austria("Austria", "AT", "Lander", "Distretti", true, true, false, true),
        svizzera("Svizzera", "CH", "", "Cantoni", true, false, true, true),
        spagna("Spagna", "ES", "Comunità", "Province", true, true, true, true),
        portogallo("Portogallo", "PT", "", "Distretti", true, false, true, true),
        olanda("Olanda", "NL", "", "Province", true, false, true, true),
        belgio("Belgio", "BE", "", "Province", true, false, true, true),;

        /**
         * nome dello stato
         */
        private String nome;

        /**
         * sigla dello stato
         */
        private String sigla;


        /**
         * nome della pagina su wiki per la lista delle suddivisioni
         */
        private String paginaPrimoLivello;

        /**
         * nome della pagina su wiki per la lista delle suddivisioni
         */
        private String paginaSecondoLivello;

        /**
         * flag per l'esistenza della corrispondente colonna sulla pagina wiki
         */
        private boolean nomeComune;

        /**
         * flag per l'esistenza della corrispondente colonna sulla pagina wiki
         */
        private boolean usaPrimo;

        /**
         * flag per l'esistenza della corrispondente colonna sulla pagina wiki
         */
        private boolean usaSigla;

        /**
         * flag per l'esistenza della corrispondente colonna sulla pagina wiki
         */
        private boolean nomeCompleto;

        /**
         * codice del record corrispondente sulla tavola nazioni
         */
        private int codice;


        /**
         * Costruttore completo con parametri.
         *
         * @param nome dello stato
         * @param sigla dello stato
         * @param paginaPrimoLivello - nome della pagina su wiki per la lista delle suddivisioni
         * @param paginaSecondoLivello - nome della pagina su wiki per la lista delle suddivisioni
         * @param nomeComune - flag per l'esistenza della corrispondente colonna sulla pagina wiki
         * @param usaPrimo - flag per l'esistenza della corrispondente colonna sulla pagina wiki
         * @param usaSigla - flag per l'esistenza della corrispondente colonna sulla pagina wiki
         * @param nomeCompleto - flag per l'esistenza della corrispondente colonna sulla pagina wiki
         */
        Stato(String nome,
              String sigla,
              String paginaPrimoLivello,
              String paginaSecondoLivello,
              boolean nomeComune,
              boolean usaPrimo,
              boolean usaSigla,
              boolean nomeCompleto) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setSigla(sigla);
                this.setPaginaPrimoLivello(paginaPrimoLivello);
                this.setPaginaSecondoLivello(paginaSecondoLivello);
                this.setNomeComune(nomeComune);
                this.setUsaPrimo(usaPrimo);
                this.setUsaSigla(usaSigla);
                this.setNomeCompleto(nomeCompleto);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String getPaginaPrimoLivello() {
            return paginaPrimoLivello;
        }


        private void setPaginaPrimoLivello(String paginaPrimoLivello) {
            this.paginaPrimoLivello = paginaPrimoLivello;
        }


        public String getPaginaSecondoLivello() {
            return paginaSecondoLivello;
        }


        private void setPaginaSecondoLivello(String paginaSecondoLivello) {
            this.paginaSecondoLivello = paginaSecondoLivello;
        }


        public boolean isNomeComune() {
            return nomeComune;
        }


        private void setNomeComune(boolean nomeComune) {
            this.nomeComune = nomeComune;
        }


        public boolean isUsaPrimo() {
            return usaPrimo;
        }


        private void setUsaPrimo(boolean usaPrimo) {
            this.usaPrimo = usaPrimo;
        }


        public boolean isUsaSigla() {
            return usaSigla;
        }


        private void setUsaSigla(boolean usaSigla) {
            this.usaSigla = usaSigla;
        }


        public boolean isNomeCompleto() {
            return nomeCompleto;
        }


        private void setNomeCompleto(boolean nomeCompleto) {
            this.nomeCompleto = nomeCompleto;
        }


        public int getCodice() {
            return codice;
        }


        public void setCodice(int codice) {
            this.codice = codice;
        }
    }// fine della classe

}// fine della classe
