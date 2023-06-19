package com.thomazcm.financeira.api.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import com.thomazcm.financeira.model.Entry;
import com.thomazcm.financeira.model.Expense;
import com.thomazcm.financeira.model.User;
import com.thomazcm.financeira.repository.EntryRepository;
import com.thomazcm.financeira.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EntryService<T extends Entry> {

    private UserIdentifierHelper helper;
    private UserRepository userRepository;

    public EntryService(UserIdentifierHelper helper, UserRepository userRepository) {
        this.helper = helper;
        this.userRepository = userRepository;
    }

    public List<T> findAllFromUser(HttpServletRequest request, EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        List<T> entries = repository.findByUser_Id(userId);
        return entries;
    }

    public T saveEntry(T entry, HttpServletRequest request, EntryRepository<T> repository) {
        User user = helper.getUserFromRequest(request);
        user.addEntry(entry);
        userRepository.save(user);
        return entry;
    }

    public T findById(int id, HttpServletRequest request, EntryRepository<T> repository) {
        Long userId = helper.getUserIdFromRequest(request);
        Optional<T> incomeOptional = repository.findByIdAndUserId(Long.valueOf(id), userId);
        return incomeOptional.orElse(null);
    }

    public boolean deleteById(int id, HttpServletRequest request, EntryRepository<T> repository) {
        if (repository.existsById(Long.valueOf(id))) {
            repository.deleteById(Integer.toUnsignedLong(id));
            return true;
        } else {
            return false;
        }
    }

    public T updateEntry(T entry, T updatedEntry, EntryRepository<T> repository) {
        entry.setName(updatedEntry.getName() == null ? entry.getName() : updatedEntry.getName());
        entry.setValue(
                updatedEntry.getValue() == null ? entry.getValue() : updatedEntry.getValue());
        entry.setDate(updatedEntry.getDate() == null ? entry.getDate() : updatedEntry.getDate());
        if (entry instanceof Expense) {
            updateCategory(entry, updatedEntry);
        }
        return repository.save(entry);
    }

    private void updateCategory(T entry, T updatedEntry) {
        // TODO Auto-generated method stub

    }

    public BodyBuilder created(String path, Long id) {
        URI uri = UriComponentsBuilder.fromPath(path).buildAndExpand(id).toUri();
        return ResponseEntity.created(uri);
    }
}
