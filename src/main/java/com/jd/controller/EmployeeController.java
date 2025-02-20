package com.jd.controller;

import com.jd.model.Employee;
import com.jd.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentEmployee(@RequestHeader("Authorization") String token) {
        try {
            // Remove "Bearer" prefix from token
            String jwt = token.replace("Bearer ", "");

            // Parse token to get email
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody();

            String email = claims.getSubject();

            // Find employee by email
            Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();

                // Create a response without exposing the password
                Map<String, Object> employeeData = new HashMap<>();
                employeeData.put("id", employee.getId());
                employeeData.put("firstName", employee.getFirstName());
                employeeData.put("lastName", employee.getLastName());
                employeeData.put("email", employee.getEmail());
                employeeData.put("phoneNumber", employee.getPhoneNumber());
                employeeData.put("hireDate", employee.getHireDate());
                employeeData.put("salary", employee.getSalary());

                return ResponseEntity.ok(employeeData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
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
