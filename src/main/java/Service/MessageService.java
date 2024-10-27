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

    /**
     * Adds new Message.
     * @param message to add without message ID.
     * @return Message with message ID or null if message is invalid.
     */
    public Message addMessage(Message message){
        if(message.getMessage_text() == null){
            return null;
        }
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
            return null;
        }
        if(accountDAO.getAccountByID(message.getPosted_by()) == null){
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    /**
     * Gets all messages in database.
     * @return List of messages.
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Get message by ID.
     * @param messageID
     * @return message found or null if message does not exist
     */
    public Message getMessageByID(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    /**
     * Delete message with message ID
     * @param messageID
     * @return Message deleted or null if messages did not exist.
     */
    public Message deleteMessage(int messageID){
        Message message = messageDAO.getMessageByID(messageID);
        messageDAO.deleteMessageByID(messageID);
        return message;
    }

    /**
     * update message with message ID, with the given message text.
     * @param messageID
     * @param messageText
     * @return Message that was updated or null if message text is invalid or message ID does not exist.
     */
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

    /**
     * Gets all message from a user.
     * @param userID
     * @return List of messages
     */
    public List<Message> getAllMessagesByUser(int userID){
        return messageDAO.getAllMessagesByUser(userID);
    }
}
