package org.example.cubitor.service;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.dto.SettingsDTO;
import org.example.cubitor.dto.SolveDTO;
import org.example.cubitor.dto.UserDTO;
import org.example.cubitor.entity.Settings;
import org.example.cubitor.entity.Solve;
import org.example.cubitor.entity.User;
import org.example.cubitor.entity.Folder;
import org.example.cubitor.repository.EventRepository;
import org.example.cubitor.repository.FolderRepository;
import org.example.cubitor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTOService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final FolderRepository folderRepository;

    public SolveDTO toDTO(Solve solve) {
        if (solve == null) return null;
        return SolveDTO.builder()
                .id(solve.getId())
                .time(solve.getTime())
                .description(solve.getNote())
                .penalty(solve.getPenalty())
                .creationDate(solve.getCreation_date())
                .scramble(solve.getScramble())
                // id's
                .folderID(solve.getFolder() != null ? solve.getFolder().getId() : null)
                .eventID(solve.getEvent() != null ? solve.getEvent().getId() : null)
                .userID(solve.getUser() != null ? solve.getUser().getId() : null)
                .build();
    }
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getTheActualUsername())
                .email(user.getEmail())
                .elo(user.getElo())
                .lastOnline(user.getLast_online())
                .creationDate(user.getAccount_creation_date())
                .profilePic(user.getAvatar())
                .friends(user.getFriendships() != null ? user.getFriendships().stream()
                        .map(friendship -> friendship.getMyFriend(user).getId())
                        .toList() : List.of())
                .solveIDs(user.getSolves() != null ? user.getSolves().stream()
                        .map(Solve::getId)
                        .toList() : List.of())
                .folderIDs(user.getFolders() != null ? user.getFolders().stream()
                        .map(Folder::getId)
                        .toList() : List.of())
                .settingsID(user.getSettings().getId())
                .roleID(user.getRole().ordinal())
                .build();
    }

    public SettingsDTO toDTO(Settings settings) {
        if (settings == null) return null;
        return SettingsDTO.builder()
                .id(settings.getId())
                .font(settings.getFont())
                .theme(settings.getTheme())
                .timerAccuracy(settings.getTimerAccuracy())
                .celebrationTime(settings.getCelebrationTime())
                .confirmSolveDeletion(settings.getConfirmSolveDeletion())
                .widgetCount(settings.getWidgetCount())
                .useInspection(settings.getUseInspection())
                .saveMinigameSolves(settings.getSaveMinigameSolves())
                .bio(settings.getBio())
                .cubingGoal(settings.getCubingGoal())
                .favoriteEvent(settings.getFavoriteEvent())
                .widgetConfig(toDTO(settings.getWidgetConfig()))
                .minigameConfig(toDTO(settings.getMinigameConfig()))
                .statConfig(settings.getStatConfig() != null ? settings.getStatConfig().stream()
                        .map(this::toDTO)
                        .toList() : List.of())
                .build();
    }
    private SettingsDTO.WidgetConfigDTO toDTO(Settings.WidgetConfig config) {
        if (config == null) return null;
        SettingsDTO.WidgetConfigDTO dto = new SettingsDTO.WidgetConfigDTO();
        dto.setConfig(config.getConfig());
        dto.setStatus(config.getStatus());
        return dto;
    }
    private SettingsDTO.StatBlockDTO toDTO(Settings.StatBlock block) {
        if (block == null) return null;
        SettingsDTO.StatBlockDTO dto = new SettingsDTO.StatBlockDTO();
        dto.setBlock(block.getBlock());
        return dto;
    }


    public Solve toEntity(SolveDTO solveDTO) {
        if (solveDTO == null) return null;
        Solve solve = new Solve();
        solve.setId(solveDTO.getId());
        solve.setNote(solveDTO.getDescription());
        solve.setTime(solveDTO.getTime());
        solve.setPenalty(solveDTO.getPenalty());
        solve.setCreation_date(solveDTO.getCreationDate());
        solve.setScramble(solveDTO.getScramble());
        // entities
        solve.setUser(solveDTO.getUserID() != null ? userRepository.findById(solveDTO.getUserID()).orElse(null) : null);
        solve.setEvent(solveDTO.getEventID() != null ? eventRepository.findById(solveDTO.getEventID()).orElse(null) : null);
        solve.setFolder(solveDTO.getFolderID() != null ? folderRepository.findById(solveDTO.getFolderID()).orElse(null) : null);
        return solve;
    }
    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setTheActualUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setElo(dto.getElo());
        user.setLast_online(dto.getLastOnline());
        user.setAccount_creation_date(dto.getCreationDate());
        user.setAvatar(dto.getProfilePic());
        return user;
    }

    public Settings toEntity(SettingsDTO dto) {
        if (dto == null) return null;
        Settings settings = new Settings();
        settings.setId(dto.getId());
        settings.setFont(dto.getFont());
        settings.setTheme(dto.getTheme());
        settings.setTimerAccuracy(dto.getTimerAccuracy());
        settings.setCelebrationTime(dto.getCelebrationTime());
        settings.setConfirmSolveDeletion(dto.getConfirmSolveDeletion());
        settings.setWidgetCount(dto.getWidgetCount());
        settings.setUseInspection(dto.getUseInspection());
        settings.setSaveMinigameSolves(dto.getSaveMinigameSolves());
        settings.setBio(dto.getBio());
        settings.setCubingGoal(dto.getCubingGoal());
        settings.setFavoriteEvent(dto.getFavoriteEvent());
        settings.setWidgetConfig(toEntity(dto.getWidgetConfig()));
        settings.setMinigameConfig(toEntity(dto.getMinigameConfig()));
        settings.setStatConfig(dto.getStatConfig() != null ? dto.getStatConfig().stream()
                .map(this::toEntity)
                .toList() : List.of());
        return settings;
    }
    private Settings.WidgetConfig toEntity(SettingsDTO.WidgetConfigDTO dto) {
        if (dto == null) return null;
        Settings.WidgetConfig config = new Settings.WidgetConfig();
        config.setConfig(dto.getConfig());
        config.setStatus(dto.getStatus());
        return config;
    }
    private Settings.StatBlock toEntity(SettingsDTO.StatBlockDTO dto) {
        if (dto == null) return null;
        Settings.StatBlock block = new Settings.StatBlock();
        block.setBlock(dto.getBlock());
        return block;
    }

    public void updateEntity(User target, User source) {
        if (target == null || source == null) return;
        if (source.getTheActualUsername() != null) target.setTheActualUsername(source.getTheActualUsername());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getElo() != null) target.setElo(source.getElo());
        if (source.getLast_online() != null) target.setLast_online(source.getLast_online());
        if (source.getAvatar() != null) target.setAvatar(source.getAvatar());
    }

    public void updateEntity(Settings target, Settings source) {
        if (target == null || source == null) return;
        if (source.getFont() != null) target.setFont(source.getFont());
        if (source.getTheme() != null) target.setTheme(source.getTheme());
        if (source.getTimerAccuracy() != null) target.setTimerAccuracy(source.getTimerAccuracy());
        if (source.getCelebrationTime() != null) target.setCelebrationTime(source.getCelebrationTime());
        if (source.getConfirmSolveDeletion() != null) target.setConfirmSolveDeletion(source.getConfirmSolveDeletion());
        if (source.getWidgetCount() != null) target.setWidgetCount(source.getWidgetCount());
        if (source.getUseInspection() != null) target.setUseInspection(source.getUseInspection());
        if (source.getSaveMinigameSolves() != null) target.setSaveMinigameSolves(source.getSaveMinigameSolves());
        if (source.getBio() != null) target.setBio(source.getBio());
        if (source.getCubingGoal() != null) target.setCubingGoal(source.getCubingGoal());
        if (source.getFavoriteEvent() != null) target.setFavoriteEvent(source.getFavoriteEvent());
        if (source.getWidgetConfig() != null) target.setWidgetConfig(source.getWidgetConfig());
        if (source.getMinigameConfig() != null) target.setMinigameConfig(source.getMinigameConfig());
        if (source.getStatConfig() != null) target.setStatConfig(source.getStatConfig());
    }

    public void updateEntity(Solve target, Solve source) {
        if (target == null || source == null) return;
        if (source.getTime() != null) target.setTime(source.getTime());
        if (source.getScramble() != null) target.setScramble(source.getScramble());
        if (source.getCreation_date() != null) target.setCreation_date(source.getCreation_date());
        if (source.getNote() != null) target.setNote(source.getNote());
        if (source.getPenalty() != null) target.setPenalty(source.getPenalty());
        if (source.getEvent() != null) target.setEvent(source.getEvent());
        if (source.getFolder() != null) target.setFolder(source.getFolder());
    }
}

