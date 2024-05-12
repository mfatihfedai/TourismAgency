package entity;

import business.PensionManager;
import core.ComboItem;

public class Room {
    private int id;
    private Otel otel;
    private Season season;
    private Pension pension;
    private int otel_id;
    private int pension_id;
    private int season_id;
    private int stok;
    private int bed;
    private int mtrsqr;
    private int prc_chld;
    private int prc_adult;
    private boolean aircndtn;
    private boolean minibar;
    private boolean tv;
    private boolean safe;
    private boolean fridge;
    private Room.Type type;
    public enum Type{
        SingleRoom,
        DoubleRoom,
        JuniorSuiteRoom,
        SuiteRoom
    }
    public Room(){
        this.pension = new Pension();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Otel getOtel() {
        return otel;
    }

    public void setOtel(Otel otel) {
        this.otel = otel;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public int getPension_id() {
        return pension_id;
    }

    public void setPension_id(int pension_id) {
        this.pension_id = pension_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getMtrsqr() {
        return mtrsqr;
    }

    public void setMtrsqr(int mtrsqr) {
        this.mtrsqr = mtrsqr;
    }

    public int getPrc_chld() {
        return prc_chld;
    }

    public void setPrc_chld(int prc_chld) {
        this.prc_chld = prc_chld;
    }

    public int getPrc_adult() {
        return prc_adult;
    }

    public void setPrc_adult(int prc_adult) {
        this.prc_adult = prc_adult;
    }

    public boolean isAircndtn() {
        return aircndtn;
    }

    public void setAircndtn(boolean aircndtn) {
        this.aircndtn = aircndtn;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    public boolean isFridge() {
        return fridge;
    }

    public void setFridge(boolean fridge) {
        this.fridge = fridge;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", otel=" + otel +
                ", season=" + season +
                ", pension=" + pension +
                ", otel_id=" + otel_id +
                ", pension_id=" + pension_id +
                ", season_id=" + season_id +
                ", stok=" + stok +
                ", bed=" + bed +
                ", mtrsqr=" + mtrsqr +
                ", prc_chld=" + prc_chld +
                ", prc_adult=" + prc_adult +
                ", aircndtn=" + aircndtn +
                ", minibar=" + minibar +
                ", tv=" + tv +
                ", safe=" + safe +
                ", fridge=" + fridge +
                ", type=" + type +
                '}';
    }
}
