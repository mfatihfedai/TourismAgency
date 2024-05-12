package entity;

import core.ComboItem;

import java.time.LocalDate;

public class Season {
    private int id;
    private int otel_id;
    private LocalDate strt_date;
    private LocalDate fnsh_date;
    private Otel otel;
    public Season(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public LocalDate getStrt_date() {
        return strt_date;
    }

    public void setStrt_date(LocalDate strt_date) {
        this.strt_date = strt_date;
    }

    public LocalDate getFnsh_date() {
        return fnsh_date;
    }

    public void setFnsh_date(LocalDate fnsh_date) {
        this.fnsh_date = fnsh_date;
    }

    public Otel getOtel() {
        return otel;
    }

    public void setOtel(Otel otel) {
        this.otel = otel;
    }
    public ComboItem getComboItem(){
        return new ComboItem(this.getId(), this.getStrt_date() + " - " + this.getFnsh_date());
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", otel_id=" + otel_id +
                ", strt_date=" + strt_date +
                ", fnsh_date=" + fnsh_date +
                '}';
    }
}
