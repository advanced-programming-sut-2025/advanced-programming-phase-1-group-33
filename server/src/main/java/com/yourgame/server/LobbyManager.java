package com.yourgame.server;

import com.yourgame.network.lobby.Lobby;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

public class LobbyManager {
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public LobbyManager() {
        // Deleting the unused Lobbies 
        scheduler.scheduleAtFixedRate(this::cleanupInactiveLobbies, 5, 5, TimeUnit.MINUTES);
    }


    public Lobby createLobby(String name, String adminUsername, boolean isPrivate, String password, boolean isVisible) {
        // Lobby Id Is unique 
        String id = generateLobbyId();
        Lobby newLobby = new Lobby(name, adminUsername, isPrivate, password, isVisible);
        lobbies.put(id, newLobby);
        System.out.println("Lobby created with ID: " + id + " by " + adminUsername);
        return newLobby;
    }

    /**
     * یک بازیکن را به لابی اضافه می‌کند.
     */
    public boolean addPlayerToLobby(String lobbyId, String username, String password) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby == null) {
            return false; // لابی پیدا نشد
        }

        // بررسی پسورد برای لابی‌های خصوصی
        if (lobby.isPrivate() && !lobby.checkPassword(password)) {
            return false; // پسورد اشتباه است
        }

        return lobby.addPlayer(username);
    }
    
    /**
     * یک بازیکن را از لابی حذف می‌کند.
     */
    public void removePlayerFromLobby(String username, String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null) {
            lobby.removePlayer(username);
            // اگر لابی خالی شد، آن را حذف می‌کند.
            if (lobby.isEmpty()) {
                lobbies.remove(lobbyId);
                System.out.println("Lobby " + lobbyId + " has been closed due to being empty.");
            } else {
                lobby.updateLastActivityTime();
            }
        }
    }

    /**
     * لیست لابی‌های عمومی و قابل مشاهده را برمی‌گرداند.
     */
    public List<Lobby> getVisibleLobbies() {
        return lobbies.values().stream()
                      .filter(Lobby::isVisible)
                      .collect(Collectors.toList());
    }

    /**
     * یک لابی را بر اساس ID آن پیدا می‌کند.
     */
    public Lobby findLobbyById(String lobbyId) {
        return lobbies.get(lobbyId);
    }

    /**
     * وظیفه دوره‌ای برای حذف لابی‌های غیرفعال.
     */
    private void cleanupInactiveLobbies() {
        long currentTime = System.currentTimeMillis();
        lobbies.entrySet().removeIf(entry -> {
            Lobby lobby = entry.getValue();
            // لابی‌هایی که به مدت ۵ دقیقه هیچ بازیکنی وارد آن‌ها نشده باشد، حذف می‌شوند.
            if (currentTime - lobby.getLastActivityTime() > TimeUnit.MINUTES.toMillis(5) && lobby.getPlayers().size() == 1) {
                System.out.println("Lobby " + lobby.getId() + " was closed due to inactivity.");
                return true;
            }
            return false;
        });
    }

    /**
     * یک شناسه تصادفی و کوتاه برای لابی تولید می‌کند.
     */
    private String generateLobbyId() {
        // یک ID تصادفی ۸ رقمی تولید می‌کند.
        return UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8);
    }
    
    // متد شروع بازی (برای استفاده در ClientHandler)
    public boolean startGame(String lobbyId, String username) {
        Lobby lobby = lobbies.get(lobbyId);
        if (lobby != null && lobby.getAdminUsername().equals(username)) {
            return lobby.startGame();
        }
        return false;
    }
}