package ru.task.dao;

import com.sun.org.apache.xpath.internal.operations.Div;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.task.model.Division;
import ru.task.model.Divisions;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;

/**
 * Created by nikk on 05.07.2017.
 */
@Repository
public class DaoAction {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Division> queryAllDivisions() {
        final String QUERY_SQL = "SELECT * FROM DIVISIONS";
        List<Division> divisions = jdbcTemplate.query(QUERY_SQL, new RowMapper<Division>() {
            public Division mapRow(ResultSet resulSet, int rowNum) throws SQLException {
                Division division = new Division();
                division.setId(resulSet.getInt("ID"));
                division.setDepCode(resulSet.getString("DepCode"));
                division.setDepJob(resulSet.getString("DepJob"));
                division.setDescription(resulSet.getString("Description"));
                return division;
            }
        });
        return divisions;
    }

    public void synchronizedDivisions(HashSet<Division> setInsert, HashSet<Division> setUpdate, HashSet<Division> setDelete){

        final String INSERT_SQL = "INSERT INTO DIVISIONS (DepCode, DepJob, Description) VALUES (?,?,?)";
        final String UPDATE_SQL = "UPDATE DIVISIONS SET Description=? WHERE DepCode = ? AND DepJob = ?";
        final String DELETE_SQL = "DELETE FROM DIVISIONS WHERE ID=?";

        Connection conn = null;

        try {

            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);
            Iterator iterator = setInsert.iterator();
            while (iterator.hasNext()){
                Division division = (Division) iterator.next();
                ps.setString(1, division.getDepCode());
                ps.setString(2, division.getDepJob());
                ps.setString(3, division.getDescription());
                ps.executeUpdate();
            }
            ps.close();

            ps = conn.prepareStatement(UPDATE_SQL);
            iterator = setUpdate.iterator();
            while (iterator.hasNext()){
                Division division = (Division) iterator.next();
                ps.setString(1, division.getDescription());
                ps.setString(2, division.getDepCode());
                ps.setString(3, division.getDepJob());
                ps.executeUpdate();
            }
            ps.close();

            ps = conn.prepareStatement(DELETE_SQL);
            iterator = setDelete.iterator();
            while (iterator.hasNext()){
                Division division = (Division) iterator.next();
                ps.setInt(1, division.getId());
                ps.executeUpdate();
            }
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

}
