package com.yashpz.journel.journel.controller;

import com.yashpz.journel.journel.entity.JournalEntry;
import com.yashpz.journel.journel.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournals(){
        return new ResponseEntity<>(journalEntryService.getAllJournals(),HttpStatus.OK);
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<JournalEntry>> getJournalsByUser(){
        return new ResponseEntity<>(journalEntryService.getJournalsByUser(),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(id);
        if(journalEntry.isPresent())
            return new ResponseEntity<>(journalEntry,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry newEntry){
        try{
            JournalEntry newJournal = journalEntryService.createNewJournal(newEntry);
            return new ResponseEntity<>(newJournal,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JournalEntry> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
        JournalEntry jEntry = journalEntryService.updateJournal(id,journalEntry);
        if(jEntry!=null)
            return new ResponseEntity<>(jEntry,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteJournal(@PathVariable ObjectId id){
        boolean deleteJournal = journalEntryService.deleteJournal(id);

        if (deleteJournal)
            return new ResponseEntity<>(true,HttpStatus.OK);
        else
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
    }

}
