package com.thomazcm.financeira.api.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.thomazcm.financeira.model.Entry;
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
        Long userId = helper.getIdFromRequest(request);
        List<T> entries = repository.findByUser_Id(userId);
        return entries;
    }
    
    public T saveEntry(T entry, HttpServletRequest request, EntryRepository<T> repository) {
        User user = helper.getUserFromRequest(request);
        user.addEntry(entry);
        userRepository.save(user);
        return entry;
    }
}
