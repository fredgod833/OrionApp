package com.openclassrooms.mddapi.services.implementation;

import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepo;

    public ThemeServiceImpl(ThemeRepository themeRepo) {
        this.themeRepo = themeRepo;
    }

    public List<Theme> getAll() {
        return themeRepo.findAll();
    }

    @Override
    public Theme getById(int id) {
        return themeRepo.getReferenceById(id);
    }

    @Override
    public Theme getByName(String name) {
        return themeRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Theme with name " + name + " not found"));
    }
}
