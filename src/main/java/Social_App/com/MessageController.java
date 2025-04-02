package Social_App.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // POST API to save a message
    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        return messageRepository.save(message);
    }

    // GET API to fetch all messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
