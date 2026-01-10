package com.wordsmith.Controllers;

import com.wordsmith.Entity.NovelReleasePolicy;
import com.wordsmith.Repositories.ChapterRepository;
import com.wordsmith.Repositories.NovelReleasePolicyRepository;
import com.wordsmith.Repositories.NovelRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/release-policy")
public class AdminReleasePolicyController {

    private final NovelReleasePolicyRepository repo;
    private final NovelRepository novelRepo;
    private final ChapterRepository chapterRepo;

    public AdminReleasePolicyController(NovelReleasePolicyRepository repo, NovelRepository novelRepo, ChapterRepository chapterRepo) {
        this.repo = repo;
        this.novelRepo = novelRepo;
        this.chapterRepo = chapterRepo;
    }

    // --------------------------------------------------
    // 📋 LIST
    // --------------------------------------------------
    @GetMapping
    public String list(Model model) {
        model.addAttribute("policies", repo.findAll());
        return "release-policy-list";
    }

    // --------------------------------------------------
    // ➕ CREATE FORM
    // --------------------------------------------------
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("policy", new NovelReleasePolicy());
        model.addAttribute("novelnames", novelRepo.allNovelName());
        return "release-policy-form";
    }

    // --------------------------------------------------
    // ✏️ EDIT FORM
    // --------------------------------------------------
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        NovelReleasePolicy policy = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy id"));

        model.addAttribute("policy", policy);
        return "release-policy-form";
    }

    // --------------------------------------------------
    // 💾 SAVE (CREATE / UPDATE)
    // --------------------------------------------------
    @PostMapping("/save")
    public String save(@ModelAttribute NovelReleasePolicy policy) {

        if (policy.getId() == null) {
            // New policy, ensure novelName is unique
            boolean exists = repo.findByActiveTrue().stream()
                    .anyMatch(p -> p.getNovelName().equals(policy.getNovelName()));
            if (exists) {
                throw new IllegalArgumentException("A release policy for this novel already exists.");
            }
        }

        // Safety defaults
        if (policy.getMinStockpileRequired() < 1) {
            policy.setMinStockpileRequired(5);
        }

        repo.save(policy);
        return "redirect:/admin/release-policy";
    }

    // --------------------------------------------------
    // 🔁 ENABLE / DISABLE
    // --------------------------------------------------
    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id) {

        NovelReleasePolicy policy = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy id"));

        policy.setActive(!policy.isActive());
        repo.save(policy);

        return "redirect:/admin/release-policy";
    }
}
