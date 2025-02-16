package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.SQLException;

public class JurnalDetail {
    private int id;
    private int jurnalId;
    private int perkiraanId;
    private double debet;
    private double kredit;

    private transient Perkiraan perkiraan; // Lazy Loading

    public JurnalDetail() {}

    public JurnalDetail(int id, int jurnalId, int perkiraanId, double debet, double kredit) {
        this.id = id;
        this.jurnalId = jurnalId;
        this.perkiraanId = perkiraanId;
        this.debet = debet;
        this.kredit = kredit;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getJurnalId() { return jurnalId; }
    public void setJurnalId(int jurnalId) { this.jurnalId = jurnalId; }

    public int getPerkiraanId() { return perkiraanId; }
    public void setPerkiraanId(int perkiraanId) { this.perkiraanId = perkiraanId; }

    public double getDebet() { return debet; }
    public void setDebet(double debet) { this.debet = debet; }

    public double getKredit() { return kredit; }
    public void setKredit(double kredit) { this.kredit = kredit; }

    // Lazy Load Perkiraan
    public Perkiraan getPerkiraan() throws SQLException {
        if (perkiraan == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraan = perkiraanDAO.getPerkiraanById(this.getPerkiraanId());
        }
        return perkiraan;
    }
}
