package com.bprasojo.ekspedisi.model;

import com.bprasojo.ekspedisi.dao.PerkiraanDAO;
import java.sql.SQLException;

public class JurnalDetail {
    private int id;
    private int jurnalId;
    private int perkiraanId;
    private int debet;
    private int kredit;

    private transient Perkiraan perkiraan; // Lazy Loading

    public JurnalDetail() {}

    public JurnalDetail(int id, int perkiraanId, int debet, int kredit) {
        this.id = id;
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

    public int getDebet() { return debet; }
    public void setDebet(int debet) { this.debet = debet; }

    public int getKredit() { return kredit; }
    public void setKredit(int kredit) { this.kredit = kredit; }

    // Lazy Load Perkiraan
    public Perkiraan getPerkiraan() throws SQLException {
        if (perkiraan == null) {
            PerkiraanDAO perkiraanDAO = new PerkiraanDAO();
            perkiraan = perkiraanDAO.getById(this.getPerkiraanId());
        }
        return perkiraan;
    }
}
