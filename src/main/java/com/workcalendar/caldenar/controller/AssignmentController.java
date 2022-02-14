package com.workcalendar.caldenar.controller;

import com.workcalendar.caldenar.exceptions.ResourceNotFoundException;
import com.workcalendar.caldenar.model.Assignment;
import com.workcalendar.caldenar.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class AssignmentController {

    @Autowired
    AssignmentRepository assignmentRepository;

    @GetMapping("/assignment")
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @PostMapping("/assignment")
    public ResponseEntity<Assignment> postAssignment(@RequestBody Assignment assignment) {
        System.out.println(assignment);
        assignmentRepository.save(assignment);
        System.out.println(assignment);

        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @DeleteMapping("/assignment/{id}")
    public ResponseEntity<Object> deleteAssignment(@PathVariable int id) {

        try {
            assignmentRepository.deleteById(id);

        } catch (Exception err) {

            return new ResponseEntity<>("assignment with specified id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/assignment/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable int assignmentID, @RequestBody Assignment assignment) {
        System.out.println(assignmentID);
        Optional<Assignment> assignmentDetails = assignmentRepository.findById(assignmentID);
        if (assignmentDetails.isPresent()) {
            Assignment _assignment = assignmentDetails.get();
            _assignment.setAssignmentID(assignment.getAssignmentID());
            _assignment = assignmentRepository.save(_assignment);
            return new ResponseEntity<>(_assignment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
