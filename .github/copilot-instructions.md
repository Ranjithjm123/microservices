# AI Copilot Instructions for Notes-App Microservices

## Architecture Overview
This is a **two-microservice Spring Boot application** with independent databases:
- **User-App** (port 8080): User management service using H2/PostgreSQL, DB: `users`
- **Post-It-App** (port 8081): Notes management service using PostgreSQL, DB: `notes`
- Both services communicate via HTTP headers (e.g., `X-User-Id`) for cross-service validation
- **Infrastructure**: PostgreSQL (port 5432), PGAdmin UI (port 5050), Docker Compose orchestration

## Project Structure & Patterns
Each microservice follows **layered architecture** (MVC pattern):
```
src/main/java/com/notes_app/{service_name}/
├── controller/    # REST endpoints (@RestController, @RequestMapping)
├── services/      # Business logic (@Service, @RequiredArgsConstructor)
├── dto/          # Data transfer objects (Request/Response)
├── models/       # JPA entities (@Entity, @Data from Lombok)
└── repositories/ # Spring Data JPA (@Repository)
```

**Key conventions:**
- Use Lombok (`@Data`, `@NoArgsConstructor`, `@RequiredArgsConstructor`) for boilerplate
- DTOs are Request/Response pairs; services map models to DTOs
- Always use `Optional<T>` return types instead of null checks
- Use `@Valid` annotation on @RequestBody for input validation
- Repositories extend `JpaRepository` for CRUD operations
- Timestamps: `@CreationTimestamp` and `@UpdateTimestamp` on model fields
- Endpoints: RESTful naming (e.g., `/api/notes/{id}`, `/api/user`)
- Input validation: Use Jakarta validation annotations (`@NotBlank`, `@Email`, `@Size`, etc.)

## Build & Deployment
**Maven-based, Java 21:**
- Build: `mvn clean package` in each service directory
- Run locally: `mvn spring-boot:run` from service root
- Docker: Each service has `Dockerfile` built automatically by compose
- Docker Compose: `docker-compose up -d` starts all services + PostgreSQL + PGAdmin

**Configuration:**
- Application properties at `src/main/resources/application.yaml`
- Database URLs differ between local (`localhost:5432`) and Docker (`db:5432` service hostname)
- Port 8080: User-App, Port 8081: Post-It-App
- PGAdmin: `admin@gmail.com` / `admin` at port 5050

## Integration Points
- **Cross-service validation**: `X-User-Id` header passed with requests (TODOs in controllers remain for implementation)
- **Database initialization**: `/init/create_dbs.sql` auto-runs on PostgreSQL startup
- **Shared package namespace**: `com.notes_app.*` for all services
- **No inter-service REST calls yet**: Currently header-based validation only; future enhancement needed

## Development Workflow
1. **Branch naming**: `<branch-name> - <intent>:<issue_number>` (e.g., `feat-add-auth - feature:42`)
2. **Testing**: Unit tests in `src/test/java/`; run via `mvn test`
3. **OpenAPI/Swagger**: Both services have `springdoc-openapi-starter-webmvc-ui`; access at `/swagger-ui.html`
4. **Debugging**: Spring Boot applications support remote debugging on port 5005
5. **Git workflow**: Commit only necessary files; push to feature branch before PR

## Code Quality & Best Practices (Post-Optimization)
- **Null Safety**: All services now use `Optional<T>` instead of null returns
- **Input Validation**: All DTOs include Jakarta validation annotations
- **Consistent Naming**: RESTful endpoints follow `/api/{resource}` pattern
- **Type Safety**: Use primitive `boolean` instead of `Boolean` wrapper
- **Proper HTTP Status**: 201 CREATED for POST, 200 OK for GET, 204 NO_CONTENT for DELETE, 404 NOT_FOUND
- **Response Types**: Avoid `ResponseEntity<?>` wildcard; use concrete types
- **Mapper Methods**: Services handle DTO-Entity mapping (see `mapToNoteResponse()`, `mapToUserResponse()`)

## Common Pitfalls to Avoid
- **Null Returns**: Services return `Optional<T>` not null; use `.orElseGet()` in controllers
- **Missing @Valid**: Always validate @RequestBody with `@Valid @RequestBody`
- **Database Connection Issues**: Verify service names (`db`) and credentials match docker-compose.yml
- **Entity Scan Misconfiguration**: Each app uses `@EntityScan` pointing to its own package
- **Duplicate Email Handling**: User creation checks existing emails and returns `Optional.empty()` on conflict
- **Field Naming**: Use camelCase (e.g., `note` not `Note`)
