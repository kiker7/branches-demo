package com.example.demo.exception;

public class BranchNotFoundException extends RuntimeException {

    public BranchNotFoundException(long id) {
        super("Could not found branch with id: " + id);
    }
}