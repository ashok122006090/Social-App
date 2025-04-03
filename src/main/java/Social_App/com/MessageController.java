package Social_App.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@RequestMapping("/messages")


@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})

//@CrossOrigin(origins = "*")  // Allow all origins (Frontend can access)
//@CrossOrigin(origins = "https://social-app-frontend-sooty.vercel.app")

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
