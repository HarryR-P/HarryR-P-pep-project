package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login",this::loginHandler);
        app.post("/messages",this::postMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}",this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages",this::getAllMessagesByUserHandler);


        return app;
    }

    /**
     * Register a new user. Username and password are in the body. Sends 400 status for an invalid unsername/password
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void registerAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account addedAccount = accountService.registerUser(account);
        if(addedAccount != null){
            context.json(om.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    /**
     * Checks if the account exists. Sends 401 status for an invalid unsername/password
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount != null){
            context.json(om.writeValueAsString(loginAccount));
        }else{
            context.status(401);
        }
    }

    /**
     * Posts a new message. Message is in the body. Sends 400 status for an invalid message
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null){
            context.json(om.writeValueAsString(addedMessage));
        }else{
            context.status(400);
        }
    }

    /**
     * Gets all messeges.
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        List<Message> messageList = messageService.getAllMessages();
        context.json(om.writeValueAsString(messageList));
    }

    /**
     * Get message with given message_id. ID passed as path parameter. Sends empty body if the message does not exist.
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(messageID);
        if(message != null){
            context.json(om.writeValueAsString(message));
        }
    }

    /**
     * Deletes message with given message_id. ID passed as path parameter. Sends the deleted message in the body if it exists.
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessage(messageID);
        if(message != null){
            context.json(om.writeValueAsString(message));
        }
    }

    /**
     * Updates message with given message_id with the text sent in the body. ID passed as path parameter.
     * Sends 400 status if the text is invalid or ID does not exist.
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = om.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(messageID, message.getMessage_text());
        if(updatedMessage != null){
            context.json(om.writeValueAsString(updatedMessage));
        }else{
            context.status(400);
        }
    }

    /**
     * Gets all messages from a given user. User ID passed as path parameter.
     * @param context of the HTTP request
     * @throws JsonProcessingException if there is a error in converting the JSON to an object.
     */
    private void getAllMessagesByUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int accountID = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messageList = messageService.getAllMessagesByUser(accountID);
        context.json(om.writeValueAsString(messageList));
    }
}