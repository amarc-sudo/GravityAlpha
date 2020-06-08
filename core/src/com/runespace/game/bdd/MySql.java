package com.runespace.game.bdd;

import com.runespace.game.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author aurelien Marc
 */
public class MySql {
    /**
     * Connect to a sample database
     */
    public static Connection conn = null;
    public static String compte = null;
    
    public static void connect(String user, String pwd) {
        compte = user;
        try {
            // db parameters
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String url = "jdbc:mysql://mysql-gravity.alwaysdata.net/gravity_db";
            // create a connection to the database
            conn = DriverManager.getConnection(url , user, pwd);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    public static Boolean disconnect() {
        try {
            if (conn != null) {
                conn.close();
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        ;
        return false;
    }

    public static void addScore(int level , int score) throws SQLException {
        //Statement st = conn.createStatement();
        if(conn != null) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `Score` (id , level , score) VALUES (?,?,?)");
            pstmt.setString(1, Constants.NAME);
            pstmt.setInt(2, level);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
            //st.execute("INSERT into Score VALUES ( '" + compte + "', " + level + ", " + score + " )");
            pstmt.close();
            conn.commit();
        }
    }

    public static String getQuerry(String sql, int i) {
        String score = "0", id = "nobody";
        try {
            Statement stmt = conn.createStatement();
            try {
                ResultSet rs = stmt.executeQuery(sql);
                //stmt.close();
                try {
                    if(rs.next()) {
                        score = rs.getString("max(score)");
                        id = rs.getString("id");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                stmt.close();
            }catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String text = "Level " + i + " : Score Max : " + score + " : Auteur : " + id;
        return text;

    }



}