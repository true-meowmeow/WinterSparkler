 # Repository Guidelines
 
 ## Project Structure & Module Organization
 - `src/main/java/`: application source (packages: `core`, `swing.*`). Entry point: `core.Main`.
 - `src/test/java/`: demos/tests (e.g., layout/selection demos). Add unit tests here.
 - `pom.xml`: Maven build configuration (Java 17, dependencies: FlatLaf, JNA, MigLayout).
 - `target/`: build outputs (`classes/`, packaged JARs).
 
 ## Build, Test, and Development Commands
 - `mvn clean compile`: compiles sources to `target/classes`.
 - `mvn clean package`: builds artifacts in `target/`.
 - `java -cp target/classes core.Main`: runs the app directly after compile.
 - `mvn test`: runs tests under `src/test/java` (add JUnit 5 to use).
 - IDE tip: set language level to Java 17.
 
 ## Coding Style & Naming Conventions
 - Indentation: 4 spaces; UTF‑8 encoding.
 - Packages: lowercase dot‑separated (`swing.ui.pages.home`).
 - Classes: PascalCase; methods/fields: lowerCamelCase; constants: UPPER_SNAKE_CASE.
 - Organize Swing UI under `swing.ui.*`; non‑UI logic under `core.*`.
 - Keep UI changes on the EDT (e.g., `SwingUtilities.invokeLater`).
 
 ## Testing Guidelines
 - Framework: JUnit 5 recommended (add to `pom.xml`).
 - Location: mirror package paths in `src/test/java`.
 - Naming: `ClassNameTest.java` or `*Test.java`.
 - Run: `mvn test` or from IDE. Prefer fast, headless tests; keep interactive demos separate.
 
 ## Commit & Pull Request Guidelines
 - Commits: imperative, scoped, concise. Example: `feat(ui): add playlist drag selection`.
 - PRs: include description, linked issues, screenshots for UI, and steps to verify.
 - CI/Local checks: `mvn -q -DskipTests=false test` passes; app runs via `core.Main`.
 
 ## Security & Configuration Tips
 - Avoid hard‑coding absolute paths; prefer central helpers (e.g., path managers in `swing.objects.objects`).
 - Do not commit generated files from `target/`. Review `.idea/` changes before committing.
