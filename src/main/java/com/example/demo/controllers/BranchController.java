package com.example.demo.controllers;

import com.example.demo.data.domain.Branch;
import com.example.demo.exception.BranchNotFoundException;
import com.example.demo.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping("/branches")
    public List<Branch> all() {
        return branchService.getAll();
    }

    @PostMapping("/branches")
    public Branch addBranch(@RequestBody Branch newBranch) {
        return branchService.addBranch(newBranch);
    }

    @GetMapping("/branches/{id}")
    public Branch getBranch(@PathVariable long id) {
        return branchService.getBranch(id)
                .orElseThrow(() -> new BranchNotFoundException(id));
    }

    @PutMapping("/branches/{id}")
    public Branch updateBranch(@RequestBody Branch updatedBranch, @PathVariable long id) {
        return branchService.updateBranch(id, updatedBranch);
    }

    @DeleteMapping("/branches/{id}")
    public void deleteBranch(@PathVariable long id) {
        branchService.deleteBranch(id);
    }
}
