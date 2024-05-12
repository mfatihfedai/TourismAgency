package entity;

import core.ComboItem;
import business.OtelManager;

public class Pension {
    private int id;
    private int otel_id;
    private Pension.Name name;
    private Otel otel;
    public enum Name {
        Ultra_All_Inclusive,
        All_Inclusive,
        Room_Breakfast,
        Full_Pension,
        Half_Pension,
        Only_Bed,
        Alcohol_Except_Full_Credit
    }
    public Pension(){
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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Otel getOtel() {
            return otel;
    }

    public void setOtel(Otel otel) {
        this.otel = otel;
    }
    public ComboItem getComboItem(){
        return new ComboItem(this.getId(), this.getName().toString());
    }

    @Override
    public String toString() {
        return "Pension{" +
                "id=" + id +
                ", otel_id=" + otel_id +
                ", name=" + name +
                ", otel=" + otel +
                '}';
    }
}
