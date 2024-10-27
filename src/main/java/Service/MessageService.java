package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message message){
        if(message.getMessage_text() == null){
            return null;
        }
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
            return null;
        }
        // if posting user does not exist
        if(accountDAO.getAccountByID(message.getPosted_by()) == null){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessage(int messageID){
        Message message = messageDAO.getMessageByID(messageID);
        messageDAO.deleteMessageByID(messageID);
        return message;
    }

    public Message updateMessage(int messageID, String messageText){
        if(messageText == null){
            return null;
        }
        if(messageText.length() == 0 || messageText.length() > 255){
            return null;
        }
        messageDAO.updateMessageByID(messageID, messageText);
        return messageDAO.getMessageByID(messageID);
    }

    public List<Message> getAllMessagesByUser(int userID){
        return messageDAO.getAllMessagesByUser(userID);
    }
}
