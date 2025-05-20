package Social_App.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    // POST API to save a message
    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        // Call the local ML API to get emotion
        String emotion = detectEmotionFromText(message.getText());
        message.setEmotion(emotion); // Assuming your Message entity has an 'emotion' field

        return messageRepository.save(message);
    }

    // GET API to fetch all messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    private String detectEmotionFromText(String text) {
        try {
            String url = "http://127.0.0.1:8000/detect-emotion";
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String json = "{\"text\": \"" + text.replace("\"", "\\\"") + "\"}";
            HttpEntity<String> request = new HttpEntity<>(json, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                return root.get("emotion").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown"; // fallback if something goes wrong
    }
}
