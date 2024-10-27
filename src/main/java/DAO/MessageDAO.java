package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet results = preparedStatement.executeQuery();
            while(results.next()){
                messageList.add(
                    new Message(results.getInt("message_id"),
                        results.getInt("posted_by"),
                        results.getString("message_text"),
                        results.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return messageList;
    }

    public List<Message> getAllMessagesByUser(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountID);

            ResultSet results = preparedStatement.executeQuery();
            while(results.next()){
                messageList.add(
                    new Message(results.getInt("message_id"),
                        results.getInt("posted_by"),
                        results.getString("message_text"),
                        results.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return messageList;
    }

    public Message getMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,messageID);

            ResultSet results = preparedStatement.executeQuery();
            if(results.next()){
                return new Message(
                    results.getInt("message_id"),
                    results.getInt("posted_by"),
                    results.getString("message_text"),
                    results.getLong("time_posted_epoch")
                    );
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet genResultSet = preparedStatement.getGeneratedKeys();
            if(genResultSet.next()){
                int messageID = (int) genResultSet.getLong(1);
                return new Message(messageID, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                    );
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public int deleteMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,messageID);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return -1;
    }
    
    public int updateMessageByID(int messageID, String messageText){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,messageText);
            preparedStatement.setInt(2,messageID);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted;
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return -1;
    }
}
