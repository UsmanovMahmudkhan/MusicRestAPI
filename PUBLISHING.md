# Publishing to GitHub Packages

This specific project is configured to publish artifacts to GitHub Packages.

## Prerequisites

### 1. Personal Access Token (PAT)
To publish locally or to allow other developers to publish, a Personal Access Token is required.

**Scopes required:**
- `read:packages`
- `write:packages`
- `delete:packages` (optional, for cleanup)

**Token Configuration:**
You have provided a token. **Do not commit this token to Git.** Instead, configure it in your specific global Maven settings.

### 2. Local `settings.xml` Configuration
Add the following configuration to your `~/.m2/settings.xml` file (create it if it doesn't exist):

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <servers>
        <server>
            <id>github</id>
            <username>UsmanovMahmudkhan</username>
            <password>YOUR_PERSONAL_ACCESS_TOKEN_HERE</password>
        </server>
    </servers>

    <profiles>
        <profile>
            <id>github</id>
            <repositories>
                <repository>
                    <id>github</id>
                    <name>GitHub Packages</name>
                    <url>https://maven.pkg.github.com/UsmanovMahmudkhan/MusicRestAPI</url>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>github</activeProfile>
    </activeProfiles>
</settings>
```
Replace `YOUR_PERSONAL_ACCESS_TOKEN_HERE` with your actual token (starting with `ghp_...`).

## Usage

### Publishing via GitHub Actions (Recommended)
The project includes a workflow `.github/workflows/maven-publish.yml` that automatically publishes the package when a **Release** is created in GitHub.

1. Go to the "Releases" section of the repository.
2. Draft a new release (e.g., `v0.1.0`).
3. Publish the release.
4. The workflow will run, build the project with Java 21, and publish artifacts to GitHub Packages.

### Publishing Locally
If you have configured your `~/.m2/settings.xml` correctly, you can run:

```bash
./mvnw deploy
```

## Troubleshooting

### 401 Unauthorized
- **Cause**: Incorrect username or PAT, or PAT lacks `write:packages` scope.
- **Fix**: Verify your `settings.xml` credentials. Ensure the PAT has the correct scopes.

### 403 Forbidden
- **Cause**: You are trying to overwrite an existing version that doesn't support overwriting (Maven releases are immutable by default on GitHub Packages, SNAPSHOTS are mutable).
- **Fix**: Update the version in `pom.xml` (e.g., `0.1.1-SNAPSHOT` or `0.1.0`).

### "Not a managed type" or Build Errors
- **Cause**: Project code issues or Java version mismatch.
- **Fix**: Ensure you are running with Java 21 locally if that's what the project requires (`java -version`).

## Pom.xml Notes
The `distributionManagement` section names the repository `github`. This ID **must match** the `<server><id>` in your `settings.xml` and the `server-id` in the GitHub Actions workflow.

```xml
<distributionManagement>
    <repository>
        <id>github</id> 
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/UsmanovMahmudkhan/MusicRestAPI</url>
    </repository>
</distributionManagement>
```
