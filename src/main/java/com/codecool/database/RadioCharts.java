package com.codecool.database;

import java.sql.*;

public class RadioCharts {
    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;

    public RadioCharts(String DB_URL, String USERNAME, String PASSWORD) {
        this.DB_URL = DB_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getMostPlayedSong() {
        String mostPlayedSong = "";
        String sql = "SELECT song, sum(times_aired) AS sum_times_aired FROM music_broadcast " +
                "GROUP BY song ORDER BY sum_times_aired DESC";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int maxPlay = 0;
            while (resultSet.next()) {
                int actualPlayed = resultSet.getInt(2);
                if (maxPlay == 0) {
                    maxPlay = actualPlayed;
                }
                if (actualPlayed != maxPlay) {
                    return mostPlayedSong;
                }
                mostPlayedSong = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostPlayedSong;
    }

    public String getMostActiveArtist() {
        String mostActiveArtist = "";
        String sql = "SELECT artist, count(artist) AS song_count FROM (SELECT DISTINCT * FROM music_broadcast) " +
                "GROUP BY song ORDER BY song_count DESC";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int maxCount = 0;
            while (resultSet.next()) {
                int actualCount = resultSet.getInt(2);
                if (maxCount == 0) {
                    maxCount = actualCount;
                }
                if (actualCount != maxCount) {
                    return mostActiveArtist;
                }
                mostActiveArtist = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostActiveArtist;
    }
}

