package com.jd.controller;

import com.jd.model.Employee;
import com.jd.repository.EmployeeRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Value("${jwt.secret}")  // Read the JWT secret from application.properties
    private String jwtSecret;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        System.out.println("Fetched employees: " + employees);
        return employees;
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
        try {
            System.out.println("Received employee: " + employee.getFirstName() + " " + employee.getLastName());
            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(hashedPassword);

            Employee savedEmployee = employeeRepository.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (Exception e) {
            System.err.println("Error registering employee: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error registering employee: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee loginRequest) {
        System.out.println("Login attempt for email: " + loginRequest.getEmail());

        Optional<Employee> employeeOptional = employeeRepository.findByEmail(loginRequest.getEmail());

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            System.out.println("Stored Hashed Password: " + employee.getPassword());
            System.out.println("Entered Password: " + loginRequest.getPassword());

            if (passwordEncoder.matches(loginRequest.getPassword(), employee.getPassword())) {
                // Generate JWT token
                String token = Jwts.builder()
                        .setSubject(employee.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // one day expiration
                        .signWith(SignatureAlgorithm.HS256, jwtSecret)
                        .compact();
               return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
