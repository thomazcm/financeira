package com.thomazcm.financeira.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.thomazcm.financeira.api.form.UpdateObject;
import com.thomazcm.financeira.model.Entry;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.EntryRepository;
import com.thomazcm.financeira.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EntryService<T extends Entry> {

    private static final Logger logger = LoggerFactory.getLogger(EntryService.class);

    private UserIdentifierHelper helper;
    private UserRepository userRepository;

    public EntryService(UserIdentifierHelper helper, UserRepository userRepository) {
        this.helper = helper;
        this.userRepository = userRepository;
    }

    public List<T> findAllEntries(HttpServletRequest request, EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        return repository.findByUser_Id(userId);
    }

    public List<T> findEntryByName(String name, HttpServletRequest request,
            EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        return repository.findByUser_IdAndName(userId, name);
    }

    public T saveEntry(T entry, HttpServletRequest request, EntryRepository<T> repository) {
        User user = helper.getUserFromRequest(request);
        user.addEntry(entry);
        userRepository.save(user);
        return entry;
    }

    public T findEntryById(Long id, HttpServletRequest request, EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        Optional<T> incomeOptional = repository.findByIdAndUserId(id, userId);
        return incomeOptional.orElse(null);
    }

    public boolean deleteEntryById(Long entryId, HttpServletRequest request,
            EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        Optional<T> entryOptional = repository.findByIdAndUserId(entryId, userId);
        if (entryOptional.isPresent()) {
            repository.delete(entryOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean updateEntry(T entry, UpdateObject update, EntryRepository<T> repository) {
        try {
            update.updateEntry(entry);
        } catch (Exception e) {
            logger.error("Error updating Entry records", e);
            return false;
        }
        return repository.save(entry) != null;
    }

    public List<T> listEntriesFromMonth(int year, int month, HttpServletRequest request,
            EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);
        return repository.findByUser_IdAndDateBetween(userId, startDate, endDate);
    }
}
