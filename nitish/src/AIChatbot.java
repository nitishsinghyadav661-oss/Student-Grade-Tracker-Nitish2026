import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;

    // Knowledge base
    private final Map<String, String> knowledge = new LinkedHashMap<>();

    public AIChatbot() {

        setTitle("CodeAlpha AI Chatbot");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createKnowledgeBase();
        createGUI();

        chatArea.append("Bot: Hello! Welcome to CodeAlpha AI Chatbot.\n");
        chatArea.append("Bot: I can answer CSE questions about Java, C, C++, Python, DBMS, SQL,\n");
        chatArea.append("     OS, Computer Networks, Data Structures, HTML, CSS, AI/ML and more.\n\n");
    }

    // ================= KNOWLEDGE BASE =================

    private void createKnowledgeBase() {

        // Computer Fundamentals
        add("computer", "Computer is an electronic device that accepts data, processes it and produces useful information.");
        add("cpu", "CPU stands for Central Processing Unit. It processes instructions and performs calculations.");
        add("ram", "RAM stands for Random Access Memory. It temporarily stores data used by running programs.");
        add("rom", "ROM stands for Read Only Memory. It stores data that normally remains available when power is removed.");
        add("hardware", "Hardware means the physical parts of a computer, such as keyboard, mouse, monitor and CPU.");
        add("software", "Software is a collection of programs and instructions that tell a computer what to do.");
        add("operating system", "An Operating System manages computer hardware, software, files, memory and processes.");
        add("keyboard", "Keyboard is an input device used to enter text, numbers and commands.");
        add("mouse", "Mouse is an input device used to control the pointer and select items.");
        add("internet", "The Internet is a worldwide network that connects computers and other devices.");

        // Java
        add("java", "Java is a high-level, object-oriented programming language.");
        add("jdk", "JDK stands for Java Development Kit. It provides tools required to develop Java applications.");
        add("jre", "JRE stands for Java Runtime Environment. It provides the environment required to run Java applications.");
        add("jvm", "JVM stands for Java Virtual Machine. It executes Java bytecode.");
        add("class", "A class is a blueprint or template used to create objects in Java.");
        add("object", "An object is an instance of a class. It contains state and behavior.");
        add("constructor", "A constructor is a special method-like member used to initialize an object.");
        add("inheritance", "Inheritance allows one class to acquire properties and methods of another class.");
        add("polymorphism", "Polymorphism means one interface or method name can have different forms of behavior.");
        add("encapsulation", "Encapsulation means combining data and methods in a class and controlling access to the data.");
        add("abstraction", "Abstraction hides unnecessary implementation details and shows only essential features.");
        add("exception", "An exception is an event that interrupts the normal flow of program execution.");
        add("array", "An array stores multiple values of the same type in a fixed-size collection.");
        add("string", "String is a sequence of characters. In Java, String is a class.");
        add("method", "A method is a block of code designed to perform a particular task.");
        add("interface", "An interface defines a contract that classes can implement.");
        add("package", "A package is used to group related Java classes and interfaces.");

        // C / C++
        add("c language", "C is a general-purpose procedural programming language.");
        add("c++", "C++ is a general-purpose programming language that supports procedural and object-oriented programming.");
        add("pointer", "A pointer is a variable that stores the memory address of another variable.");
        add("function", "A function is a reusable block of code that performs a specific task.");
        add("compiler", "A compiler translates source code into machine code or another executable form.");

        // Python
        add("python", "Python is a high-level, interpreted and general-purpose programming language.");
        add("list", "A Python list is an ordered and mutable collection of elements.");
        add("tuple", "A Python tuple is an ordered and immutable collection of elements.");
        add("dictionary", "A Python dictionary stores data as key-value pairs.");

        // DBMS
        add("dbms", "DBMS stands for Database Management System. It is used to store, manage and retrieve data.");
        add("database", "A database is an organized collection of data that can be stored and managed electronically.");
        add("primary key", "A primary key uniquely identifies each record in a database table.");
        add("foreign key", "A foreign key is a field that refers to a key in another table.");
        add("normalization", "Normalization organizes database tables to reduce redundancy and improve data integrity.");
        add("sql", "SQL stands for Structured Query Language. It is used to work with relational databases.");
        add("join", "A SQL JOIN combines related rows from two or more tables.");
        add("select", "SELECT is an SQL command used to retrieve data from a database.");
        add("insert", "INSERT is an SQL command used to add new records to a table.");
        add("update", "UPDATE is an SQL command used to modify existing records.");
        add("delete", "DELETE is an SQL command used to remove records from a table.");

        // Operating System
        add("process", "A process is a program that is currently executing.");
        add("thread", "A thread is a small unit of execution within a process.");
        add("paging", "Paging is a memory management technique that divides memory into fixed-size pages and frames.");
        add("virtual memory", "Virtual memory allows a system to use storage space as an extension of RAM.");
        add("deadlock", "Deadlock occurs when processes wait indefinitely for resources held by each other.");
        add("scheduling", "CPU scheduling decides which ready process should receive CPU time.");

        // Computer Networks
        add("computer network", "A computer network connects devices so that they can communicate and share resources.");
        add("lan", "LAN stands for Local Area Network and usually covers a limited geographical area.");
        add("wan", "WAN stands for Wide Area Network and covers a large geographical area.");
        add("ip address", "An IP address identifies a device on an IP network.");
        add("mac address", "A MAC address is a hardware-level address associated with a network interface.");
        add("router", "A router forwards packets between different networks.");
        add("switch", "A network switch connects devices within a network and forwards data to the appropriate device.");
        add("tcp", "TCP is a connection-oriented transport protocol that provides reliable data delivery.");
        add("udp", "UDP is a connectionless transport protocol with low overhead.");
        add("dns", "DNS translates domain names into IP addresses.");

        // Data Structures
        add("data structure", "A data structure is a way of organizing and storing data for efficient use.");
        add("stack", "A stack follows the LIFO principle: Last In, First Out.");
        add("queue", "A queue generally follows the FIFO principle: First In, First Out.");
        add("linked list", "A linked list consists of nodes where each node stores data and a link to another node.");
        add("tree", "A tree is a hierarchical data structure consisting of nodes connected by edges.");
        add("graph", "A graph consists of vertices and edges used to represent relationships between objects.");
        add("binary search", "Binary search efficiently searches a sorted collection by repeatedly dividing the search range.");

        // Web
        add("html", "HTML stands for HyperText Markup Language. It provides the structure of web pages.");
        add("css", "CSS stands for Cascading Style Sheets. It controls the presentation and styling of web pages.");
        add("javascript", "JavaScript is a programming language commonly used to add behavior and interactivity to web pages.");
        add("website", "A website is a collection of related web pages and resources available through the Internet.");
        add("browser", "A web browser is software used to access and display web pages.");

        // AI / ML / NLP
        add("artificial intelligence", "Artificial Intelligence, or AI, enables computers to perform tasks that normally require human intelligence.");
        add("machine learning", "Machine Learning is a branch of AI in which systems learn patterns from data.");
        add("deep learning", "Deep Learning uses multi-layer neural networks to learn complex patterns from data.");
        add("nlp", "NLP stands for Natural Language Processing. It helps computers process and understand human language.");
        add("neural network", "A neural network is a computing model inspired by networks of interconnected biological neurons.");

        // Internship / Project
        add("internship", "An internship provides practical experience and helps students develop technical and professional skills.");
        add("project", "A software project applies programming and technical knowledge to solve a particular problem.");
    }

    private void add(String keyword, String answer) {
        knowledge.put(keyword, answer);
    }

    // ================= GUI =================

    private void createGUI() {

        JLabel heading = new JLabel(
                "CodeAlpha AI Chatbot",
                SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 26));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        sendButton = new JButton("Send");
        clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        setLayout(new BorderLayout(10, 10));

        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Send button
        sendButton.addActionListener(e -> sendMessage());

        // Enter key
        inputField.addActionListener(e -> sendMessage());

        // Clear button
        clearButton.addActionListener(e -> {
            chatArea.setText("");
            chatArea.append("Bot: Chat cleared. Ask me a CSE question.\n\n");
        });
    }

    // ================= MESSAGE PROCESSING =================

    private void sendMessage() {

        String input = inputField.getText().trim();

        if (input.isEmpty()) {
            return;
        }

        chatArea.append("You: " + input + "\n");

        String answer = getResponse(input);

        chatArea.append("Bot: " + answer + "\n\n");

        inputField.setText("");
    }

    // Basic NLP / text processing
    private String cleanText(String text) {

        text = text.toLowerCase();

        text = text.replace("?", "");
        text = text.replace("!", "");
        text = text.replace(".", "");
        text = text.replace(",", "");

        return text.trim();
    }

    private String getResponse(String userInput) {

        String input = cleanText(userInput);

        // Greetings
        if (input.equals("hi") ||
                input.equals("hello") ||
                input.equals("hey")) {

            return "Hello! How can I help you with your CSE questions?";
        }

        // Thanks
        if (input.contains("thank")) {
            return "You're welcome! Happy learning.";
        }

        // Bye
        if (input.equals("bye") ||
                input.equals("exit")) {

            return "Goodbye! Have a great day.";
        }

        // Help
        if (input.contains("help")) {

            return "I can answer questions about Java, C, C++, Python, DBMS, SQL, OS, Networks, Data Structures, HTML, CSS, AI, ML and NLP.";
        }

        /*
         * Search the knowledge base.
         * This allows different question formats such as:
         * "What is Java?"
         * "Explain Java"
         * "Tell me about Java"
         * "Java kya hai"
         */

        String bestAnswer = null;
        int bestScore = 0;

        for (Map.Entry<String, String> entry : knowledge.entrySet()) {

            String keyword = entry.getKey();

            int score = calculateScore(input, keyword);

            if (score > bestScore) {
                bestScore = score;
                bestAnswer = entry.getValue();
            }
        }

        if (bestAnswer != null && bestScore > 0) {
            return bestAnswer;
        }

        return "Sorry, I don't know that yet. Try asking me about Java, C, C++, Python, DBMS, SQL, Operating System, Computer Networks, Data Structures, HTML, CSS, AI, ML or NLP.";
    }

    // Simple keyword matching algorithm
    private int calculateScore(String input, String keyword) {

        int score = 0;

        String[] words = keyword.split(" ");

        for (String word : words) {

            if (input.contains(word)) {
                score++;
            }
        }

        // Exact keyword gets higher priority
        if (input.contains(keyword)) {
            score += 2;
        }

        return score;
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AIChatbot chatbot = new AIChatbot();

            chatbot.setVisible(true);
        });
    }
}