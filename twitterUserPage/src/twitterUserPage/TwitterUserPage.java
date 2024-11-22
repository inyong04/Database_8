/*v1, example
package twitterUserPage;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TwitterUserPage extends JFrame {
    private String userId;
    private String userName;
    private int followingCount;
    private int followerCount;
    private boolean isCurrentUser;
    private Connection connection;

    public TwitterUserPage(String userId, String userName, int followingCount, int followerCount, boolean isCurrentUser) {
        this.userId = userId;
        this.userName = userName;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.isCurrentUser = isCurrentUser;

        // Set up JDBC connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TWITTER", "root", "yuyu1234"); // DB 연결 정보 수정 필요
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        setTitle("Twitter User Profile");
        setSize(500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top Section: User Info and Follow Button
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Middle Section: Posts and Likes Tabs
        JPanel tabPanel = createTabPanel();
        mainPanel.add(tabPanel, BorderLayout.CENTER);

        // Bottom Navigation Bar
        JPanel bottomNav = createBottomNav();
        mainPanel.add(bottomNav, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        // User Info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel displayNameLabel = new JLabel(userName);
        displayNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel userIdLabel = new JLabel("@" + userId);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setForeground(Color.GRAY);

        JLabel followingLabel = new JLabel("Following: " + followingCount + "  |  Followers: " + followerCount);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        userInfoPanel.add(displayNameLabel);
        userInfoPanel.add(userIdLabel);
        userInfoPanel.add(followingLabel);

        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Follow Button
        if (!isCurrentUser) {
            JButton followButton = createStyledButton("Follow");
            topPanel.add(followButton, BorderLayout.EAST);
        }

        return topPanel;
    }

    private JPanel createTabPanel() {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());
        tabPanel.setBackground(Color.WHITE);

        // Tab Buttons
        JPanel tabButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton myPostsTab = createStyledButton("My Posts");
        JButton likedPostsTab = createStyledButton("Liked Posts");

        tabButtonsPanel.add(myPostsTab);
        tabButtonsPanel.add(likedPostsTab);
        tabPanel.add(tabButtonsPanel, BorderLayout.NORTH);

        // Post Area
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBackground(Color.WHITE);

        // Load posts from database
        List<String> posts = loadPostsFromDatabase();
        if (posts.isEmpty()) {
            postsPanel.add(new JLabel("No posts to display."));
        } else {
            for (String post : posts) {
                JPanel postPanel = new JPanel(new BorderLayout());
                postPanel.setBackground(Color.WHITE);
                postPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                JLabel postLabel = new JLabel(post);
                postLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                postPanel.add(postLabel, BorderLayout.CENTER);
                postsPanel.add(postPanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabPanel.add(scrollPane, BorderLayout.CENTER);

        return tabPanel;
    }

    private JPanel createBottomNav() {
        JPanel bottomNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomNav.setBackground(Color.WHITE);

        String[] navItems = {"Home", "Search", "Bookmark", "Post"};
        for (String item : navItems) {
            JButton button = createStyledButton(item);
            bottomNav.add(button);
        }

        return bottomNav;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(29, 161, 242));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private List<String> loadPostsFromDatabase() {
        List<String> posts = new ArrayList<>();
        String query = "SELECT content FROM POST WHERE user_id = ? ORDER BY create_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TwitterUserPage("XKorea", "Korea", 590, 1256000, false));
    }
}
*/

/*v2
package twitterUserPage;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TwitterUserPage extends JFrame {
    private String userId;
    private String userName;
    private int followingCount;
    private int followerCount;
    private boolean isCurrentUser;
    private Connection connection;

    public TwitterUserPage(String userId, String userName, int followingCount, int followerCount, boolean isCurrentUser) {
        this.userId = userId;
        this.userName = userName;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.isCurrentUser = isCurrentUser;

        // Set up JDBC connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TWITTER", "root", "password"); // DB 연결 정보 수정 필요
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        setTitle("Twitter User Profile");
        setSize(500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top Section: User Info and Follow Button
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Middle Section: Posts and Likes Tabs
        JPanel tabPanel = createTabPanel();
        mainPanel.add(tabPanel, BorderLayout.CENTER);

        // Bottom Navigation Bar
        JPanel bottomNav = createBottomNav();
        mainPanel.add(bottomNav, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);

        // User Info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel displayNameLabel = new JLabel(userName);
        displayNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel userIdLabel = new JLabel("@" + userId);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setForeground(Color.GRAY);

        JLabel followingLabel = new JLabel("Following: " + followingCount + "  |  Followers: " + followerCount);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        userInfoPanel.add(displayNameLabel);
        userInfoPanel.add(userIdLabel);
        userInfoPanel.add(followingLabel);

        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Follow Button
        if (!isCurrentUser) {
            JButton followButton = createStyledButton("Follow");
            topPanel.add(followButton, BorderLayout.EAST);
        }

        return topPanel;
    }

    private JPanel createTabPanel() {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());
        tabPanel.setBackground(Color.WHITE);

        // Tab Buttons
        JPanel tabButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton myPostsTab = createStyledButton("My Posts");
        JButton likedPostsTab = createStyledButton("Liked Posts");

        tabButtonsPanel.add(myPostsTab);
        tabButtonsPanel.add(likedPostsTab);
        tabPanel.add(tabButtonsPanel, BorderLayout.NORTH);

        // Post Area
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBackground(Color.WHITE);

        // Load posts from database
        List<String> posts = loadPostsFromDatabase();
        if (posts.isEmpty()) {
            postsPanel.add(new JLabel("No posts to display."));
        } else {
            for (String post : posts) {
                JPanel postPanel = new JPanel(new BorderLayout());
                postPanel.setBackground(Color.WHITE);
                postPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                JLabel postLabel = new JLabel(post);
                postLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                postPanel.add(postLabel, BorderLayout.CENTER);
                postsPanel.add(postPanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabPanel.add(scrollPane, BorderLayout.CENTER);

        return tabPanel;
    }

    private JPanel createBottomNav() {
        JPanel bottomNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomNav.setBackground(Color.WHITE);

        String[] navItems = {"Home", "Search", "Bookmark", "Post"};
        for (String item : navItems) {
            JButton button = createStyledButton(item);
            bottomNav.add(button);
        }

        return bottomNav;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(29, 161, 242));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private List<String> loadPostsFromDatabase() {
        List<String> posts = new ArrayList<>();
        String query = "SELECT content FROM POST WHERE user_id = ? ORDER BY create_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TwitterUserPage("XKorea", "Korea", 590, 1256000, false));
    }
}

*/


/*v3
package twitterUserPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class TwitterUserPage extends JFrame {
    private String userId;
    private String userName;
    private int followingCount;
    private int followerCount;
    private boolean isCurrentUser;

    private Connection connection;

    public TwitterUserPage(String userId, boolean isCurrentUser) {
        this.userId = userId;
        this.isCurrentUser = isCurrentUser;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TWITTER", "root", "password");

            // Fetch user details from the database
            fetchUserDetails();

            setTitle("Twitter User Profile");
            setSize(500, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            createAndShowGUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchUserDetails() throws SQLException {
        String userQuery = "SELECT id, CONCAT(first_name, ' ', last_name) AS full_name, " +
                "(SELECT COUNT(*) FROM FOLLOW WHERE follow_id = ?) AS following, " +
                "(SELECT COUNT(*) FROM FOLLOW WHERE followed_id = ?) AS followers " +
                "FROM USER WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(userQuery)) {
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            stmt.setString(3, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.userName = rs.getString("full_name");
                this.followingCount = rs.getInt("following");
                this.followerCount = rs.getInt("followers");
            }
        }
    }

    private ArrayList<String> fetchPosts(boolean likedPosts) throws SQLException {
        String postQuery = likedPosts
                ? "SELECT p.content FROM POST p JOIN POST_LIKE pl ON p.id = pl.post_id WHERE pl.user_id = ?"
                : "SELECT content FROM POST WHERE user_id = ?";
        ArrayList<String> posts = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(postQuery)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                posts.add(rs.getString("content"));
            }
        }

        return posts;
    }

    private void createAndShowGUI() {
        // Main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Top Section with Profile and Follow Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // User Info Section
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel displayNameLabel = new JLabel(userName);
        displayNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel userIdLabel = new JLabel("@" + userId);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setForeground(Color.GRAY);

        userInfoPanel.add(displayNameLabel);
        userInfoPanel.add(userIdLabel);

        // Follow Button (Only if viewing another user's profile)
        if (!isCurrentUser) {
            JButton followButton = createStyledButton("Follow");
            topPanel.add(followButton, BorderLayout.EAST);
        }

        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Stats Section (Following, Followers)
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        statsPanel.setBackground(Color.WHITE);

        JLabel followingLabel = new JLabel("Following: " + followingCount);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel followerLabel = new JLabel("Followers: " + followerCount);
        followerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        statsPanel.add(followingLabel);
        statsPanel.add(followerLabel);

        // Tabs for Posts and Liked Posts
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Posts", createPostPanel(false));
        tabbedPane.addTab("Liked Posts", createPostPanel(true));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(tabbedPane, BorderLayout.SOUTH);

        // Bottom Navigation Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navBar.setBackground(Color.WHITE);

        String[] navItems = {"Home", "Search", "Bookmarks", "Post"};
        for (String navItem : navItems) {
            JButton navButton = createStyledButton(navItem);
            navButton.addActionListener(e -> JOptionPane.showMessageDialog(this, navItem + " clicked! (Dummy Action)"));
            navBar.add(navButton);
        }

        panel.add(navBar, BorderLayout.PAGE_END);

        add(panel);
        setVisible(true);
    }

    private JPanel createPostPanel(boolean likedPosts) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);

        try {
            ArrayList<String> posts = fetchPosts(likedPosts);
            if (posts.isEmpty()) {
                JLabel noPostsLabel = new JLabel(likedPosts ? "No liked posts." : "No posts yet.");
                noPostsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                noPostsLabel.setForeground(Color.GRAY);
                postPanel.add(noPostsLabel);
            } else {
                for (String post : posts) {
                    JLabel postLabel = new JLabel("<html>" + post + "</html>");
                    postLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                    postLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    postPanel.add(postLabel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new JScrollPane(postPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(29, 161, 242));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TwitterUserPage("XKorea", false));
    }
}
*/