package com.yashpz.journel.journel.service;

import com.yashpz.journel.journel.entity.JournalEntry;
import com.yashpz.journel.journel.entity.User;
import com.yashpz.journel.journel.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

//    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    public List<JournalEntry> getAllJournals(){
        return journalEntryRepository.findAll();
    }

    public List<JournalEntry> getJournalsByUser(){
        User user= userService.getCurrentUser();
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id){
        User user = userService.getCurrentUser();

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).toList();

        if (!collect.isEmpty())
            return journalEntryRepository.findById(id);
        else
            return null;
    }

    @Transactional
    public JournalEntry createNewJournal(JournalEntry newEntry){
        try {
            newEntry.setDateTime(LocalDateTime.now());
            JournalEntry saved = saveJournalEntry(newEntry);

            User user = userService.getCurrentUser();
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

            return saved;
        } catch (Exception e) {
            log.warn("This is WARN");
            log.error("This is ERROR");
            log.info("This is INFO");
            log.debug("This is DEBUG");
            throw new RuntimeException("An Error Occurred while Saving Journal Entry.",e);
        }
    }

    public JournalEntry saveJournalEntry(JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    public JournalEntry updateJournal(ObjectId id, JournalEntry newEntry){
        User user = userService.getCurrentUser();

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(journalEntry -> journalEntry.getId().equals(id)).toList();

        if (!collect.isEmpty()){
            JournalEntry oldEntry = getJournalById(id).orElse(null);
            if(oldEntry!=null) {
                oldEntry.setContent(newEntry.getContent() != null && !Objects.equals(newEntry.getContent(), "") ? newEntry.getContent() : oldEntry.getContent());
                oldEntry.setTitle(newEntry.getTitle() != null && !Objects.equals(newEntry.getTitle(), "") ? newEntry.getTitle() : oldEntry.getTitle());
                saveJournalEntry(oldEntry);
            }
            return oldEntry;
        }
        else
            return null;
    }

    public boolean deleteJournal(ObjectId id){
        User user = userService.getCurrentUser();
        boolean removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
        }
        return removed;
    }
}
