package dao;

import core.Db;
import entity.Season;
import org.postgresql.jdbc.PreferQueryMode;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeasonDao {
    private Connection con;
    private final OtelDao otelDao;
    public SeasonDao(){
        this.con = Db.getInstance();
        this.otelDao = new OtelDao();
    }
    public ArrayList<Season> findAll(){
        return this.selectByQuery("SELECT * FROM public.season ORDER BY season_id ASC ");
    }
    public ArrayList<Season> selectByQuery(String query){
        ArrayList<Season> seasons = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                seasons.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }
    public Season getById(int id){
        Season obj = null;
        String query = "SELECT * FROM public.season WHERE season_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public Season match(ResultSet rs) throws SQLException{
        Season season = new Season();
        season.setId(rs.getInt("season_id"));
        season.setOtel_id(rs.getInt("season_otel_id"));
        season.setStrt_date(LocalDate.parse(rs.getString("strt_date")));
        season.setFnsh_date(LocalDate.parse(rs.getString("fnsh_date")));
        season.setOtel(this.otelDao.getById(rs.getInt("season_otel_id")));
        return season;
    }
    public boolean save(Season season){
        String query = "INSERT INTO public.season " +
                "(" +
                "season_otel_id, " +
                "strt_date, " +
                "fnsh_date" +
                ")" +
                " VALUES (?,?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, season.getOtel_id());
            pr.setDate(2, Date.valueOf(season.getStrt_date()));
            pr.setDate(3, Date.valueOf(season.getFnsh_date()));
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete(int id){
        String query = "DELETE FROM public.season WHERE season_id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public ArrayList<Season> getSeasonsByOtelId(int otelId) {
        ArrayList<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM public.season WHERE season_otel_id = ?";

        try (PreparedStatement pr = con.prepareStatement(query)) {
            pr.setInt(1, otelId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Season season = match(rs);
                seasons.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seasons;
    }
}
